package org.me.sfBackendClient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

/**
 * symfony request
 * thanks to Cansin http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
 * @author jean-philippe serafin <jean-philippe.serafin@dev-solutions.fr>
 */
public class sfRequest{
	protected String url;
	protected String method;
	protected List<NameValuePair> params;
	protected HttpGet getRequest;
	protected HttpPost postRequest;
	protected HttpResponse response;
	protected String result;
	protected DefaultHttpClient httpClient;

	/**
	 * constructor
	 */
	public sfRequest(DefaultHttpClient client){
		this.httpClient = client;
		this.params = new ArrayList<NameValuePair>(2);
	}

	public void setUrl(String url){
		this.url = url;
	}
	public String getUrl(){
		return this.url;
	}

	public void setMethod(String method){
		this.method = method;
	}

	public String getMethod(){
		return this.method;
	}
	
	public sfRequest clone() {
		sfRequest request = new sfRequest(this.httpClient);
		request.setUrl(this.url);
		request.setMethod(this.method);
		
		Iterator<NameValuePair> iter = this.params.iterator();
		
		while (iter.hasNext()) {
			NameValuePair param = iter.next();
			request.addParam(param.getName(), param.getValue());
		}
		
		return request;
	}

	/**
	 * Adding parameter
	 * @param key
	 * @param value
	 */
	public void addParam(String key, String value){
		this.params.add(new BasicNameValuePair(key, value));
	}

	/**
	 * getting response text
	 * @return String response
	 */
	public String getResult(){
		return this.result;
	}

	/**
	 * executing request
	 */
	public void execute(){
		try {
			if (this.getMethod() == null)
				this.setMethod("GET");
			if (this.method.compareTo("GET") == 0){
				String url = this.url;

				if (params.size() > 0) {
					if (!url.contains("?"))
						url = url + "?";
					if (!url.endsWith("?"))
						url = url + "&";
					for (int i = 0; i < params.size(); i++)
						url = url + params.get(i).getName() + "=" + params.get(i).getValue() + "&";
				}

				this.getRequest = new HttpGet(url);
				this.response = this.httpClient.execute(this.getRequest);
			}else if(this.method.compareToIgnoreCase("POST") == 0
					|| this.method.compareToIgnoreCase("PUT") == 0
					|| this.method.compareToIgnoreCase("DELETE") == 0){
				this.addParam("sf_method", this.getMethod());
				this.postRequest = new HttpPost(this.getUrl());
				this.postRequest.setEntity(new UrlEncodedFormEntity(this.params, "UTF-8"));
				this.response = this.httpClient.execute(this.postRequest);
			}
			HttpEntity entity = response.getEntity();
			if(entity != null){
				InputStream inputStream = entity.getContent();
				this.result = convertStreamToString(inputStream);
			}
		} catch (ClientProtocolException e) {
			//e.printStackTrace();
			this.result = e.getMessage();
		} catch (IOException e) {
			this.result = e.getMessage();
		}
		this.params.clear();
	}
	/**
	 * converting stream reader to string
	 * @return String
	 */
	private static String convertStreamToString(InputStream is) {
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		StringBuilder stringBuilder = new StringBuilder();

		String line = null;
		try {
			while ((line = reader.readLine()) != null) {
				stringBuilder.append(line + "\n");
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				is.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return stringBuilder.toString();
	}
}