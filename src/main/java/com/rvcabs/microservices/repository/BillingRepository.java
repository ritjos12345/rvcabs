package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rvcabs.microservices.entity.BillingEntity;
import com.rvcabs.microservices.entity.BookingDetailEntity;

public interface BillingRepository extends JpaRepository<BillingEntity, String> {
	// Pageable sortedBydutySlipDateDesc = PageRequest.of(0, 10,
	// Sort.by("dutySlipDate").descending());

	Page<BillingEntity> findAll(Pageable pageable);

	Page<BillingEntity> findAllByTypeOfDuty(String typeOfDuty, Pageable pageable);

	Page<BillingEntity> findAllByVehicleSeg(String vehicleSeg, Pageable pageable);

	Page<BillingEntity> findAllByNameOfCompany(String nameOfCompany, Pageable pageable);

	Page<BillingEntity> findAllByTypeOfDutyAndVehicleSeg(String typeOfDuty, String vehicleSeg, Pageable pageable);

	Page<BillingEntity> findAllByTypeOfDutyAndNameOfCompany(String typeOfDuty, String nameOfCompany, Pageable pageable);

	Page<BillingEntity> findAllByVehicleSegAndNameOfCompany(String vehicleSeg, String nameOfCompany, Pageable pageable);

	Page<BillingEntity> findAllByVehicleSegAndNameOfCompanyAndTypeOfDuty(String vehicleSeg, String nameOfCompany,
			String typeOfDuty, Pageable pageable);

	@Query(value = "SELECT * FROM bookingdetail e where e.tripStatus in(1,2) and e.updatedOn  between  DATE_SUB(current_timestamp(), INTERVAL 20 MINUTE) and DATE_SUB(current_timestamp(), INTERVAL 15 MINUTE)", nativeQuery = true)
	public List<BookingDetailEntity> findBillingDetailsById();

	@Query(value = "SELECT * FROM travelbill e where e.id in :ids ", nativeQuery = true)
	public List<BillingEntity> findBillingDetailsByIds(@Param("ids") List<String> ids);
	
	@Query(value = "select costCentre from BillingEntity where costCentre like %:costCentre% group by costCentre")
	public List<String> findCostCenter(@Param("costCentre") String costCentre,Pageable page);
	
	@Query(value = "select travelId from BillingEntity where travelId like %:travelId% group by travelId")
	public List<String> findAllByTravelIdContaining(@Param("travelId") String travelId,Pageable page);
	
	@Query(value = "select empNo from BillingEntity where empNo like %:empNo% group by empNo")
	public List<String> findAllByEmpNoContaining(@Param("empNo") String empNo,Pageable page);

}
