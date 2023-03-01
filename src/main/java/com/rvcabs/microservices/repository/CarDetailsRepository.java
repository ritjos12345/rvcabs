package com.rvcabs.microservices.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.CarDetailsEntity;

public interface CarDetailsRepository extends PagingAndSortingRepository<CarDetailsEntity, String> {

	public CarDetailsEntity findByAccountId(String accountId);
}
