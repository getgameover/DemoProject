package com.luqili.db.dao.tool;

import java.util.Date;
import java.util.Map;

import org.apache.commons.lang3.time.DateFormatUtils;
/**
 * 格式化日期
 * @author 46155
 *
 */
public class DateObjectFilter implements ObjectFilter {
	private String pattern="yyyy-MM-dd";
	@Override
	public Object handleOneToString(Object obj) {
		if(obj instanceof Date){
			return DateFormatUtils.format((Date)obj, pattern);
		}else{
			return "";
		}
	}
	public DateObjectFilter(){
		super();
	}
	public DateObjectFilter(String pattern){
		this.pattern=pattern;
	}
	@Override
	public void handleLineMap(Map<String, Object> map) {
	}
}
