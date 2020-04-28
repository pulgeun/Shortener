package com.shortener.app;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.shortener.app.controller.ShortenerController;
import com.shortener.app.service.URLDataService;

@RunWith(SpringRunner.class)
@WebMvcTest(ShortenerController.class)
@AutoConfigureMockMvc
@ComponentScan("com.shortener.app.service")
public class ShortenerApplicationTests {

	@Autowired
	private MockMvc mockMvc;

	
	@Autowired
	URLDataService urlDataService;
	 
	@Before
	public void setUp() {
		// mvc = MockMvcBuilders.standaloneSetup(controller).build();
	}

//	@Test
//	public void getShortenerUrlTestaaa() throws Exception {
//		
//		String longUrl = "https://www.naver.com/";
//		String longUrl2 = "https://en.wikipedia.org/wiki/URL_shortening";
//
//
//		String shortUrl1 = urlDataService.getShortUrl(longUrl);
//		String shortUrl2 = urlDataService.getShortUrl(longUrl2);
//
//		assertEquals(shortUrl1, shortUrl2);
//
//	}
	 

	@Test
	public void getShortenerUrlTest2() throws Exception {

		mockMvc.perform(get("/shortener/exchange")).andDo(print());
	}
	
	
	@Test
	public void getShortenerUrlTest3() throws Exception {
		mockMvc.perform(post("/shortener/exchange")
					.param("longUrl", "https://www.naver.com/")
					.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk());
		
		//String content = result.getResponse().getContentAsString();
		
	}

	@Test
	public void getShortenerTest2() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/shortener/exchange")
				.param("longUrl", "https://www.naver.com/")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		int status1 = mvcResult.getResponse().getStatus();
		assertEquals(200, status1);

		String content = mvcResult.getResponse().getContentAsString();

		MvcResult mvcResult2 = mockMvc.perform(post("/shortener/exchange")
				.param("longUrl", "https://www.naver.com/")
				.contentType(MediaType.APPLICATION_JSON))
				.andDo(print())
				.andReturn();

		int status2 = mvcResult2.getResponse().getStatus();
		assertEquals(200, status2);

		String content2 = mvcResult.getResponse().getContentAsString();

		assertEquals(content, content2);
	}

}
