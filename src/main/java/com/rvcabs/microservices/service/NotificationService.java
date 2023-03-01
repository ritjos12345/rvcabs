package com.rvcabs.microservices.service;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rvcabs.microservices.dto.NotificationDto;
import com.rvcabs.microservices.dto.SMSParameters;
import com.rvcabs.microservices.entity.UserNotificationEntity;
import com.rvcabs.microservices.notification.MobiMarketing;
import com.rvcabs.microservices.notification.PushApnsServiceImpl;
import com.rvcabs.microservices.notification.PushGcmServiceImpl;
import com.rvcabs.microservices.repository.NotificationRepository;

@Service
public class NotificationService {

	@Autowired
	private PushApnsServiceImpl pushApnsServiceImpl;

	@Autowired
	private PushGcmServiceImpl pushGcmServiceImpl;

	@Autowired
	private MobiMarketing mobiMarketing;

	@Autowired
	private NotificationRepository notificationRepository;

	public void notifyDriver(String message, String title, String deviceToken,
			Map<String, String> customNotificationMap) {

		String deviceType = new String();
		if (deviceToken.substring(3, deviceToken.length()) != null
				&& !deviceToken.substring(3, deviceToken.length()).equals("")) {
			deviceType = deviceToken.substring(0, 3);

			if (deviceType.equals("AND")) {
				pushGcmServiceImpl.gcmNotify(deviceToken.substring(3, deviceToken.length()), title, message,
						"AAAA07uqiww:APA91bGmVMefNdr-LYfgm-lt41G61Gkxv_4c-mMrJDlEN23yeihpgeSzJQ1tqYijXmM7Ken6MsnVAN_T811iBy3Q3GwWcClyqy_km6CGaOru2Jb75ZbfGRWpezFK-FN8pRqkAAFOHhbE",
						customNotificationMap);

			} else if (deviceType.equals("IOS")) {
				pushApnsServiceImpl.apnsNotify(deviceToken.substring(3, deviceToken.length()), title, message,
						customNotificationMap);
			}

		}
	}

	public void sendSms(String smsId, String smsTemplate) {
		if (!smsId.equals("false")) {
			SMSParameters smsParams = new SMSParameters();
			if (smsId.contains("-")) {
				String[] splitMobile = smsId.split("-");
				smsParams.COUNTRY_CODE = splitMobile[0];
				smsParams.targetDeviceNum = splitMobile[1];
			} else
				smsParams.targetDeviceNum = smsId;
			smsParams.message = smsTemplate;
			mobiMarketing.sendSms(smsParams);
		}
	}

	public String saveNotificaton(NotificationDto notificationDto) {
		UserNotificationEntity userNotificationEntity = new UserNotificationEntity();
		userNotificationEntity.setAccountId(notificationDto.getAccountId());
		userNotificationEntity.setBookingId(notificationDto.getBookingTripId());
		userNotificationEntity.setDeviceToken(notificationDto.getDeviceToken());
		userNotificationEntity.setMobileNumber(notificationDto.getSmsId());
		userNotificationEntity.setSmsText(notificationDto.getSms());
		userNotificationEntity.setPushNotificationExp(notificationDto.getNotificationExpTime());
		userNotificationEntity.setPushNotifyMessage(notificationDto.getNotificationSubject());
		userNotificationEntity.setType(notificationDto.getType());
		userNotificationEntity = notificationRepository.save(userNotificationEntity);
		if (userNotificationEntity.getId() != null) {
			try {
				notifyDriver(notificationDto.getNotificationSubject(), notificationDto.getNotificationHeader(),
						notificationDto.getDeviceToken(), notificationDto.getConfigMap());
			} catch (Exception e) {
				// TODO: handle exception
			}
			try {
				sendSms(notificationDto.getSmsId(), notificationDto.getSms());
			} catch (Exception e) {
				// TODO: handle exception
			}
			return userNotificationEntity.getId();
		}
		return null;
	}

	/*
	 * public NotificationMasterDto getTemplate(String templateName) {
	 * NotificationMasterDto notificationMasterDto = null; if
	 * (notifyTemplateMap.containsKey(templateName)) { notificationMasterDto =
	 * notifyTemplateMap.get(templateName); } else { notificationMasterDto =
	 * notifyMasterService.get(templateName); notifyTemplateMap.put(templateName,
	 * notificationMasterDto); } return notificationMasterDto; }
	 */
}
