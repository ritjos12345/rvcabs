package com.rvcabs.microservices.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.TripFilesEntity;

public interface TripFilesRepository extends PagingAndSortingRepository<TripFilesEntity, Integer> {

}
