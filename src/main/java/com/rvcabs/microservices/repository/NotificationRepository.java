package com.rvcabs.microservices.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import com.rvcabs.microservices.entity.UserNotificationEntity;

public interface NotificationRepository extends PagingAndSortingRepository<UserNotificationEntity, String> {

}
