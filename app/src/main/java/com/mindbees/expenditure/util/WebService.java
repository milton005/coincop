package com.mindbees.expenditure.util;

import android.content.Context;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.PersistentCookieStore;
import com.loopj.android.http.RequestParams;

import org.apache.http.client.params.ClientPNames;

/**
 * Created by tony on 16/5/15.
 */
public class WebService {
	final static int DEFAULT_TIMEOUT = 20 * 1000;
    private static AsyncHttpClient client = new AsyncHttpClient();
    
    
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
//    	client.setTimeout(DEFAULT_TIMEOUT);
//    	 RequestConfig.custom().setCircularRedirectsAllowed(true).build(); 
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.addHeader(Const.KEY_API, Const.VALUE_API);
        client.get(url, params, responseHandler);
    }

    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.addHeader(Const.KEY_API, Const.VALUE_API);
        client.get(url, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.addHeader(Const.KEY_API, Const.VALUE_API);
        client.post(url, params, responseHandler);
    }

    public static void post(String url, AsyncHttpResponseHandler responseHandler) {
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.addHeader(Const.KEY_API, Const.VALUE_API);
        client.post(url, responseHandler);
    }
    /*public static void delete(String url, AsyncHttpResponseHandler responseHandler) {
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        client.delete(url, responseHandler);
    }
    public static void setCookies(Context con) {
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        PersistentCookieStore myCookieStore = new PersistentCookieStore(con);
        client.setCookieStore(myCookieStore);
    }
    public static void clearCookies(Context con) {
        client.getHttpClient().getParams().setParameter(ClientPNames.ALLOW_CIRCULAR_REDIRECTS, true);
        PersistentCookieStore myCookieStore = new PersistentCookieStore(con);
        myCookieStore.clear();
        client.setCookieStore(myCookieStore);
    }*/


}
