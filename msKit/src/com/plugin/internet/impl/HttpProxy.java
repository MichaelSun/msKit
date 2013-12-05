/**
 * Copyright 2011-2012 Renren Inc. All rights reserved.
 * － Powered by Team Pegasus. －
 */

package com.plugin.internet.impl;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import org.apache.http.HttpHost;

final class HttpProxy {

	static public HttpHost getProxyHttpHost(Context context) {
		if (context == null) {
			return null;
		}
		ConnectivityManager ConnMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = ConnMgr.getActiveNetworkInfo();
		String proxyHost = null;
		int proxyPort = 0;
		if (info != null) {
			if (info.getType() == ConnectivityManager.TYPE_WIFI) {
			} else {
				// GPRS: APN http proxy
				proxyHost = android.net.Proxy.getDefaultHost();
				proxyPort = android.net.Proxy.getDefaultPort();
			}
		}
		if (proxyHost != null) {
			return new HttpHost(proxyHost, proxyPort);
		} else {
			return null;
		}
	}
}
