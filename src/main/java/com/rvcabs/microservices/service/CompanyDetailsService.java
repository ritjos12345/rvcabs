package com.rvcabs.microservices.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.rvcabs.microservices.constants.Constants;
import com.rvcabs.microservices.dto.CompanyDetailDto;
import com.rvcabs.microservices.dto.ContactPersonDto;
import com.rvcabs.microservices.dto.CustomerDto;
import com.rvcabs.microservices.dto.StatusDto;
import com.rvcabs.microservices.entity.CompanyDetailsEntity;
import com.rvcabs.microservices.entity.ContactPersonEntity;
import com.rvcabs.microservices.entity.CustomerEntity;
import com.rvcabs.microservices.repository.CompanyDetailsRepository;
import com.rvcabs.microservices.repository.ContactPersonRepository;
import com.rvcabs.microservices.repository.CustomerRepository;
import com.rvcabs.microservices.utilities.GenericMapper;
import com.rvcabs.microservices.utilities.Utilities;

@Service
@Transactional
public class CompanyDetailsService {

	static ModelMapper modelMapper = new ModelMapper();

	private MultiValueMap<String, String> headers = null;

	@Autowired
	private CompanyDetailsRepository companyDetailsRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private ContactPersonRepository contactPersonRepository;

	private CompanyDetailsEntity companyDetailsEntity;

	private CustomerEntity customerEntity;

	private StatusDto statusDto;

	public ResponseEntity<?> addNew(CompanyDetailDto request) {
		Boolean status = false;
		statusDto = new StatusDto();
		headers = Utilities.getDefaultHeader();
		List<ContactPersonEntity> contactPersonEntities = new ArrayList<ContactPersonEntity>();
		try {
			companyDetailsEntity = modelMapper.map(request, CompanyDetailsEntity.class);
			companyDetailsEntity = companyDetailsRepository.save(companyDetailsEntity);
			if (companyDetailsEntity.getId() != null) {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Company Details Created Successfully");
				statusDto.setStatus(companyDetailsEntity.getId());
			} else {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Company Details Not Created ");
			}
			if (request.getContactPersonDtoList() != null && !request.getContactPersonDtoList().isEmpty()
					&& companyDetailsEntity.getId() != null) {
				for (ContactPersonDto contactPersonDto : request.getContactPersonDtoList()) {
					contactPersonDto.setCompanyId(companyDetailsEntity.getId());
					contactPersonEntities.add(GenericMapper.mapper.map(contactPersonDto, ContactPersonEntity.class));
				}
				contactPersonRepository.saveAll(contactPersonEntities);
			}

		} catch (Exception exception) {
			exception.printStackTrace();
		}
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> addCustomer(CustomerDto request) {
		Boolean status = false;
		statusDto = new StatusDto();
		headers = Utilities.getDefaultHeader();

		try {
			customerEntity = modelMapper.map(request, CustomerEntity.class);
			customerEntity = customerRepository.save(customerEntity);
			if (customerEntity.getId() != null) {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Customer Details Created Successfully");
				statusDto.setStatus(customerEntity.getId());
			} else {
				headers.add(Constants.STATUS, HttpStatus.OK.toString());
				headers.add(Constants.MESSAGE, "Customer Details Not Created ");
			}
		} catch (Exception exception) {
		}
		return new ResponseEntity<>(statusDto, headers, HttpStatus.OK);
	}

}
