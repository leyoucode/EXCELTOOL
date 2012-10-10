package com.william.excel.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.william.excel.log.Log;
import com.william.excel.utils.DateUtils;

public class DataBaseConfig {
	private static String url = "";
	private static String dbUser = "";
	private static String dbPass = "";
	private static String driveClass = "";
	
	public static Connection getConnnect(){
		url = Configuration.getDATABASE_URL();
		dbUser = Configuration.getDATABASE_USER();
		dbPass = Configuration.getDATABASE_PASS();
		driveClass = Configuration.getDRIVER_CLASS();
		
		Connection conn = null;
		try {
				Class.forName(driveClass);
				conn = DriverManager.getConnection(url,dbUser,dbPass);
				return conn;
			}catch (SQLException e) {
				if (e != null) {
					Log.writeLog("-----------------"+DateUtils.getLongSysDate()+"----------------",true);
					Log.writeLog("数据库连接异常:"+e.getMessage(), true);
					e.printStackTrace();
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
			return null;
	}
	
	
	public static void close(Connection conn){
		if(conn!=null){
			try {
				if(!conn.isClosed())
					conn.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
