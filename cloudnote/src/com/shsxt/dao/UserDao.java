package com.shsxt.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import com.shsxt.po.User;
import com.shsxt.util.DButil;
import com.sun.corba.se.impl.ior.GenericTaggedComponent;

public class UserDao {
	private BaseDao baseDao=new BaseDao();
	public User findname(String uname){
		User user=null;
		Connection connection=null;
		PreparedStatement preparedStatement=null;
		ResultSet resultSet=null;
		try{
			connection=DButil.getConnection();
			String sql="select * from tb_user where uname=?";
			preparedStatement=connection.prepareStatement(sql);
			preparedStatement.setString(1, uname);
			resultSet=preparedStatement.executeQuery();
			if(resultSet.next()){
				user=new User();
				user.setHead(resultSet.getString("head"));
				user.setUid(resultSet.getInt("uid"));
				user.setUpwd(resultSet.getString("upwd"));
				user.setUname(resultSet.getString("uname"));
				user.setMood(resultSet.getString("mood"));
				user.setNickname(resultSet.getString("nickname"));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		finally{
			DButil.close(connection, preparedStatement, resultSet);
		}
		return user;
	}

	public User findnicknamedao(String nickname,int uid) {
		// TODO Auto-generated method stub
		String sql="select * from tb_user where nickname=? and uid !=?";
		List params=new ArrayList<>();
		params.add(nickname);
		params.add(uid);
		User user=(User)baseDao.queryRow(sql, params, User.class);
		
		return user;
	}

	public int updateinfo(String nickname, String filename, String mood, int uid) {
		// TODO Auto-generated method stub
		//sql语句
		String sql=" update tb_user set nickname=?,head=?,mood=? where uid=?";
		//传入参数
		List<Object> params=new ArrayList<>();
		params.add(nickname);
		params.add(filename);
		params.add(mood);
		params.add(uid);
		//调用basedao
		int row=baseDao.execUpdate(sql, params);
		//返回受影响的行数
		return row;
	}

}	
