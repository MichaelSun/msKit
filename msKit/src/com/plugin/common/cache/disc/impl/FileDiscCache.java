package com.plugin.common.cache.disc.impl;

import java.io.File;

import com.plugin.common.cache.disc.BasicDiscCache;
import com.plugin.common.cache.disc.DiscCacheOption;
import com.plugin.common.cache.disc.IDiscCache;
import com.plugin.common.cache.disc.naming.FileNameGenerator;

public class FileDiscCache extends BasicDiscCache<File> {


	public FileDiscCache(DiscCacheOption option) {
		super(option);
	}

	@Override
	public File get(String key) {
		String fileName = fileNameGenerator.generate(key);
		File file = new File(discDir, fileName);
		if(file.exists()){
			updateInfoWithGet(file);
		}
		return file;
	}
	
	@Override
	public String put(String key, File src) {
		return null;
	}

	
	

}
