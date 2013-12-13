package com.plugin.common.cache.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.plugin.common.cache.disc.DiscCacheOption;
import com.plugin.common.cache.disc.naming.HashCodeFileNameGenerator;

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
	 * 
	 * @param maxMemoryCacheSize
	 *            unite by MB
	 */
	public void setMaxMemoryCacheSize(int maxMemoryCacheSize) {
		this.maxMemoryCacheSize = MB * maxMemoryCacheSize;
	}

	/**
	 * memory Cache category
	 */
	public static final String IMAGE_CACHE_CATEGORY_USER_HEAD_ROUNDED = "user_head_rounded";
	public static final String IMAGE_CACHE_CATEGORY_RAW = "image_cache_category_source";
	public static final String IMAGE_CACHE_CATEGORY_THUMB = "image_cache_category_thumb";
	public static final String IMAGE_CACHE_CATEGORY_SMALL = "image_cache_category_small";



	private Map<String, DiscCacheOption> discCaches = new HashMap<String, DiscCacheOption>();

	public MemoryCacheOption() {
		discCaches.put(IMAGE_CACHE_CATEGORY_USER_HEAD_ROUNDED,
				new DiscCacheOption(IMAGE_CACHE_CATEGORY_USER_HEAD_ROUNDED,
						IMAGE_CACHE_CATEGORY_USER_HEAD_ROUNDED_DIR,
						new HashCodeFileNameGenerator()));
		discCaches.put(IMAGE_CACHE_CATEGORY_RAW, new DiscCacheOption(
				IMAGE_CACHE_CATEGORY_RAW, IMAGE_CACHE_CATEGORY_RAW_DIR,
				new HashCodeFileNameGenerator()));
		discCaches.put(IMAGE_CACHE_CATEGORY_THUMB, new DiscCacheOption(
				IMAGE_CACHE_CATEGORY_THUMB, IMAGE_CACHE_CATEGORY_THUMB_DIR,
				new HashCodeFileNameGenerator()));
		discCaches.put(IMAGE_CACHE_CATEGORY_SMALL, new DiscCacheOption(
				IMAGE_CACHE_CATEGORY_SMALL, IMAGE_CACHE_CATEGORY_SMALL_DIR,
				new HashCodeFileNameGenerator()));
	}

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

	private DiscCacheOption discCacheOption;

	public DiscCacheOption getDiscCacheOption() {
		return discCacheOption;
	}

	public void setDiscCacheOption(DiscCacheOption discCacheOption) {
		this.discCacheOption = discCacheOption;
	}

}
