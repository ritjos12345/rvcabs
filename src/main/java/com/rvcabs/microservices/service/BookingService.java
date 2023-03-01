package com.rvcabs.microservices.service;

import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.rvcabs.microservices.constants.Constants;
import com.rvcabs.microservices.dto.BookingDto;
import com.rvcabs.microservices.dto.BookingShortDto;
import com.rvcabs.microservices.dto.CompanyDetailDto;
import com.rvcabs.microservices.dto.MasterDataDto;
import com.rvcabs.microservices.dto.NotificationDto;
import com.rvcabs.microservices.dto.StatusDto;
import com.rvcabs.microservices.dto.TravelerDetailDto;
import com.rvcabs.microservices.entity.BookingDetailEntity;
import com.rvcabs.microservices.entity.CompanyDetailsEntity;
import com.rvcabs.microservices.entity.DriverTripDetailEntity;
import com.rvcabs.microservices.entity.OtherTripStatusEntity;
import com.rvcabs.microservices.entity.SysBookingRequestTypeEntity;
import com.rvcabs.microservices.entity.SysCarCategoryEntity;
import com.rvcabs.microservices.entity.SysCarMasterEntity;
import com.rvcabs.microservices.repository.ApplicationUserRepository;
import com.rvcabs.microservices.repository.BookingTripRepository;
import com.rvcabs.microservices.repository.DriverTripDetailRepository;
import com.rvcabs.microservices.repository.OtherTripStatusRepository;
import com.rvcabs.microservices.repository.TripCoordinateRepository;
import com.rvcabs.microservices.repository.TripDetailRepository;
import com.rvcabs.microservices.utilities.GenericMapper;
import com.rvcabs.microservices.utilities.Utilities;

@Service
@Transactional
public class BookingService {

	@Autowired
	private BookingTripRepository bookingTripRepository;

	@Autowired
	private DriverTripDetailRepository driverTripDetailRepo;

	@Autowired
	private TripDetailRepository tripDetailRepository;

	@Autowired
	private TripCoordinateRepository tripCoordinateRepository;

	@Autowired
	private OtherTripStatusRepository otherTripRepo;

	@Autowired
	private ApplicationUserRepository applicationUserRepo;

	@Autowired
	private DriverAlloatmentService driverAlloatmentService;

	@Autowired
	private NotificationService notify;

	@PersistenceContext
	private EntityManager entityManager;

	static ModelMapper modelMapper = new ModelMapper();

	private MultiValueMap<String, String> headers;

	private String driverAccountId;

	private StatusDto statusDto;

	private NotificationDto notificationDto;

	private OtherTripStatusEntity otherTripStatusEntity;

	private Map<String, Object> responceMap;

