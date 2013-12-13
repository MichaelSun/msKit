package com.plugin.common.download;

import com.plugin.common.cache.memory.MemoryCacheOption;

public class DownloaderOption {

	private  String downloadTmpPath;
	
	public String getDownloadTmpPath() {
		return downloadTmpPath;
	}
	
	public void setDownloadTmpPath(String downloadTmpPath) {
		this.downloadTmpPath = downloadTmpPath;
	}
	
	private boolean isStop;
	
	public boolean isStop() {
		return isStop;
	}
	
	public void setStop(boolean isStop) {
		this.isStop = isStop;
	}
	
	public static final int DEFAULT_KEEPALIVE = 5 * 1000;
	
	private int keepAlive = DEFAULT_KEEPALIVE;
	
	public int getKeepAlive() {
		return keepAlive;
	}
	
	public void setKeepAlive(int keepAlive) {
		this.keepAlive = keepAlive;
	}
	
	private MemoryCacheOption memoryOption;
	
	public MemoryCacheOption getMemoryOption() {
		return memoryOption;
	}
	
	public void setMemoryOption(MemoryCacheOption memoryOption) {
		this.memoryOption = memoryOption;
	}
}
