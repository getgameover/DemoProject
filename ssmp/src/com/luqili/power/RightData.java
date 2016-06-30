package com.luqili.power;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum RightData {
	Common(1L,1,"common","公共权限","用户基础权限"),
	System(1L<<62,2,"system","系统管理","管理员权限");
	private long right;
	private int order;
	private String rightName;
	private String name;
	private String description;
	
	RightData(long right,int order,String rightName,String name,String description){
		this.right=right;
		this.order=order;
		this.rightName=rightName;
		this.name=name;
		this.description=description;
	}
	public static List<RightData> getRightDataByRight(long right){
		RightData[] rdatas=RightData.values();
		List<RightData> ltr = new ArrayList<>();
		for(RightData r:rdatas){
			if((right&r.getRight())==r.getRight()){
				ltr.add(r);
			}
		}
		sortRight(ltr);
		return ltr;
	}
	/**
	 * 根据权限名获取权限
	 * @param rightName
	 * @return
	 */
	public static Long getRightValue(String rightName){
		RightData[] rdatas=RightData.values();
		for(RightData r:rdatas){
			if(r.getRightName().equals(rightName)){
				return r.getRight();
			}
		}
		return null;
	}
	/**
	 * 获取全部的权限信息
	 * @return
	 */
	public static List<Map<String, Object>> getRightMsgAll(){
		RightData[] rdatas=RightData.values();
		List<RightData> rights = Arrays.asList(rdatas);
		sortRight(rights);
		List<Map<String, Object>> ltmap = new ArrayList<>();
		for(RightData right:rights){
			ltmap.add(getRightMsg(right));
		}
		return ltmap;
	}
	/**
	 * 根据权限值获取权限信息
	 * @param right
	 * @return
	 */
	public static List<Map<String, Object>> getRightMsg(long rightValue){
		List<RightData> rights=RightData.getRightDataByRight(rightValue);
		sortRight(rights);
		List<Map<String, Object>> ltmap = new ArrayList<>();
		for(RightData right:rights){
			ltmap.add(getRightMsg(right));
		}
		return ltmap;
	}
	/**
	 * 将Right 转换为Map
	 * @param right
	 * @return
	 */
	public static Map<String, Object> getRightMsg(RightData right){
		Map<String, Object> map = new HashMap<>();
		map.put("right", right.getRight());
		map.put("rightName", right.getRightName());
		map.put("description", right.getDescription());
		map.put("name", right.getName());
		map.put("order", right.getOrder());
		return map;
	}
	/**
	 * 根据权限名,获取合成的权限值
	 * @param rightNames
	 * @return
	 */
	public static long getRightValueByRightNames(List<String> rightNames){
		long value=0;
		for(String rightName:rightNames){
			Long rv=getRightValue(rightName);
			if(rv!=null){
				value=value|rv;
			}
		}
		return value;
	}
	
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public long getRight() {
		return right;
	}
	public void setRight(long right) {
		this.right = right;
	}
	public String getRightName() {
		return rightName;
	}
	public void setRightName(String rightName) {
		this.rightName = rightName;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getOrder() {
		return order;
	}
	public void setOrder(int order) {
		this.order = order;
	}
	
	private static void sortRight(List<RightData> rights){
		if(rights==null){
			return ;
		}
		rights.sort(new Comparator<RightData>() {
			@Override
			public int compare(RightData o1, RightData o2) {
				return o1.getOrder()-o2.getOrder();
			}
		});
	}
	
}


