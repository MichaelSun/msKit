package com.plugin.common.cache.disc;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicInteger;

import com.plugin.common.cache.disc.naming.FileNameGenerator;
import com.plugin.common.cache.disc.naming.HashCodeFileNameGenerator;
import com.plugin.common.cache.disc.utils.DisCacheUtil;

public abstract class BasicDiscCache<T> implements IDiscCache<T>{

	private static final String ERROR_ARG_NULL = "\"%s\" argument must be not null";
	
	protected File discDir;
	
	protected FileNameGenerator fileNameGenerator;
	
	private Object obj = new Object();
	
	protected int discCacheSize;
	
//	public BasicDiscCache(File dir) {
//		this(dir, new HashCodeFileNameGenerator());
//	}
	
	public BasicDiscCache(File dir, FileNameGenerator nameGenerator, int discCacheSize){
		if(dir == null){
			throw new IllegalArgumentException(String.format(ERROR_ARG_NULL, "dir"));
		}
		
		if(nameGenerator == null){
			throw new IllegalArgumentException(String.format(ERROR_ARG_NULL, "FileNameGenerator"));
		}
		
		this.discDir = dir;
		this.fileNameGenerator = nameGenerator;
		this.discCacheSize = discCacheSize;
	}
	
	
	@Override
	public String put(String key, InputStream in) {
		String fileName = fileNameGenerator.generate(key);
		File file = new File(discDir, fileName);
		String fullName = DisCacheUtil.saveFileByStream(in, file);
		return fullName;
	}
	
	@Override
	public String put(String key, byte[] bytes) {
		String fileName = fileNameGenerator.generate(key);
		File file = new File(discDir, fileName);
		String fullName = DisCacheUtil.saveFileByBytes(file, bytes);
		return fullName;
	}


	@Override
	public boolean remove(String key) {
		String fileName = fileNameGenerator.generate(key);
		synchronized (obj) {
			File[] files = discDir.listFiles();
			if (files != null) {
				for (File f : files) {
					if(f.getName().equals(fileName)){
						f.delete();
						return true;
					}
				}
			}
		}
		return false;
	}

	@Override
	public void clear() {
		File[] files = discDir.listFiles();
		if (files != null) {
			for (File f : files) {
				f.delete();
			}
		}
	}
	
}
