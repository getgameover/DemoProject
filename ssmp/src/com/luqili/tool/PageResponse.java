package com.luqili.tool;

import java.io.IOException;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.View;

import com.fasterxml.jackson.databind.ObjectMapper;
/**
 * 前台页面Response工具类
 * @author 46155
 *
 */
public class PageResponse extends HashMap<String, Object> implements View{
	private static final long serialVersionUID = 1L;
	private boolean success;
	private ResponseStatusCode statusCode=ResponseStatusCode.SUCCESS;
	private String message;
	private Object result;
	private String navTabId;
	private String rel;
	private String callbackType;
	private String forwardUrl;
	private String confirmMsg;
	
	public PageResponse(boolean success){
		this.success=success;
		this.put("success", success);
	}
	public PageResponse(){
		this.setStatusCode(ResponseStatusCode.SUCCESS);
	}
	public boolean isSuccess() {
		return success;
	}
	public void setSuccess(boolean success) {
		this.success = success;
		this.put("success", success);
	}
	public ResponseStatusCode getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(ResponseStatusCode statusCode) {
		this.statusCode = statusCode;
		if(statusCode!=ResponseStatusCode.SUCCESS){
			this.setSuccess(false);
		}else{
			this.setSuccess(true);
		}
		this.put("statusCode", statusCode.toString());
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
		this.put("message", message);
	}
	public Object getResult() {
		return result;
	}
	public void setResult(Object result) {
		this.result = result;
		this.put("result", result);
	}
	
	public String getNavTabId() {
		return navTabId;
	}
	public void setNavTabId(String navTabId) {
		this.navTabId = navTabId;
		this.put("navTabId", navTabId);
	}
	public String getRel() {
		return rel;
	}
	public void setRel(String rel) {
		this.rel = rel;
		this.put("rel", rel);
	}
	public String getCallbackType() {
		return callbackType;
	}
	public void setCallbackType(String callbackType) {
		this.callbackType = callbackType;
		this.put("callbackType", callbackType);
	}
	public String getForwardUrl() {
		return forwardUrl;
	}
	public void setForwardUrl(String forwardUrl) {
		this.forwardUrl = forwardUrl;
		this.put("forwardUrl", forwardUrl);
	}
	public String getConfirmMsg() {
		return confirmMsg;
	}
	public void setConfirmMsg(String confirmMsg) {
		this.confirmMsg = confirmMsg;
		this.put("confirmMsg", confirmMsg);
	}
	@Override
	public String getContentType() {
		return this.getClass().getName();
	}
	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		response.setContentType("application/json");
		StringWriter sw = new StringWriter();
		ObjectMapper mapper = new ObjectMapper();
	    try {
	    	mapper.writeValue(sw, this);
	    } catch (IOException e) {
	    	throw new RuntimeException(e);
	    }
	    response.setStatus(200);//屏蔽系统状态
	    response.setCharacterEncoding("UTF-8");
	    ServletOutputStream out = response.getOutputStream();
	    out.write(sw.toString().getBytes("UTF-8"));
	    out.flush();
	    out.close();
	}
	
}
