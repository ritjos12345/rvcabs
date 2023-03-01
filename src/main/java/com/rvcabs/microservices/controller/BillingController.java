package com.rvcabs.microservices.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.rvcabs.microservices.dto.FillterDto;
import com.rvcabs.microservices.service.BillingService;

@RestController
@RequestMapping(path = "/bill")
public class BillingController {

	@Autowired
	private BillingService billingService;

	@PostMapping(value = "/retriveData/{pageIndex}/{pageSize}")
	public ResponseEntity<?> retriveData(@PathVariable int pageIndex, @PathVariable int pageSize,
			@RequestBody FillterDto fillterDto) {
		return billingService.billList(pageIndex, pageSize, fillterDto);
	}

	@PostMapping(value = "/report")
	public ResponseEntity<?> report(@RequestBody FillterDto fillterDto) {
		return billingService.report(fillterDto);
	}

	@PostMapping(value = "/generateInvoice")
	public ResponseEntity<?> generateInvoice(@RequestBody List<String> billingList) {
		return billingService.generateInvoice(billingList);
	}

	@GetMapping(value = "/generatee")
	public ResponseEntity<?> generatee() {
		billingService.fetchingRateFromDB();
		billingService.createFinalBill();
		return null;
	}

	@GetMapping(value = "/adjustInvoice/{invoiceId}/{adjKm}/{adjustHr}")
	public ResponseEntity<?> adjustInvoice(@PathVariable String invoiceId, @PathVariable int adjKm,
			@PathVariable int adjustHr) {
		return billingService.adjustInvoice(invoiceId, adjKm, adjustHr);

	}
}
