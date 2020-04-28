package com.shortener.app.service;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shortener.app.model.UrlData;
import com.shortener.app.util.IdRandomUtil;

@Service
public class URLDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLDataService.class);
	
	//데이터 베이스를 쓰지 않으므로 두개의 인덱스 필요
	/**
	 * key shorturl value UrlData
	 */
	private Map<String, UrlData> shortUrlKeys = new ConcurrentHashMap<String, UrlData>();
	
	/**
	 * key longurl  value UrlData
	 */
	private Map<String, UrlData> longUrlKeys = new ConcurrentHashMap<String, UrlData>();
	
	/**
	 * url 정보를 추가한다.
	 * @param longUrl
	 * @return 생성된 short url 객체
	 */
	public UrlData addUrl(String longUrl) {
	    
		//키가 없는 경우 
		UrlData urlData = longUrlKeys.computeIfAbsent(longUrl, url -> {
			String shortUrl = IdRandomUtil.getShortUrl();
			//생성된 shorturl 이 이미 다른곳에 할당된 경우 새로운 키를 얻음
			while (shortUrlKeys.containsKey(shortUrl)) {
				shortUrl = IdRandomUtil.getShortUrl();
			}
			UrlData urlData1 = new UrlData(shortUrl, url);
			shortUrlKeys.put(shortUrl, urlData1);
			return urlData1;
		});
		
		return urlData;
		
		/*
		 * String shortUrl = (String)longUrlKeys.get(longUrl);
		 * 
		 * if (shortUrl == null) { // 중복된 short url인지 확인하여, 신규 short url이 나올때 까지 루핑
		 * while (shortUrl == null || shortUrlKeys.get(shortUrl) != null) { shortUrl =
		 * IdRandomUtil.getShortUrl(); } }
		 * 
		 * UrlData urlData = new UrlData(shortUrl, longUrl);
		 * 
		 * shortUrlKeys.put(shortUrl, urlData); longUrlKeys.put(longUrl, shortUrl);
		 * 
		 * return urlData;
		 */
	}

	/**
	 * short url 정보 가져오기
	 * @param longUrl
	 * @return
	 */
    public String getShortUrl(String longUrl) {
//    	longUrlKeys.computeIfPresent(longUrl, new BiFunction<String, String, String>() {
//
//			@Override
//			public String apply(String t, String u) {
//				
//				return null;
//			}
//    	});
//    	
//    	String shortUrl = (String)longUrlKeys.get(longUrl);
//    	
//    	if (shortUrl == null) {
//    		UrlData urlData = addUrl(longUrl);
//    		shortUrl = urlData.getShortUrl();
//    		
//    		LOGGER.info("hit count : " + urlData.getHitCount());
//    	}
//        return shortUrl;
    	UrlData urlData = addUrl(longUrl);
    	
    	LOGGER.info("hit count : " + urlData.getHitCount());
    	return urlData.getShortUrl();
    }

    /**
     * long url 정보를 가져온다.
     * @param shortUrl
     * @return
     * @throws Exception
     */
    public String getLongUrl(String shortUrl) throws Exception {
    	UrlData urlData = shortUrlKeys.get(shortUrl);
    	
    	if (urlData != null) {
	    	String longUrl = urlData.getLongUrl();
	    	//hitcount incr
	    	urlData.incrHitCount();
	    	
	    	LOGGER.info("hit count : " + urlData.getHitCount());
	    	return longUrl;
    	}
    	throw new Exception("Unkown short URL"); 
    }
}
