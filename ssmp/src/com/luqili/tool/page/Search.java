package com.luqili.tool.page;

/**
 * @date 2014年12月10日
 */
public class Search {

	private String value;
	private String regex;
	private String type="String";//支持的数据类型  Integer String 
	
	public String getRegex() {
		return regex;
	}
	public void setRegex(String regex) {
		this.regex = regex;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getValue() {
		return value;
	}
	public void setValue(String value) {
		this.value = value;
	}
	
}
