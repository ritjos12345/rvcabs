package com.rvcabs.microservices.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.InvoiceEntity;

public interface InvoiceRepository extends PagingAndSortingRepository<InvoiceEntity, String> {



}
