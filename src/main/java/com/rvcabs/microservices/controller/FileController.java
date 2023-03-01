package com.rvcabs.microservices.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.Errors;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.rvcabs.microservices.dto.FileDto;
import com.rvcabs.microservices.dto.TravelerDetailDto;
import com.rvcabs.microservices.request.ApplicationUserRequest;
import com.rvcabs.microservices.service.ApplicationService;
import com.rvcabs.microservices.utilities.Utilities;

@RestController
@RequestMapping(path = "/file")
public class FileController {

	private static final Logger logger = LoggerFactory.getLogger(FileController.class);

	@Autowired
	ApplicationService applicationService;

	private String picFolderName = "RVCImage";

	@PostMapping(path = "/signup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> addNew(@Validated @RequestBody final ApplicationUserRequest requestBody, Errors e,
			final HttpServletRequest httpRequest) {
		if (e.hasErrors()) {
			Utilities.prepareBadRequestExceptionAndThrow(e);
		}
		return applicationService.addNew(requestBody);
	}

	@PostMapping(path = "/userSignup", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> userSignup(@RequestBody TravelerDetailDto trveDetailDto) {
		ApplicationUserRequest applicationUserRequest = new ApplicationUserRequest();
		applicationUserRequest.setMobileNumber(trveDetailDto.getMobileNumber());
		applicationUserRequest.setFirstName(trveDetailDto.getFirstName());
		applicationUserRequest.setLastName(trveDetailDto.getLastName());
		applicationUserRequest.setEmployID(trveDetailDto.getEmployeeId());
		applicationUserRequest.setUserType("CUSTOMER");
		applicationUserRequest.setSecMobileNumber(trveDetailDto.getSecMobileNumber());
		return applicationService.addNew(applicationUserRequest);
	}

	/*
	 * Uploading document byte array and returning path
	 */
	@PostMapping(path = "/upload", produces = MediaType.APPLICATION_JSON_VALUE, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<String>> upload(@RequestBody List<FileDto> fileDtos) {
		String fileName = System.getProperty("user.dir");
		List<String> documentURLS = new ArrayList<String>();
		for (FileDto fileDto : fileDtos) {
			String filePath = fileName + File.separator + picFolderName + File.separator + fileDto.getDescription();
			byte[] bytes = fileDto.getContent();
			File file = new File(filePath);
			if (!file.exists()) {
				file.mkdirs();
			}
			String line = null;
			OutputStream os = null;
			try {
				os = new FileOutputStream(
						file + File.separator + fileDto.getFileName() + "." + fileDto.getContentType());
				os.write(bytes);
				System.out.println("Write bytes to file.");
				/*
				 * BufferedReader br = new BufferedReader(new FileReader(file)); while ((line =
				 * br.readLine()) != null) { System.out.println(line); } br.close();
				 */
				os.close();
				/*
				 * documentURLS.add( documentURL + fileDto.getFileName() + File.separator + date
				 * + "-" + fileDto.getDescription());
				 */
				documentURLS.add(filePath + File.separator + fileDto.getFileName() + "." + fileDto.getContentType());
			} catch (Exception e) {
				logger.error("FileUpload");
			}
		}
		return new ResponseEntity<List<String>>(documentURLS, HttpStatus.OK);

	}

	@RequestMapping(value = "/getFile", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getFile(@RequestParam("path") String path) {
		// String fileName = System.getProperty("user.dir");
		InputStream is = null;
		try {
			File file = new File(path);
			is = new FileInputStream(file);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(is));
		} catch (Exception e) {
			logger.error("Unable to get the flag", e);
		}
		is = FileController.class.getResourceAsStream("/images/document.png");
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(is));

	}

	@RequestMapping(value = "/getImage", method = RequestMethod.GET)
	public ResponseEntity<InputStreamResource> getImage(@RequestParam("filepath") String filepath) {
		// String fileName = System.getProperty("user.dir");
		InputStream is = null;
		try {
			File file = new File(filepath);
			is = new FileInputStream(file);
			return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(is));
		} catch (Exception e) {
			logger.error("Unable to get the flag", e);
		}
		is = FileController.class.getResourceAsStream("/images/document.png");
		return ResponseEntity.ok().contentType(MediaType.IMAGE_PNG).body(new InputStreamResource(is));

	}
}
