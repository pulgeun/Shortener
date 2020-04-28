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
	
	//������ ���̽��� ���� �����Ƿ� �ΰ��� �ε��� �ʿ�
	/**
	 * key shorturl value UrlData
	 */
	private Map<String, UrlData> shortUrlKeys = new ConcurrentHashMap<String, UrlData>();
	
	/**
	 * key longurl  value UrlData
	 */
	private Map<String, UrlData> longUrlKeys = new ConcurrentHashMap<String, UrlData>();
	
	/**
	 * url ������ �߰��Ѵ�.
	 * @param longUrl
	 * @return ������ short url ��ü
	 */
	public UrlData addUrl(String longUrl) {
	    
		//Ű�� ���� ��� 
		UrlData urlData = longUrlKeys.computeIfAbsent(longUrl, url -> {
			String shortUrl = IdRandomUtil.getShortUrl();
			//������ shorturl �� �̹� �ٸ����� �Ҵ�� ��� ���ο� Ű�� ����
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
		 * if (shortUrl == null) { // �ߺ��� short url���� Ȯ���Ͽ�, �ű� short url�� ���ö� ���� ����
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
	 * short url ���� ��������
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
     * long url ������ �����´�.
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
