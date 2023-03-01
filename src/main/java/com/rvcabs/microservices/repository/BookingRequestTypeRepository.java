package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.SysBookingRequestTypeEntity;

public interface BookingRequestTypeRepository extends PagingAndSortingRepository<SysBookingRequestTypeEntity, Integer> {

	List<SysBookingRequestTypeEntity> findAllByActiveTrue(Pageable pageable);

	List<SysBookingRequestTypeEntity> findByNameContaining(String companyName, Pageable pageable);

}
