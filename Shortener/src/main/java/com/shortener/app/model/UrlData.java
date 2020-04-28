package com.shortener.app.model;

import java.util.concurrent.atomic.AtomicInteger;

public class UrlData {
	
	private String shortUrl;
	private String longUrl;
//	private int hitCount;
	private AtomicInteger count = new AtomicInteger(0);

	public UrlData() {}

	public UrlData(String shortUrl, String longUrl) {
		this.shortUrl = shortUrl;
		this.longUrl = longUrl;
//		this.hitCount = 0;
	}

	public String getShortUrl() {
		return shortUrl;
	}

//	public void setShortUrl(String shortUrl) {
//		this.shortUrl = shortUrl;
//	}

	public String getLongUrl() {
		return longUrl;
	}

//	public void setLongUrl(String longUrl) {
//		this.longUrl = longUrl;
//	}

	public int getHitCount() {
		return count.get();
	}

	public int incrHitCount() {
		return count.getAndIncrement();
	}
	
//	public void setHitCount(int hitCount) {
//		this.hitCount = hitCount;
//	}
}
