package com.shsxt.dao;

import java.util.ArrayList;
import java.util.List;

import com.shsxt.po.NoteType;

public class NoteDao {
	private BaseDao baseDao=new BaseDao();
	public List daoview(int uid) {
		// TODO Auto-generated method stub
		//��ѯ�� sql���
		String sql="select * from tb_note_type where uid=?";
		//�����б�
		List<Object> params = new ArrayList<>();
		//��Ӳ���
		params.add(uid);
		//����basedao�ķ���,���ؼ���
		List list=baseDao.queryRows(sql, params, NoteType.class);
		return list;
	}

}
