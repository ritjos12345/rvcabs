package com.rvcabs.microservices.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.rvcabs.microservices.dto.DropDto;
import com.rvcabs.microservices.dto.TripAddInfoDto;
import com.rvcabs.microservices.service.TripDetailService;

import io.swagger.annotations.Api;

@Api
@RestController
@RequestMapping(path = "/trip")
public class TripDetailController {

	@Autowired
	private TripDetailService tripDetailService;

	@Autowired
	private FileController fileController;

	@GetMapping(value = "updateDriverLocation/{accountId}/{srcLat}/{srcLng}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateDriverLocation(@PathVariable String accountId, @PathVariable String srcLat,
			@PathVariable String srcLng, @PathVariable String srcName) {
		return tripDetailService.updateDriverLocation(accountId, srcLat, srcLng);

	}
	
	@GetMapping(value = "getFeedback/{id}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getFeedback(@PathVariable String id) {
		return tripDetailService.getFeedBackDetails(id);

	}

	@PostMapping(value = "tripStart", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> tripStart(@RequestBody TripAddInfoDto addInfoDto) {
		return tripDetailService.tripStart(addInfoDto);
	}

	@GetMapping(value = "pickUpMember/{tripId}/{srcLat}/{srcLng}/{srcName:.+}/{distance}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> pickUpMember(@PathVariable String tripId, @PathVariable String srcLat,
			@PathVariable String srcLng, @PathVariable String srcName, @PathVariable String distance) {
		return tripDetailService.pickUpMember(tripId, srcLat, srcLng, srcName, distance);
	}

	@GetMapping(value = "getTripCoordinates/{tripId}", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> getTripCoordinates(@PathVariable String tripId) {
		return tripDetailService.getCoordinates(tripId);
	}
	
	@RequestMapping(value = "getSignature/{tripId:.+}", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getSignature(@PathVariable String tripId) {
		 String filepath = tripDetailService.getSignature(tripId);
		InputStream is = null;
		try {
			File file = new File(filepath);
			is = new FileInputStream(file);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(is));
		} catch (Exception e) {
		}
		is = FileController.class.getResourceAsStream("/images/document.png");
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(is));

	}

	@PostMapping(value = "updateTollParking", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateTollParking(@RequestBody TripAddInfoDto addInfoDto) {
		return tripDetailService.updateTollParking(addInfoDto);
	}

	@PostMapping(value = "dropMember", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> dropMember(@RequestBody DropDto dropDto) {
		String fileName = System.getProperty("user.dir");
		String filePath = fileName + File.separator + "RVCImage" + File.separator + "EndTripSignature";
		try {
			byte[] bytes = dropDto.getContent();
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			} 
			String line = null;
			OutputStream os = null;

			os = new FileOutputStream(file + File.separator + dropDto.getTripId());
			os.write(bytes);
			os.close();
			dropDto.setSigURl(filePath + File.separator + dropDto.getTripId());
		} catch (Exception e) {
			// TODO: handle exception
		}

		return tripDetailService.dropMember(dropDto);
	}

	@PostMapping(value = "endTrip", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> endTrip(@RequestBody TripAddInfoDto addInfoDto) {
		return tripDetailService.endTrip(addInfoDto);
	}

}
