package com.rvcabs.microservices.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.SysCarMasterEntity;

public interface CarBrandRepository extends PagingAndSortingRepository<SysCarMasterEntity, Integer> {

	List<SysCarMasterEntity> findBynameStartsWith(String carBrandName,Pageable pageRequest);

	List<SysCarMasterEntity> findAllByActive(boolean flag,Pageable pageRequest);
	
	Optional<SysCarMasterEntity> findById(Integer id);
}
