package com.plugin.common.cache.memory.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import com.plugin.common.cache.disc.DiscCacheFactory;
import com.plugin.common.cache.disc.impl.ImageDiscCache;
import com.plugin.common.cache.disc.naming.HashCodeFileNameGenerator;
import com.plugin.common.cache.memory.IMemoryCache;
import com.plugin.common.cache.memory.MemoryCacheOption;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class BaseImageMemoryCache implements IMemoryCache<String, Bitmap> {

	protected LruCache<String, Bitmap> lruCache;
	
	private ImageDiscCache imageDiscCache;
	
	private boolean autoSave2Disk;
	
	private boolean autoFetchFromDisk;
	
	protected BaseImageMemoryCache(MemoryCacheOption option) {
		this.lruCache = new LruCache<String, Bitmap>(
				option.getMaxMemoryCacheSize()) {
			@Override
			protected int sizeOf(String key, Bitmap bmp) {
				return bmp.getRowBytes() * bmp.getHeight();
			}
		};
		
		imageDiscCache = (ImageDiscCache) DiscCacheFactory.getInstance().getDiscCache(option.getDiscCacheOption());
		
		autoSave2Disk = option.isAutoSave2Disk();
		autoFetchFromDisk = option.isAutoFetchFromDisk();
	}

	@Override
	public boolean put(String key, Bitmap value) {
		if(TextUtils.isEmpty(key) || value == null){
			return false;
		}
		this.lruCache.put(key, value);
		if(autoSave2Disk){
			imageDiscCache.put(key, value);
		}
		return true;
	}

	@Override
	public boolean put(String key, InputStream in) {
		if(TextUtils.isEmpty(key) || in == null){
			return false;
		}
		BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
		Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
		if(bmp != null){
			this.lruCache.put(key, bmp);
			
			if(autoSave2Disk){
				imageDiscCache.put(key, bmp);
			}
			
			return true;
		}
		return false;
	}

	@Override
	public boolean put(String key, byte[] bytes) {
		if(TextUtils.isEmpty(key) || bytes == null){
			return false;
		}
		
		Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		if(bmp != null){
			this.lruCache.put(key, bmp);
			
			if(autoSave2Disk){
				imageDiscCache.put(key, bmp);
			}
			
			return true;
		}
		
		return false;
	}
	
	@Override
	public boolean put(String key, String sourceFilePath) {
	    Bitmap bmp = get(key);
        if(bmp != null){
        	return true;
        }
        String path = this.imageDiscCache.copy(sourceFilePath, key);
		if(path != null){
			return true;
		}
        return false;
	}

	@Override
	public Bitmap get(String key) {
		if (!TextUtils.isEmpty(key)) {
			Bitmap bmp = lruCache.get(key);
			
			if(bmp == null && autoFetchFromDisk){
				bmp = imageDiscCache.get(key);
				if(bmp != null){
					put(key, bmp);
				}
			}
			return bmp;
		}
		return null;
	}

	@Override
	public void remove(String key) {
		if(!TextUtils.isEmpty(key)){
			lruCache.remove(key);
		}
	}

	@Override
	public Collection<String> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		lruCache.evictAll();
	}



}
