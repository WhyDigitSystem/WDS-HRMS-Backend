package com.efit.hrms.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.efit.hrms.entity.NotificationVO;

@Service
public interface NotificationService {
	
	void createNotification(Long userId, Long auctionId, String message,String type);

    List<NotificationVO> getNotifications(Long userId);

    void markAsRead(Long notificationId);

    void deleteNotification(Long notificationId);

    void clearAll(Long userId);


}