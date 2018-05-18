package com.cbers.models;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import com.cbers.db.DbUtils;

public class FireBaseModel {

	public static String getToken(long patient_id) {
		String query = "select token from `fcm_tokens` where patient_id = "+patient_id+";";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		if (result.size() == 1) {
			return result.get(0).get("token");
		}
		else 
			return null;
	}

	public static String getTokenFromIncident(long incident_id) {
		String query = "select ft.token from `incidents` i join `fcm_tokens` ft on (i.`patient_id` = ft.`patient_id`) "
				+ "where i.`id` = "+incident_id+" order by ft.`id` desc limit 1;";
		List<Map<String, String>> result = DbUtils.getDBEntries(query);
		if (result.size() == 1) {
			return result.get(0).get("token");
		}
		else 
			return null;
	}

	public static long addToken(long patient_id, String token) throws SQLException {
		String query = "INSERT INTO `fcm_tokens` (`patient_id`, `token`) VALUES ("+patient_id+", '"+token+"');";
		return DbUtils.runUpdate(query);
	}

}
