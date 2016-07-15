package com.luqili.json.sign;

import java.io.IOException;
import java.io.StringWriter;

import org.apache.commons.codec.digest.DigestUtils;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class SignUtil {
	public static String objToJson(Object obj){
		StringWriter sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(Include.NON_EMPTY);
		mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS,false);  
		//mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY,true);//属性排序  
		
		if (null != obj) {
		    try {
		    	mapper.writeValue(sw, obj);
		    } catch (IOException e) {
		    	throw new RuntimeException(e);
		    }
		}
		return sw.toString();
	}
	public static String signRequest(RequestBean request,String pwd){
		String json=objToJson(request)+pwd;
		String sign= DigestUtils.sha1Hex(json);
		request.setSign(sign);
		return sign;
	} 
	public static String signResponse(ResponseBean res,String pwd){
		String json=objToJson(res)+pwd;
		String sign= DigestUtils.sha1Hex(json);
		res.setSign(sign);
		return sign;
	} 
}
