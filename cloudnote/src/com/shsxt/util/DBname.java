package com.shsxt.util;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class DBname {
	/*
	 * �жϸ��û��������ݿ����Ƿ���ڣ�������true����������false��
	 */
	public static boolean isExist(String uname){
		Connection connection = null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			connection=DButil.getConnection();
			String sql="select * from tb_user where uname=?";
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, uname);
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				return true;
			}

		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DButil.close(connection, preparedStatement,resultSet);
			
		}
		return false;
	}
	
}
