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
		//дsql���
		String sql="select * from tb_note_type where uid=?";
		//��Ų������б�
		List<Object> params = new ArrayList<>();
		params.add(uid);
		//����basedao�ķ���
		List list=baseDao.queryRows(sql, params, NoteType.class);
		return list;
	}
	public NoteType checkTypeName(int uid, String typeName, String typeId) {
		// TODO Auto-generated method stub
		//sql���
		String sql="select * from tb_note_type where uid=? and typename=?";
		//��������б��������
		List<Object> params = new ArrayList<>();
		params.add(uid);
		params.add(typeName);
		//�������޸Ĳ�ѯ������Ӳ�ѯ
		if(StringUtil.isNotEmpty(typeId)){
			sql+="and typeid!=?";
			params.add(typeId);
		}
		//����base���ķ���������object
		NoteType noteType=(NoteType)baseDao.queryRow(sql, params, NoteType.class);
		return noteType;
	}
	public int delete(String typeid) {
		// TODO Auto-generated method stub
		//дsql���
		String sql="delete from tb_note_type where typeid=?";
		//���������б�
		List<Object> params=new ArrayList<>();
		params.add(typeid);
		//����basedao�ķ���
		int row=baseDao.execUpdate(sql, params);
		return row;
	}
	public int addtype(int uid, String typeName, String typeid) {
		// TODO Auto-generated method stub
		//дsql���
		String sql;
		//д�����б�
		List<Object> params =new ArrayList<>();
		//��������ӻ����޸�
		if(StringUtil.isNotEmpty(typeid)){
			sql="update tb_note_type set typename=? where typeid=?";
			params.add(typeName);
			params.add(typeid);
		}else{
			//дsql���
			sql="insert into tb_note_type (typename,uid) values(?,?) ";
			params.add(typeName);
			params.add(uid);
		}
		//����basedao������������Ӱ�������
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
