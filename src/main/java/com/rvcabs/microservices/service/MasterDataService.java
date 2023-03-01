package com.rvcabs.microservices.service;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.MultiValueMap;

import com.rvcabs.microservices.constants.Constants;
import com.rvcabs.microservices.dto.CompanyDetailDto;
import com.rvcabs.microservices.dto.ContactPersonDto;
import com.rvcabs.microservices.dto.CustomerDto;
import com.rvcabs.microservices.dto.DocumentTypeDto;
import com.rvcabs.microservices.dto.MasterDataDto;
import com.rvcabs.microservices.entity.CompanyDetailsEntity;
import com.rvcabs.microservices.entity.ContactPersonEntity;
import com.rvcabs.microservices.entity.CustomerEntity;
import com.rvcabs.microservices.entity.SysBookingRequestTypeEntity;
import com.rvcabs.microservices.entity.SysCarCategoryEntity;
import com.rvcabs.microservices.entity.SysCarColorEntity;
import com.rvcabs.microservices.entity.SysCarIntColorEntity;
import com.rvcabs.microservices.entity.SysCarMasterEntity;
import com.rvcabs.microservices.entity.SysCarSubTypeEntity;
import com.rvcabs.microservices.entity.SysDocumentTypeEntity;
import com.rvcabs.microservices.repository.BookingRequestTypeRepository;
import com.rvcabs.microservices.repository.CarBrandRepository;
import com.rvcabs.microservices.repository.CarBrandSubTypeRepository;
import com.rvcabs.microservices.repository.CarCategoriesRepository;
import com.rvcabs.microservices.repository.CarColorRepository;
import com.rvcabs.microservices.repository.CarInteriorColorRepository;
import com.rvcabs.microservices.repository.CompanyDetailsRepository;
import com.rvcabs.microservices.repository.ContactPersonRepository;
import com.rvcabs.microservices.repository.CustomerRepository;
import com.rvcabs.microservices.repository.DocumentTypeRepository;
import com.rvcabs.microservices.utilities.GenericMapper;
import com.rvcabs.microservices.utilities.Utilities;

@Service
public class MasterDataService {

	private static Logger logger = LoggerFactory.getLogger(MasterDataService.class);

	@Autowired
	private DocumentTypeRepository documentTypeRepository;

	@Autowired
	private CustomerRepository customerRepository;

	@Autowired
	private CarBrandRepository carBrandRepository;

	@Autowired
	private CarBrandSubTypeRepository carBrandSubTypeRepository;

	@Autowired
	private CarInteriorColorRepository carInteriorColorRepository;

	@Autowired
	private CarCategoriesRepository carCategoriesRepository;

	@Autowired
	private CompanyDetailsRepository companyDetailsRepository;

	@Autowired
	private BookingRequestTypeRepository bookingRequestTypeRepository;

	@Autowired
	private CarColorRepository carColorRepository;

	@Autowired
	private ContactPersonRepository contactPersonRepository;

	private List<SysDocumentTypeEntity> documentTypeEntities;

	private List<CustomerEntity> customerEntities;

	private List<SysCarMasterEntity> sysCarMasterEntities;

	private List<SysCarSubTypeEntity> sysCarSubTypeEntities;

	private List<SysCarColorEntity> sysCarColorEntities;

	private List<SysCarIntColorEntity> sysCarIntColorEntities;

	private List<SysCarCategoryEntity> sysCarCategories;

	private List<CompanyDetailsEntity> companyDetailsEntities;

	private List<SysBookingRequestTypeEntity> sysBookingRequestTypeEntity;

	private MultiValueMap<String, String> headers = null;

	static ModelMapper modelMapper = new ModelMapper();

	static {
		modelMapper.getConfiguration().setAmbiguityIgnored(true);
	}

