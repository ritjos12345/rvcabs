package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.SysCarColorEntity;

public interface CarColorRepository extends PagingAndSortingRepository<SysCarColorEntity, Integer> {

	List<SysCarColorEntity> findBynameStartsWith(String name,Pageable pageRequest);

	List<SysCarColorEntity> findAllByActive(boolean flag,Pageable pageRequest);
}
