package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rvcabs.microservices.entity.CompanyDetailsEntity;

public interface CompanyDetailsRepository extends PagingAndSortingRepository<CompanyDetailsEntity, String> {
	
	List<CompanyDetailsEntity> findAllByActiveTrue(Pageable pageRequest);

	List<CompanyDetailsEntity> findByCompanyNameContainingOrGstnumberContaining(String companyName,String gstNumber,Pageable pageRequest);

	@Query(value = "select e from CompanyDetailsEntity e where e.id=:id")
	public CompanyDetailsEntity findByCompanyId(@Param("id") String id);
	
	@Query(value = "select e from CompanyDetailsEntity e where e.companyName=:companyName")
	public CompanyDetailsEntity findByCompanyName(@Param("companyName") String companyName);
 
}
