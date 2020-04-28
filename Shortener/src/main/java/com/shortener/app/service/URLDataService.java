package com.shortener.app.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shortener.app.model.UrlData;
import com.shortener.app.util.IdRandomUtil;

@Service
public class URLDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLDataService.class);
	private Map<String, Object> urlInfoData = new HashMap<String, Object>();
	private Map<String, String> urlKeys = new HashMap<String, String>();
	
	/**
	 * short url ���� ��������
	 * @param longUrl
	 * @return
	 */
    public String getShortUrl(String longUrl) {
        
    	String shortUrl = (String)urlKeys.get(longUrl);
    	
    	if (shortUrl == null) {
    		UrlData urlData = addUrl(longUrl);
    		shortUrl = urlData.getShortUrl();
    		
    		LOGGER.info("hit count : " + urlData.getHitCount());
    	}
        return shortUrl;
    }

    /**
     * url ������ �߰��Ѵ�.
     * @param longUrl
     * @return
     */
    public UrlData addUrl(String longUrl) {
        
    	String shortUrl = (String)urlKeys.get(longUrl);
    	
    	if (shortUrl == null) {
    		// �ߺ��� short url���� Ȯ���Ͽ�, �ű� short url�� ���ö� ���� ����
    		while (shortUrl == null || urlInfoData.get(shortUrl) != null) {
    			shortUrl = IdRandomUtil.getShortUrl();
    		}
    	}
    	
    	UrlData urlData = new UrlData(shortUrl, longUrl);
    	
    	urlInfoData.put(shortUrl, urlData);
    	urlKeys.put(longUrl, shortUrl);
    	
    	return urlData;
    }

    /**
     * long url ������ �����´�.
     * @param shortUrl
     * @return
     * @throws Exception
     */
    public String getLongUrl(String shortUrl) throws Exception {
        
    	UrlData urlData = (UrlData)urlInfoData.get(shortUrl);
    	
    	if (urlData != null) {
	    	String longUrl = urlData.getLongUrl();
	    	int hitCount = urlData.getHitCount();
	    	
	    	// hit count�� ���� ��Ų��.
	    	hitCount++;
	    	urlData.setHitCount(hitCount);
	    	
	    	LOGGER.info("hit count : " + urlData.getHitCount());
	    	
	    	return longUrl;
    	}
    	throw new Exception("Unkown URL"); 
    }
}
