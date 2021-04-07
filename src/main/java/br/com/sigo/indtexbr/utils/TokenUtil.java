package br.com.sigo.indtexbr.utils;

import java.util.UUID;

public class TokenUtil {

	public static String generateRandomStringByUUID() {
		return UUID.randomUUID().toString();
	}

	public static String generateRandomStringByUUIDNoDash() {
		return generateRandomStringByUUID().replace("-", "");
	}

}
