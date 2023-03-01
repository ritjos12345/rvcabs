package com.rvcabs.microservices.notification;

import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
@Service
public class PushGcmServiceImpl {

	private static final Logger logger = LoggerFactory.getLogger(PushGcmServiceImpl.class);

	// static String FCM_API_KEY = "AIzaSyA-ClZNHr_juSnKlaZP4Yp2CW-Nd8DSs0Q";
	private static String FCM_API_KEY = "AAAAfPFbGHE:APA91bG390Vjq_EXycf8mWIcaHJTxQgBRLhv8QT54EWyXeer7QecdSmOMPnsuyD14FVU5wG_lDgA5bdrVF70R4Z0nkSAYRzLSw8K5QZHuvaf1REx2I9W8NEkZOo5SAoAbCj3OXboYSMU";
	static String FCM_URL = "https://fcm.googleapis.com/fcm/send";
	static HttpURLConnection conn;

	public void gcmNotify(String token, String title, String message, String fcm_api_key,
			Map<String, String> customNotificationMap) {
		logger.info("token :==>>" + token);
		logger.info("title :==>>" + title);
		logger.info("message :==>>" + message);
		logger.info("fcm_api_key :==>>" + fcm_api_key);
		createConnection(fcm_api_key);
		JSONObject json = constructJsonObject(token, title, message, customNotificationMap);
		OutputStreamWriter wr;
		try {
			logger.info("Pushing app notification");
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();
			wr.close();
			int responseCode = conn.getResponseCode();
			System.out.println("responseCode:==>>" + responseCode);
			logger.info("Pushed app notification");
		} catch (IOException e) {
			e.printStackTrace();
			logger.info("Error : While Writing JSON Object", e.getMessage());
		}

	}

	public void gcmNotifyAll(List<String> tokens, String title, String message, String fcm_api_key) {
		// TODO Auto-generated method stub

	}

	public void createConnection(String fcm_api_key) {
		try {
			URL url = new URL(FCM_URL);
			conn = (HttpURLConnection) url.openConnection();
			conn.setUseCaches(false);
			conn.setDoInput(true);
			conn.setDoOutput(true);
			conn.setRequestProperty("Authorization", "key=" + fcm_api_key);
			conn.setRequestProperty("Content-Type", "application/json");
			conn.setRequestMethod("POST");
			logger.info("FCM_API_Key----------------"+fcm_api_key);
		} catch (IOException e) {
			logger.info("Error : While Connecting URL", e.getMessage());
		}
	}

	public JSONObject constructJsonObject(String token, String title, String message,
			Map<String, String> customNotificationMap) {
		JSONObject json = new JSONObject();
		try {
			json.put("to", token.trim());
			JSONObject info = new JSONObject();
			info.put("id", System.currentTimeMillis());
			info.put("title", title);
			info.put("body", message);
			info.put("customFields", customNotificationMap);
			json.put("notification", info);
			json.put("data", info);
		} catch (JSONException e) {
			logger.info("Error : While Parsing JSON Object", e.getMessage());
		}
		return json;
	}

	public void gcmCallNotify(String token, Long roomId, Long doctorId, Long memberId, String callerName) {
		createConnection(null);
		JSONObject json = new JSONObject();
		try {
			json.put("to", token.trim());
			JSONObject info = new JSONObject();
			info.put("id", System.currentTimeMillis());
			info.put("doctorid", doctorId);
			info.put("roomid", roomId);
			info.put("memberId", memberId);
			info.put("callerName", callerName);
			json.put("data", info);
			OutputStreamWriter wr;
			logger.info("Pushing app notification");
			wr = new OutputStreamWriter(conn.getOutputStream());
			wr.write(json.toString());
			wr.flush();
			conn.getInputStream();
			logger.info("Pushed app notification");
		} catch (IOException e) {
			logger.info("Error : While Writing JSON Object", e.getMessage());
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public static void main(String[] args) {
		PushGcmServiceImpl gcm = new PushGcmServiceImpl();
		Map<String, String> customNotificationMap = null;
		gcm.gcmNotify("c4Ksn9YNRROfzzMkiRklWU:APA91bEXGhxav-y9cCcFBba5PVsUNBAhHCXkJNi6rM3Ql_tbcLs-oEMt4Eu1UObD5y7qJxF4VHn7GJnuGsfqHiOc0oCxpT3YB1iXtX0QHU8Osz6oGW1wAbzqclXAwo7iIttpsPRO5GJ0", "Procedure", "HI",
				FCM_API_KEY, customNotificationMap);
		System.out.println("hello gone");
	}
}
