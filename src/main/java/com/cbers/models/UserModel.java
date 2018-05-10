package com.cbers.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.cbers.db.DbUtils;
import com.cbers.utils.Util;

public class UserModel {

	public static User getUser(String email) {
		String query = "select * from users where email = '"+email+"';";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		if (result.size() == 1) {
			Map<String, String> user = result.get(0);
			return new User(Long.parseLong(user.get("id")), user.get("name"), user.get("email"), 
					user.get("password"), Long.parseLong(user.get("phone")), Util.getDateFromDbString(user.get("dob")), 
					Role.valueOf(user.get("role")));
		}
		else 
			return null;
	}

	public static List<User> getUsersByRole(Role role) {
		String query = "select * from users where role = '"+role+"';";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		List<User> users = new ArrayList<>();
		for (Map<String, String> user : result) {
			users.add(new User(Long.parseLong(user.get("id")), user.get("name"), user.get("email"), 
					user.get("password"), Long.parseLong(user.get("phone")), Util.getDateFromDbString(user.get("dob")), 
					Role.valueOf(user.get("role"))));
		}
		return users;
	}

	public static List<User> getAllUsers() {
		String query = "select * from users;";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		List<User> users = new ArrayList<>();
		for (Map<String, String> user : result) {
			users.add(new User(Long.parseLong(user.get("id")), user.get("name"), user.get("email"), 
					user.get("password"), Long.parseLong(user.get("phone")), Util.getDateFromDbString(user.get("dob")), 
					Role.valueOf(user.get("role"))));
		}
		return users;
	}

	public static long addUser(User user) throws SQLException {
		String query = "INSERT INTO `users` (`name`, `email`, `password`, `phone`, `dob`, `role`) "
				+ "VALUES ('"+user.getName()+"', '"+user.getEmail()+"', '"+user.getPassword()+"', "
				+ ""+user.getPhone()+", '"+Util.getStringFromDate(user.getDob())+"', '"+user.getRole()+"');";
		int rows = DbUtils.runUpdate(query);
		if (rows == 1) {
			return getUser(user.getEmail()).getId();
		}
		return 0;
	}

	public static boolean editUser(long id, String password, long phone, Date dob) throws SQLException {
		String query = "UPDATE `users` SET password = '"+password+"', phone = '"+phone+"', "
				+ "dob = '"+Util.getStringFromDate(dob)+"' WHERE id = "+id+";";
		int rows = DbUtils.runUpdate(query);
		return rows == 1;
	}

}
