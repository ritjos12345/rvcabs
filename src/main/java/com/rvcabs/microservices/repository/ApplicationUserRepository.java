package com.rvcabs.microservices.repository;

import java.time.ZonedDateTime;
import java.util.stream.Stream;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import com.rvcabs.microservices.entity.ApplicationUserEntity;

public interface ApplicationUserRepository extends PagingAndSortingRepository<ApplicationUserEntity, String> {

	@Transactional(readOnly = true)
	@Query("select e from ApplicationUserEntity e")
	Stream<ApplicationUserEntity> streamAll();

	@Transactional(readOnly = true)
	boolean existsByUserName(String name);

	@Transactional(readOnly = true)
	boolean existsByUserNameAndCustomerId(String name, String customerId);

	ApplicationUserEntity findByUserNameAndActiveTrue(String userName);

	ApplicationUserEntity getByUserName(String userName);

	/*
	 * getBy1And2And3 getBy1Or2; getBy1in findbyTop1order
	 */

	@Modifying
	@Query("update ApplicationUserEntity user set user.lastLoggedIn = :lastLoggedIn where user.accountId=:accountId")
	public Integer updateLastLoggedIn(@Param("lastLoggedIn") ZonedDateTime lastLoggedIn,
			@Param("accountId") String customerId);

	@Query("select e from ApplicationUserEntity e where e.userName=:userName")
	ApplicationUserEntity findByUserNameAndCustomerId(@Param("userName") String userName);

	public Page<ApplicationUserEntity> findByCustomerId(String customerId, Pageable pageable);

	@Modifying
	@Query("update ApplicationUserEntity user set user.profilePicURL = :profilePicURL where user.accountId=:accountId")
	public Integer updateProfile(@Param("profilePicURL") String profilePicURL, @Param("accountId") String customerId);

	@Modifying
	@Query("update ApplicationUserEntity user set user.password = :password,user.lastPasswordUpdated=null where user.userName=:userName")
	public Integer resetPassword(@Param("password") String password, @Param("userName") String userName);

	@Modifying
	@Query("update ApplicationUserEntity user set user.password = :password,user.lastPasswordUpdated=:lastPasswordUpdated where user.accountId=:accountId")
	public Integer updatePassword(@Param("password") String password, @Param("accountId") String accountId,
			@Param("lastPasswordUpdated") ZonedDateTime lastPasswordUpdated);

	public ApplicationUserEntity findByAccountId(@Param("accountId") String accountId);

	@Query(value = "select user.accountId from ApplicationUserEntity user where user.mobileNumber=:userName")
	public String findAccountIdByUserName(@Param("userName") String userName);

	@Query(value = "select user.accountId from ApplicationUserEntity user where user.customerId=:customerId")
	public String findByCustomerId(@Param("customerId") String customerId);
	
	@Query(value = "select concat(user.firstName,' ',user.lastName) from applicationuser user where user.accountId=:accountId", nativeQuery = true)
	public String findByDriverAccountId(@Param("accountId") String accountId);

}
