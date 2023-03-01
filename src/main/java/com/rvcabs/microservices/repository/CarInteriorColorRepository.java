package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.SysCarIntColorEntity;

public interface CarInteriorColorRepository extends PagingAndSortingRepository<SysCarIntColorEntity, Integer> {

	List<SysCarIntColorEntity> findBynameStartsWith(String name, Pageable pageRequest);

	List<SysCarIntColorEntity> findAllByActive(boolean flag, Pageable pageRequest);
}
