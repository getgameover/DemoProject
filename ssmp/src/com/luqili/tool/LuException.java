package com.luqili.tool;
/**
 * 自定义异常
 * @author 46155
 *
 */
public class LuException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public LuException(){
	}
	public LuException(String errorMsg){
		super(errorMsg);
	}

}
