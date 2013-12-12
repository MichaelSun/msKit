package com.plugin.common.cache.memory;

import java.io.InputStream;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.plugin.common.cache.memory.impl.ImageMemoryCache;

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
		if (!TextUtils.isEmpty(key) && bmp != null) {
			String formatKey = makeFileKeyName(category, key);
			imageMemoryCache.put(formatKey, bmp);
			return true;
		}
		
		return false;

	}

	public boolean put(String category, String key, InputStream in) {
		if (!TextUtils.isEmpty(category) && in != null) {
			String formatKey = makeFileKeyName(category, key);
			imageMemoryCache.put(key, in);
			return false;
		}
		return false;
	}

	public boolean put(String category, String key, byte[] bytes) {
		if (!TextUtils.isEmpty(category) && bytes != null) {
			String formatKey = makeFileKeyName(category, key);
			imageMemoryCache.put(formatKey, bytes);
			return false;
		}
		
		return false;
	}

	public Bitmap get(String category, String key) {
		if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key)) {
			String formatKey = makeFileKeyName(category, key);
			Bitmap bmp = imageMemoryCache.get(formatKey);
			return bmp;
		}
		return null;
	}

	public void remove(String category, String key) {
		if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key)) {
			String formatKey = makeFileKeyName(category, key);
			imageMemoryCache.remove(formatKey);
		}
	}

	public void clear() {
		this.imageMemoryCache.clear();
	}
	
    protected String makeFileKeyName(String category, String key) {
        StringBuilder sb = new StringBuilder(256);
        sb.append(category).append("/").append(key);
        return sb.toString();
    }

}
