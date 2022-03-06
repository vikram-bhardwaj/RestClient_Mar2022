package com.test;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

public class ClientTest
{

	public static void main(String[] args)
	{
		String result = "";
		try
		{
			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet("http://localhost:8080/RestServerAPPS/details");
			getRequest.addHeader("accept", "application/json");
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() != 200) 
			{
				throw new RuntimeException("Failed : HTTP error code : " + response.getStatusLine().getStatusCode());
			}
			
			BufferedReader br = new BufferedReader(new InputStreamReader((response.getEntity().getContent())));
			
			String output;
			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null)
			{
				//System.out.println(output);
				result = result.concat(output);
			}
			
			JSONObject jObject = new JSONObject(result);
			JSONArray empData = jObject.getJSONObject("details").getJSONArray("empDetails");
			int empLen = empData.length();
			for(int i = 0; i < empLen; i++)
			{
				String iterateData = empData.get(i).toString();
				JSONObject temp = new JSONObject(iterateData);
				String id = temp.getString("ID");
				String name = temp.getString("name");
				String age = temp.getString("age");
				String gender = temp.getString("gender");
				String address = temp.getString("address");
				String phonenumber = temp.getString("phonenumber");
				
				System.out.println(id + " | " + name + " | " + age + " | " + gender + " | " + address + " | " + phonenumber);
			}
			System.out.println("______________________________________________________________________");
			JSONArray compData = jObject.getJSONObject("details").getJSONArray("compDetails");
			int compLen = compData.length();
			for(int i = 0; i < compLen; i++)
			{
				String iterateData = compData.get(i).toString();
				JSONObject temp = new JSONObject(iterateData);
				String companyName = temp.getString("companyName");
				String companyLocation = temp.getString("companyLocation");
				String companyType = temp.getString("companyType");
				
				System.out.println(companyName + " | " + companyLocation + " | " + companyType);
			}
			
			httpClient.getConnectionManager().shutdown();
		}
		catch (ClientProtocolException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}
}
