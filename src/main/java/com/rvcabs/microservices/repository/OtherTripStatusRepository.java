package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.OtherTripStatusEntity;

public interface OtherTripStatusRepository extends PagingAndSortingRepository<OtherTripStatusEntity, String>{
	public List<OtherTripStatusEntity> findAllByAccountId(String accountId);
}
