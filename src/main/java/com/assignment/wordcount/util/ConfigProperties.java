package com.assignment.wordcount.util;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@ConfigurationProperties(prefix = "wordcount")
@Component
public class ConfigProperties {
	
	private String fileUploadRootDir ="temp";
	private int forkJoinThreadPoolSize = Runtime.getRuntime().availableProcessors();
	
	public String getFileUploadRootDir() {
		return fileUploadRootDir;
	}
	public void setFileUploadRootDir(String fileUploadRootDir) {
		this.fileUploadRootDir = fileUploadRootDir;
	}
	public int getForkJoinThreadPoolSize() {
		return forkJoinThreadPoolSize;
	}
	public void setForkJoinThreadPoolSize(int forkJoinThreadPoolSize) {
		this.forkJoinThreadPoolSize = forkJoinThreadPoolSize;
	}
	
}
