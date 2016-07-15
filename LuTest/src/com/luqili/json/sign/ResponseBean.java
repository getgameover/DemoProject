package com.luqili.json.sign;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
public class ResponseBean {
	private Boolean isSuccess;
	private String message;
	private String service;
	private Long time;
	private Object result;
	private String sign;
	
	public String getService() {
		return service;
	}
	public void setService(String service) {
		this.service = service;
	}
	public Long getTime() {
		return time;
	}
	public void setTime(Long time) {
		this.time = time;
	}
	
	public String getSign() {
		return sign;
	}
	public void setSign(String sign) {
		this.sign = sign;
	}
	public Boolean getIsSuccess() {
		return isSuccess;
	}
	public void setIsSuccess(Boolean isSuccess) {
		this.isSuccess = isSuccess;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
	}

	
}
class Result{
	private String  local;
	private String size;
	public String getLocal() {
		return local;
	}
	public void setLocal(String local) {
		this.local = local;
	}
	public String getSize() {
		return size;
	}
	public void setSize(String size) {
		this.size = size;
	}
	
	
}
