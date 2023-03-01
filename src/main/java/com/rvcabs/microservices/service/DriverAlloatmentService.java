package com.rvcabs.microservices.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.rvcabs.microservices.constants.NotificationTypeConstant;
import com.rvcabs.microservices.dto.BookingDto;
import com.rvcabs.microservices.dto.DriverTripDetailDto;
import com.rvcabs.microservices.dto.NotificationDto;
import com.rvcabs.microservices.repository.BookingTripRepository;
import com.rvcabs.microservices.repository.DriverTripDetailRepository;
import com.rvcabs.microservices.utilities.GenericMapper;

@Service
public class DriverAlloatmentService {

	private static final Logger logger = LoggerFactory.getLogger(DriverAlloatmentService.class);

	private NotificationDto pushNotificationDto;

	@Autowired
	private NotificationService notify;

	@Autowired
	private DriverTripDetailRepository driverTripDetailRepository;

	@Autowired
	private BookingTripRepository bookingTripRepository;

	@PersistenceContext
	private EntityManager entityManager;

	private Map<String, String> customNotificationMap;


	private List<String> driverList;

	public static Map<String, List<String>> driverMapList = new HashMap<String, List<String>>();

	private String[] selectDriver = { "KM", "RATING" };

	private SimpleDateFormat dateFormat;

	public String selectedDriverSearch(BookingDto bookingDto) {
		DriverTripDetailDto driverTripDetailDto;
		String driverAccountId = driverTripDetailRepository.searchSelectedDriver(bookingDto.getPickUpDate(),
				bookingDto.getDriverAccountId());
		if (driverAccountId != null) {
			driverTripDetailDto = GenericMapper.mapper.map(
					driverTripDetailRepository.findByAccountId(bookingDto.getDriverAccountId()),
					DriverTripDetailDto.class);
			try {
				driverList = new ArrayList<String>();
				driverList.add(bookingDto.getDriverAccountId());
				driverMapList.put(bookingDto.getId(), driverList);
			} catch (Exception e) {
				logger.error("DriverAlloatmentService:selectedDriverSearch:adding driver account id in map"
						+ e.getMessage());
			}
			return notifyDriver(bookingDto, driverTripDetailDto);
		}
		return null;
	}

	public String driverSearch(BookingDto bookingDto) {
		String driverAccountId = null;
		driverList = new ArrayList<String>();
		for (int i = 0; i < selectDriver.length; i++) {
			if (selectDriver[i].equalsIgnoreCase("KM")) {
				driverList = driverSearchByMinKM(driverList, bookingDto);
			}
			if (selectDriver[i].equalsIgnoreCase("RATING")) {
				driverList = driverSearchByMaxRating(driverList, bookingDto);
			}
		}
		/*
		 * If driverList is null means no driver available, if not we have to find 0 th
		 * index driver and notify him
		 */
		if (driverList != null && driverList.size() > 0) {
			driverAccountId = driverList.get(0);
//search driver account Id will get trip notification, and driver account id ll be entered into driverMapList so same driver will not be search again
			try {
				driverList = new ArrayList<String>();
				driverList.add(driverAccountId);
				driverMapList.put(bookingDto.getId(), driverList);
			} catch (Exception e) {
				logger.error("DriverAlloatmentService:driverSearch:adding driver account id in map" + e.getMessage());
			}
			DriverTripDetailDto driverTripDetailDto = GenericMapper.mapper
					.map(driverTripDetailRepository.findByAccountId(driverAccountId), DriverTripDetailDto.class);
			return notifyDriver(bookingDto, driverTripDetailDto);
		} else {
			// No driver available for searching trip updating
			bookingTripRepository.updateTripStatus(0, bookingDto.getId());
		}
		return null;
	}

	public List<String> driverSearchByMinKM(List<String> driList, BookingDto bookingDto) {
		List<String> obj = null;
		try {
			if (driList != null && !driList.isEmpty()) {
				obj = driverTripDetailRepository.findAllAccountIdBasedOnMinKmWithIn(bookingDto.getPickUpDate(),
						driList);
			} else if (driverMapList != null && driverMapList.get(bookingDto.getId()) != null
					&& !driverMapList.get(bookingDto.getId()).isEmpty()) {
				obj = driverTripDetailRepository.findAllAccountIdBasedOnMinKmWithIn(bookingDto.getPickUpDate(),
						driverMapList.get(bookingDto.getId()));
			} else {
				obj = driverTripDetailRepository.findAllAccountIdBasedOnMinKMWithOutIn(bookingDto.getPickUpDate());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public List<String> driverSearchByMaxRating(List<String> driList, BookingDto bookingDto) {
		List<String> obj = null;
		try {
			if (driList != null && !driList.isEmpty()) {
				obj = driverTripDetailRepository.findAllAccountIdBasedOnMaxRatingWithIn(bookingDto.getPickUpDate(),
						driList);
			} else if (driverMapList != null && driverMapList.get(bookingDto.getId()) != null
					&& !driverMapList.get(bookingDto.getId()).isEmpty()) {
				obj = driverTripDetailRepository.findAllAccountIdBasedOnMaxRatingWithIn(bookingDto.getPickUpDate(),
						driverMapList.get(bookingDto.getId()));
			} else {
				obj = driverTripDetailRepository.findAllAccountIdBasedOnMaxRatingWithOutIn(bookingDto.getPickUpDate());
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return obj;
	}

	public String notifyDriver(BookingDto bookingDto, DriverTripDetailDto driverTripDetailDto) {
		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.MINUTE, +15);
		customNotificationMap = new HashMap<String, String>();
		try {
			customNotificationMap.put("Type", NotificationTypeConstant.CUS_BOOKING_REQ);
			customNotificationMap.put("BookingId", bookingDto.getId());
			pushNotificationDto = new NotificationDto();
			pushNotificationDto.setBookingTripId(bookingDto.getId());
			pushNotificationDto.setNotificationExpTime(calendar.getTime());
			pushNotificationDto.setConfigMap(customNotificationMap);
			pushNotificationDto.setNotificationSubject("Hello " + driverTripDetailDto.getDriverName()
					+ ", Booking Request From:" + bookingDto.getPickUpLocation() + " To:"
					+ bookingDto.getReleaseLocation() + " Date:" + bookingDto.getPickUpDate());
			pushNotificationDto.setType(Integer.valueOf(NotificationTypeConstant.CUS_BOOKING_REQ));
			pushNotificationDto.setNotificationHeader("Booking Request");
			pushNotificationDto.setDeviceToken(driverTripDetailDto.getDeviceToken());
			pushNotificationDto.setSmsId(driverTripDetailDto.getMobileNumber());
			pushNotificationDto.setSms("Hello " + driverTripDetailDto.getDriverName() + ", Booking Request From:"
					+ bookingDto.getPickUpLocation() + " To:" + bookingDto.getReleaseLocation() + " Date:"
					+ bookingDto.getPickUpDate());
			pushNotificationDto.setAccountId(driverTripDetailDto.getAccountId());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		String notifyId = notify.saveNotificaton(pushNotificationDto);
		if (notifyId != null) {
			driverTripDetailRepository.updateDriverTripStatus(driverTripDetailDto.getAccountId(), bookingDto.getId());
		}

		return driverTripDetailDto.getDriverName();

	}

	public int updateNextTripTime(String pickupTime, String accountId) {
		dateFormat = new SimpleDateFormat("yyyy-mm-dd HH:MM:ss");
		Date pickupDate = null;
		try {
			pickupDate = dateFormat.parse(pickupTime);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int update = driverTripDetailRepository.updateNextTripTiming(accountId, pickupDate);
		return update;

	}
}
