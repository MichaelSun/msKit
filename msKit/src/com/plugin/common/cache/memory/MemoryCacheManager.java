package com.plugin.common.cache.memory;

import java.io.File;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.graphics.Bitmap;
import android.text.TextUtils;

import com.plugin.common.cache.disc.DiscCacheOption;
import com.plugin.common.cache.memory.impl.BaseImageMemoryCache;
import com.plugin.common.cache.memory.impl.FileMemoryCache;
import com.plugin.common.cache.memory.impl.ImageMemoryCache;
import com.plugin.common.cache.memory.impl.ThumbnailMemoryCache;

public class MemoryCacheManager {

	private static MemoryCacheManager gMemoryCacheMgr = null;

	private Map<String, FileMemoryCache> fileMemoryCaches = Collections.synchronizedMap(new HashMap<String, FileMemoryCache>());

	private Map<String, BaseImageMemoryCache> imageMemoryCaches = Collections.synchronizedMap(new HashMap<String, BaseImageMemoryCache>());
	
	private MemoryCacheOption mOption;
	
	
	private MemoryCacheManager(MemoryCacheOption option) {
		this.mOption = option;
		if(option.getDiscCacheOptions() != null){
			Iterator<Entry<String, DiscCacheOption>> discCachekeys = option.getDiscCacheOptions().entrySet().iterator();
			while(discCachekeys.hasNext()){
				Map.Entry<String, DiscCacheOption> discOption = discCachekeys.next();
				
			}
		}
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


	
	public boolean put(String category, String key, Object obj) {
		if (!TextUtils.isEmpty(category) && obj != null) {
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_FILE)){
				this.fileMemoryCache.put(key, (File) obj);
				return true;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_IMAGE)){
				this.imageMemoryCache.put(key, (Bitmap) obj);
				return true;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_THUMBNAIL)){
				this.thumbnailMemoryCache.put(key, (Bitmap) obj);
				return true;
			}
			return false;
		}
		
		return false;

	}

	public boolean put(String category, String key, InputStream in) {
		if (!TextUtils.isEmpty(category) && in != null) {
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_FILE)){
				this.fileMemoryCache.put(key, in);
				return true;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_IMAGE)){
				this.imageMemoryCache.put(key, in);
				return true;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_THUMBNAIL)){
				this.thumbnailMemoryCache.put(key, in);
				return true;
			}
			return false;
		}
		return false;
	}

	public boolean put(String category, String key, byte[] bytes) {
		if (!TextUtils.isEmpty(category) && bytes != null) {
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_FILE)){
				this.fileMemoryCache.put(key, bytes);
				return true;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_IMAGE)){
				this.imageMemoryCache.put(key, bytes);
				return true;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_THUMBNAIL)){
				this.thumbnailMemoryCache.put(key, bytes);
				return true;
			}
			return false;
		}
		
		return false;
	}

	public Object get(String category, String key) {
		if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key)) {
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_FILE)){
				File file = this.fileMemoryCache.get(key);
				return file;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_IMAGE)){
				Bitmap bmp = this.imageMemoryCache.get(key);
				return bmp;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_THUMBNAIL)){
				Bitmap bmp = this.thumbnailMemoryCache.get(key);
				return bmp;
			}
			return null;
		}
		return null;
	}

	public void remove(String category, String key) {
		if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key)) {
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_FILE)){
				this.fileMemoryCache.remove(key);
				return;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_IMAGE)){
				this.imageMemoryCache.remove(key);
				return;
			}
			if(category.equals(MemoryCacheOption.MEMORY_CACHE_CATEGORY_THUMBNAIL)){
				this.thumbnailMemoryCache.remove(key);
				return;
			}
		}
	}

	public void clear() {
		this.fileMemoryCache.clear();
		this.imageMemoryCache.clear();
		this.thumbnailMemoryCache.clear();
	}

}
