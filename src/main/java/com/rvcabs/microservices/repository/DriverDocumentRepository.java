package com.rvcabs.microservices.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rvcabs.microservices.entity.DocumentDetailEntity;;

public interface DriverDocumentRepository extends PagingAndSortingRepository<DocumentDetailEntity, String> {
	@Query(value="SELECT e FROM documentdetail e where e.accountId=:accountId and e.documentTypeId=:documentId",nativeQuery=true)
	public DocumentDetailEntity findByAccountIdAndDocumentTypeId(@Param("accountId")String accountId, @Param("documentId")String documentId);
}
