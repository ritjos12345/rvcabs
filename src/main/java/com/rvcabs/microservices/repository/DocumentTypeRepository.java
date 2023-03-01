package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.SysDocumentTypeEntity;

public interface DocumentTypeRepository extends PagingAndSortingRepository<SysDocumentTypeEntity, Integer> {

	
	List<SysDocumentTypeEntity>  findBydocNameStartsWith(String docName,Pageable pageRequest);


	List<SysDocumentTypeEntity> findAllByActive(boolean flag,Pageable pageRequest);

}
