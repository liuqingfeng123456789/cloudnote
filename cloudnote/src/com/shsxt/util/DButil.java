package com.shsxt.util;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Properties;

public class DButil {
	
	/**
	 * 得到数据库的链接
	 * @return
	 */
	public static Connection getConnection() {
		Connection connection = null;
		
		/*try {
			// 加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			
			// 得到链接
			connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/servlettest?useUnicode=true&characterEncoding=UTF-8", "root","root");
			
		} catch (Exception e) {
			e.printStackTrace();
		}	*/	
		
		try {
			// 加载配置文件
			InputStream inputStream = DButil.class.getClassLoader().getResourceAsStream("db.properties");
			// 得到配置文件对象
			Properties properties = new Properties();
			// 通过properties对象的load方法加载输入流
			properties.load(inputStream);
			
			// 通过properties对象的getProperty()方法，得到指定属性名的值
			String jdbcName = properties.getProperty("jdbcName"); // 驱动名
			String dbUrl = properties.getProperty("dbUrl"); // 连接地址
			String dbUser = properties.getProperty("dbUser"); // 用户名
			String dbPwd = properties.getProperty("dbPwd"); // 用户密码
			
			// 加载驱动
			Class.forName(jdbcName);
			
			// 得到链接
			connection = DriverManager.getConnection(dbUrl, dbUser, dbPwd);
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return connection;
	}
	
	/**
	 * 关闭资源
	 * @param connection
	 * @param preparedStatement
	 * @param resultSet
	 */
	public static void close(Connection connection, PreparedStatement preparedStatement,ResultSet resultSet) {
		try {
			// 判断对象是否为空，不为空关闭
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
			// 判断对象是否为空，不为空关闭
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
