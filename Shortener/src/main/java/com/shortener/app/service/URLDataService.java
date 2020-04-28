package com.shortener.app.service;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.shortener.app.util.IdRandomUtil;

@Service
public class URLDataService {

	private static final Logger LOGGER = LoggerFactory.getLogger(URLDataService.class);
	private Map<String, Object> urlInfos = new HashMap<String, Object>();
	private Map<String, String> urlKeys = new HashMap<String, String>();
	
	private String SHORT_URL = "SHORT-URL";
	private String LONG_URL = "LONG-URL";
	private String HIT_COUNT = "HIT-COUNT";
	
	/**
	 * short url ���� ��������
	 * @param longUrl
	 * @return
	 */
    public String getShortUrl(String longUrl) {
        
    	String shortUrl = (String)urlKeys.get(longUrl);
    	
    	if (shortUrl == null) {
    		Map<String, Object> urlInfo = addUrl(longUrl);
    		shortUrl = (String)urlInfo.get(SHORT_URL);
    	}
        return shortUrl;
    }

    /**
     * url ������ �߰��Ѵ�.
     * @param longUrl
     * @return
     */
    public Map<String, Object> addUrl(String longUrl) {
        
    	String shortUrl = (String)urlKeys.get(longUrl);
    	
    	if (shortUrl == null) {
    		// �ߺ��� short url���� Ȯ���Ͽ�, �ű� short url�� ���ö� ���� ����
    		while (shortUrl == null || urlInfos.get(shortUrl) != null) {
    			shortUrl = IdRandomUtil.getShortUrl();
    		}
    	}
    	
    	Map<String, Object> urlInfo = new HashMap<String, Object>();
    	urlInfo.put(SHORT_URL, shortUrl);
    	urlInfo.put(LONG_URL, longUrl);
    	urlInfo.put(HIT_COUNT, 0);
    	
    	urlInfos.put(shortUrl, urlInfo);
    	urlKeys.put(longUrl, shortUrl);
    	
    	return urlInfo;
    }

    /**
     * long url ������ �����´�.
     * @param shortUrl
     * @return
     * @throws Exception
     */
    public String getLongUrl(String shortUrl) throws Exception {
        
    	Map<String, Object> urlInfo = (HashMap)urlInfos.get(shortUrl);
    	
    	if (urlInfo != null) {
	    	String longUrl = (String)urlInfo.get(LONG_URL);
	    	int hitCount = (int)urlInfo.get(HIT_COUNT);
	    	
	    	// hit count�� ���� ��Ų��.
	    	hitCount++;
	    	urlInfo.put(HIT_COUNT, hitCount);
	    	
	    	return longUrl;
    	}
    	throw new Exception("Unkown URL"); 
    }
}
