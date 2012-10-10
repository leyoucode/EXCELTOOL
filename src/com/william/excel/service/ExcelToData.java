package com.william.excel.service;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.william.excel.config.ColumnConfig;
import com.william.excel.config.DataBaseConfig;
import com.william.excel.log.Log;

public class ExcelToData {

	private static Connection conn;
	private static PreparedStatement pstmt;
    private  static int count=0;
	static {
		conn = DataBaseConfig.getConnnect();
		
	}
	public static boolean insert(String tableName, List<ColumnConfig> columns,
			Object obj, boolean isCommit) {
		boolean result = false;//�Ƿ����쳣
		
		try {
			List<String> fields = new ArrayList<String>();
 			String sql = "insert into " + tableName + " ( ";
			for (int i = 0; i < columns.size(); i++) {
				if (i == columns.size() - 1) {
					sql += columns.get(i).getDatabase_column().toString();
				} else {
					sql += columns.get(i).getDatabase_column().toString() + " , ";
				}
			}
			sql += " ) values (";
			for (int i = 0; i < columns.size(); i++) {
				if (i == columns.size() - 1) {
					sql += "?";
				} else {
					sql += " ? , ";
				}
				
				//�õ��ֶ�
				fields.add(columns.get(i).getField());
			}
			sql += ")";

			System.out.println(sql);
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql);

			for (int i = 0; i < fields.size(); i++) {//////////////
				Method m;
				String field = fields.get(i);
				field=field.replace(field.substring(0,1),field.substring(0,1).toUpperCase());
				try {
					m = obj.getClass().getDeclaredMethod("get"+field,
							new Class[] {});
					String value = (String) m.invoke(obj, new Object[] {});
					pstmt.setString(i + 1, value);
					
				} catch (SecurityException e) {
					e.printStackTrace();
					result =true;
				} catch (NoSuchMethodException e) {
					e.printStackTrace();
					result =true;
				} catch (IllegalArgumentException e) {
					e.printStackTrace();
					result =true;
				} catch (IllegalAccessException e) {
					e.printStackTrace();
					result =true;
				} catch (InvocationTargetException e) {
					e.printStackTrace();
					result =true;
				}
				
			}

			pstmt.addBatch();
			pstmt.executeBatch();
			count++;//��¼��ǰ�������
			if (isCommit) {
				conn.commit();				
			}

		} catch (SQLException e) {
			result =true;
			try {
				conn.rollback();
				Log.writeLog("�����"+count+"����¼ʧ��,��������ع�" + e.toString(), true);
			} catch (SQLException e1) {
				e1.printStackTrace();
				Log.writeLog("����ع�ʧ��" + e1.toString(), true);
				result =true;
			}
		}
			return result;
	}
	
	public static boolean dropTable(String tableName){
		
		boolean result = false;//�Ƿ����쳣
		if(tableName==null || "".equals(tableName)){
			Log.writeLog("���ݿ��������Ϊ��,���������ļ�,ȷ��table���Ա���ȷָ����", true);
			return true;
		}
		Log.writeLog("��ʼɾ�����ݿ��"+tableName+"��", true);
		String sql = "if exists (select * from sysobjects where name = '"+tableName+"') drop table "+tableName;
		try {
			pstmt=conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			Log.writeLog("���ݿ⡾"+tableName+"�����ܱ�ɾ��:"+e.getMessage(), true);
			result=true;
		}
		Log.writeLog("ɾ�����ݿ��"+tableName+"�����", true);
		return result;
	}
	
	public static boolean createTable(String tableName, List<ColumnConfig> columns){
		boolean result = false;//�Ƿ����쳣
		if(tableName==null || "".equals(tableName)){
			Log.writeLog("���ݿ��������Ϊ��,���������ļ�,ȷ��table���Ա���ȷָ����", true);
			return true;
		}
		Log.writeLog("��ʼ�������ݿ��"+tableName+"��", true);
		String sql = "CREATE TABLE "+tableName+" ( id int IDENTITY(1,1) NOT NULL, ";
		for(int i = 0 ;i<columns.size() ; i++){
			if (i == columns.size() - 1) {
				sql += columns.get(i).getDatabase_column().toString();
				sql += " varchar(255) NULL  ";
			}else{
			sql += columns.get(i).getDatabase_column().toString();
			sql += " varchar(255) NULL , ";
			}
		}
		sql+=" ) ";
		try {
			System.out.println(sql);
			pstmt=conn.prepareStatement(sql);
			pstmt.executeUpdate();
			
		} catch (SQLException e) {
			e.printStackTrace();
			Log.writeLog("���ݿ⡾"+tableName+"������ʧ��:"+e.getMessage(), true);
			result=true;
		}
		Log.writeLog("�������ݿ��"+tableName+"�����", true);
		return result;
	}
//	public static void main(String[] args){
//		List<ColumnConfig> columnConfigs = new ArrayList<ColumnConfig>();
//		ColumnConfig columnConfig1 = new ColumnConfig();
//		columnConfig1.setDatabase_column("a");
//		columnConfigs.add(columnConfig1);
//		
//		ColumnConfig columnConfig2 = new ColumnConfig();
//		columnConfig2.setDatabase_column("b");
//		columnConfigs.add(columnConfig2);
//		
//		ColumnConfig columnConfig3 = new ColumnConfig();
//		columnConfig3.setDatabase_column("c");
//		columnConfigs.add(columnConfig3);
//		
//		ExcelToData.dropTable("TB_Test2");
//		ExcelToData.createTable("TB_Test2", columnConfigs);
//	}
}
