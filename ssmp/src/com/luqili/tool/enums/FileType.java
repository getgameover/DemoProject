package com.luqili.tool.enums;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public enum FileType {
	TEMP(0,0,"文档文件"),
	IMG(1,1,"风景图片");
	private Integer typeValue;
	private Integer order;
	private String typeName;
	FileType(Integer typeValue,Integer order,String typeName){
		this.typeValue=typeValue;
		this.typeName=typeName;
		this.order=order;
	}
	public Integer getTypeValue() {
		return typeValue;
	}
	public void setTypeValue(Integer typeValue) {
		this.typeValue = typeValue;
	}
	public Integer getOrder() {
		return order;
	}
	public void setOrder(Integer order) {
		this.order = order;
	}
	public String getTypeName() {
		return typeName;
	}
	public void setTypeName(String typeName) {
		this.typeName = typeName;
	}
	public static List<Map<String, Object>> getFileTypsMsg(){
		List<FileType> lttype=Arrays.asList(FileType.values());
		sortFileType(lttype);
		List<Map<String, Object>> ltmap = new ArrayList<>();
		for(FileType ft:lttype){
			ltmap.add(ft.toMap());
		}
		return ltmap;
	}
	public Map<String, Object> toMap(){
		Map<String, Object> map = new HashMap<>();
		map.put("typeValue", this.getTypeValue());
		map.put("typeName", this.getTypeName());
		map.put("order", this.getOrder());
		return map;
	}
	public static boolean validateFileTypeValue(Integer typeValue){
		for(FileType t:FileType.values()){
			if(t.getTypeValue().equals(typeValue)){
				return true;
			}
		}
		return false;
	}
	private static void sortFileType(List<FileType> fileTypes){
		if(fileTypes==null){
			return ;
		}
		fileTypes.sort(new Comparator<FileType>() {
			@Override
			public int compare(FileType o1, FileType o2) {
				return o1.getOrder()-o2.getOrder();
			}
		});
	}
}
