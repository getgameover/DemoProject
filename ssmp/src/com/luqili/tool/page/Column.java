package com.luqili.tool.page;

/**
 * @author lifeng<13563488364@139.com>
 * @date 2014年12月10日
 */
public class Column {
	
	private String data;
	private String name;
	private boolean searchable=false;
	private String orderable;
	private Search search;
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isSearchable() {
		return searchable;
	}
	public void setSearchable(boolean searchable) {
		this.searchable = searchable;
	}
	public String getOrderable() {
		return orderable;
	}
	public void setOrderable(String orderable) {
		this.orderable = orderable;
	}
	public Search getSearch() {
		return search;
	}
	public void setSearch(Search search) {
		this.search = search;
	}
	
}
