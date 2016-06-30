package com.luqili.db.util;

import org.apache.commons.lang3.StringUtils;

public class SQLTemplet {
	private String sql;
	private String select_sql;
	private String from_sql;
	private String where_sql;
	private String order_sql;
	public SQLTemplet(){
	}
	public SQLTemplet(String sql){
		this.setSql(sql);
	}
	public SQLTemplet(String select,String from,String where,String order){
		this.setSelect_sql(select);
		this.setFrom_sql(from);
		this.setWhere_sql(where);
		this.setOrder_sql(order);
	}
	
	public String getSelect_sql() {
		return select_sql;
	}
	public void setSelect_sql(String select_sql) {
		if(StringUtils.isNotBlank(select_sql)){
			this.select_sql = select_sql;
		}else{
			this.select_sql = null;
		}
	}
	public String getFrom_sql() {
		return from_sql;
	}
	public void setFrom_sql(String from_sql) {
		if(StringUtils.isNotBlank(from_sql)){
			this.from_sql = from_sql;
		}else{
			this.from_sql = null;
		}
	}
	public String getOrder_sql() {
		return order_sql;
	}
	public void setOrder_sql(String order_sql) {
		if(StringUtils.isNotBlank(order_sql)){
			this.order_sql = order_sql;
		}else{
			this.order_sql = null;
		}
	}
	public String getSql() {
		return sql;
	}
	public void setSql(String sql) {
		if(StringUtils.isNotBlank(sql)){
			this.sql = sql;
		}else{
			this.sql = null;
		}
	}
	public String getWhere_sql() {
		return where_sql;
	}
	public void setWhere_sql(String where_sql) {
		if(StringUtils.isNotBlank(where_sql)){
			this.where_sql = where_sql;
		}else{
			this.where_sql = null;
		}
	}
	
	
	
}
