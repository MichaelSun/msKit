package com.plugin.common.cache.memory.impl;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.InputStream;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.plugin.common.cache.disc.DiscCacheFactory;
import com.plugin.common.cache.disc.DiscCacheOption;
import com.plugin.common.cache.disc.IDiscCache;
import com.plugin.common.cache.disc.impl.ImageDiscCache;
import com.plugin.common.cache.disc.naming.HashCodeFileNameGenerator;
import com.plugin.common.cache.memory.IMemoryCache;
import com.plugin.common.cache.memory.MemoryCacheOption;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.text.TextUtils;

public class BaseImageMemoryCache implements IMemoryCache<String, Bitmap> {


    /**
     * memory Cache category
     */
    public static final String IMAGE_CACHE_CATEGORY_USER_HEAD_ROUNDED = "user_head_rounded";
    public static final String IMAGE_CACHE_CATEGORY_RAW = "image_cache_category_source";
    public static final String IMAGE_CACHE_CATEGORY_THUMB = "image_cache_category_thumb";
    public static final String IMAGE_CACHE_CATEGORY_SMALL = "image_cache_category_small";

	protected LruCache<String, Bitmap> lruCache;
	
	private ImageDiscCache imageDiscCache;
	
	private boolean autoSave2Disk;
	
	private boolean autoFetchFromDisk;

    private Map<String, IDiscCache>  discCaches = new HashMap<String, IDiscCache>();
	
	protected BaseImageMemoryCache(MemoryCacheOption option) {
		this.lruCache = new LruCache<String, Bitmap>(
				option.getMaxMemoryCacheSize()) {
			@Override
			protected int sizeOf(String key, Bitmap bmp) {
				return bmp.getRowBytes() * bmp.getHeight();
			}
		};
		
		imageDiscCache = (ImageDiscCache) DiscCacheFactory.getInstance().getDiscCache(option.getDiscCacheOption());
		
		autoSave2Disk = option.isAutoSave2Disk();
		autoFetchFromDisk = option.isAutoFetchFromDisk();
	}

	@Override
	public boolean put(String category, String key, Bitmap value) {
		if(TextUtils.isEmpty(category)|| TextUtils.isEmpty(key) || value == null){
			return false;
		}
        String formatKey = makeFileKeyName(category, key);
		this.lruCache.put(formatKey, value);
		save2Disc(autoSave2Disk,category,key,value);
		return true;
	}

	@Override
	public boolean put(String category, String key, InputStream in) {
		if(TextUtils.isEmpty(category)|| TextUtils.isEmpty(key) || in == null){
			return false;
		}
        String formatKey = makeFileKeyName(category, key);
        BufferedInputStream bufferedInputStream = new BufferedInputStream(in);
		Bitmap bmp = BitmapFactory.decodeStream(bufferedInputStream);
		if(bmp != null){
			this.lruCache.put(formatKey, bmp);

            save2Disc(autoSave2Disk,category,key,in);

            return true;
		}
		return false;
	}

	@Override
	public boolean put(String category, String key, byte[] bytes) {
		if(TextUtils.isEmpty(category)|| TextUtils.isEmpty(key) || bytes == null){
			return false;
		}
        String formatKey = makeFileKeyName(category, key);
        Bitmap bmp = BitmapFactory.decodeByteArray(bytes, 0, bytes.length);
		if(bmp != null){
			this.lruCache.put(formatKey, bmp);

            save2Disc(autoSave2Disk,category,key,bytes);
            return true;
		}
		
		return false;
	}
	
	@Override
	public boolean put(String category, String key, String sourceFilePath) {
	    if(TextUtils.isEmpty(category)|| TextUtils.isEmpty(key)|| TextUtils.isEmpty(sourceFilePath)){
            return false;
        }
        Bitmap bmp = get(category,key);
        if(bmp != null){
        	return true;
        }
        String formatKey = makeFileKeyName(category, key);
        String path = this.imageDiscCache.copy(sourceFilePath, formatKey);
		if(path != null){
			return true;
		}
        return false;
	}

	@Override
	public Bitmap get(String category, String key) {
		if (!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key)) {
			Bitmap bmp = lruCache.get(key);
			
			if(bmp == null && autoFetchFromDisk){
				bmp = fetchFromDisc(category,key);
				if(bmp != null){
					put(category, key, bmp);
				}
			}
			return bmp;
		}
		return null;
	}

	@Override
	public void remove(String category, String key) {
		if(!TextUtils.isEmpty(category) && !TextUtils.isEmpty(key)){
			lruCache.remove(key);
		}
	}


	@Override
	public void clear() {
		lruCache.evictAll();
        clearAllDisc();
	}

    protected String makeFileKeyName(String category, String key) {
        StringBuilder sb = new StringBuilder(256);
        sb.append(category).append("/").append(key);
        return sb.toString();
    }

    private void save2Disc(boolean autoSave2Disk, String category, String key, Bitmap value){
        if(autoSave2Disk){
            ImageDiscCache imageCache = (ImageDiscCache) this.discCaches.get(category);
            if(imageCache != null){
                imageCache.put(key,value);
                return;
            }

            createNewCategoryDisc(category);
            if(imageCache != null){
                imageCache.put(key,value);
                return;
            }
        }
    }

    private void save2Disc(boolean autoSave2Disk, String category, String key, byte[] bytes){
        if(autoSave2Disk){
            ImageDiscCache imageCache = (ImageDiscCache) this.discCaches.get(category);
            if(imageCache != null){
                imageCache.put(key,bytes);
                return;
            }

            createNewCategoryDisc(category);
            if(imageCache != null){
                imageCache.put(key,bytes);
                return;
            }
        }
    }

    private void save2Disc(boolean autoSave2Disk, String category, String key, InputStream in){
        if(autoSave2Disk){
            ImageDiscCache imageCache = (ImageDiscCache) this.discCaches.get(category);
            if(imageCache != null){
                imageCache.put(key,in);
                return;
            }

            createNewCategoryDisc(category);
            if(imageCache != null){
                imageCache.put(key,in);
                return;
            }
        }
    }

    private Bitmap fetchFromDisc(String category, String key){
            ImageDiscCache imageCache = (ImageDiscCache) this.discCaches.get(category);
            if(imageCache != null){
                return imageCache.get(key);
            }
            return null;
    }

    private void clearAllDisc(){
        Iterator<Map.Entry<String, IDiscCache>> iterator= this.discCaches.entrySet().iterator();
        while(iterator.hasNext()){
            Map.Entry<String, IDiscCache> entry = iterator.next();
            ImageDiscCache discCache = (ImageDiscCache) entry.getValue();
            discCache.clear();
        }

        this.discCaches.clear();

    }

    //TODO  路径尚未配置好
    private void createNewCategoryDisc(String category){
        DiscCacheOption option = new DiscCacheOption(category,new HashCodeFileNameGenerator());
        ImageDiscCache imageDiscCache = new ImageDiscCache(option);
        this.discCaches.put(category, imageDiscCache);
    }

}
