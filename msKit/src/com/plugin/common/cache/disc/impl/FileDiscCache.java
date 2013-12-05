package com.plugin.common.cache.disc.impl;

import java.io.File;

import com.plugin.common.cache.disc.BasicDiscCache;
import com.plugin.common.cache.disc.DiscCacheOption;
import com.plugin.common.cache.disc.IDiscCache;
import com.plugin.common.cache.disc.naming.FileNameGenerator;

public class FileDiscCache extends BasicDiscCache<File> {

	private static final String ERROR_ARG_NULL = "\"%s\" argument must be not null";

	private static FileDiscCache gDiscCache;

	public static IDiscCache getIntance(DiscCacheOption option) {
		if (option == null) {
			throw new IllegalArgumentException(String.format(ERROR_ARG_NULL,
					option));
		}

		if (gDiscCache == null) {
			synchronized (FileDiscCache.class) {
				if (gDiscCache == null) {
					gDiscCache = new FileDiscCache(new File(
							option.getDisCachedir()),
							option.getNameGenerator(),
							option.getDiscCacheSize());
				}
			}
		}

		return gDiscCache;

	}

	// private FileDiscCache(File dir) {
	// super(dir);
	// }

	private FileDiscCache(File dir, FileNameGenerator nameGenerator,
			int discCacheSize) {
		super(dir, nameGenerator, discCacheSize);
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
