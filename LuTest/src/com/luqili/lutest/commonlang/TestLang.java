package com.luqili.lutest.commonlang;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.codec.digest.HmacUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.time.DateUtils;

public class TestLang {

	public static void main(String[] args) {
		String json="{\"isSuccess\":true,\"message\":\"成功\",\"result\":\"\",\"service\":null,\"sign\":\"46c7e683a01a4bae4d337e403944a0d11a4a8444\",\"timestamp\":1473330914852}";
		String note="timestamp";
		String a=removeJsonNode(json, note);
		System.out.println(json);
		System.out.println(a);
		
	}
	 public static String removeJsonNode(String jsonString,String nodeName){
	    	int index_n=jsonString.indexOf("\""+nodeName+"\":");
	    	if(index_n==-1){
	    		//不包含该属性
	    		return jsonString;
	    	}
	    	if(jsonString.indexOf(",\""+nodeName+"\":")!=-1){
	    		//不是第一位属性
	    		index_n=index_n-1;
	    	}
	    	int index_e=jsonString.indexOf(",",index_n+nodeName.length());//查询属性结尾位置
	    	if(index_e==-1){
	    		index_e=jsonString.indexOf("}",index_n);
	    	}
	    	if(index_e<0){
	    		throw new RuntimeException("Json格式错误");
	    	}
	    	return jsonString.substring(0, index_n)+jsonString.substring(index_e);
	    }
	    

}
