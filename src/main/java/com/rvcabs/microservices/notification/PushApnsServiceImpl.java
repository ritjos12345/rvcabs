package com.rvcabs.microservices.notification;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.notnoop.apns.APNS;
import com.notnoop.apns.ApnsService;
import com.notnoop.apns.PayloadBuilder;

@Service
public class PushApnsServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(PushApnsServiceImpl.class);

	private String password = "Qwerty@1";

	static ApnsService apnsService;

	public void apnsNotify(String token, PayloadBuilder payloadBuilder) {
		apnsService.push(token, payloadBuilder.build());
	}

	public void apnsNotifyAll(List<String> tokens, PayloadBuilder payloadBuilder) {
		apnsService.push(tokens, payloadBuilder.build());
	}

	public void apnsNotify(String token, String title, String message, Map<String, String> customNotificationMap) {
		try {
			logger.info("Pushing apns notification");
			logger.info("token :==>>" + token);
			logger.info("title :==>>" + title);
			logger.info("message :==>>" + message);

			String fileName = System.getProperty("user.dir");
			apnsService = APNS.newService().withCert(new FileInputStream(fileName + "/AppleCer/ABMCabs.p12"), password)
					.withSandboxDestination().build();
			PayloadBuilder payloadBuilder = APNS.newPayload();
			payloadBuilder = payloadBuilder.alertTitle(title).alertBody(message).sound("default").badge(1)
					.customFields(customNotificationMap);
			apnsNotify(token, payloadBuilder);
			logger.info("Pushed apns notification");
		} catch (Exception e) {

			logger.info("Error : While sending APNS Notification", e.getMessage());
		}

	}

	public void apnsNotifyAll(List<String> tokens, String title, String message, String certPath, String certPassword) {
		try {
			apnsService = APNS.newService().withCert(new FileInputStream(certPath), certPassword)
					.withSandboxDestination().build();

			PayloadBuilder payloadBuilder = APNS.newPayload();
			payloadBuilder = payloadBuilder.alertTitle(title).alertBody(message).sound("default").badge(1);
			apnsNotifyAll(tokens, payloadBuilder);
		} catch (Exception e) {
			logger.info("Error : While sending multicast APNS Notification", e.getMessage());
		}
	}

	public static void main(String[] args) {
		PushApnsServiceImpl gcm = new PushApnsServiceImpl();
		Map customNotificationMap = new HashMap<String, String>();
		customNotificationMap.put("Type", 7);
		gcm.apnsNotify("e41d5ca42112e3772f11c70c2929aa83e4dfb969437c137d4cf43d79f43d5cf8p", "Procedure",
				"Hi.. Just for testing", customNotificationMap);
		System.out.println("hello gone");
	}
}
