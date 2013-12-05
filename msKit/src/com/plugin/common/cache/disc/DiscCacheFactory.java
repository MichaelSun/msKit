package com.plugin.common.cache.disc;

import com.plugin.common.cache.disc.DiscCacheOption.DiscCacheCategory;
import com.plugin.common.cache.disc.impl.FileDiscCache;

public class DiscCacheFactory {

	public static DiscCacheFactory gDiscCache = new DiscCacheFactory();
	
	public static synchronized IDiscCache getInstance(DiscCacheCategory category, DiscCacheOption option){
		switch(category){
		case DIR_FILE:
			FileDiscCache.getIntance(option);
			break;
		case DIR_IMAGE:
			break;
		case DIR_THUMBNAIL:
			break;
		}
		return null;
	}
	

	
}
