package com.efit.hrms.service;

import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.efit.hrms.entity.NotificationVO;
import com.efit.hrms.repo.NotificationRepo;

@Service
public class NotificationServiceImpl implements NotificationService {
	
	public static final Logger LOGGER = LoggerFactory.getLogger(NotificationServiceImpl.class);
	
	
	@Autowired
	NotificationRepo notificationRepo;

	@Override
	public void createNotification(Long userId, Long auctionId, String message,String type) {
		NotificationVO n = new NotificationVO();
        n.setUserid(userId);
        n.setAuctionsid(auctionId);
        n.setMessage(message);
        n.setNotificationType(type);
        notificationRepo.save(n);	
	}

	@Override
	public List<NotificationVO> getNotifications(Long userId) {
		// TODO Auto-generated method stub
		return notificationRepo.findByUseridAndIsReadFalseAndIsDeletedFalse(userId);
	}

	@Override
    @Transactional
    public void markAsRead(Long notificationId) {
        NotificationVO n = notificationRepo.findById(notificationId).orElseThrow();
        n.setRead(true);
        notificationRepo.save(n);
    }

    @Override
    @Transactional
    public void deleteNotification(Long notificationId) {
        NotificationVO n = notificationRepo.findById(notificationId).orElseThrow();
        n.setDeleted(true);
        notificationRepo.save(n);
    }

    @Override
    @Transactional
    public void clearAll(Long userId) {
        List<NotificationVO> list = notificationRepo.findByUserid(userId);
        list.forEach(n -> n.setDeleted(true));
        notificationRepo.saveAll(list);
    }

}
