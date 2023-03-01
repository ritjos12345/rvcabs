package com.rvcabs.microservices.security;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.TripRatesEntity;

public interface TripRatesRepository extends PagingAndSortingRepository<TripRatesEntity, Integer> {

//	public List<TripRatesEntity> findAllByCartypeAndTriptype();

}
