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
		//TODO longurl�� �ְ� shorturl ����
		//shorturl �� �ٽ� ������ longurl �� ��ġ�ϴ��� �׽�Ʈ
	}
}
