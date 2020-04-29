package com.shortener.app.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.Assert;
import org.junit.Test;

import com.shortener.app.model.UrlData;
import com.shortener.app.util.IdRandomUtil;

public class URLDataServiceTest {

	/**
	 * longurl�� �ְ� shorturl ����
	 * ��ȯ�� shorturl �� �ٽ� ���� longUrl �� ��
	 */
	@Test
	public void longUrlForShortUrlTest() {
		URLDataService service = new URLDataService();
		String longUrl = "http://google.com/";
		UrlData data = service.addUrl(longUrl);
		assertNotNull(data);
		Assert.assertEquals(longUrl, data.getLongUrl());

		//shorturl �� �ٽ� ������ longurl �� ��ġ�ϴ��� �׽�Ʈ
		String shortUrl = data.getShortUrl();
		
		String asserrLongUrl = service.getLongUrl(shortUrl);
		Assert.assertEquals(longUrl, asserrLongUrl);
	}
	
	/**
	 * �ٸ� URL �� ��� �ٸ� shorturl �� ��������� 
	 * @throws Exception
	 */
	@Test
	public void getShortenerUrlTest() throws Exception {
		URLDataService service = new URLDataService();
		String longUrl1 = "https://www.naver.com/";
		String longUrl2 = "http://www.naver.com/";


		String shortUrl1 = service.getShortUrl(longUrl1);
		String shortUrl2 = service.getShortUrl(longUrl2);

		assertNotEquals(shortUrl1, shortUrl2);
	}
	
	/**
	 * ���� url�� ���� ��� ������ short �� ��������� �׽�Ʈ
	 */
	@Test
	public void duplicateLongUrlForShortUrlTest() {
		URLDataService service = new URLDataService();
		String longUrl = "http://google.com/";
		
		UrlData data1 = service.addUrl(longUrl);
		assertNotNull(data1);
		String shortUrl1 = data1.getShortUrl();
		
		UrlData data2 = service.addUrl(longUrl);
		assertNotNull(data2);
		String shortUrl2 = data2.getShortUrl();
		
		Assert.assertEquals(shortUrl1, shortUrl2);
	}
	
	/**
	 * ��ϵ��� ���� url Ž�� ����
	 */
	@Test(expected = IllegalArgumentException.class)
	public void notFoundExceptionTest() {
		URLDataService service = new URLDataService();

		//������ 8���ڸ� ����
		String shortUrl = IdRandomUtil.generate();
		
		String asserrLongUrl = service.getLongUrl(shortUrl);
		Assert.assertNull(asserrLongUrl);
	}
}
