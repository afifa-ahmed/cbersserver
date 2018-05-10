package com.cbers.db;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DbUtils {

	public static List<List<String>> runSelect(String query) throws SQLException {
		System.out.println("DB: Running Select > "+query);
		List<List<String>> results = new ArrayList<List<String>>();
		try (
				Connection conn = Database.getWriteConnection();
				Statement statement = conn.createStatement();
				){
			try (ResultSet rs = statement.executeQuery(query)) {
				ResultSetMetaData meta = rs.getMetaData();

				while (rs.next()) {
					List<String> lines = new ArrayList<String>();
					for (int col = 1; col < meta.getColumnCount() + 1; col++) {
						lines.add(rs.getString(col));
					}
					results.add(lines);
				}
			}
		} 
		return results;
	}

	public static List<Map<String, String>> getDBEntries(String query) {
		System.out.println("DB: Running Select > "+query);
		try (
				Connection conn = Database.getWriteConnection();
				Statement statement = conn.createStatement();
				){
			try (ResultSet rs = statement.executeQuery(query)) {
				List<Map<String, String>> result = new ArrayList<>();
				ResultSetMetaData rsmd = rs.getMetaData();

				while (rs.next()) {
					Map<String, String> map = new HashMap<String, String>();
					for (int i = 1; i <= rsmd.getColumnCount(); i++) {
						String name = rsmd.getColumnLabel(i);
						map.put(name, rs.getString(name));
					}
					result.add(map);
				}
				return result;
			} 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static int runUpdate(String query) throws SQLException {
		System.out.println("DB: Running Update > "+query);
		try (
				Connection conn = Database.getWriteConnection();
				Statement statement = conn.createStatement();
				){
			int count = statement.executeUpdate(query);
			System.out.println(count + " rows affected.");
			return count;
		}
	}

	public static boolean tableExists(String tableName) throws SQLException{
		System.out.println("DB: Running tableExists > "+tableName);
		try (
				Connection conn = Database.getWriteConnection();
				){
			DatabaseMetaData md = conn.getMetaData();
			ResultSet rs = md.getTables(null, null, tableName, null);
			if (rs.last())
				return rs.getRow() == 1;
			else
				return false;
		}
	}

}
