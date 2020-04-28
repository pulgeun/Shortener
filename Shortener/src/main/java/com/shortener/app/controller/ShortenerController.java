package com.shortener.app.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.shortener.app.service.URLDataService;
import com.shortener.app.util.UrlValidator;

@Controller
public class ShortenerController {
	private static final Logger LOGGER = LoggerFactory.getLogger(ShortenerController.class);
	
	
	@Autowired
	URLDataService urlDataService;
	
	/**
	 * ù������ ȭ�� ��� url�� redirect ����
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/", method=RequestMethod.GET)
	public String home() throws Exception {
		return "redirect:shortener/exchange";
	}
	
	/**
	 * ȭ�� ��� url
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shortener/exchange", method=RequestMethod.GET)
	public ModelAndView shortenUrl() throws Exception {
		ModelAndView mv = new ModelAndView("exchangeUrl");
		return mv;
		//return "exchangeUrl";
	}
	
	/**
	 * �ٲ��ִ� �޼���
	 * @param longUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/shortener/exchange", method=RequestMethod.POST)
	public @ResponseBody String getShortenUrl(@RequestParam String longUrl) throws Exception {
		
		LOGGER.info("longUrl : "+longUrl);
		if (UrlValidator.checkUrl(longUrl)) {
			
			String shortUrl = urlDataService.getShortUrl(longUrl);
			
			LOGGER.info("shortUrl : "+shortUrl);
			return shortUrl ;
			
		}
		return "Unkown URL";
	}
	
	
	/**
	 * �����̷�Ʈ �޼���
	 * @param shortUrl
	 * @return
	 * @throws Exception
	 */
	@RequestMapping(value = "/{shortUrl}", method=RequestMethod.GET)
	public String redirectUrl(@PathVariable("shortUrl") String shortUrl) throws Exception {
		
		String longUrl = urlDataService.getLongUrl(shortUrl);
		return "redirect:"+longUrl;
	}
	
	
	/**
	 * ���ã�� ������ ã�� url ó��
	 */
	@RequestMapping("favicon.ico") 
	public @ResponseBody void returnNoFavicon() {
	}

}
