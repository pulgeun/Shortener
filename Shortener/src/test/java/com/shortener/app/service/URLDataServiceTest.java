package com.shortener.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Assert;
import org.junit.Test;

import com.shortener.app.model.UrlData;

public class URLDataServiceTest {

	
	@Test
	public void longUrlTransShortUrlTest() {
		
		URLDataService service = new URLDataService();
		String longUrl = "http://google.com/";
		UrlData data = service.addUrl(longUrl);
		assertNotNull(data);
		Assert.assertEquals(longUrl, data.getLongUrl());
		//TODO longurl을 넣고 shorturl 얻음
		//shorturl 로 다시 동일한 longurl 과 일치하는지 테스트
	}
}
