package com.shsxt.dao;

import java.util.ArrayList;
import java.util.List;

import com.shsxt.po.Note;
import com.shsxt.po.NoteType;
import com.shsxt.util.StringUtil;

import sun.swing.UIAction;

public class TypeDao {
	private BaseDao baseDao=new BaseDao();
	public List<NoteType> typelist(int uid) {
		// TODO Auto-generated method stub
		//写sql语句
		String sql="select * from tb_note_type where uid=?";
		//存放参数的列表
		List<Object> params = new ArrayList<>();
		params.add(uid);
		//调用basedao的方法
		List list=baseDao.queryRows(sql, params, NoteType.class);
		return list;
	}
	public NoteType checkTypeName(int uid, String typeName, String typeId) {
		// TODO Auto-generated method stub
		//sql语句
		String sql="select * from tb_note_type where uid=? and typename=?";
		//定义参数列表，传入参数
		List<Object> params = new ArrayList<>();
		params.add(uid);
		params.add(typeName);
		//区分是修改查询还是添加查询
		if(StringUtil.isNotEmpty(typeId)){
			sql+="and typeid!=?";
			params.add(typeId);
		}
		//调用base到的方法，返回object
		NoteType noteType=(NoteType)baseDao.queryRow(sql, params, NoteType.class);
		return noteType;
	}
	public int delete(String typeid) {
		// TODO Auto-generated method stub
		//写sql语句
		String sql="delete from tb_note_type where typeid=?";
		//创建参数列表
		List<Object> params=new ArrayList<>();
		params.add(typeid);
		//调用basedao的方法
		int row=baseDao.execUpdate(sql, params);
		return row;
	}
	public int addtype(int uid, String typeName, String typeid) {
		// TODO Auto-generated method stub
		//写sql语句
		String sql;
		//写参数列表
		List<Object> params =new ArrayList<>();
		//区分是添加还是修改
		if(StringUtil.isNotEmpty(typeid)){
			sql="update tb_note_type set typename=? where typeid=?";
			params.add(typeName);
			params.add(typeid);
		}else{
			//写sql语句
			sql="insert into tb_note_type (typename,uid) values(?,?) ";
			params.add(typeName);
			params.add(uid);
		}
		//调用basedao方法，返回受影响的行数
		int row=baseDao.execUpdate(sql, params);
		return row;
		
	}
	public NoteType selectType(int uid, String typeName) {
		// TODO Auto-generated method stub
		String sql="select * from tb_note_type where uid=? and typename=?";
		List<Object> params =new ArrayList<>();
		params.add(uid);
		params.add(typeName);
		NoteType noteType=(NoteType)baseDao.queryRow(sql, params, NoteType.class);
		return noteType;
	}

}
