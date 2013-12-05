package com.plugin.common.cache.disc.impl;

import java.io.File;

import android.graphics.Bitmap;

import com.plugin.common.cache.disc.BasicDiscCache;
import com.plugin.common.cache.disc.DiscCacheOption;
import com.plugin.common.cache.disc.IDiscCache;
import com.plugin.common.cache.disc.naming.FileNameGenerator;
import com.plugin.common.cache.disc.utils.DisCacheUtil;
import com.plugin.common.utils.LogUtil;

public class ThumbnailCache extends BasicDiscCache<Bitmap>{

	private static final String ERROR_ARG_NULL = "\"%s\" argument must be not null";

	private static ThumbnailCache gDiscCache;

	public static IDiscCache getIntance(DiscCacheOption option) {
		if (option == null) {
			throw new IllegalArgumentException(String.format(ERROR_ARG_NULL,
					option));
		}

		if (gDiscCache == null) {
			synchronized (FileDiscCache.class) {
				if (gDiscCache == null) {
					gDiscCache = new ThumbnailCache(new File(
							option.getDisCachedir()),
							option.getNameGenerator(),
							option.getDiscCacheSize());
				}
			}
		}

		return gDiscCache;

	}
	
	
	private ThumbnailCache(File dir, FileNameGenerator nameGenerator, int discCacheSize) {
		super(dir, nameGenerator, discCacheSize);
	}

	@Override
	public String put(String key, Bitmap src) {
		String fileName = fileNameGenerator.generate(key);
		File file = new File(discDir, fileName);
		if(DisCacheUtil.compressBitmapToFile(src, file)){
			return file.getAbsolutePath();
		}
		return null;
	}
	
	@Override
	public Bitmap get(String key) {
		String fileName = fileNameGenerator.generate(key);
		File bmpFile =  new File(discDir, fileName);
		if (!bmpFile.exists()) {
            if (LogUtil.UTILS_DEBUG) {
                LogUtil.LOGD("[[getBitmapFromDiskWithReuseBitmap]] file name = " + bmpFile.getName() + " <<false>>");
            }
            return null;
        }
		
		updateInfoWithGet(bmpFile);
		
		Bitmap bmp = DisCacheUtil.loadBitmapWithSizeOrientation(bmpFile);
		
		 if (LogUtil.UTILS_DEBUG) {
			 LogUtil.LOGD("[[getBitmapFromDiskWithReuseBitmap]] file name = " + bmpFile.getName() + " <<true>>");
	        }
	    return bmp;
	}

}
