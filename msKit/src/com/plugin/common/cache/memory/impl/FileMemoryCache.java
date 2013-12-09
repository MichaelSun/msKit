package com.plugin.common.cache.memory.impl;

import java.io.File;
import java.io.InputStream;
import java.util.Collection;

import com.plugin.common.cache.disc.DiscCacheFactory;
import com.plugin.common.cache.disc.DiscCacheOption;
import com.plugin.common.cache.disc.impl.FileDiscCache;
import com.plugin.common.cache.memory.IMemoryCache;
import com.plugin.common.cache.memory.MemoryCacheOption;

public class FileMemoryCache implements IMemoryCache<String, File>{
	
	private static FileMemoryCache gFileMemoryCache;
	
	private FileDiscCache fileDiscCache;
	
	private MemoryCacheOption mOption;
	
	public static FileMemoryCache getInstance(MemoryCacheOption option){
		if(gFileMemoryCache == null){
			synchronized (FileMemoryCache.class) {
				if(gFileMemoryCache == null){
					return new FileMemoryCache(option);
				}
			}
		}
		
		return gFileMemoryCache;
	}
	
	
	
	private FileMemoryCache(MemoryCacheOption option) {
		DiscCacheOption discCacheOption = option.getDiscCacheOption();
		fileDiscCache = (FileDiscCache) DiscCacheFactory.getInstance().getDiscCache(discCacheOption);
		this.mOption = option;
	}
	
	@Override
	public boolean put(String key, File value) {
		return false;
	}

	@Override
	public boolean put(String key, InputStream in) {
		fileDiscCache.put(key, in);
		return true;
	}

	@Override
	public boolean put(String key, byte[] bytes) {
		fileDiscCache.put(key, bytes);
		return false;
	}

	@Override
	public File get(String key) {
		return fileDiscCache.get(key);
	}

	@Override
	public void remove(String key) {
		fileDiscCache.remove(key);
	}

	@Override
	public Collection<String> keys() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void clear() {
		fileDiscCache.clear();
	}


}
