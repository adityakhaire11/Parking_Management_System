package com.connection;


import java.security.SecureRandom;
import java.sql.*;
import java.util.Random;
public class DBconnection {

	//connection object
	static Connection con;

	//method to return conne to mysql 
	public static Connection getConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		return DriverManager.getConnection("jdbc:mysql://localhost:3306/PMS", "RK", "Rk@1/#@1");
	}

	//method to execute select query
	public static ResultSet getResultFromSqlQuery(String SqlQueryString) {
		ResultSet rs = null;
		try {
			con = getConnection();
			rs = con.createStatement().executeQuery(SqlQueryString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return rs;
	}

	//method to execute insert update queries 
	public static void insertUpdateFromSqlQuery(String SqlQueryString) {
		try {
			if (con == null) {
				getConnection();
			}
			con.createStatement().executeUpdate(SqlQueryString);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	
	//metohod to randomly generate the password
	public static String randompasswordgeneration()
	{
		Random RANDOM = new SecureRandom();
		final int PASSWORD_LENGTH = 10;		//pass lenght
		String letters = "abcdefghjkmnpqrstuvwxyzABCDEFGHJKMNPQRSTUVWXYZ23456789@";
		String password = "";
	
		for (int i = 0; i < PASSWORD_LENGTH; i++) {
			int index = (int) (RANDOM.nextDouble() * letters.length());
			password += letters.substring(index, index + 1);
		}
		
		return password;
	}
	
	
	
	
}
