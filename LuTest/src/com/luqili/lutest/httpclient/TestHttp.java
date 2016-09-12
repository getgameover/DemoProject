package com.luqili.lutest.httpclient;

import javax.crypto.Mac;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.HmacUtils;

public class TestHttp {

	public static void main(String[] args) {
		String key="z8kJUwpkWL777SIobt8EA8/msbqdtOtWZ/7yO/8ofDSbvNCO7s0Gh7xwlaLhkuGLitSMMU9Qp80QWUcSVPaiQw==";
		byte[] keys=Base64.decodeBase64(key);
		String json="";
		String sign=HmacUtils.hmacSha1Hex(keys, json.getBytes());
		System.out.println(sign);
	}

}
