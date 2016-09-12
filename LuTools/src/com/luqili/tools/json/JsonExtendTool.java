package com.luqili.tools.json;

public class JsonExtendTool {
	/**
	 * 移除Json字符串中的指定属性
	 *@author luqili 2016年9月10日
	 * @param jsonString
	 * @param noteName 指定的属性名
	 * @return
	 */
	public static String removeNode(String jsonString,String nodeName){
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
