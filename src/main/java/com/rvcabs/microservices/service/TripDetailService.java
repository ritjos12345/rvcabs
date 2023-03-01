package com.rvcabs.microservices.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.SecureRandom;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.rvcabs.microservices.constants.Constants;
import com.rvcabs.microservices.dto.DropDto;
import com.rvcabs.microservices.dto.FeedBackDto;
import com.rvcabs.microservices.dto.NotificationDto;
import com.rvcabs.microservices.dto.StatusDto;
import com.rvcabs.microservices.dto.TripAddInfoDto;
import com.rvcabs.microservices.dto.TripCoordinateDto;
import com.rvcabs.microservices.entity.TripCoordinateEntity;
import com.rvcabs.microservices.entity.TripDetailEntity;
import com.rvcabs.microservices.entity.TripFilesEntity;
import com.rvcabs.microservices.repository.BookingTripRepository;
import com.rvcabs.microservices.repository.DriverTripDetailRepository;
import com.rvcabs.microservices.repository.TripCoordinateRepository;
import com.rvcabs.microservices.repository.TripDetailRepository;
import com.rvcabs.microservices.repository.TripFilesRepository;
import com.rvcabs.microservices.utilities.Utilities;

@Transactional
@Service
public class TripDetailService {

	private static Logger logger = LoggerFactory.getLogger(TripDetailService.class);

	@Autowired
	private BookingTripRepository bookingTripRepository;

	@Autowired
	private DriverTripDetailRepository driverTripDetailRepository;

	@Autowired
	private TripDetailRepository tripDetailRepository;

	@Autowired
	private TripCoordinateRepository tripCoordinateRepository;

	@Autowired
	private NotificationService notify;

	static ModelMapper modelMapper = new ModelMapper();

	private MultiValueMap<String, String> headers = null;

	private TripCoordinateEntity tripCoordinateEntity;

	private TripFilesEntity tripPicFilesEntity;

	@Autowired
	private TripFilesRepository tripPicFilesRepository;

	private SecureRandom rand;

	private NotificationDto pushNotificationDto;

	private StatusDto statusDto;

