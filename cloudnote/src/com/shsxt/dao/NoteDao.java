package com.shsxt.dao;

import java.util.ArrayList;
import java.util.List;

import com.shsxt.po.NoteType;

public class NoteDao {
	private BaseDao baseDao=new BaseDao();
	public List daoview(int uid) {
		// TODO Auto-generated method stub
		//查询到 sql语句
		String sql="select * from tb_note_type where uid=?";
		//参数列表
		List<Object> params = new ArrayList<>();
		//添加参数
		params.add(uid);
		//调用basedao的方法,返回集合
		List list=baseDao.queryRows(sql, params, NoteType.class);
		return list;
	}

}
