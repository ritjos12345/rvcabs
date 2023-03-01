package com.rvcabs.microservices.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvcabs.microservices.dto.BookingDto;
import com.rvcabs.microservices.dto.StatusDto;
import com.rvcabs.microservices.request.ApplicationUserRequest;
import com.rvcabs.microservices.service.ApplicationService;
import com.rvcabs.microservices.service.BookingService;
import com.rvcabs.microservices.service.CompanyDetailsService;
import com.rvcabs.microservices.utilities.Utilities;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping(path = "/booking")
public class BookingController {

	@Autowired
	private FileController fileController;

	@Autowired
	private CompanyDetailsService companyDetailsService;

	@Autowired
	private BookingService bookingService;

	@Autowired
	private ApplicationService applicationService;

	private StatusDto statusDto;

	@PostMapping(path = "/bookTrip", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> bookTrip(@Validated @RequestBody BookingDto requestBody, Errors e) {
		String companyId;
		if (e.hasErrors()) {
			Utilities.prepareBadRequestExceptionAndThrow(e);
		}
		try {
			if (requestBody.getCompanyDetailDto().getId() == null || requestBody.getCompanyDetailDto().equals("")) {
				statusDto = new StatusDto();
				statusDto = (StatusDto) companyDetailsService.addNew(requestBody.getCompanyDetailDto()).getBody();
				requestBody.getCompanyDetailDto().setId(statusDto.getStatus());
			}
		} catch (Exception exception) {
		}
		// Registering user if user not available in DB or fetching accountID of user
		try {
			ApplicationUserRequest applicationUserRequest = new ApplicationUserRequest();
			applicationUserRequest.setMobileNumber(requestBody.getTravelerDetailDto().getMobileNumber());
			applicationUserRequest.setFirstName(requestBody.getTravelerDetailDto().getFirstName());
			applicationUserRequest.setLastName(requestBody.getTravelerDetailDto().getLastName());
			applicationUserRequest.setEmployID(requestBody.getTravelerDetailDto().getEmployeeId());
			applicationUserRequest.setUserType("CUSTOMER");
			ResponseEntity<?> responseEntity = applicationService.addNew(applicationUserRequest);
			HashMap<String, String> hashMap = (HashMap<String, String>) responseEntity.getBody();
			requestBody.setAccountId(hashMap.get("AccountId"));
		} catch (Exception exception) {
		}

		return bookingService.addNew(requestBody);
	}

	/*
	 * Force closer of trip
	 */
	@GetMapping(path = "/closeTrip/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> closeTrip(@PathVariable String bookingId) {
		return bookingService.updatingTripStatusToClose(bookingId);
	}

	/*
	 * Searching Driver Document Type based on search term
	 */
	@GetMapping(path = "/bookingNotificationResponse/{answer}/{bookingId}/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> bookingNotificationResponse(@PathVariable int answer, @PathVariable String bookingId,
			@PathVariable String accountId) {
		return bookingService.bookingNotificationResponse(answer, bookingId, accountId);
	}

	/*
	 * Searching Trip which has assigned to driver and trip is not yet started
	 */
	@GetMapping(path = "/bookingByAccountId/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> bookingByAccountId(@PathVariable String accountId) {
		return bookingService.bookingByAccountId(accountId);
	}

	/*
	 * Searching Trip which has assigned to driver and trip is finished
	 */
	@GetMapping(path = "/finishedTripByDriver/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> finishedTripByDriver(@PathVariable String accountId) {
		return bookingService.finishedTripByDriver(accountId);
	}

	/*
	 * Searching Trip which has assigned to driver and trip is not yet started
	 */
	@GetMapping(path = "/bookingByAccountIdFrAdmin/{pageIndex}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> bookingByAccountIdFrAdmin(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return bookingService.bookingByAccountIdFrAdmin(pageIndex, pageSize);
	}

	/*
	 * Searching Trip which has assigned to driver and trip is finished
	 */
	@GetMapping(path = "/finishedTripByDriverFrAdmin/{pageIndex}/{pageSize}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> finishedTripByDriverFrAdmin(@PathVariable int pageIndex, @PathVariable int pageSize) {
		return bookingService.finishedTripByDriverFrAdmin(pageIndex, pageSize);
	}

	/*
	 * Searching booking trip by bookingId
	 */
	@GetMapping(path = "/getBooking/{bookingId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getBooking(@PathVariable String bookingId) {
		return bookingService.getBooking(bookingId);
	}

	/*
	 * Searching booking trip by bookingId
	 */
	@GetMapping(path = "/allotingDriver/{bookingId}/{driverAccountId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> allotingDriver(@PathVariable String bookingId, @PathVariable String driverAccountId) {
		return bookingService.allotingDriver(bookingId, driverAccountId);
	}

}
