package com.rvcabs.microservices.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvcabs.microservices.dto.CompanyDetailDto;
import com.rvcabs.microservices.dto.CustomerDto;
import com.rvcabs.microservices.service.ApplicationService;
import com.rvcabs.microservices.service.BillingService;
import com.rvcabs.microservices.service.CompanyDetailsService;
import com.rvcabs.microservices.service.MasterDataService;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping(path = "/masterdata")
@Transactional(readOnly = false)
public class MasterDataController {

	@Autowired
	private MasterDataService masterDataService;

	@Autowired
	private CompanyDetailsService companyDetailsService;

	@Autowired
	ApplicationService applicationService;

	@Autowired
	private BillingService billingService;

	/*
	 * Searching Driver Document Type based on search term
	 */
	@GetMapping(path = { "/documentSearch/{pageIndex}/{pageSize}",
			"/documentSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> documentSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return masterDataService.documentSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}

	/*
	 * Searching Driver Partner Type based on search term
	 */
	@GetMapping(path = { "/partnerSearch/{pageIndex}/{pageSize}",
			"/partnerSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> partnerSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return masterDataService.partnerSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}

	@PostMapping(path = "/addCustomer", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCustomer(@Validated @RequestBody CustomerDto customerDto) {
		return companyDetailsService.addCustomer(customerDto);

	}

	/*
	 * Searching Driver Car Brand based on search term
	 */
	@GetMapping(path = { "/carBrandSearch/{pageIndex}/{pageSize}",
			"/carBrandSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> carBrandSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return masterDataService.carBrandSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}

	/*
	 * Searching Driver Car Brand sub type ex:alto,zen based on search term
	 */
	@GetMapping(path = { "/carBrandSubTypeSearch/{pageIndex}/{pageSize}",
			"/carBrandSubTypeSearch/{pageIndex}/{pageSize}/{carType}",
			"/carBrandSubTypeSearch/{pageIndex}/{pageSize}/{carType}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> carBrandSubTypeSearch(@PathVariable("carType") Optional<Integer> carType,
			@PathVariable("searchTerm") Optional<String> searchTerm, @PathVariable("pageSize") int pageSize,
			@PathVariable("pageIndex") int pageIndex) {
		return masterDataService.carBrandSubTypeSearch(pageIndex, pageSize,
				searchTerm.isPresent() ? searchTerm.get() : "", carType.isPresent() ? carType.get() : 0);
	}

	/*
	 * Searching Driver Car Color based on search term
	 */
	@GetMapping(path = { "/carColorSearch/{pageIndex}/{pageSize}",
			"/carColorSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> carColorSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return masterDataService.carColorSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}

	/*
	 * Searching Driver Car Brand based on search term
	 */
	@GetMapping(path = { "/carIntColorSearch/{pageIndex}/{pageSize}",
			"/carIntColorSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> carIntColorSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return masterDataService.carIntColorSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}

	/*
	 * Searching Driver Car Category based on search term
	 */
	@GetMapping(path = { "/carCategorySearch/{pageIndex}/{pageSize}",
			"/carCategorySearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> carCategorySearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return masterDataService.carCategorySearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}

	/*
	 * Searching company details based on search term
	 */
	@GetMapping(path = { "/companyDetailsSearch/{pageIndex}/{pageSize}",
			"/companyDetailsSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> companyDetailsSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return masterDataService.companyDetailsSearch(pageIndex, pageSize,
				searchTerm.isPresent() ? searchTerm.get() : "");
	}

	@PostMapping(path = "/addCompany", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addCompany(@Validated @RequestBody CompanyDetailDto companyDetailDto) {
		return companyDetailsService.addNew(companyDetailDto);

	}

	/*
	 * Searching Driver Car Category based on search term
	 */
	@GetMapping(path = { "/driverSearch/{pageIndex}/{pageSize}",
			"/driverSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> driverSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return applicationService.driverSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}
	
	/*
	 * Searching Costomers  based on search term
	 */
	@GetMapping(path = { "/costomerSearch/{pageIndex}/{pageSize}",
			"/costomerSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> costomerSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return applicationService.customerSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}

	/*
	 * Searching Trip Category based on search term
	 */
	@GetMapping(path = { "/bookingSearch/{pageIndex}/{pageSize}",
			"/bookingSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> bookingSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return masterDataService.bookingTypeSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}

	/*
	 * Searching cost center based on search term
	 */
	@GetMapping(path = { "/costCenterSearch/{pageIndex}/{pageSize}",
			"/costCenterSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> costCenterSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return billingService.costCenterSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}
	/*
	 * Searching cost center based on search term
	 */

	@GetMapping(path = { "/costEmployIdSearch/{pageIndex}/{pageSize}",
			"/costEmployIdSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> costEmployIdSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return billingService.costEmployeIdSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}
	/*
	 * Searching travelId based on search term
	 */

	@GetMapping(path = { "/travelIdSearch/{pageIndex}/{pageSize}",
			"/travelIdSearch/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> travelIdSearch(@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex) {
		return billingService.costTravelIdSearch(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}

}
