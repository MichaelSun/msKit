package com.plugin.common.cache.memory;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.plugin.common.cache.memory.impl.ImageMemoryCache;
import org.w3c.dom.Text;

public class MemoryCacheManager {

	private static MemoryCacheManager gMemoryCacheMgr = null;

	private ImageMemoryCache imageMemoryCache;
	
	private MemoryCacheOption mOption;
	
	
	private MemoryCacheManager(MemoryCacheOption option) {
		this.mOption = option;
		imageMemoryCache = ImageMemoryCache.getInstance(option);
		
	}
	

	public static MemoryCacheManager getIntance(MemoryCacheOption option) {
		if (gMemoryCacheMgr == null) {
			synchronized (MemoryCacheManager.class) {
				if (gMemoryCacheMgr == null) {
					gMemoryCacheMgr = new MemoryCacheManager(option);
				}
			}
		}

		return gMemoryCacheMgr;
	}


	
	public boolean put(String category, String key, Bitmap bmp) {
		if (!TextUtils.isEmpty(key) && !TextUtils.isEmpty(key) && bmp != null) {
			imageMemoryCache.put(category, key, bmp);
			return true;
		}
		
		return false;

	}

	public boolean put(String category, String key, InputStream in) {
		if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key) && in != null) {
			imageMemoryCache.put(category, key, in);
			return false;
		}
		return false;
	}

	public boolean put(String category, String key, byte[] bytes) {
		if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key) && bytes != null) {
			imageMemoryCache.put(category, key, bytes);
			return false;
		}
		
		return false;
	}

	public Bitmap get(String category, String key) {
		if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key)) {
			Bitmap bmp = imageMemoryCache.get(category, key);
			return bmp;
		}
		return null;
	}

	public void remove(String category, String key) {
		if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key)) {
			imageMemoryCache.remove(category ,key);
		}
	}

	public void clear() {
		this.imageMemoryCache.clear();
	}
	

}
