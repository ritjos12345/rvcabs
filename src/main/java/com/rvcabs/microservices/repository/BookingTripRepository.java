package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.rvcabs.microservices.entity.BookingDetailEntity;

public interface BookingTripRepository extends JpaRepository<BookingDetailEntity, String> {

	@Modifying
	@Query("update BookingDetailEntity user set user.tripStatus = :tripStatus,updatedOn=sysdate()  where user.id=:bookingId")
	public Integer updateTripStatus(@Param("tripStatus") Integer tripStatus, @Param("bookingId") String bookingId);

	@Query(value = "select e.driverAccountId from BookingDetailEntity e where e.id=:bookingId")
	public String findDriverAccountIdById(@Param("bookingId") String bookingId);

	@Modifying
	@Query("update BookingDetailEntity user set user.tripStatus = :tripStatus,user.driverAccountId=:driverId,updatedOn=sysdate()   where user.id=:bookingId")
	public Integer updateTripAndDriverStatus(@Param("tripStatus") Integer tripStatus,
			@Param("bookingId") String bookingId, @Param("driverId") String driverId);

	@Query(value = "select  concat(pickUpDate, ' ', pickUpTime) as timestampDemo from BookingDetailEntity where id=:bookingId")
	public String findPickUpTimeByBookingId(@Param("bookingId") String bookingId);

	@Query(value = "select  appuser.mobileNumber from BookingDetailEntity book,ApplicationUserEntity appuser  where book.id=:bookingId and appuser.accountId=book.accountId")
	public String findMobileNumOfUser(@Param("bookingId") String bookingId);

	@Query(value = "select concat(appuser.firstName, ' ',appuser.lastName),appuser.mobileNumber from ApplicationUserEntity appuser  where  appuser.accountId=:accountId")
	public String[] findDriverDetailsByBookingId(@Param("accountId") String accountId);

	@Query(value = "select e from BookingDetailEntity e where e.id=:id")
	public BookingDetailEntity findByBookingId(@Param("id") String id);

	@Query(value = "select firstName,lastName,mobileNumber,employID from ApplicationUserEntity  where accountId=:accountId")
	public String findUserByAccountId(@Param("accountId") String accountId);

	@Query(value = "SELECT * FROM bookingdetail e where e.tripStatus in(1,2) and e.updatedOn  between  DATE_SUB(current_timestamp(), INTERVAL 20 MINUTE) and DATE_SUB(current_timestamp(), INTERVAL 15 MINUTE)", nativeQuery = true)
	public List<BookingDetailEntity> findIdAndDriverIdByTime();

	public List<BookingDetailEntity> findAllByTripStatus(Integer tripStatus);
	




}
