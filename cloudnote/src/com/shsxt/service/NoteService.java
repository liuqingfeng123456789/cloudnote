package com.shsxt.service;

import java.util.List;

import javax.print.attribute.ResolutionSyntax;

import com.shsxt.dao.NoteDao;
import com.shsxt.po.NoteType;
import com.shsxt.po.vo.ResultInfo;
import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class NoteService {
	private NoteDao noteDao=new NoteDao();

	public ResultInfo<List<NoteType>> noteview(int uid) {
		// TODO Auto-generated method stub
		ResultInfo<List<NoteType>> resultInfo=new ResultInfo<>();
		//����dao��ķ��������ؼ���
		List list= noteDao.daoview(uid);
		//�ж�list�����list��Ϊ�գ�����ֵ����list����resultinfo��
		if(list!=null && list.size()>0){
			resultInfo.setCode(1);
			resultInfo.setMsg("����������");
			resultInfo.setResult(list);
			return resultInfo;
		}
		//���listΪ��
		resultInfo.setCode(0);
		resultInfo.setMsg("û����������,���������������");
		return resultInfo;
	}
	
}
