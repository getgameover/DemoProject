package com.luqili.tool;

import java.io.File;
import java.io.FileOutputStream;
import java.util.Date;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

public class FileUtils {
	/**
	 * 根据文件名获取后缀
	 * @param fileName
	 * @return
	 */
	public static String getFileExt(String fileName){
		String[] exts=StringUtils.split(fileName,".");
		String ext= exts!=null&&exts.length>1?exts[exts.length-1]:"";
		return ext.toLowerCase();
	}
	public static String getFileNameRandomByTime(String fileName){
		String path=DateFormatUtils.format(new Date(), "/yyyyMMdd/hhmmsss");
		String name=path+RandomStringUtils.randomAlphanumeric(5);
		String ext=getFileExt(fileName);
		return name+"."+ext;
	}
	/**
	 * HTML 前台读取文件后,后台接收转换为指定文件
	 * @param data
	 * @param file
	 */
	public static void writeAsDataURL(String srcdata,File file){
		File parentFile=file.getParentFile();
		if(!parentFile.isDirectory()){
			parentFile.mkdirs();
		}
		
		if(StringUtils.isBlank(srcdata)){
			return;
		}
		String[]datas=StringUtils.split(srcdata, ",");
		if(datas==null||datas.length!=2){
			throw new LuException("上传数据解析错误");
		}
		String data=datas[1];
		try(FileOutputStream fos = new FileOutputStream(file);){
			fos.write(Base64.decodeBase64(data));
		}catch (Exception e) {
			throw new LuException(e.getMessage());
		}
	}
}
