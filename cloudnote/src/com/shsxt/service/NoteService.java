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
		//调用dao层的方法，返回集合
		List list= noteDao.daoview(uid);
		//判断list，如果list不为空，且有值，将list塞入resultinfo中
		if(list!=null && list.size()>0){
			resultInfo.setCode(1);
			resultInfo.setMsg("有数据类型");
			resultInfo.setResult(list);
			return resultInfo;
		}
		//如果list为空
		resultInfo.setCode(0);
		resultInfo.setMsg("没有数据类型,请先添加数据类型");
		return resultInfo;
	}
	
}
