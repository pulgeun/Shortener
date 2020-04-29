package com.shortener.app;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.validation.ConstraintDeclarationException;
import javax.validation.ConstraintViolationException;

import org.apache.tomcat.util.bcel.classfile.Constant;
import org.hamcrest.Matchers;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

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
	}


	/**
	 * 화면 출력 테스트
	 * @throws Exception
	 */
	@Test
	public void exchangeUrlTest() throws Exception {
		mockMvc.perform(get("/shortener/exchange"))
			.andExpect(status().isOk())
			.andExpect(forwardedUrl("/WEB-INF/jsp/exchangeUrl.jsp"))
			.andDo(print());
	}
	
	
	/**
	 * shorturl 을 얻어 오기 테스트
	 * @throws Exception
	 */
	@Test
	public void getShortenerUrlTest() throws Exception {
		mockMvc.perform(post("/shortener/exchange")
					.param("longUrl", "https://www.naver.com/")
					.contentType(MediaType.APPLICATION_JSON))
		.andDo(print())
		.andExpect(status().isOk())
		.andExpect(content().string(Matchers.hasLength(8)))
		;
		
		//String content = result.getResponse().getContentAsString();
	}

	/**
	 * 같은 url인 경우 같은 shorturl 이 얻어지는지
	 * @throws Exception
	 */
	@Test
	public void getShortenerTest2() throws Exception {

		MvcResult mvcResult = mockMvc.perform(post("/shortener/exchange")
				.param("longUrl", "https://www.naver.com/")
				.contentType(MediaType.APPLICATION_JSON))
//				.andDo(print())
				.andReturn();

		int status1 = mvcResult.getResponse().getStatus();
		assertEquals(200, status1);

		String shortUrl1 = mvcResult.getResponse().getContentAsString();

		MvcResult mvcResult2 = mockMvc.perform(post("/shortener/exchange")
				.param("longUrl", "https://www.naver.com/")
				.contentType(MediaType.APPLICATION_JSON))
//				.andDo(print())
				.andReturn();

		int status2 = mvcResult2.getResponse().getStatus();
		assertEquals(200, status2);

		String shortUrl2 = mvcResult.getResponse().getContentAsString();

		assertEquals(shortUrl1, shortUrl2);
	}
	
	/**
	 * 변환한 shorturl로 다시 longurl 을 얻을 수 있는지 테스트
	 * @throws Exception
	 */
	@Test
	public void getShortenerEqualTest() throws Exception {

		String longUrl = "https://www.naver.com/";
		MvcResult mvcResult1 = mockMvc.perform(post("/shortener/exchange")
				.param("longUrl", longUrl)
				.contentType(MediaType.APPLICATION_JSON))
				.andReturn();

		int status1 = mvcResult1.getResponse().getStatus();
		assertEquals(200, status1);

		String shortUrl1 = mvcResult1.getResponse().getContentAsString();

		MvcResult mvcResult2 = mockMvc.perform(get("/"+shortUrl1))
				.andReturn();

		int status2 = mvcResult2.getResponse().getStatus();
		assertEquals(302, status2);
	
		String longUrl2 = mvcResult2.getResponse().getRedirectedUrl();
		assertEquals(longUrl, longUrl2);
	}
	
	/**
	 * 잘못된 URL 오류
	 * @throws Throwable
	 */
	@Test(expected = ConstraintViolationException.class)
	public void urlValidationException() throws Throwable {

		try {
			MvcResult mvcResult = mockMvc.perform(post("/shortener/exchange")
				.param("longUrl", "htt://www.naver.com")
				.contentType(MediaType.APPLICATION_JSON))
//				.andDo(print())
				.andReturn();
		} catch (Exception e) {
			Throwable cause = e.getCause();
			throw cause;
		}
	}
	
	/**
	 * 잘못된 Short URL 오류
	 * @throws Throwable
	 */
	@Test(expected = ConstraintViolationException.class)
	public void shortLengthException() throws Throwable {

		try {
			MvcResult mvcResult = mockMvc.perform(get("/12345"))
				.andReturn();
		} catch (Exception e) {
			Throwable cause = e.getCause();
			throw cause;
		}
	}
}
