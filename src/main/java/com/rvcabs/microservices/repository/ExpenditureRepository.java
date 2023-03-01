package com.rvcabs.microservices.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rvcabs.microservices.entity.ExpenditureEntity;

public interface ExpenditureRepository extends PagingAndSortingRepository<ExpenditureEntity, String> {

	@Query(value = "SELECT e FROM ExpenditureEntity e where   month(sysdate())=month(e.entryDate) and e.status like %:status% and e.driverAccountId like %:driverAccountId% or e.vehicleNumber like %:vechicalNum% ")
	List<ExpenditureEntity> findVehicleNumberContainingOrDriverAccountIdContainingAndStatusContaining(
			@Param("vechicalNum") String vechicalNum, @Param("driverAccountId") String driverAccountId,
			@Param("status") String status, Pageable pageRequest);

	// @Query(value = "SELECT e FROM ExpenditureEntity e where e.status like
	// %:status% and (e.driverAccountId like %:driverAccountId% ) and
	// Date(e.entryDate) between ':startDate' and ':endDate'")
	List<ExpenditureEntity> findAllByVehicleNumberContainingOrDriverAccountIdContainingAndStatusContainingAndEntryDateBetween(
			String vehicleNumber, String driverAccountId, String status,Date startDate,Date endDate, Pageable pageRequest);

	@Modifying
	@Query("update  ExpenditureEntity set status=:status where id=:id")
	public Integer updateStatus(@Param("id") String id, @Param("status") String status);

	@Query(value = "SELECT appuser.accountId,concat(appuser.firstName,' ',appuser.lastName),car.regNumber FROM applicationuser appuser,cardetail car where appuser.accountId=car.accountId", nativeQuery = true)
	List<Object[]> getDriverDetails(Pageable pageabl);

	@Query(value = "SELECT  SUM(trip.tripTotalDistance) FROM bookingdetail book, tripdetail trip WHERE trip.bookingId = book.id AND MONTH(book.pickUpDate) = MONTH(SYSDATE()) AND book.driverAccountId = :driverAccountId", nativeQuery = true)
	Object getTotalDistance(@Param("driverAccountId") String driverAccountId);

	@Query(value = "SELECT  SUM(trip.tripTotalDistance) FROM bookingdetail book, tripdetail trip WHERE trip.bookingId = book.id AND Date(book.pickUpDate) between :startDate AND :endDate\n"
			+ " AND book.driverAccountId = :driverAccountId", nativeQuery = true)
	Object getTotalDistanceByDate(@Param("driverAccountId") String driverAccountId,
			@Param("startDate") String startDate, @Param("endDate") String endDate);

	@Query(value = "select sum(totalLt),sum(totalAmount) from expenditure where MONTH(entryDate) = MONTH(SYSDATE()) and driverAccountId=:driverAccountId and status like '%Approved%'", nativeQuery = true)
	String getTotalExp(@Param("driverAccountId") String driverAccountId);

	@Query(value = "select sum(totalLt),sum(totalAmount) from expenditure where Date(entryDate) between :startDate AND :endDate and driverAccountId=:driverAccountId and status like '%Approved%'", nativeQuery = true)
	String getTotalExpByDate(@Param("driverAccountId") String driverAccountId, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

	// @Query(value = "select * from expenditure where MONTH(entryDate) =
	// MONTH(SYSDATE()) and status like '%':status'%'", nativeQuery = true)
	List<ExpenditureEntity> findAllByStatusContaining(String status, Pageable pageRequest);

	@Query(value = "select sum(totalAmount) from expenditure where MONTH(entryDate) = MONTH(SYSDATE()) and expenceType!='Fuel' and driverAccountId=:driverAccountId and status like '%Approved%'", nativeQuery = true)
	Object getTotalNonFuel(@Param("driverAccountId") String driverAccountId);

	@Query(value = "select sum(totalAmount) from expenditure where Date(entryDate) between :startDate AND :endDate and expenceType!='Fuel' and driverAccountId=:driverAccountId and status like '%Approved%'", nativeQuery = true)
	Object getTotalNonFuelByDate(@Param("driverAccountId") String driverAccountId, @Param("startDate") String startDate,
			@Param("endDate") String endDate);

	@Query(value = "select concat(user.firstName,' ',user.lastName) from applicationuser user where user.accountId=:accountId", nativeQuery = true)
	public String findByDriverAccountId(@Param("accountId") String accountId);

}
