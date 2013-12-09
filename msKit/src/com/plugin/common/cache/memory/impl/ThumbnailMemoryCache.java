package com.plugin.common.cache.memory.impl;

import com.plugin.common.cache.memory.MemoryCacheOption;

public class ThumbnailMemoryCache extends BaseImageMemoryCache{

	private static ThumbnailMemoryCache gThumbnailMemoryCache;
	
	public static ThumbnailMemoryCache getIntance(MemoryCacheOption option){
		if(gThumbnailMemoryCache == null){
			synchronized (ThumbnailMemoryCache.class) {
				if(gThumbnailMemoryCache == null){
					gThumbnailMemoryCache = new ThumbnailMemoryCache(option);
					return gThumbnailMemoryCache;
				}
			}
		}
		
		return gThumbnailMemoryCache;
	}
	
	private ThumbnailMemoryCache(MemoryCacheOption option) {
		super(option);
	}

}
