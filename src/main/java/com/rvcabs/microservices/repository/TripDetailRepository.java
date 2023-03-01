package com.rvcabs.microservices.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rvcabs.microservices.entity.TripDetailEntity;

public interface TripDetailRepository extends PagingAndSortingRepository<TripDetailEntity, String> {

	@Query(value = "select app.mobileNumber from BookingDetailEntity book,TripDetailEntity trip,ApplicationUserEntity app where book.id=trip.bookingId and book.accountId=app.accountId and trip.id=:tripId")
	public String userMobileNumberByTrip(@Param("tripId") String tripId);

	@Modifying
	@Query("update TripDetailEntity set tripEndTime=sysdate() where id=:tripId")
	public Integer updateTripStatus(@Param("tripId") String tripId);

	@Query(value = "select bookingId from TripDetailEntity  where id=:tripId")
	public String fndBookingIdByTripId(@Param("tripId") String tripId);

	@Query(value = "select id from TripDetailEntity  where bookingId=:bookingId")
	public String findOneByBookingId(@Param("bookingId") String bookingId);

	@Modifying
	@Query(value = "update tripdetail set tripTotalDistance=tripTotalDistance+:distance where id=:tripId", nativeQuery = true)
	public Integer updateDistance(@Param("tripId") String tripId, @Param("distance") String distance);

	@Query(value = "select signatureURL from TripDetailEntity  where id=:tripId")
	public String findSignatureByTripId(@Param("tripId") String tripId);

	@Query(value = "SELECT id,bookingId,tripRating,tripFeedBack FROM tripdetail where id=:id or bookingId=:id", nativeQuery = true)
	public String findFeedBackDetails(@Param("id") String id);
	
	
	public TripDetailEntity findOneById(@Param("tripId") String tripId);

	

}
