package com.shsxt.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DButil {
	
	/**
	 * �õ����ݿ������
	 * @return
	 */
	public static Connection getConnection() {
		Connection connection = null;
		
		/*try {
			// ��������
			Class.forName("com.mysql.jdbc.Driver");
			
			// �õ�����
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/servlettest?useUnicode=true&characterEncoding=UTF-8", "root","root");
			
		} catch (Exception e) {
			e.printStackTrace();
		}	*/	
		
		try {
			// ���������ļ�
			InputStream inputStream = DButil.class.getClassLoader().getResourceAsStream("db.properties");
			// �õ������ļ�����
			Properties properties = new Properties();
			// ͨ��properties�����load��������������
			properties.load(inputStream);
			
			// ͨ��properties�����getProperty()�������õ�ָ����������ֵ
			String jdbcName = properties.getProperty("jdbcName"); // ������
			String dbUrl = properties.getProperty("dbUrl"); // ���ӵ�ַ
			String dbUser = properties.getProperty("dbUser"); // �û���
			String dbPwd = properties.getProperty("dbPwd"); // �û�����
			
			// ��������
			Class.forName(jdbcName);
			
			// �õ�����
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	/**
	 * �ر���Դ
	 * @param connection
	 * @param preparedStatement
	 * @param resultSet
	 */
	public static void close(Connection connection, PreparedStatement preparedStatement,ResultSet resultSet) {
		try {
			// �ж϶����Ƿ�Ϊ�գ���Ϊ�չر�
			if (resultSet != null) {
				resultSet.close();
			}
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	public static void close(Connection connection, PreparedStatement preparedStatement){
		try {
			// �ж϶����Ƿ�Ϊ�գ���Ϊ�չر�
			if (preparedStatement != null) {
				preparedStatement.close();
			}
			if (connection != null) {
				connection.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
