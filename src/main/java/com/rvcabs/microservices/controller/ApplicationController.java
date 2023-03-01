package com.rvcabs.microservices.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.csrf.CsrfToken;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.rvcabs.microservices.aop.LogExecution;
import com.rvcabs.microservices.aop.LogRequest;
import com.rvcabs.microservices.constants.Constants;
import com.rvcabs.microservices.dto.ApplicationSignInResponseDto;
import com.rvcabs.microservices.dto.ApplicationUserDto;
import com.rvcabs.microservices.dto.AuthResponseDto;
import com.rvcabs.microservices.dto.CarDetailDto;
import com.rvcabs.microservices.dto.DocumentDetailDto;
import com.rvcabs.microservices.dto.FeedBackDto;
import com.rvcabs.microservices.dto.FileDto;
import com.rvcabs.microservices.dto.ForgotPasswordRequest;
import com.rvcabs.microservices.dto.TokenDto;
import com.rvcabs.microservices.request.ApplicationUserRequest;
import com.rvcabs.microservices.request.ChangePasswordRequest;
import com.rvcabs.microservices.request.SignInRequest;
import com.rvcabs.microservices.service.ApplicationService;
import com.rvcabs.microservices.service.TripDetailService;
import com.rvcabs.microservices.utilities.JwtUtil;
import com.rvcabs.microservices.utilities.Utilities;

import io.swagger.annotations.Api;

@Api
@RestController
// @RequestMapping(path="/public")
public class ApplicationController {

	@Autowired
	ApplicationService applicationService;

	@Autowired
	TripDetailService tripDetailService;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUtil jwtUtil;

	protected static final String REQUEST_ATTRIBUTE_NAME = "_csrf";
	protected static final String RESPONSE_HEADER_NAME = "X-CSRF-HEADER";
	protected static final String RESPONSE_PARAM_NAME = "X-CSRF-PARAM";
	protected static final String RESPONSE_TOKEN_NAME = "X-CSRF-TOKEN";

	@LogRequest
	@LogExecution
//	@PreAuthorize("hasAuthority('" + RoleAndAuthorityConstants.CREATE_USER + "')")
	@PostMapping(path = "/private/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNew(@Validated @RequestBody final ApplicationUserRequest requestBody, Errors e,
			final HttpServletRequest httpRequest) {
		if (e.hasErrors()) {
			Utilities.prepareBadRequestExceptionAndThrow(e);
		}
		return applicationService.addNew(requestBody);
	}

	@LogRequest
	@LogExecution
	@PostMapping(path = "/public/signIn")
	public ResponseEntity<?> signIn(@Validated @RequestBody final SignInRequest authenticationRequest)
			throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
					authenticationRequest.getUserName(), authenticationRequest.getPassword()));
		} catch (BadCredentialsException e) {
			throw new Exception("Incorrect username or password", e);
		}

		final TokenDto userDetails = (TokenDto) applicationService
				.loadUserByUsername(authenticationRequest.getUserName());

		final String jwt = jwtUtil.generateToken(userDetails);
		AuthResponseDto authResponseDto = new AuthResponseDto();
		authResponseDto.setAccountId(userDetails.getAccountId());
		authResponseDto.setToken(jwt);
		authResponseDto.setUserType(userDetails.getUserType());
		//authResponseDto.setExpires(10);
		return ResponseEntity.ok(authResponseDto);

	}

	@PostMapping(path = "/public/feedback")
	public ResponseEntity<?> feedback(@RequestBody FeedBackDto feedBackDto) {
		return tripDetailService.updateFeedBackDetails(feedBackDto);
	}

	@GetMapping(path = "/public/forgotPasswordOTP/{mobileNumber}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forgotPasswordOTP(@PathVariable("mobileNumber") String mobileNumber) {
		return applicationService.forgotPasswordOTP(mobileNumber);
	}

	@PostMapping(path = "/public/forgotPassword", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> forgotPassword(@Validated @RequestBody final ForgotPasswordRequest requestBody) {
		return applicationService.forgotPassword(requestBody);
	}

	@PostMapping(path = "/public/changePassword", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> changePassword(@Validated @RequestBody final ChangePasswordRequest requestBody) {
		return applicationService.changePassword(requestBody);
	}

	@GetMapping(path = "/private/read/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> read(@PathVariable("accountId") String accountId) {
		MultiValueMap<String, String> headers = Utilities.getDefaultHeader();
		ApplicationUserDto appDto = applicationService.findById(accountId);
		if (appDto == null) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "Driver are not found ");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Driver are found successfully");

		}
		return new ResponseEntity<>(appDto, headers, HttpStatus.OK);
	}

	@GetMapping(path = "private/updateProfile/{accountId}/{firstName}/{lastName}/{emailId}/{mobileNumber}/{active}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateProfile(@PathVariable("accountId") String accountId,
			@PathVariable("firstName") String firstName, @PathVariable("lastName") String lastName,
			@PathVariable("emailId") String emailId, @PathVariable("mobileNumber") String mobileNumber,
			@PathVariable boolean active) {
		return applicationService.updateProfile(accountId, firstName, lastName, emailId, mobileNumber, active);
	}

	@GetMapping(path = "/private/readDoc/{accountId}/{documentId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> readDoc(@PathVariable("accountId") String accountId,
			@PathVariable("documentId") String documentId) {
		return applicationService.readDoc(accountId, documentId);
	}

	@PostMapping(path = "/private/uploadPrfilePic", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> uploadPrfilePic(@RequestBody FileDto fileDto) {
		return applicationService.uploadPrfilePic(fileDto);
	}

	@GetMapping(path = "/private/readCarDetail/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> readCarDetail(@PathVariable("accountId") String accountId) {
		return applicationService.readCarDetails(accountId);
	}

	@PostMapping(path = "/private/saveDoc", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveDoc(@RequestBody DocumentDetailDto documentDetailDto) {
		return applicationService.saveDoc(documentDetailDto);

	}

	@PostMapping(path = "/private/saveCarDeatils", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> saveCarDeatils(@RequestBody CarDetailDto carDetailDto) {
		return applicationService.saveCarDeatils(carDetailDto);

	}

	@GetMapping(path = "/private/getDriverDetailByDriverId/{accountId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getDriverDetailByDriverId(@PathVariable("accountId") String accountId) {
		return applicationService.getDriverDetailByDriverId(accountId);
	}

}
