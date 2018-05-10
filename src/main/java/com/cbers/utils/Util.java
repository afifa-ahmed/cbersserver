package com.cbers.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
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

	public static String decrypt(String str) {
		System.out.println("Decrypting String: "+str);
		byte[] result = Base64.getDecoder().decode(str.getBytes());
		return (new String(result));
	}

}
