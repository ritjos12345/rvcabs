package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.SysCarCategoryEntity;

public interface CarCategoriesRepository extends PagingAndSortingRepository<SysCarCategoryEntity, Integer> {

	List<SysCarCategoryEntity> findBynameStartsWith(String name, Pageable pageRequest);

	List<SysCarCategoryEntity> findAllByActive(boolean flag, Pageable pageRequest);

	List<SysCarCategoryEntity> findAllByActive(boolean flag);

}
