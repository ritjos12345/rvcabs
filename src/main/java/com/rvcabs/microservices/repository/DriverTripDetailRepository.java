package com.rvcabs.microservices.repository;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rvcabs.microservices.entity.DriverTripDetailEntity;

public interface DriverTripDetailRepository extends PagingAndSortingRepository<DriverTripDetailEntity, String> {

	@Modifying
	@Query("update DriverTripDetailEntity user set user.isOngoingTrip=:status where user.accountId=:accountId")
	public Integer updateDrivingStatus(@Param("status") boolean status, @Param("accountId") String accountId);

	@Modifying
	@Query("update DriverTripDetailEntity user set user.onGoingTripStartTime=sysdate() where user.accountId=:accountId")
	public Integer updateDrivingOngoingTime(@Param("accountId") String accountId);

	@Modifying
	@Query("update DriverTripDetailEntity user set user.driverLat=:lat,user.driverLng=:lng where user.accountId=:accountId")
	public Integer updateDriverPosition(@Param("accountId") String accountId, @Param("lat") String lat,
			@Param("lng") String lng);

	public DriverTripDetailEntity findByAccountId(@Param("accountId") String accountId);

	@Modifying
	@Query("update DriverTripDetailEntity user set user.deviceToken = :deviceToken where user.accountId=:accountId")
	public Integer updateDeviceToken(@Param("deviceToken") String deviceToken, @Param("accountId") String customerId);

	@Query(value = "SELECT accountId FROM DriverTripDetailEntity WHERE active=true and  DATE(onGoingTripStartTime) < DATE(:pickupdate ) AND DATE(nextTripStartTime) != DATE(:pickupdate)  AND totalDistance = (SELECT  MIN(totalDistance)  FROM DriverTripDetailEntity) and accountId  in (:driList)")
	public List<String> findAllAccountIdBasedOnMinKmWithIn(@Param("pickupdate") Date pickupdate,
			@Param("driList") List<String> driList);

	@Query(value = "SELECT accountId FROM DriverTripDetailEntity WHERE active=true and  DATE(onGoingTripStartTime) < DATE(:pickupdate ) AND DATE(nextTripStartTime) != DATE(:pickupdate)  AND totalDistance = (SELECT  MIN(totalDistance)  FROM DriverTripDetailEntity) ")
	public List<String> findAllAccountIdBasedOnMinKMWithOutIn(@Param("pickupdate") Date pickupdate);

	@Query(value = "SELECT accountId FROM DriverTripDetailEntity WHERE  active=true and DATE(onGoingTripStartTime) < DATE(:pickupdate ) AND DATE(nextTripStartTime) != DATE(:pickupdate)  AND totalRating = (SELECT  MAX(totalRating)  FROM DriverTripDetailEntity) and accountId  in (:driList)")
	public List<String> findAllAccountIdBasedOnMaxRatingWithIn(@Param("pickupdate") Date pickupdate,
			@Param("driList") List<String> driList);

	@Query(value = "SELECT accountId FROM DriverTripDetailEntity WHERE active=true and  DATE(onGoingTripStartTime) < DATE(:pickupdate ) AND DATE(nextTripStartTime) != DATE(:pickupdate)  AND totalRating = (SELECT  MAX(totalRating)  FROM DriverTripDetailEntity) ")
	public List<String> findAllAccountIdBasedOnMaxRatingWithOutIn(@Param("pickupdate") Date pickupdate);

	// select distinct ae.parentAuthCode from AuthoritiesEntity ae where ae.authCode
	// IN (:authCodes) and ae.parentAuthCode is not null
	@Modifying
	@Query("update DriverTripDetailEntity user set user.nextTripStartTime=:pickuptime where user.accountId=:accountId")
	public Integer updateNextTripTiming(@Param("accountId") String accountId, @Param("pickuptime") Date pickuptime);

	@Query(value = "SELECT e FROM DriverTripDetailEntity e where e.nextTripStartTime  between addtime(current_timestamp(),'0 1:0:0') and addtime(current_timestamp(),'0 1:15:0')")
	public List<DriverTripDetailEntity> findAllByNextTripTime();

	@Modifying
	@Query("update BookingDetailEntity book set book.tripStatus=2,book.driverAccountId=:driverAccountId,updatedOn=sysdate()  where id=:id")
	public Integer updateDriverTripStatus(@Param("driverAccountId") String driverAccountId, @Param("id") String id);

	@Modifying
	@Query(value = "UPDATE drivertripdetail SET totalRating = totalRating+(SELECT tripRating FROM tripdetail WHERE  id =:tripId),totalDistance=totalDistance+(SELECT tripTotalDistance FROM tripdetail WHERE  id =:tripId) WHERE accountId =:driverAccountId", nativeQuery = true)
	public Integer updateRating(@Param("tripId") String tripId, @Param("driverAccountId") String driverAccountId);

	@Query(value = "SELECT accountId FROM DriverTripDetailEntity WHERE active=true and  DATE(onGoingTripStartTime) < DATE(:pickupdate ) AND DATE(nextTripStartTime) != DATE(:pickupdate) and accountId=:driverAccountId")
	public String searchSelectedDriver(@Param("pickupdate") Date pickupdate,
			@Param("driverAccountId") String driverAccountId);
	
	@Modifying
	@Query("update DriverTripDetailEntity user set user.isOngoingTrip = false,user.onGoingTripStartTime='0000-00-00 00:00:00' where user.accountId=:accountId")
	public Integer updateOnGoingTripAndFlag(@Param("accountId") String customerId);
}
