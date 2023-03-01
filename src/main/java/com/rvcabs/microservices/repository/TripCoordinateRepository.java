package com.rvcabs.microservices.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import com.rvcabs.microservices.entity.TripCoordinateEntity;

public interface TripCoordinateRepository extends PagingAndSortingRepository<TripCoordinateEntity, String> {

	@Modifying
	@Query("update TripCoordinateEntity set dstLat=:dstLat,dstLng=:dstLng,dstName=:dstName,updatedOn=sysdate(),distance=:distance,timeDifference=SEC_TO_TIME(TIMEDIFF(sysdate(), createdOn)) where tripId=:tripId and dstLat is null and dstLng is null and dstName is null")
	public Integer updateTripStatus(@Param("tripId") String tripId, @Param("dstName") String dstName,
			@Param("dstLng") String dstLng, @Param("dstLat") String dstLat, @Param("distance") String distance);

	@Query("SELECT e FROM TripCoordinateEntity e where  e.tripId=:tripd and e.dstLat is not null")
	public List<TripCoordinateEntity> findAllByTripIdOrderByCreatedOnAsc(@Param("tripd") String tripd);

	@Query(value = "SELECT  tripTotalDistance,TIMESTAMPDIFF(MINUTE,  tripStartTime,tripEndTime) FROM tripdetail WHERE id =:tripId", nativeQuery = true)
	public String findTotalDistAndTimeByTripId(@Param("tripId") String tripId);

	@Query("SELECT distance,SEC_TO_TIME(TIMEDIFF(updatedOn, createdOn)) FROM TripCoordinateEntity WHERE id =:id AND dstLat IS NOT NULL")
	public String findDistAndTimeByTripCoId(@Param("id") String id);

	@Query("SELECT count(tripId) FROM TripCoordinateEntity  where tripId=:tripId")
	public int countByTripId(@Param("tripId") String tripId);

	@Query("select id from  TripCoordinateEntity  where tripId=:tripId and dstLat is null and dstLng is null and dstName is null")
	public String findTripCoId(@Param("tripId") String tripId);
}
