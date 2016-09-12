package com.luqili.lutest.commonlang;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang.time.DateFormatUtils;
import org.apache.commons.lang.time.DateUtils;

public class TestDate {

	public static void main(String[] args) throws ParseException {
		String str="2016-09-09";
		String[] pp = new String[]{"yyyy-MM-dd","yyyy-MM-dd HH:mm"};
		Date  d =DateUtils.parseDate(str, pp);
		System.out.println(isRestDate(d) );
	}
	public static boolean isRestDate(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		int dow=c.get(Calendar.DAY_OF_WEEK);
		return dow==Calendar.SUNDAY||dow==Calendar.SATURDAY; 
	}

}
