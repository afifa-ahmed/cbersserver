package com.cbers.models;

import com.cbers.db.DbUtils;

public class UserModel {

	public static Object getRole(String password) {
		// TODO Auto-generated method stub
		return "admin";
	}

	public static boolean validUser(String email, String password) {
		String query = "select id from users where email = '"+email+"' and password = '"+password+"';";
		return DbUtils.getDBEntries(query).size() == 1;
	}

}
