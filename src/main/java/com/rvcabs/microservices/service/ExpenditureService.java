package com.rvcabs.microservices.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.rvcabs.microservices.constants.Constants;
import com.rvcabs.microservices.dto.ExpenditureDto;
import com.rvcabs.microservices.dto.StatusDto;
import com.rvcabs.microservices.entity.ExpenditureEntity;
import com.rvcabs.microservices.repository.ExpenditureRepository;
import com.rvcabs.microservices.utilities.GenericMapper;
import com.rvcabs.microservices.utilities.Utilities;

@Transactional
@Service
public class ExpenditureService {

	@Autowired
	private ExpenditureRepository expenditureRepository;

	static ModelMapper modelMapper = new ModelMapper();

	private MultiValueMap<String, String> headers = Utilities.getDefaultHeader();

	private String driverAccountId;

	private StatusDto statusDto;

	public ResponseEntity<?> expList(int pageIndex, int pageSize, Date startDate, Date endDate) {
		headers = Utilities.getDefaultHeader();
		Pageable page = PageRequest.of(pageIndex, pageSize);
		Map<String, String> keyValueMap = null;
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		float avg = 0;
		List<Object[]> driverDetails = (List<Object[]>) expenditureRepository.getDriverDetails(page);

		if (driverDetails == null && driverDetails.isEmpty()) {

			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No record Found ");
		} else {
			for (Object[] object : driverDetails) {
				keyValueMap = new HashMap<String, String>();
				keyValueMap.put("driverAccountId", (String) object[0]);
				keyValueMap.put("driverName", (String) object[1]);
				keyValueMap.put("carRegNo", (String) object[2] == null ? "0" : (String) object[2]);
				if (startDate == null && endDate == null) {
					Calendar calendar = Calendar.getInstance();
					calendar.set(Calendar.DATE, calendar.getActualMinimum(Calendar.DATE));
					keyValueMap.put("startDate", calendar.getTime().toString());
					calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DATE));
					keyValueMap.put("endDate", calendar.getTime().toString());
					keyValueMap.put("totalDist",
							expenditureRepository.getTotalDistance((String) object[0]) == null ? "0"
									: expenditureRepository.getTotalDistance((String) object[0]).toString());
					String expArray = expenditureRepository.getTotalExp((String) object[0]);
					keyValueMap.put("totalLt", expArray.split(",")[0].equals("null") ? "0" : expArray.split(",")[0]);
					keyValueMap.put("totalAmt", expArray.split(",")[1].equals("null") ? "0" : expArray.split(",")[1]);
					keyValueMap.put("otherExpTotal",
							expenditureRepository.getTotalNonFuel((String) object[0]) == null ? "0"
									: expenditureRepository.getTotalNonFuel((String) object[0]).toString());
				} else {
					SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
					keyValueMap.put("startDate", startDate.toString());
					keyValueMap.put("endDate", endDate.toString());
					keyValueMap
							.put("totalDist",
									expenditureRepository.getTotalDistanceByDate((String) object[0],
											dateFormat.format(startDate), dateFormat.format(endDate)) == null
													? "0"
													: expenditureRepository.getTotalDistanceByDate((String) object[0],
															dateFormat.format(startDate), dateFormat.format(endDate))
															.toString());
					String expArray = expenditureRepository.getTotalExpByDate((String) object[0],
							dateFormat.format(startDate), dateFormat.format(endDate));
					keyValueMap.put("totalLt", expArray.split(",")[0].equals("null") ? "0" : expArray.split(",")[0]);
					keyValueMap.put("totalAmt", expArray.split(",")[1].equals("null") ? "0" : expArray.split(",")[1]);
					keyValueMap
							.put("otherExpTotal",
									expenditureRepository.getTotalNonFuelByDate((String) object[0],
											dateFormat.format(startDate), dateFormat.format(endDate)) == null
													? "0"
													: expenditureRepository.getTotalNonFuelByDate((String) object[0],
															dateFormat.format(startDate), dateFormat.format(endDate))
															.toString());

				}
				try {
					avg = Float.valueOf(keyValueMap.get("totalDist")) / Float.valueOf(keyValueMap.get("totalLt"));
				} catch (Exception e) {
				}
				keyValueMap.put("avg", String.valueOf(avg));

				list.add(keyValueMap);
			}

			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Expense List");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> allExpList(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();
		Pageable page = PageRequest.of(pageIndex, pageSize);
		List<ExpenditureDto> dtos = null;
		Map<String, String> keyValueMap = new HashMap<String, String>();
		List<ExpenditureEntity> expList = (List<ExpenditureEntity>) expenditureRepository
				.findAllByStatusContaining(searchTerm, page);

		if (expList == null && expList.isEmpty()) {

			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No record Found ");
		} else {
			Iterable<ExpenditureEntity> iterable = expList;
			dtos = GenericMapper.mapper.mapAsList(iterable, ExpenditureDto.class);
			for (ExpenditureDto expenditureDto : dtos) {
				expenditureDto.setDriverName(
						expenditureRepository.findByDriverAccountId(expenditureDto.getDriverAccountId()));
			}
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Expense List");
		}
		return new ResponseEntity<>(dtos, headers, HttpStatus.OK);
	}

	public ResponseEntity<List<ExpenditureDto>> searchByDriver(int pageIndex, int pageSize, String searchTerm,
			Date startDate, Date endDate) {
		headers = Utilities.getDefaultHeader();
		Pageable page = PageRequest.of(pageIndex, pageSize);
		String[] keyValue;
		List<ExpenditureDto> dtos = null;
		List<ExpenditureEntity> expenditureEntities = null;
		if (startDate == null && endDate == null) {
			expenditureEntities = (List<ExpenditureEntity>) expenditureRepository
					.findVehicleNumberContainingOrDriverAccountIdContainingAndStatusContaining(searchTerm, searchTerm,
							"Approved", page);
		} else {
			SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

			expenditureEntities = (List<ExpenditureEntity>) expenditureRepository
					.findAllByVehicleNumberContainingOrDriverAccountIdContainingAndStatusContainingAndEntryDateBetween(
							searchTerm,searchTerm, "Approved",startDate,endDate, page);
		}
		if (expenditureEntities == null && expenditureEntities.isEmpty()) {

			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No record found");
		} else {
			Iterable<ExpenditureEntity> iterable = expenditureEntities;
			dtos = GenericMapper.mapper.mapAsList(iterable, ExpenditureDto.class);

			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Expence Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(dtos, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> updateStatus(String id, String status) {
		headers = Utilities.getDefaultHeader();
		int update = 0;
		update = expenditureRepository.updateStatus(id, status);
		headers.add(Constants.STATUS, HttpStatus.OK.toString());
		headers.add(Constants.MESSAGE, update == 1 ? "Ststus Updated successfully" : "Status not updated");
		statusDto = new StatusDto();
		statusDto.setStatus(update == 1 ? "true" : "false");
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);

	}

	public ResponseEntity<?> create(ExpenditureDto expenditureDto) {
		headers = Utilities.getDefaultHeader();
		statusDto = new StatusDto();
		ExpenditureEntity expenditureEntity = null;
		try {
			expenditureEntity = GenericMapper.mapper.map(expenditureDto, ExpenditureEntity.class);
			expenditureEntity = expenditureRepository.save(expenditureEntity);
			if (expenditureEntity.getId() != null) {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Expenditure Request Generated Successfully");
				statusDto.setStatus("true");
			} else {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Expenditure Request Created Successfully");
				statusDto.setStatus("false");
			}
		} catch (Exception exception) {
			exception.getStackTrace();
		}

		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
	}

}