	public ResponseEntity<?> updateTollParking(TripAddInfoDto addInfoDto) {
		headers = Utilities.getDefaultHeader();
		// String tripCoordinateId =
		// tripCoordinateRepository.findTripCoId(addInfoDto.getTripId());
		String fileName = System.getProperty("user.dir");
		String filePath = fileName + File.separator + "RVCImage" + File.separator + "TripTollParking";
		try {
			byte[] bytes = addInfoDto.getContent();
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String line = null;
			OutputStream os = null;
			os = new FileOutputStream(file + File.separator + addInfoDto.getTripId());
			os.write(bytes);
			os.close();
			addInfoDto.setPicUrl(filePath + File.separator + addInfoDto.getTripId());
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Saving Toll reading and pic \
		try {
			tripPicFilesEntity = new TripFilesEntity();
			tripPicFilesEntity.setActive(true);
			tripPicFilesEntity.setBookingType(addInfoDto.getTripType().toUpperCase());
			tripPicFilesEntity.setPicUrl(addInfoDto.getPicUrl());
			tripPicFilesEntity.setTripCoordinateId(tripCoordinateRepository.findTripCoId(addInfoDto.getTripId()));
			tripPicFilesEntity.setCount(String.valueOf(addInfoDto.getCount()));
			tripPicFilesRepository.save(tripPicFilesEntity);
		} catch (Exception e) {

		}

		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Trip data updated successfully");
		statusDto = new StatusDto();
		statusDto.setStatus("true");
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> endTrip(TripAddInfoDto addInfoDto) {
		headers = Utilities.getDefaultHeader();

		String tripcoId = tripCoordinateRepository.findTripCoId(addInfoDto.getTripId());
		tripCoordinateRepository.updateTripStatus(addInfoDto.getTripId(), addInfoDto.getSrcName(),
				addInfoDto.getSrcLng(), addInfoDto.getSrcLat(), addInfoDto.getDistance());
		tripDetailRepository.updateDistance(addInfoDto.getTripId(), addInfoDto.getDistance());
		tripDetailRepository.updateTripStatus(addInfoDto.getTripId());
		driverTripDetailRepository.updateDrivingStatus(false, addInfoDto.getDriverAccountId());

		bookingTripRepository.updateTripStatus(5, tripDetailRepository.fndBookingIdByTripId(addInfoDto.getTripId()));
		// Upating TotalDistance and rating in drivertripdetail
		driverTripDetailRepository.updateRating(addInfoDto.getTripId(), addInfoDto.getDriverAccountId());

		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Trip data updated successfully");

		String fileName = System.getProperty("user.dir");
		String filePath = fileName + File.separator + "RVCImage" + File.separator + "TripEndReading";
		try {
			byte[] bytes = addInfoDto.getContent();
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String line = null;
			OutputStream os = null;
			os = new FileOutputStream(file + File.separator + addInfoDto.getTripId());
			os.write(bytes);
			os.close();
			addInfoDto.setPicUrl(filePath + File.separator + addInfoDto.getTripId());
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Saving car reading and pic
		try {
			tripPicFilesEntity = new TripFilesEntity();
			tripPicFilesEntity.setActive(true);
			tripPicFilesEntity.setBookingType("ENDREADING");
			tripPicFilesEntity.setPicUrl(addInfoDto.getPicUrl());
			tripPicFilesEntity.setTripCoordinateId(tripcoId);
			tripPicFilesEntity.setCount(String.valueOf(addInfoDto.getCount()));
			tripPicFilesRepository.save(tripPicFilesEntity);
		} catch (Exception e) {

		}

		statusDto = new StatusDto();
		statusDto.setStatus("true");
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> dropMember(DropDto dropDto) {
		headers = Utilities.getDefaultHeader();

		Optional<TripDetailEntity> tripDetailEntity;
		tripDetailEntity = tripDetailRepository.findById(dropDto.getTripId());
		tripDetailEntity.get().setSignatureURL(dropDto.getSigURl());
		tripDetailEntity.get().setTripFeedBack(dropDto.getFeedback());
		tripDetailEntity.get().setTripRating(dropDto.getRating());
		tripDetailRepository.save(tripDetailEntity.get());
		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, "Trip data updated successfully");
		statusDto = new StatusDto();
		statusDto.setStatus("true");
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);

	}

	public String getSignature(String tripId) {

		return tripDetailRepository.findSignatureByTripId(tripId);
	}

	public ResponseEntity<?> getCoordinates(String tripId) {
		headers = Utilities.getDefaultHeader();

		String userMobileNumber = null;
		List<TripCoordinateEntity> tripList = null;
		TripCoordinateDto tripCoDto = new TripCoordinateDto();
		try {
			userMobileNumber = tripDetailRepository.userMobileNumberByTrip(tripId);
		} catch (Exception exception) {
		}
		try {
			tripList = tripCoordinateRepository.findAllByTripIdOrderByCreatedOnAsc(tripId);
		} catch (Exception exception) {
		}
		try {
			if (tripList != null && !tripList.isEmpty()) {
				/*
				 * for (TripCoordinateEntity coordinateEntity : tripList) { coordinateEntity
				 * .setTime(tripCoordinateRepository.findDistAndTimeByTripCoId(coordinateEntity.
				 * getId())); }
				 */
				String totalDistAndTime = tripCoordinateRepository.findTotalDistAndTimeByTripId(tripId);
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Trip data updated successfully");
				headers.add("Time", totalDistAndTime.split(",")[1]);
				headers.add("Distance", totalDistAndTime.split(",")[0]);
				tripCoDto.setTripList(tripList);
				tripCoDto.setTotalTime(totalDistAndTime.split(",")[1]);
				tripCoDto.setTotalDistance(totalDistAndTime.split(",")[0]);
			}
		} catch (Exception exception) {
		}
		try {
			pushNotificationDto = new NotificationDto();
			pushNotificationDto.setSms(
					"Thank you for travelling with us,please share your valuable feedback with us,http://103.224.240.187:8090/rvcabsv2/feedback.html?tripId="
							+ tripId);
			pushNotificationDto.setSmsId(userMobileNumber);
			notify.saveNotificaton(pushNotificationDto);
		} catch (Exception exception) {
		}
		return new ResponseEntity<>(tripCoDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> getFeedBackDetails(String id) {
		headers = Utilities.getDefaultHeader();
		String feedBack = null;
		List<TripCoordinateEntity> tripList = null;
		Map<String, String> feedBackupMap = new HashMap<String, String>();
		TripCoordinateDto tripCoDto = new TripCoordinateDto();
		try {
			feedBack = tripDetailRepository.findFeedBackDetails(id);
		} catch (Exception exception) {
			logger.error("TripDetailService:getFeedBackDetails:" + exception.getMessage());
		}

		try {
			if (feedBack != null) {
				String[] feedBackArray = feedBack.split(",");
				feedBackupMap.put("tripId", feedBackArray[0]);
				feedBackupMap.put("bookingId", feedBackArray[1]);
				feedBackupMap.put("rating", feedBackArray[2]);
				feedBackupMap.put("feedBack", feedBack.substring(feedBack.indexOf(feedBackArray[2]) + 4));
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Trip data updated successfully");
				return new ResponseEntity<>(feedBackupMap, headers, HttpStatus.OK);
			}
		} catch (Exception exception) {
		}

		return new ResponseEntity<>(tripCoDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> updateFeedBackDetails(FeedBackDto feedBackDto) {
		headers = Utilities.getDefaultHeader();
		TripDetailEntity tripDetailEntity = null;
		try {
			tripDetailEntity = tripDetailRepository.findOneById(feedBackDto.getTripId());
		} catch (Exception exception) {
			logger.error("TripDetailService:getFeedBackDetails:" + exception.getMessage());
		}
		try {
			if (tripDetailEntity != null) {
				tripDetailEntity.setTripRating(feedBackDto.getRating());
				tripDetailEntity.setTripFeedBack(feedBackDto.getFeedBack());
				tripDetailEntity = tripDetailRepository.save(tripDetailEntity);
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "FeedBack updated successfully");

			}
		} catch (Exception exception) {
		}

		return new ResponseEntity<>(true, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> pickUpMember(String tripId, String srcLat, String srcLng, String srcName,
			String distance) {
		headers = Utilities.getDefaultHeader();

		String userMobileNumber = null;
		int otp = 0;
		int flag = 0;
		statusDto = new StatusDto();
		try {
			flag = tripCoordinateRepository.countByTripId(tripId);
			tripDetailRepository.updateDistance(tripId, distance);

		} catch (Exception e) {
			// TODO: handle exception
		}
		try {
			// create instance of Random class
			rand = SecureRandom.getInstanceStrong();
			tripCoordinateRepository.updateTripStatus(tripId, srcName, srcLng, srcLat, distance);

			// Updating total distance for perticular trip

			// Creating new record for tracking source and destination to track multiple
			// stops
			tripCoordinateEntity = new TripCoordinateEntity();
			tripCoordinateEntity.setTripId(tripId);
			tripCoordinateEntity.setSrcLat(srcLat);
			tripCoordinateEntity.setSrcLng(srcLng);
			tripCoordinateEntity.setSrcName(srcName);
			tripCoordinateEntity = tripCoordinateRepository.save(tripCoordinateEntity);

			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Coordinates updated successfully");
			statusDto.setInfo(String.valueOf(0));

		} catch (Exception exception) {
		}

		try {
			if (flag == (1)) {

				statusDto.setStatus(String.valueOf(otp));
				statusDto.setInfo(String.valueOf(flag));
			}
		} catch (Exception exception) {
		}
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> tripStart(TripAddInfoDto addInfoDto) {
		headers = Utilities.getDefaultHeader();

		statusDto = new StatusDto();
		TripDetailEntity tripDetailEntity = new TripDetailEntity();
		tripCoordinateEntity = new TripCoordinateEntity();
		// Update BookingDetailEntity
		try {
			if (addInfoDto.getBookingId() != null && !addInfoDto.getBookingId().equals("")) {
				bookingTripRepository.updateTripStatus(4, addInfoDto.getBookingId());
			}
			if (addInfoDto.getDriverAccountId() != null && !addInfoDto.getDriverAccountId().equals("")) {
				driverTripDetailRepository.updateDrivingStatus(true, addInfoDto.getDriverAccountId());
			}
			// Updating on going trip start time
			if (addInfoDto.getDriverAccountId() != null && !addInfoDto.getDriverAccountId().equals("")) {
				driverTripDetailRepository.updateDrivingOngoingTime(addInfoDto.getDriverAccountId());
			}
			// Creating New record for trip in TripDetailEntity
			tripDetailEntity.setBookingId(addInfoDto.getBookingId());
			tripDetailEntity.setTripStartTime(ZonedDateTime.now(ZoneId.of("Asia/Kolkata")));
			tripDetailEntity = tripDetailRepository.save(tripDetailEntity);
			// Creating new record for tracking source and destination to track multiple
			// stops
			tripCoordinateEntity.setTripId(tripDetailEntity.getId());
			tripCoordinateEntity.setSrcLat(addInfoDto.getSrcLat());
			tripCoordinateEntity.setSrcLng(addInfoDto.getSrcLng());
			tripCoordinateEntity.setSrcName(addInfoDto.getSrcName());
			tripCoordinateEntity = tripCoordinateRepository.save(tripCoordinateEntity);
		} catch (Exception e) {
		}
		if (!tripCoordinateEntity.getId().isEmpty() && !tripDetailEntity.getId().isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Trip started successfull");
			statusDto.setStatus(String.valueOf(tripDetailEntity.getId()));

		} else {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "Trip start fail");
		}
		try {
			rand = SecureRandom.getInstanceStrong();
			String[] driverDetail = bookingTripRepository.findDriverDetailsByBookingId(addInfoDto.getDriverAccountId());
			String mobileNumber = bookingTripRepository.findMobileNumOfUser(addInfoDto.getBookingId());
			String[] userDetail = driverDetail[0].split(",");
			// Generate random integers in range 0 to 999
			int otp = rand.nextInt(1000) + 1000;
			// String userMobileNumber =
			// tripDetailRepository.userMobileNumberByTrip(tripDetailEntity.getId());
			System.out.println(
					"##########Hello,please provide OTP:" + otp + " to driver,have a great journey ###########");
			// notify.sendSms(userMobileNumber, "Hello,please provide OTP:" + otp + " to
			// driver,have a great journey");
			notify.sendSms(mobileNumber,
					"Hello,driver " + userDetail[0] + " left from " + addInfoDto.getSrcName()
							+ " to pick you,contact number of driver is " + userDetail[1]
							+ ".Please provide OTP:1111 to driver,have a great journey");
		} catch (Exception exception) {
		}

		String fileName = System.getProperty("user.dir");
		String filePath = fileName + File.separator + "RVCImage" + File.separator + "TripStartReading";
		try {
			byte[] bytes = addInfoDto.getContent();
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String line = null;
			OutputStream os = null;
			os = new FileOutputStream(file + File.separator + tripDetailEntity.getId());
			os.write(bytes);
			os.close();
			addInfoDto.setPicUrl(filePath + File.separator + tripDetailEntity.getId());
		} catch (Exception e) {
			// TODO: handle exception
		}

		// Saving car reading and pic
		try {
			tripPicFilesEntity = new TripFilesEntity();
			tripPicFilesEntity.setActive(true);
			tripPicFilesEntity.setBookingType("STARTREADING");
			tripPicFilesEntity.setPicUrl(addInfoDto.getPicUrl());
			tripPicFilesEntity.setTripCoordinateId(tripCoordinateEntity.getId());
			tripPicFilesEntity.setCount(String.valueOf(addInfoDto.getCount()));
			tripPicFilesRepository.save(tripPicFilesEntity);
		} catch (Exception e) {

		}
		statusDto.setInfo(String.valueOf(1111));

		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> updateDriverLocation(String accountId, String srcLat, String srcLng) {
		headers = Utilities.getDefaultHeader();
		Integer positionUpdate = 0;
		positionUpdate = driverTripDetailRepository.updateDriverPosition(accountId, srcLat, srcLng);
		statusDto = new StatusDto();
		statusDto.setStatus(String.valueOf(positionUpdate));
		if (positionUpdate != 1) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "Driver location update fail");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Driver location update  is successfull");
		}

		statusDto = new StatusDto();
		statusDto.setStatus(String.valueOf(positionUpdate));
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);

	}
}