	public ResponseEntity<?> updatingTripStatusToClose(String bookingId) {
		headers = Utilities.getDefaultHeader();
		responceMap = new HashMap<String, Object>();
		// Status 9 is used for force closer of trip
		try {
			if (1 == bookingTripRepository.updateTripStatus(9, bookingId)) {
				String driveraccountId = bookingTripRepository.findDriverAccountIdById(bookingId);
				if (driveraccountId != null) {
					Integer update = driverTripDetailRepo.updateOnGoingTripAndFlag(driveraccountId);
				}

				// Response Positive
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Trip status updated successfully");
				responceMap.put(Constants.STATUS, HttpStatus.OK.toString());
				responceMap.put(Constants.MESSAGE, "Trip status updated successfully");
				return new ResponseEntity<>(responceMap, headers, HttpStatus.OK);
			} else {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Trip status updated unsuccessfully");
				responceMap.put(Constants.STATUS, HttpStatus.OK.toString());
				responceMap.put(Constants.MESSAGE, "Trip status updated unsuccessfully");
			}
		} catch (Exception exception) {
			headers.add(Constants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.toString());
			headers.add(Constants.MESSAGE, "Trip status updated unsuccessfully");
			responceMap.put(Constants.STATUS, HttpStatus.INTERNAL_SERVER_ERROR.toString());
			responceMap.put(Constants.MESSAGE, "Trip status updated unsuccessfully");
		}
		return new ResponseEntity<>(responceMap, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> allotingDriver(String bookingId, String driverAccountId) {
		headers = Utilities.getDefaultHeader();
		String preDriverAccountId;
		DriverTripDetailEntity driverTripDetatil;
		BookingDto bookingDto = GenericMapper.mapper.map(bookingTripRepository.findByBookingId(bookingId),
				BookingDto.class);
		// Searching driver for bookingId if any
		preDriverAccountId = bookingTripRepository.findDriverAccountIdById(bookingId);
		if (preDriverAccountId != null) {
			driverTripDetatil = driverTripDetailRepo.findByAccountId(preDriverAccountId);
			notificationDto = new NotificationDto();
			notificationDto.setSmsId(driverTripDetatil.getMobileNumber());
			notificationDto.setSms("Hello, your ride is has been cancled ");
			notificationDto.setDeviceToken(driverTripDetatil.getDeviceToken());
			notificationDto.setNotificationHeader("Booking Cancellation");
			notificationDto.setNotificationSubject("Hello, your ride is has been cancled ");
			notify.saveNotificaton(notificationDto);
		}

		// updating driver account id
		if (1 == bookingTripRepository.updateTripAndDriverStatus(3, bookingId, driverAccountId)) {
			driverTripDetatil = driverTripDetailRepo.findByAccountId(driverAccountId);
			// Notifing driver about trip
			notificationDto = new NotificationDto();
			notificationDto.setSmsId(driverTripDetatil.getMobileNumber());
			notificationDto.setSms("Hello, admin has assigned you ride ");
			notificationDto.setDeviceToken(driverTripDetatil.getDeviceToken());
			notificationDto.setNotificationHeader("Booking Confirmation");
			notificationDto.setNotificationSubject("Hello, admin has assigned you ride ");
			notify.saveNotificaton(notificationDto);
			// Response Positive
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Success");
			statusDto = new StatusDto();
			statusDto.setStatus("true");
			return new ResponseEntity<BookingDto>(bookingDto, headers, HttpStatus.OK);
		}
		// Response Negative
		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Success");
		statusDto = new StatusDto();
		statusDto.setStatus("true");
		return new ResponseEntity<BookingDto>(bookingDto, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> getBooking(String bookingId) {
		headers = Utilities.getDefaultHeader();

		String[] splitDetail;
		BookingDetailEntity bookingDetailEntity = bookingTripRepository.findByBookingId(bookingId);
		BookingDto bookingDto = GenericMapper.mapper.map(bookingDetailEntity, BookingDto.class);

		ZoneId id = ZoneId.of("Asia/Kolkata");
		bookingDto.setNotificationExpTime(
				ZonedDateTime.ofInstant(bookingDetailEntity.getUpdatedOn().toInstant(), id).plusMinutes(15));
		CompanyDetailDto companyDetailDto = new CompanyDetailDto();
		bookingDto.setCreatedDate(ZonedDateTime.ofInstant(bookingDetailEntity.getCreatedOn().toInstant(), id));

		bookingDto.setCreatedTime(ZonedDateTime.ofInstant(bookingDetailEntity.getCreatedOn().toInstant(), id));
		companyDetailDto.setCompanyName(bookingDetailEntity.getCompanyDetailsEntity().getCompanyName());
		bookingDto.setCompanyDetailDto(companyDetailDto);
		MasterDataDto sysCarCategoryDto = new MasterDataDto();
		MasterDataDto sysBookingRequestTypeDto = new MasterDataDto();

		sysBookingRequestTypeDto.setId((bookingDetailEntity.getSysBookingRequestTypeEntity().getId()).longValue());
		sysBookingRequestTypeDto.setName(bookingDetailEntity.getSysBookingRequestTypeEntity().getName());

		sysCarCategoryDto.setId((bookingDetailEntity.getSysCarCategoryEntity().getId()).longValue());
		sysCarCategoryDto.setName(bookingDetailEntity.getSysCarCategoryEntity().getName());

		bookingDto.setSysBookingRequestTypeDto(sysBookingRequestTypeDto);
		bookingDto.setSysCarCategoryDto(sysCarCategoryDto);

		TravelerDetailDto travelerDetailDto = new TravelerDetailDto();
		String userDetails = bookingTripRepository.findUserByAccountId(bookingDetailEntity.getAccountId());
		splitDetail = userDetails.split(",");
		travelerDetailDto.setFirstName(splitDetail[0]);
		travelerDetailDto.setLastName(splitDetail[1]);
		travelerDetailDto.setMobileNumber(splitDetail[2]);
		travelerDetailDto.setEmployeeId(splitDetail[3]);
		bookingDto.setTravelerDetailDto(travelerDetailDto);

		if (bookingDetailEntity.getDriverAccountId() != null && bookingDetailEntity.getDriverAccountId() != " ") {
			TravelerDetailDto driverDetailDto = new TravelerDetailDto();
			String driverDetails = bookingTripRepository.findUserByAccountId(bookingDetailEntity.getDriverAccountId());
			splitDetail = driverDetails.split(",");
			driverDetailDto.setFirstName(splitDetail[0]);
			driverDetailDto.setLastName(splitDetail[1]);
			driverDetailDto.setMobileNumber(splitDetail[2]);
			bookingDto.setDriverDetailDto(driverDetailDto);
		}
		// Fetching tripId if trip status is 5
		if (bookingDto.getTripStatus() >= 5) {
			bookingDto.setTripId(tripDetailRepository.findOneByBookingId(bookingId));
			String timeDistance = tripCoordinateRepository.findTotalDistAndTimeByTripId(bookingDto.getTripId());
			bookingDto.setTotalTime(timeDistance.split(",")[1]);
			bookingDto.setTotalDistance(timeDistance.split(",")[0]);
		}

		if (bookingDto != null && bookingDto.getId() != null) {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Success");
		} else {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Data");
		}
		return new ResponseEntity<BookingDto>(bookingDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> bookingByAccountId(String driverAccoutId) {
		headers = Utilities.getDefaultHeader();

		List<BookingShortDto> listBookingShortDto = new ArrayList<BookingShortDto>();
		String query = "SELECT     b.id,    b.pickUpDate,    b.pickUpCity,"
				+ "    b.pickUpTime,    b.releaseLocation,    b.releaseAddress,"
				+ "    b.pickUpLocation,    b.tripStatus,    concat(a.firstName,\" \",a.lastName),"
				+ "    a.mobileNumber,    c.companyname,b.driverAccountId FROM"
				+ "    bookingdetail b,applicationuser a,companydetails c WHERE    b.driverAccountId = '"
				+ driverAccoutId + "'"
				+ "        AND b.tripStatus IN (1,2,3,4) and a.accountId=b.accountId and c.id=b.companyId";
		Query qry = entityManager.createNativeQuery(query);
		List<Object[]> obj = (List<Object[]>) qry.getResultList();
		for (Object[] object : obj) {
			BookingShortDto bookingShortDto = new BookingShortDto();
			bookingShortDto.setId((String) object[0]);
			bookingShortDto.setPickUpDate((Date) object[1]);
			bookingShortDto.setPickUpCity((String) object[2]);
			bookingShortDto.setPickUpTime((Date) object[3]);
			bookingShortDto.setReleaseLocation((String) object[4]);
			bookingShortDto.setReleaseAddress((String) object[5]);
			bookingShortDto.setPickUpLocation((String) object[6]);
			bookingShortDto.setStatus((int) object[7]);
			bookingShortDto.setTravellerName((String) object[8]);
			bookingShortDto.setTravellerMobile((String) object[9]);
			bookingShortDto.setCompanyName((String) object[10]);
			bookingShortDto.setDriverAccountId((String) object[11]);

			listBookingShortDto.add(bookingShortDto);
		}
		if (listBookingShortDto != null && !listBookingShortDto.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Success");
		} else {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Data");
		}
		return new ResponseEntity<List<BookingShortDto>>(listBookingShortDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> finishedTripByDriver(String driverAccoutId) {
		headers = Utilities.getDefaultHeader();

		String query;
		Query qry;
		List<Object[]> obj;
		List<BookingShortDto> listBookingShortDto = new ArrayList<BookingShortDto>();
		query = "SELECT     b.id,    b.pickUpDate,    b.pickUpCity,"
				+ "    b.pickUpTime,    b.releaseLocation,    b.releaseAddress,"
				+ "    b.pickUpLocation,    b.tripStatus,    concat(a.firstName,\" \",a.lastName),"
				+ "    a.mobileNumber,    c.companyname,b.driverAccountId FROM"
				+ "    bookingdetail b,applicationuser a,companydetails c WHERE    b.driverAccountId = '"
				+ driverAccoutId + "'"
				+ "        AND b.tripStatus IN (5,8) and a.accountId=b.accountId and c.id=b.companyId";
		qry = entityManager.createNativeQuery(query);
		obj = (List<Object[]>) qry.getResultList();
		for (Object[] object : obj) {
			BookingShortDto bookingShortDto = new BookingShortDto();
			bookingShortDto.setId((String) object[0]);
			bookingShortDto.setPickUpDate((Date) object[1]);
			bookingShortDto.setPickUpCity((String) object[2]);
			bookingShortDto.setPickUpTime((Date) object[3]);
			bookingShortDto.setReleaseLocation((String) object[4]);
			bookingShortDto.setReleaseAddress((String) object[5]);
			bookingShortDto.setPickUpLocation((String) object[6]);
			bookingShortDto.setStatus((int) object[7]);
			bookingShortDto.setTravellerName((String) object[8]);
			bookingShortDto.setTravellerMobile((String) object[9]);
			bookingShortDto.setCompanyName((String) object[10]);
			bookingShortDto.setDriverAccountId((String) object[11]);

			listBookingShortDto.add(bookingShortDto);
		}

		try {
			query = "SELECT     b.id,    b.pickUpDate,    b.pickUpCity,    b.pickUpTime,"
					+ "    b.releaseLocation,    b.releaseAddress,    b.pickUpLocation,"
					+ "    other.status,    CONCAT(a.firstName, '', a.lastName),    a.mobileNumber,"
					+ "    c.companyname FROM othertripstatus other,    bookingdetail b,"
					+ "    applicationuser a,    companydetails c WHERE other.accountId = '" + driverAccoutId
					+ "'    AND other.bookingId=b.id         AND other.status IN (5,6,7)"
					+ "        AND a.accountId = b.accountId        AND c.id = b.companyId";
			qry = entityManager.createNativeQuery(query);
			obj = (List<Object[]>) qry.getResultList();
			for (Object[] object : obj) {
				BookingShortDto bookingShortDto = new BookingShortDto();
				bookingShortDto.setId((String) object[0]);
				bookingShortDto.setPickUpDate((Date) object[1]);
				bookingShortDto.setPickUpCity((String) object[2]);
				bookingShortDto.setPickUpTime((Date) object[3]);
				bookingShortDto.setReleaseLocation((String) object[4]);
				bookingShortDto.setReleaseAddress((String) object[5]);
				bookingShortDto.setPickUpLocation((String) object[6]);
				bookingShortDto.setStatus((int) object[7]);
				bookingShortDto.setTravellerName((String) object[8]);
				bookingShortDto.setTravellerMobile((String) object[9]);
				bookingShortDto.setCompanyName((String) object[10]);
				bookingShortDto.setDriverAccountId((String) object[11]);

				listBookingShortDto.add(bookingShortDto);
			}
		} catch (Exception exception) {
		}
		if (listBookingShortDto != null && !listBookingShortDto.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Success");
		} else {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Data");
		}
		return new ResponseEntity<List<BookingShortDto>>(listBookingShortDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> bookingByAccountIdFrAdmin(int pageIndex, int pageSize) {
		headers = Utilities.getDefaultHeader();

		List<BookingShortDto> listBookingShortDto = new ArrayList<BookingShortDto>();
		String query = "SELECT     b.id,    b.pickUpDate,    b.pickUpCity,"
				+ "    b.pickUpTime,    b.releaseLocation,    b.releaseAddress,"
				+ "    b.pickUpLocation,    b.tripStatus,    concat(a.firstName,\" \",a.lastName),"
				+ "    a.mobileNumber,    c.companyname ,b.driverAccountId FROM"
				+ "    bookingdetail b,applicationuser a,companydetails c WHERE   b.tripStatus IN (0,1,2 , 3,4) and a.accountId=b.accountId and c.id=b.companyId order by concat(b.pickUpDate, ' ', b.pickUpTime) desc";
		Query qry = entityManager.createNativeQuery(query).setFirstResult(pageIndex * pageSize).setMaxResults(pageSize);
		List<Object[]> obj = (List<Object[]>) qry.getResultList();
		for (Object[] object : obj) {
			BookingShortDto bookingShortDto = new BookingShortDto();
			bookingShortDto.setId((String) object[0]);
			bookingShortDto.setPickUpDate((Date) object[1]);
			bookingShortDto.setPickUpCity((String) object[2]);
			bookingShortDto.setPickUpTime((Date) object[3]);
			bookingShortDto.setReleaseLocation((String) object[4]);
			bookingShortDto.setReleaseAddress((String) object[5]);
			bookingShortDto.setPickUpLocation((String) object[6]);
			bookingShortDto.setStatus((int) object[7]);
			bookingShortDto.setTravellerName((String) object[8]);
			bookingShortDto.setTravellerMobile((String) object[9]);
			bookingShortDto.setCompanyName((String) object[10]);
			bookingShortDto.setDriverAccountId((String) object[11]);
			listBookingShortDto.add(bookingShortDto);
		}
		if (listBookingShortDto != null && !listBookingShortDto.isEmpty()) {
			for (BookingShortDto bookingShortDto : listBookingShortDto) {
				if (bookingShortDto.getDriverAccountId() != null && bookingShortDto.getDriverAccountId() != " ") {
					bookingShortDto.setDriverName(
							applicationUserRepo.findByDriverAccountId(bookingShortDto.getDriverAccountId()));
				}
			}
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Success");
		} else {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Data");
		}
		return new ResponseEntity<List<BookingShortDto>>(listBookingShortDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> finishedTripByDriverFrAdmin(int pageIndex, int pageSize) {
		headers = Utilities.getDefaultHeader();

		List<BookingShortDto> listBookingShortDto = new ArrayList<BookingShortDto>();
		String query = "SELECT     b.id,    b.pickUpDate,    b.pickUpCity,"
				+ "    b.pickUpTime,    b.releaseLocation,    b.releaseAddress,"
				+ "    b.pickUpLocation,    b.tripStatus,    concat(a.firstName,' ',a.lastName),"
				+ "    a.mobileNumber,    c.companyname,b.driverAccountId FROM"
				+ "    bookingdetail b,applicationuser a,companydetails c WHERE"
				+ "   b.tripStatus in (5,8,9) and a.accountId=b.accountId and c.id=b.companyId order by concat(b.pickUpDate, ' ', b.pickUpTime) desc";
		Query qry = entityManager.createNativeQuery(query).setFirstResult(pageIndex * pageSize).setMaxResults(pageSize);
		List<Object[]> obj = (List<Object[]>) qry.getResultList();
		for (Object[] object : obj) {
			BookingShortDto bookingShortDto = new BookingShortDto();
			bookingShortDto.setId((String) object[0]);
			bookingShortDto.setPickUpDate((Date) object[1]);
			bookingShortDto.setPickUpCity((String) object[2]);
			bookingShortDto.setPickUpTime((Date) object[3]);
			bookingShortDto.setReleaseLocation((String) object[4]);
			bookingShortDto.setReleaseAddress((String) object[5]);
			bookingShortDto.setPickUpLocation((String) object[6]);
			bookingShortDto.setStatus((int) object[7]);
			bookingShortDto.setTravellerName((String) object[8]);
			bookingShortDto.setTravellerMobile((String) object[9]);
			bookingShortDto.setCompanyName((String) object[10]);
			bookingShortDto.setDriverAccountId((String) object[11]);

			listBookingShortDto.add(bookingShortDto);
		}

		if (listBookingShortDto != null && !listBookingShortDto.isEmpty()) {
			for (BookingShortDto bookingShortDto : listBookingShortDto) {
				if (bookingShortDto.getDriverAccountId() != null && bookingShortDto.getDriverAccountId() != " ") {
					bookingShortDto.setDriverName(
							applicationUserRepo.findByDriverAccountId(bookingShortDto.getDriverAccountId()));
				}
			}
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Success");
		} else {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Data");
		}
		return new ResponseEntity<List<BookingShortDto>>(listBookingShortDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> bookingNotificationResponse(int answer, String bookingId, String accountId) {
		headers = Utilities.getDefaultHeader();
		int update;
		if (answer == 1) {
			update = bookingTripRepository.updateTripAndDriverStatus(3, bookingId, accountId);
			if (update == 1) {
				String pickupTime = bookingTripRepository.findPickUpTimeByBookingId(bookingId);
				update = driverAlloatmentService.updateNextTripTime(pickupTime, accountId);

				// Removing BookingId in Map , Driver is alloted to this trip,further search
				// will not take place
				DriverAlloatmentService.driverMapList.remove(bookingId);
			}

			/*
			 * //Notifinig driver confirmation of ride notificationDto = new
			 * NotificationDto(); notificationDto.setBookingTripId(bookingDto.getId());
			 */
		} else {
			// Entering driverId and booking id for driver decline record
			otherTripStatusEntity = new OtherTripStatusEntity();
			otherTripStatusEntity.setAccountId(accountId);
			otherTripStatusEntity.setBookingId(bookingId);
			otherTripStatusEntity.setStatus(6);
			otherTripRepo.save(otherTripStatusEntity);

			// If answer is declined then, driver accountId need to enter in map so he will
			// not be serched again and other driver search will start
			String driverName = driverAlloatmentService
					.driverSearch(GenericMapper.mapper.map(bookingTripRepository.getOne(bookingId), BookingDto.class));
		}
		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Success");
		statusDto = new StatusDto();
		statusDto.setStatus("true");
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> addNew(BookingDto request) {
		headers = Utilities.getDefaultHeader();

		if (request.getDriverAccountId() != null && !request.getDriverAccountId().equals("")) {
			driverAccountId = request.getDriverAccountId();
		}
		String bookingRes = null;
		statusDto = new StatusDto();
		BookingDetailEntity bookingDetailEntity = null;
		try {
			request.setTripStatus(1);
			bookingDetailEntity = GenericMapper.mapper.map(request, BookingDetailEntity.class);
			bookingDetailEntity.setSysBookingRequestTypeEntity(
					GenericMapper.mapper.map(request.getSysBookingRequestTypeDto(), SysBookingRequestTypeEntity.class));
			bookingDetailEntity.setCompanyDetailsEntity(
					GenericMapper.mapper.map(request.getCompanyDetailDto(), CompanyDetailsEntity.class));
			bookingDetailEntity.setSysCarCategoryEntity(
					GenericMapper.mapper.map(request.getSysCarCategoryDto(), SysCarCategoryEntity.class));
			bookingDetailEntity.setSysCarMasterEntity(
					GenericMapper.mapper.map(request.getSysCarMasterDto(), SysCarMasterEntity.class));
			bookingDetailEntity.setDriverAccountId(null);
			bookingDetailEntity = bookingTripRepository.save(bookingDetailEntity);
			if (bookingDetailEntity.getId() != null) {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Booking Request Generated Successfully");
				statusDto.setStatus("true");
			} else {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Booking Request Generated Successfully");
				statusDto.setStatus("false");
			}
		} catch (Exception exception) {
			exception.getStackTrace();
		}
		try {
			// Notifying Driver if
			if (bookingDetailEntity.getId() != null && driverAccountId != null && !driverAccountId.equals("")) {
				request.setId(bookingDetailEntity.getId());
				bookingRes = driverAlloatmentService.selectedDriverSearch(request);
			}
		} catch (Exception exception) {
			exception.getStackTrace();
		}
		try {
			if (bookingDetailEntity.getId() != null && bookingRes == null) {
				request.setId(bookingDetailEntity.getId());
				driverAlloatmentService.driverSearch(request);
			}
		} catch (Exception exception) {
		}
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
	}

}
