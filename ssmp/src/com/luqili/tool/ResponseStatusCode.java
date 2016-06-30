package com.luqili.tool;
/**
 * Response 状态
 * @author 46155
 *
 */
public enum ResponseStatusCode {
	SUCCESS("200"),OUTTIME("301"),ERROR("500"),NOTRIGHT("401"),NOTFIND("404");;
	private String statusCode;
	private ResponseStatusCode(String statusCode){
		this.statusCode=statusCode;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
	@Override
	public String toString() {
		return this.statusCode;
	}
	
}
