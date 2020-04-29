package com.shortener.app.util;

import org.apache.commons.lang3.RandomStringUtils;

public class  IdRandomUtil {

	final static int SHORT_ID_LENGTH = 8;

	/**
	 * 8잘 영문(대소)+숫자의 랜덤 문자열 반환
	 * @return
	 */
	public static String generate() {
		String shortId = RandomStringUtils.randomAlphanumeric(SHORT_ID_LENGTH);
		
		return shortId;
	}
	
}
