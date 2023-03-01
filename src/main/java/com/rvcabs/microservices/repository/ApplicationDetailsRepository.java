package com.rvcabs.microservices.repository;

import java.util.List;
import java.util.stream.Stream;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rvcabs.microservices.entity.ApplicationDetails;

public interface ApplicationDetailsRepository extends JpaRepository<ApplicationDetails, String> {

	@Transactional(readOnly = true)
	@Query("select e from ApplicationDetails e")
	Stream<ApplicationDetails> streamAll();

	@Transactional(readOnly = true)
	boolean existsByApplicationName(String name);
	
	@Query("select e from ApplicationDetails e where e.applicationName=:applicationName")
	ApplicationDetails findByApplicationName(@Param("applicationName") String applicationName);

	@Query("select e from ApplicationDetails e where e.accountId=:accountId")
	List<ApplicationDetails> findAllApplicationsByAccountId(@Param("accountId") String accountId);
}
