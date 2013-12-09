package com.plugin.common.cache.memory.impl;

import com.plugin.common.cache.memory.MemoryCacheOption;

public class ImageMemoryCache extends BaseImageMemoryCache{

	private static ImageMemoryCache gImagelMemoryCache;

	public static ImageMemoryCache getIntance(MemoryCacheOption option) {
		if (gImagelMemoryCache == null) {
			synchronized (ImageMemoryCache.class) {
				if (gImagelMemoryCache == null) {
					gImagelMemoryCache = new ImageMemoryCache(option);
					return gImagelMemoryCache;
				}
			}
		}

		return gImagelMemoryCache;
	}

	private ImageMemoryCache(MemoryCacheOption option) {
		super(option);
	}
}
