package com.rvcabs.microservices.utilities;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class HttpURLConnetion {

	public static String httpPOSTConnection(String url,String request) throws Exception {
		String responseObject= new String();
		URL obj = new URL(url);
		HttpURLConnection postConnection = (HttpURLConnection) obj.openConnection();
		postConnection.setRequestMethod("POST");
		postConnection.setRequestProperty("Content-Type", "application/json");
		postConnection.setDoOutput(true);
		OutputStream os = postConnection.getOutputStream();
		os.write(request.getBytes());
		os.flush();
		os.close();
		int responseCode = postConnection.getResponseCode();
		System.out.println("POST Response Code :  " + responseCode);
		System.out.println("POST Response Message : " + postConnection.getResponseMessage());
		if (responseCode == HttpURLConnection.HTTP_OK) { // success
			BufferedReader in = new BufferedReader(new InputStreamReader(postConnection.getInputStream()));
			String inputLine;
			StringBuffer response = new StringBuffer();
			while ((inputLine = in.readLine()) != null) {
				response.append(inputLine);
			}
			in.close();
			// print result
			System.out.println(response.toString());
			responseObject =response.toString();
			
		} else {
			responseObject=null;
			System.out.println("POST NOT WORKED");
		}
		
		return responseObject;
	}

	public static void main(String[] args) {
	/*	try {
			UpdateCardStatusRequest request = new UpdateCardStatusRequest();
			request.setCardNumber("4032506404351443");
			request.setStatus("ACTIVATE");
			//httpPOSTConnection("https://boostera2a-fakeuat.ichange360.com/api/setstatus/",request);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
}
