package com.plugin.common.cache.memory;

import java.util.HashMap;
import java.util.Map;

import com.plugin.common.cache.disc.DiscCacheOption;

public class MemoryCacheOption {
	
	/**
	 * memory cache size, united by MB
	 */
	private int maxMemoryCacheSize;
	
	private static final int MB = 1024 * 1024; 
	
	public int getMaxMemoryCacheSize() {
		return maxMemoryCacheSize;
	}
	
	/**
	 * 设置memoryCache 大小
	 * @param maxMemoryCacheSize  unite by MB
	 */
	public void setMaxMemoryCacheSize(int maxMemoryCacheSize) {
		this.maxMemoryCacheSize = MB * maxMemoryCacheSize;
	}
	
	/**
	 * memory Cache category
	 */
	public static final String MEMORY_CACHE_CATEGORY_FILE = "memory_cache_category_file";
	
	public static final String MEMORY_CACHE_CATEGORY_IMAGE = "memory_cache_category_image";
	
	
	/**
	 * 是否自动写disc
	 */
	private boolean autoSave2Disk;
	
	public boolean isAutoSave2Disk() {
		return autoSave2Disk;
	}
	
	public void setAutoSave2Disk(boolean autoSave2Disk) {
		this.autoSave2Disk = autoSave2Disk;
	}
	
	
	/**
	 * 是否自动从disc读取
	 */
	private boolean autoFetchFromDisk;
	
	public boolean isAutoFetchFromDisk() {
		return autoFetchFromDisk;
	}
	
	public void setAutoFetchFromDisk(boolean autoFetchFromDisk) {
		this.autoFetchFromDisk = autoFetchFromDisk;
	}
	
	public enum ImageType{
		RAW, SPECIAL
	}
	
	
	private DiscCacheOption discCacheOption;
	
	public DiscCacheOption getDiscCacheOption() {
		return discCacheOption;
	}
	
	public void setDiscCacheOption(DiscCacheOption discCacheOption) {
		this.discCacheOption = discCacheOption;
	}
	
}
