package com.rvcabs.microservices.notification; 

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class HTTPHelper
{
	public static String sendRequest(String url,String queryParam)
	{
		StringBuffer resultBuffer = new StringBuffer();
		URLConnection connection = null;
		OutputStreamWriter out = null;
		BufferedReader in = null;
		String response = null;
		try
		{
			URL sms_url = new URL(url);
			connection = sms_url.openConnection();connection.setDoOutput(true);
			out = new OutputStreamWriter(connection.getOutputStream());
			out.write(queryParam);
			out.close();
			in = new BufferedReader(new InputStreamReader(
					connection.getInputStream()));
			while (null != (response = in.readLine()))
			{
				resultBuffer.append(response);
			}
			in.close();
		} 
		catch (MalformedURLException e)
		{
			e.printStackTrace();
		} 
		catch (IOException e)
		{
			e.printStackTrace();
		} 
		finally
		{
			connection = null;
			out = null;
			in = null;
		}
		response = resultBuffer.toString();
		return (response);
	}
}
