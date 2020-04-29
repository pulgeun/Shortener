package com.shortener.app.model;

import java.util.concurrent.atomic.AtomicInteger;

public class UrlData {
	
	private String shortUrl;
	private String longUrl;
	private AtomicInteger count = new AtomicInteger(0);

	public UrlData() {}

	public UrlData(String shortUrl, String longUrl) {
		this.shortUrl = shortUrl;
		this.longUrl = longUrl;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public String getLongUrl() {
		return longUrl;
	}

	public int getHitCount() {
		return count.get();
	}

	public int incrHitCount() {
		return count.getAndIncrement();
	}
	
}
