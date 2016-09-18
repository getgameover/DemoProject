package com.luqili.lutest.commonlang;

import java.math.BigInteger;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

public class TestBase64 {

	public static void main(String[] args) {
		Long a=555555555L;
		String b="2300000041121";
		BigInteger bi = new BigInteger(b);
		
		System.out.println( bi.toString(36));

	}

}
