/**
 * Objective : Database Connection class is used to insert and retrieve data into database.
 * @Author Swati Jadhav
 * Date : 04/09/2019
 */

package com.tnt.commonutilities;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class Database_Connection {

	/*
	 * This method is used to retrieve data from TNT_AUTO_TEST database. This
	 * methods accepts four parameters, ColumnName to be used in Select clause,
	 * TableName, ConditionParam and ConditionValue to be used in Where clause This
	 * method returns the value fetched for column ColumnName
	 */
	static Config_Reader config_Reader;
	static Properties prop = null;

	public static String retrieveTestData(String ColumnName, String TableName, String ConditionParam,
			String ConditionValue) {
		config_Reader = new Config_Reader();
		prop = config_Reader.loadProperties();
		String strFetchedDataValue = null;
		PreparedStatement psmt=null;
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		

		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(prop.getProperty("DBConnection_URL"), prop.getProperty("DBUserName"),
					prop.getProperty("DBPassword"));
			psmt=con.prepareStatement("Select "+ColumnName+" from "+TableName+" WHERE " +ConditionParam+ " = ?");	
			psmt.setString(1, ConditionValue);
			rs = psmt.executeQuery();
			while (rs.next()) {
				strFetchedDataValue = rs.getString(1);
			}
			System.out.println("Test post DB Connection");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (psmt != null) {
				try {
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return strFetchedDataValue;
	}

	/*
	 * This method is used to insert data into TNT_AUTO_TEST database. This methods
	 * accepts four parameters, ColumnName to be used in Set clause, TableName,
	 * ConditionParam and ConditionValue to be used in Where clause
	 */
	public static void Insert_Into_Database(String TableName, String ColumnName, String Value, String ConditionParam,
			String ConditionValue) {
		config_Reader = new Config_Reader();
		prop = config_Reader.loadProperties();
		Statement stmt = null;
		ResultSet rs = null;
		Connection con = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(prop.getProperty("DBConnection_URL"), prop.getProperty("DBUserName"),
					prop.getProperty("DBPassword"));
			psmt=con.prepareStatement("update "+TableName+" set "+ColumnName+" = ? WHERE " +ConditionParam+ " = ?");	
			psmt.setString(1, Value);
			psmt.setString(2, ConditionValue);
			rs = psmt.executeQuery();
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (psmt != null) {
				try {
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static int Retrieve_Count(String ColumnName, String TableName, String ConditionParam,
			String ConditionValue) {
		config_Reader = new Config_Reader();
		prop = config_Reader.loadProperties();
		int strFetchedDataValue = 0;
		Connection con = null;
		Statement stmt = null;
		ResultSet rs = null;
		try {
			Class.forName("oracle.jdbc.driver.OracleDriver");
			con = DriverManager.getConnection(prop.getProperty("DBConnection_URL"), prop.getProperty("DBUserName"),
					prop.getProperty("DBPassword"));
			psmt=con.prepareStatement("Select count(" + ColumnName + ") from " + TableName + " WHERE " +ConditionParam+ " = ?");	
			psmt.setString(1, ConditionValue);
			rs = psmt.executeQuery();
			while (rs.next()) {
				strFetchedDataValue = Integer.parseInt(rs.getString(1));
			}
			con.close();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (psmt != null) {
				try {
					psmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (rs != null) {
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			if (con != null) {
				try {
					con.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		return strFetchedDataValue;
	}

}
