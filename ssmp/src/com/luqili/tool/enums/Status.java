package com.luqili.tool.enums;

public enum Status {
	Disabled(0,"失效"),
	Enabled(1,"有效"),
	Other(2,"其他");
	private Integer statusValue;
	private String name;
	private Status(Integer statusValue,String name){
		this.statusValue=statusValue;
		this.name=name;
	}
	public Integer getStatusValue() {
		return statusValue;
	}
	public void setStatusValue(Integer statusValue) {
		this.statusValue = statusValue;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean equalsValue(Integer statusValue){
		return this.getStatusValue().equals(statusValue);
	}
	public static boolean validateStatusValue(Integer statusValue){
		for(Status s:Status.values()){
			if(s.getStatusValue().equals(statusValue)){
				return true;
			}
		}
		return false;
	}
	
}
