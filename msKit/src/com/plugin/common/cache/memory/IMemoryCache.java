package com.plugin.common.cache.memory;

import java.io.InputStream;
import java.util.Collection;
import java.util.Collections;

public interface IMemoryCache<K,V> {

	boolean put(K key,V value);
	
	boolean put(K key, InputStream in);
	
	boolean put(K key, byte[] bytes);
	
	V get(K key);
	
	void remove(K key);
	
	Collection<K> keys();
	
	void clear();
}
