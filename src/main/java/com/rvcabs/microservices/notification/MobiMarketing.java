package com.rvcabs.microservices.notification;

import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.rvcabs.microservices.dto.SMSParameters;

@Service
public class MobiMarketing {

	private static final Logger logger = LoggerFactory.getLogger(MobiMarketing.class);

	public MobiMarketing() {
	}

	public String sendSms(SMSParameters theParam) {

		logger.info("UnicelMessageSender : Sending SMS to :" + ((SMSParameters) theParam).targetDeviceNum);
		String status = null;

		String smsGateWayURL;

		StringBuffer sb = new StringBuffer();

		String message = ((SMSParameters) theParam).message;

		String destination = ((SMSParameters) theParam).targetDeviceNum;

		// truncate message if it's longer than the size limit
		// TODO: do this for other connectors as well.

		/*
		 * sb = new
		 * StringBuilder().append("uname=").append(uname).append("&pass=").append(pass).
		 * append("&send=").append(message).append("&dest=").append(destination).append(
		 * "&msg=").append(message);
		 */
		sb = new StringBuffer()
				.append("<MESSAGE><AUTHKEY>8574ATRzOBwHfbK55e85d71cP11</AUTHKEY><SENDER>RVTECH</SENDER>\r\n" + "\r\n"
						+ "<ROUTE>6</ROUTE>\r\n" + "\r\n" + "<CAMPAIGN>RVCAB</CAMPAIGN>\r\n" + "\r\n"
						+ "<COUNTRY>91</COUNTRY>")
				.append("<SMS TEXT='").append(theParam.message).append("'>").append("<ADDRESS TO='")
				.append(theParam.targetDeviceNum).append("'/>").append("</SMS></MESSAGE>");

		status = connectMobiMarketing(sb.toString());

		/*
		 * try { URL obj = new URL("http://bulkdnd.mobi-marketing.biz/api/postsms.php");
		 * 
		 * HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		 * con.setRequestMethod("POST");
		 */

		/*
		 * // For POST only - START con.setDoOutput(true); OutputStream os =
		 * con.getOutputStream(); //
		 * os.write(convertStringToXMLDocument(sb.toString())); os.flush(); os.close();
		 * // For POST only - END
		 * 
		 * int responseCode = con.getResponseCode();
		 * System.out.println("POST Response Code :: " + responseCode);
		 * 
		 * } catch (Exception e) { logger.info("Error : While sending  SMS " + e); }
		 */

		return status;
	}

	// For Registration and forgot password the password was printing in log as
	// it is. Need to mask it. private String SmsToDisplay(String sms) { String
	/*
	 * displaySms=sms;try
	 * 
	 * { if (displaySms.contains(".Your new password is")) { displaySms =
	 * displaySms.replace(displaySms.substring(displaySms.
	 * lastIndexOf(".Your new password is")), ".Your new password is ******"); }
	 * else if (displaySms.contains("Password:")) { displaySms = displaySms.replace(
	 * displaySms.substring(displaySms.indexOf("Password:"),
	 * displaySms.indexOf(" at ")), "Password:*****"); } }catch( Exception e) {
	 * 
	 * }return displaySms;
	 * 
	 * }
	 */

	private String connectMobiMarketing(String xmlBody) {

		StringBuilder response = new StringBuilder();

		//String request = "<Employee><Name>Sunil</Name></<Employee>";

		try {
			URL url = new URL("http://bulkdnd.mobi-marketing.biz/api/postsms.php");
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();

			// Set timeout as per needs connection.setConnectTimeout(20000);
			connection.setReadTimeout(20000);

			// Set DoOutput to true if you want to use URLConnection for output.
			// Default is false
			connection.setDoOutput(true);

			connection.setUseCaches(true);
			connection.setRequestMethod("POST");

			// Set Headers connection.setRequestProperty("Accept", "application/xml");
			connection.setRequestProperty("Content-Type", "application/xml");

			// Write XML
			OutputStream outputStream = connection.getOutputStream();
			byte[] b = xmlBody.getBytes("UTF-8");
			outputStream.write(b);
			outputStream.flush();
			outputStream.close();

			// Read XML
			InputStream inputStream = connection.getInputStream();
			byte[] res = new byte[2048];
			int i = 0;
			while ((i = inputStream.read(res)) != -1) {
				response.append(new String(res, 0, i));
			}
			inputStream.close();

			System.out.println("Response= " + response.toString());
		} catch (Exception exception) {
			logger.error("MobiMarketing:connectMobiMarket:Error while sending XML to mobimarket");
		}
		return response.toString();

	}

}
