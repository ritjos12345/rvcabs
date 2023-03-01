package com.rvcabs.microservices.controller;

import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvcabs.microservices.dto.ExpenditureDto;
import com.rvcabs.microservices.service.ExpenditureService;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping(path = "/expenditure")
public class ExpenditureController {

	@Autowired
	private ExpenditureService expenditureService;

	@PostMapping(value = "create", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> create(@RequestBody ExpenditureDto expenditureDto) {
		return expenditureService.create(expenditureDto);
	}

	/*
	 * Searching Driver Document Type based on search term
	 * 
	 * @GetMapping(path = { "/searchByDriver/{pageIndex}/{pageSize}",
	 * "/searchByDriver/{pageIndex}/{pageSize}/{searchTerm}",
	 * "/searchByDriver/{pageIndex}/{pageSize}/{searchTerm}/{startDate}" }, produces
	 * = MediaType.APPLICATION_JSON_VALUE, consumes =
	 * MediaType.APPLICATION_JSON_VALUE) public ResponseEntity<?>
	 * searchByDriver(@PathVariable("searchTerm") Optional<String> searchTerm,
	 * 
	 * @PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int
	 * pageIndex,
	 * 
	 * @PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd")
	 * Optional<Date> startDate) { return
	 * expenditureService.searchByDriver(pageIndex, pageSize, searchTerm.isPresent()
	 * ? searchTerm.get() : "", startDate.isPresent() ? startDate.get() : null, new
	 * Date()); }
	 */

	/*
	 * Searching Driver Document Type based on search term
	 */
	@GetMapping(path = { "/searchByDriver/{pageIndex}/{pageSize}/{searchTerm}",
			"/searchByDriver/{pageIndex}/{pageSize}/{searchTerm}/{startDate}/{endDate}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> searchByDriver(@PathVariable("pageSize") int pageSize,
			@PathVariable("pageIndex") int pageIndex,@PathVariable("searchTerm") Optional<String> searchTerm,
			@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> startDate,
			@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> endDate) {
		return expenditureService.searchByDriver(pageIndex, pageSize,searchTerm.get(), startDate.isPresent() ? startDate.get() : null,
				endDate.isPresent() ? endDate.get() : null);
	}

	/*
	 * Searching Driver Document Type based on search term
	 */
	@GetMapping(path = { "/expList/{pageIndex}/{pageSize}",
			"/expList/{pageIndex}/{pageSize}/{startDate}/{endDate}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> expList(@PathVariable("pageSize") int pageSize, @PathVariable("pageIndex") int pageIndex,
			@PathVariable("startDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> startDate,
			@PathVariable("endDate") @DateTimeFormat(pattern = "yyyy-MM-dd") Optional<Date> endDate) {
		return expenditureService.expList(pageIndex, pageSize, startDate.isPresent() ? startDate.get() : null,
				endDate.isPresent() ? endDate.get() : null);
	}

	/*
	 * Updating Driver Exp Status Type based on search term
	 */
	@GetMapping(path = "/updateStatus/{id}/{status}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateStatus(@PathVariable("id") String id, @PathVariable("status") String status) {
		return expenditureService.updateStatus(id, status);
	}

	/*
	 * Searching Driver Document Type based on search term
	 */
	@GetMapping(path = { "/allExpList/{pageIndex}/{pageSize}",
			"/allExpList/{pageIndex}/{pageSize}/{searchTerm}" }, produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> allExpList(@PathVariable("pageSize") int pageSize,
			@PathVariable("pageIndex") int pageIndex, @PathVariable("searchTerm") Optional<String> searchTerm) {
		return expenditureService.allExpList(pageIndex, pageSize, searchTerm.isPresent() ? searchTerm.get() : "");
	}
}
