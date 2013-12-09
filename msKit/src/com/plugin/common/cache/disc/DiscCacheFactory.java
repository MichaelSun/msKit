package com.plugin.common.cache.disc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import com.plugin.common.cache.disc.impl.FileDiscCache;
import com.plugin.common.cache.disc.impl.ImageDiscCache;

public class DiscCacheFactory {

	public static DiscCacheFactory gDiscCacheMgr = null;
	
	private Map<String,IDiscCache> discCaches = Collections.synchronizedMap(new HashMap<String, IDiscCache>());
	
	public static DiscCacheFactory getInstance(){
		if(gDiscCacheMgr == null){
			synchronized (DiscCacheFactory.class) {
				if(gDiscCacheMgr == null){
					gDiscCacheMgr = new DiscCacheFactory();
				}
			}
		}
		
		return gDiscCacheMgr;
	}
	
	private DiscCacheFactory() {
		
	}
	
	
	public IDiscCache getDiscCache(DiscCacheOption option){
		IDiscCache discCache = discCaches.get(option.getDisCachedir());
		if(discCache == null){
			if(option.getCategory().equals(DiscCacheOption.CATEGORY_FILE)){
				discCache = new FileDiscCache(option);
			}
			if(option.getCategory().equals(DiscCacheOption.CATEGORY_IMAGE)){
				discCache = new ImageDiscCache(option);
			}
			
			this.discCaches.put(option.getDisCachedir(), discCache);
			
		}
		
		return discCache;
	}
	
	public void clear(){
		discCaches.clear();
	}
	
	
}