	public ResponseEntity<List<DocumentTypeDto>> documentSearch(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();
		Pageable page = PageRequest.of(pageIndex, pageSize);
		String[] keyValue;
		if (searchTerm == null || searchTerm.equals("")) {
			documentTypeEntities = (List<SysDocumentTypeEntity>) documentTypeRepository.findAllByActive(true, page);
		} else {
			documentTypeEntities = (List<SysDocumentTypeEntity>) documentTypeRepository
					.findBydocNameStartsWith(searchTerm, page);
		}
		Type tokenType = new TypeToken<List<DocumentTypeDto>>() {
		}.getType();
		List<DocumentTypeDto> list = modelMapper.map(documentTypeEntities, tokenType);
		if (list != null && list.isEmpty()) {

			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Document Found For SearchTerm");
		} else {

			for (DocumentTypeDto documentTypeDto : list) {
				keyValue = documentTypeDto.getKeyName().split(",");
				Map<String, String> map = new HashedMap();
				for (int i = 0; i < keyValue.length; i++) {
					String[] valueOfKey = keyValue[i].split(":");
					map.put(valueOfKey[0], valueOfKey.length > 1 ? valueOfKey[1] : "");
				}
				documentTypeDto.setDocumentField(map);
			}
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Document Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> partnerSearch(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();
		Pageable page = PageRequest.of(pageIndex, pageSize);

		if (searchTerm == null || searchTerm.equals("")) {
			customerEntities = (List<CustomerEntity>) customerRepository.findAllByActive(true, page);
		} else {
			customerEntities = (List<CustomerEntity>) customerRepository.findBynameStartsWith(searchTerm, page);
		}
		Type tokenType = new TypeToken<List<CustomerDto>>() {
		}.getType();
		List<CustomerDto> list = modelMapper.map(customerEntities, tokenType);
		if (list != null && list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Partner Found For SearchTerm");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Partner Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> carBrandSearch(int pageIndex, int pageSize, String searchTerm) {
		PageRequest page = PageRequest.of(pageIndex, pageSize);

		headers = Utilities.getDefaultHeader();
		if (searchTerm == null || searchTerm.equals("")) {
			sysCarMasterEntities = (List<SysCarMasterEntity>) carBrandRepository.findAllByActive(true, page);
		} else {
			sysCarMasterEntities = (List<SysCarMasterEntity>) carBrandRepository.findBynameStartsWith(searchTerm, page);
		}
		Type tokenType = new TypeToken<List<MasterDataDto>>() {
		}.getType();
		List<MasterDataDto> list = modelMapper.map(sysCarMasterEntities, tokenType);
		if (list != null && list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Car Brand  Found For SearchTerm");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Car Brand Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> carBrandSubTypeSearch(int pageIndex, int pageSize, String searchTerm, Integer carType) {
		headers = Utilities.getDefaultHeader();
		PageRequest page = PageRequest.of(pageIndex, pageSize);

		if (searchTerm == null || searchTerm.equals("") && (carType.equals(0) || carType.equals(1))) {
			sysCarSubTypeEntities = (List<SysCarSubTypeEntity>) carBrandSubTypeRepository.findAllByActive(true, page);
		} else if (searchTerm == null || searchTerm.equals("") && !carType.equals(0) && !carType.equals(1)) {
			sysCarSubTypeEntities = (List<SysCarSubTypeEntity>) carBrandSubTypeRepository.findAllByCartypeId(carType,
					page);
		} else {
			sysCarSubTypeEntities = (List<SysCarSubTypeEntity>) carBrandSubTypeRepository
					.findBynameStartsWithAndCartypeId(searchTerm, carType, page);
		}
		Type tokenType = new TypeToken<List<MasterDataDto>>() {
		}.getType();
		List<MasterDataDto> list = modelMapper.map(sysCarSubTypeEntities, tokenType);
		if (list == null || list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Car Brand SubType Found For SearchTerm");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Car Brand SubType Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> carColorSearch(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();
		PageRequest page = PageRequest.of(pageIndex, pageSize);
		if (searchTerm == null || searchTerm.equals("")) {
			sysCarColorEntities = (List<SysCarColorEntity>) carColorRepository.findAllByActive(true, page);
		} else {
			sysCarColorEntities = (List<SysCarColorEntity>) carColorRepository.findBynameStartsWith(searchTerm, page);
		}
		Type tokenType = new TypeToken<List<MasterDataDto>>() {
		}.getType();
		List<MasterDataDto> list = modelMapper.map(sysCarColorEntities, tokenType);
		if (list != null && list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Car Color  Found For SearchTerm");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Car Color Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> carIntColorSearch(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();
		PageRequest page = PageRequest.of(pageIndex, pageSize);

		if (searchTerm == null || searchTerm.equals("")) {
			sysCarIntColorEntities = (List<SysCarIntColorEntity>) carInteriorColorRepository.findAllByActive(true,
					page);
		} else {
			sysCarIntColorEntities = (List<SysCarIntColorEntity>) carInteriorColorRepository
					.findBynameStartsWith(searchTerm, page);
		}
		Type tokenType = new TypeToken<List<MasterDataDto>>() {
		}.getType();
		List<MasterDataDto> list = modelMapper.map(sysCarIntColorEntities, tokenType);
		if (list != null && list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Car Interior Color  Found For SearchTerm");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Car Interior Color Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> carCategorySearch(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();
		PageRequest page = PageRequest.of(pageIndex, pageSize);

		if (searchTerm == null || searchTerm.equals("")) {
			sysCarCategories = (List<SysCarCategoryEntity>) carCategoriesRepository.findAllByActive(true, page);
		} else {
			sysCarCategories = (List<SysCarCategoryEntity>) carCategoriesRepository.findBynameStartsWith(searchTerm,
					page);
		}
		Type tokenType = new TypeToken<List<MasterDataDto>>() {
		}.getType();
		List<MasterDataDto> list = modelMapper.map(sysCarCategories, tokenType);
		if (list != null && list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Car Category Found For SearchTerm");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Car Category Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<?> companyDetailsSearch(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();
		PageRequest page = PageRequest.of(pageIndex, pageSize);

		if (searchTerm == null || searchTerm.equals("")) {
			companyDetailsEntities = (List<CompanyDetailsEntity>) companyDetailsRepository.findAllByActiveTrue(page);
		} else {
			companyDetailsEntities = (List<CompanyDetailsEntity>) companyDetailsRepository
					.findByCompanyNameContainingOrGstnumberContaining(searchTerm, searchTerm, page);
		}
		Type tokenType = new TypeToken<List<CompanyDetailDto>>() {
		}.getType();
		List<CompanyDetailDto> list = modelMapper.map(companyDetailsEntities, tokenType);
		if (list != null && !list.isEmpty()) {
			for (CompanyDetailDto companyDetailDto : list) {
				List<ContactPersonEntity> contactPersonEntities = contactPersonRepository
						.findByCompanyId(companyDetailDto.getId());
				if (contactPersonEntities != null && !contactPersonEntities.isEmpty()) {
					List<ContactPersonDto> contactPersonDtos = new ArrayList<ContactPersonDto>();
					for (ContactPersonEntity contactPersonEntity : contactPersonEntities) {
						ContactPersonDto contactPersonDto = new ContactPersonDto();
						contactPersonDtos.add(GenericMapper.mapper.map(contactPersonEntity, ContactPersonDto.class));
					}
					companyDetailDto.setContactPersonDtoList(contactPersonDtos);
				}
			}
		}
		if (list != null && list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Car Category Found For SearchTerm");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Car Category Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}

	public ResponseEntity<List<MasterDataDto>> bookingTypeSearch(int pageIndex, int pageSize, String searchTerm) {
		headers = Utilities.getDefaultHeader();
		PageRequest page = PageRequest.of(pageIndex, pageSize);
		if (searchTerm == null || searchTerm.equals("")) {
			sysBookingRequestTypeEntity = (List<SysBookingRequestTypeEntity>) bookingRequestTypeRepository
					.findAllByActiveTrue(page);
		} else {
			sysBookingRequestTypeEntity = (List<SysBookingRequestTypeEntity>) bookingRequestTypeRepository
					.findByNameContaining(searchTerm, page);
		}
		Type tokenType = new TypeToken<List<MasterDataDto>>() {
		}.getType();
		List<MasterDataDto> list = modelMapper.map(sysBookingRequestTypeEntity, tokenType);
		if (list != null && list.isEmpty()) {
			headers.add(Constants.STATUS, HttpStatus.NO_CONTENT.toString());
			headers.add(Constants.MESSAGE, "No Document Found For SearchTerm");
		} else {
			headers.add(Constants.STATUS, HttpStatus.OK.toString());
			headers.add(Constants.MESSAGE, "Document Found Successfully For SearchTerm");
		}
		return new ResponseEntity<>(list, headers, HttpStatus.OK);
	}
}
