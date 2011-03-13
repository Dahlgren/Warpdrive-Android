package org.me.sfBackendClient;

import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpParams;

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
		HttpParams params = new BasicHttpParams();

		// Create and initialize scheme registry 
		SchemeRegistry schemeRegistry = new SchemeRegistry();
		schemeRegistry.register(new Scheme("http", PlainSocketFactory.getSocketFactory(), 80));

		// Create an HttpClient with the ThreadSafeClientConnManager.
		// This connection manager must be used if more than one thread will
		// be using the HttpClient.
		ClientConnectionManager cm = new ThreadSafeClientConnManager(params, schemeRegistry);

		this.httpClient = new DefaultHttpClient(cm, params);
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