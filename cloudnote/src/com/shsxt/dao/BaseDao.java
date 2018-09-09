package com.shsxt.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.*;
import com.shsxt.util.DButil;


//��װ�û�����ɾ�ģ���sql���Ͳ������ݽ�������������
//delete from user where uid=?
public class BaseDao {
	public int execUpdate(String sql,List<Object> params){
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		int row=0;
		try{
			connection=DButil.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			if(params!=null || params.size()>0){
				for(int i=0;i<params.size();i++){
					preparedStatement.setObject(i+1, params.get(i));
				}
			}
			row=preparedStatement.executeUpdate();
		}
		catch (Exception e) {
			// TODO: handle exception
		}finally{
			DButil.close(connection, preparedStatement);
		}
		return row;
	}
	
	//��ѯĳһ���ֶ�,
	//select uname from user where uid=? ;
	//select count(*) from user;
	public Object findField(String sql,List<Object> params){
		Object object=null;
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			connection=DButil.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			if(params!=null || params.size()>0){
				for(int i=0;i<params.size();i++){
					preparedStatement.setObject(i+1, params.get(i));
				}
			}
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				object=resultSet.getObject(1);
			}
		}catch(Exception e){
			e.printStackTrace();
			
		}finally {
			DButil.close(connection, preparedStatement,resultSet);
		}
		return object;
	}
	
	/**
	 * ��ѯ���󼯺�
	 */
	public List queryRows(String sql,List<Object> params,Class cls){

		List list=new ArrayList();
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try {
			connection=DButil.getConnection();
			preparedStatement=connection.prepareStatement(sql);
			if(params!=null && params.size()>0){
				for(int i=0;i<params.size();i++){
					preparedStatement.setObject(i+1, params.get(i));
				}
				
			}
			resultSet=preparedStatement.executeQuery();
			ResultSetMetaData metaData= resultSet.getMetaData();
			int columncount=metaData.getColumnCount();
			
			while(resultSet.next()){
				//Ϊÿ����¼����һ������
				Object object=cls.newInstance();
				for(int i=0;i<columncount;i++){
					//��ȡ���ݿ���ֶ���
					String fielname=metaData.getColumnLabel(i+1);
					//ͨ�������ȡ�ֶεĶ���
					Field field=cls.getDeclaredField(fielname);
					//ƴ�ӷ�����
					String methodname="set"+fielname.substring(0,1).toUpperCase()+fielname.substring(1);
					//ͨ������õ�method����
					Method method=cls.getDeclaredMethod(methodname, field.getType());
					//ͨ��������ø÷���
					method.invoke(object, resultSet.getObject(fielname));
				}
				list.add(object);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//��ѯ����
	public Object queryRow(String sql,List<Object> params,Class cls){
		Object object=null;
		List list=queryRows(sql, params, cls);
		if(list!=null && list.size()>0){
			object=list.get(0);
		}
		return object;
	}
	
}
