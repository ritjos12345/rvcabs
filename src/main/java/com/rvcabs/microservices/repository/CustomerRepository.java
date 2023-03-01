package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.CustomerEntity;

public interface CustomerRepository extends PagingAndSortingRepository<CustomerEntity, Integer> {
	
	List<CustomerEntity>  findBynameStartsWith(String customerName,Pageable pageRequest);

	List<CustomerEntity> findAllByActive(boolean flag,Pageable pageRequest);
}
