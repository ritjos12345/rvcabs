package com.rvcabs.microservices.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.rvcabs.microservices.constants.NotificationTypeConstant;
import com.rvcabs.microservices.dto.BookingDto;
import com.rvcabs.microservices.dto.NotificationDto;
import com.rvcabs.microservices.entity.BookingDetailEntity;
import com.rvcabs.microservices.entity.DriverTripDetailEntity;
import com.rvcabs.microservices.entity.OtherTripStatusEntity;
import com.rvcabs.microservices.repository.BookingTripRepository;
import com.rvcabs.microservices.repository.DriverTripDetailRepository;
import com.rvcabs.microservices.repository.OtherTripStatusRepository;
import com.rvcabs.microservices.utilities.GenericMapper;

@Component
public class ShedularService {

	private static Logger logger = LoggerFactory.getLogger(ShedularService.class);

	@Autowired
	private NotificationService notify;

	@Autowired
	private DriverTripDetailRepository driverTripDetailRepository;

	@Autowired
	private BookingTripRepository bookingTripRepository;

	@Autowired
	private OtherTripStatusRepository otherTripRepo;

	@Autowired
	private DriverAlloatmentService driverAlloatmentService;

	@Autowired
	private BillingService billingService;

	private NotificationDto pushNotificationDto;

	private Map<String, String> customNotificationMap;

//Sending Reminder to driver of trip before 1 hr
	@Scheduled(fixedRate = 900000, initialDelay = 60000)
	public void sendReminderOfTripToDriver() {
		List<DriverTripDetailEntity> tripList = null;
		try {
			tripList = driverTripDetailRepository.findAllByNextTripTime();
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		if (tripList != null && !tripList.isEmpty()) {
			for (DriverTripDetailEntity driverTripDetailEntity : tripList) {
				customNotificationMap = new HashMap<String, String>();

				try {
					customNotificationMap.put("Type", NotificationTypeConstant.CUS_BOOKING_REQ);
					// customNotificationMap.put("BookingId", driverTripDetailEntity.get);
					pushNotificationDto = new NotificationDto();
					pushNotificationDto.setBookingTripId(null);
					pushNotificationDto.setNotificationExpTime(null);
					pushNotificationDto.setConfigMap(customNotificationMap);
					pushNotificationDto.setNotificationSubject("Dear " + driverTripDetailEntity.getDriverName()
							+ ",your pick up is sheduled at " + driverTripDetailEntity.getNextTripStartTime());
					pushNotificationDto.setType(Integer.valueOf(NotificationTypeConstant.BOOKING_RIDE_RIMINDER));
					pushNotificationDto.setNotificationHeader("PickUp Reminder");
					pushNotificationDto.setDeviceToken(driverTripDetailEntity.getDeviceToken());
					pushNotificationDto.setSmsId(driverTripDetailEntity.getMobileNumber());
					pushNotificationDto.setSms("Dear " + driverTripDetailEntity.getDriverName()
							+ ",your pick up is sheduled at " + driverTripDetailEntity.getNextTripStartTime());
					pushNotificationDto.setAccountId(driverTripDetailEntity.getAccountId());
					String notifyId = notify.saveNotificaton(pushNotificationDto);

				} catch (Exception exception) {
				}
			}
		}

	}

	// Moving to admin or searching new driver for every 30 min
	@Transactional
	@Scheduled(fixedRate = 300000, initialDelay = 60000)
	public void changeBookingStatus() {
		System.out.println("UPDATING ==============");
		List<BookingDetailEntity> bookingArray = bookingTripRepository.findIdAndDriverIdByTime();
		System.out.println(bookingArray);
		if (bookingArray != null && !bookingArray.isEmpty()) {
			for (BookingDetailEntity bookigData : bookingArray) {
//If Booking status is 1, driver allotment control went to admin an now its expire have to search new driver
				if (bookigData.getTripStatus().equals(1)) {
					driverAlloatmentService.driverSearch(GenericMapper.mapper.map(bookigData, BookingDto.class));
				} else {
					bookingTripRepository.updateTripStatus(1, bookigData.getId());
					// Entering driverId and booking id for driver decline record
					OtherTripStatusEntity otherTripStatusEntity = new OtherTripStatusEntity();
					otherTripStatusEntity.setAccountId(bookigData.getDriverAccountId());
					otherTripStatusEntity.setBookingId(bookigData.getId());
					otherTripStatusEntity.setStatus(7);
					otherTripRepo.save(otherTripStatusEntity);
				}

			}
		}
	}

	// Moving to admin or searching new driver for every 30 min
	@Transactional
	@Scheduled(fixedRate = 300000, initialDelay = 10000)
	public void fetchingRateFromDB() {
		billingService.fetchingRateFromDB();
	}

	// Moving to admin or searching new driver for every 30 min
	@Transactional
	@Scheduled(cron="0 44 21 * * ?")
	public void generateFinalBill() {
		System.out.println("HI");
		billingService.createFinalBill();
	}
}
