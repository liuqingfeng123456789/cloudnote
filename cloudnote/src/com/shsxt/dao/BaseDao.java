package com.shsxt.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.*;
import com.shsxt.util.DButil;


//封装用户的增删改，将sql语句和参数传递进来，其它不变
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
	
	//查询某一个字段,
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
	 * 查询对象集合
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
				//为每个记录创建一个对象
				Object object=cls.newInstance();
				for(int i=0;i<columncount;i++){
					//获取数据库的字段名
					String fielname=metaData.getColumnLabel(i+1);
					//通过反射获取字段的对象
					Field field=cls.getDeclaredField(fielname);
					//拼接方法名
					String methodname="set"+fielname.substring(0,1).toUpperCase()+fielname.substring(1);
					//通过反射得到method对象
					Method method=cls.getDeclaredMethod(methodname, field.getType());
					//通过反射调用该方法
					method.invoke(object, resultSet.getObject(fielname));
				}
				list.add(object);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return list;
	}
	//查询对象
	public Object queryRow(String sql,List<Object> params,Class cls){
		Object object=null;
		List list=queryRows(sql, params, cls);
		if(list!=null && list.size()>0){
			object=list.get(0);
		}
		return object;
	}
	
}
