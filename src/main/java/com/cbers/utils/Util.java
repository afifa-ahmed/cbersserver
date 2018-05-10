package com.cbers.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class Util {

	public static Date getDateFromDbString(String dbDate) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try {
			return dt.parse(dbDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public static String getStringFromDate(Date date) {
		SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");
		return dt.format(date);
	}

	public static int getAgeFromDOB(Date dobDate) {
		return (int) TimeUnit.MILLISECONDS.toDays(new Date().getTime() - dobDate.getTime())/365;
	}

}
