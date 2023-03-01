package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.SysCarSubTypeEntity;

public interface CarBrandSubTypeRepository extends PagingAndSortingRepository<SysCarSubTypeEntity, Integer>{

	List<SysCarSubTypeEntity> findBynameStartsWithAndCartypeId(String name,Integer carType,Pageable pageRequest);

	List<SysCarSubTypeEntity> findAllByActive(boolean flag,Pageable pageRequest);
	
	List<SysCarSubTypeEntity> findAllByCartypeId(Integer carType,Pageable pageRequest);

}