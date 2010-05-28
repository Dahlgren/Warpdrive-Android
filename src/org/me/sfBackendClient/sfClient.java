package org.me.sfBackendClient;

import org.apache.http.impl.client.DefaultHttpClient;

/**
 * symfony application client singleton
 * thanks to Cansin http://senior.ceng.metu.edu.tr/2009/praeda/2009/01/11/a-simple-restful-client-at-android/
 * @author jean-philippe serafin <jean-philippe.serafin@dev-solutions.fr>
 */

public class sfClient{
	private static sfClient instance;
	protected DefaultHttpClient httpClient;

	/**
	 * constructor
	 */
	private sfClient(){
		this.httpClient = new DefaultHttpClient();
	}

	/**
	 * instance accessor
	 */

	public static sfClient getInstance(){
		if(null == instance){
			instance = new sfClient();
		}

		return instance;
	}

	/**
	 * creating new request
	 */

	public sfRequest createRequest(){
		sfRequest request = new sfRequest(this.httpClient);
		return request;
	}
}