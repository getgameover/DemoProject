package config;

import java.util.Date;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import com.luqili.tool.FileUtils;

public class LoadConfig {
	public static void main(String[]args){
		String path=DateFormatUtils.format(new Date(), "/yyyyMMdd/hhmmssSSS");
		String path1=DateFormatUtils.format(new Date(), "/yyyyMMdd/hhmmssSSS");
		System.out.println(path);
		System.out.println(path1);
		String random=RandomStringUtils.randomAlphanumeric(5);
		String random1=RandomStringUtils.randomAlphanumeric(5);
		String random2=RandomStringUtils.randomAlphanumeric(5);
		System.out.println(random);
		System.out.println(random1);
		System.out.println(random2);
		System.out.println(FileUtils.getFileNameRandomByTime("a.png"));
		System.out.println(FileUtils.getFileNameRandomByTime("a.png"));
		System.out.println(FileUtils.getFileNameRandomByTime("a.png"));
		System.out.println(FileUtils.getFileNameRandomByTime("a.png"));
		System.out.println(FileUtils.getFileNameRandomByTime("a.png"));
	}
}
