package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.ContactPersonEntity;

public interface ContactPersonRepository extends PagingAndSortingRepository<ContactPersonEntity, String> {

	List<ContactPersonEntity> findByCompanyId(String companyId);
	
	
}
