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
	 * longurl을 넣고 shorturl 얻음
	 * 반환돈 shorturl 을 다시 원본 longUrl 과 비교
	 */
	@Test
	public void longUrlForShortUrlTest() {
		URLDataService service = new URLDataService();
		String longUrl = "http://google.com/";
		UrlData data = service.addUrl(longUrl);
		assertNotNull(data);
		Assert.assertEquals(longUrl, data.getLongUrl());

		//shorturl 로 다시 동일한 longurl 과 일치하는지 테스트
		String shortUrl = data.getShortUrl();
		
		String asserrLongUrl = service.getLongUrl(shortUrl);
		Assert.assertEquals(longUrl, asserrLongUrl);
	}
	
	/**
	 * 다른 URL 인 경우 다른 shorturl 이 얻어지는지 
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
	 * 동일 url을 넣은 경우 동일한 short 가 얻어지는지 테스트
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
	 * 등록되지 않은 url 탐색 오류
	 */
	@Test(expected = IllegalArgumentException.class)
	public void notFoundExceptionTest() {
		URLDataService service = new URLDataService();

		//임의의 8문자를 생성
		String shortUrl = IdRandomUtil.generate();
		
		String asserrLongUrl = service.getLongUrl(shortUrl);
		Assert.assertNull(asserrLongUrl);
	}
}
