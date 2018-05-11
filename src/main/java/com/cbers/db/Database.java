package com.cbers.db;

import java.sql.Connection;
import java.sql.SQLException;

import org.apache.tomcat.dbcp.dbcp.BasicDataSource;


public class Database {

	private static final BasicDataSource dataSourceWriter = new BasicDataSource();
	private static final String DB_WRITE_USER = "root"; 
	private static final String DB_WRITE_PASSWORD = "";
	private static final String DB_URL = "jdbc:mysql://127.0.0.1:3306/cbers";
	private static final String DB_DRIVER = "com.mysql.jdbc.Driver";

	static {
		dataSourceWriter.setDriverClassName(DB_DRIVER);
		dataSourceWriter.setUrl(DB_URL);
		dataSourceWriter.setUsername(DB_WRITE_USER);
		dataSourceWriter.setPassword(DB_WRITE_PASSWORD);
	}

	private Database() {
		//
	}

	public static Connection getWriteConnection() throws SQLException {
		System.out.println("DB: Creating DB Connection.");
		return dataSourceWriter.getConnection();
	}

}
