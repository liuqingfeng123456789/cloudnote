package com.shsxt.service;

import java.util.List;
import com.shsxt.dao.TypeDao;
import com.shsxt.po.Note;
import com.shsxt.po.NoteType;
import com.shsxt.po.vo.ResultInfo;
import com.shsxt.util.StringUtil;

public class TypeService {
	private TypeDao typeDao=new TypeDao();
		
	public ResultInfo<List<NoteType>> typelist(int uid) {
		// TODO Auto-generated method stub
		ResultInfo<List<NoteType>> resultInfo=new ResultInfo<>();
		if(uid<0){
			resultInfo.setCode(0);
			resultInfo.setMsg("发生了不可描述的错误");
			return resultInfo;
		}
		List<NoteType> list= typeDao.typelist(uid);
		
		if(list==null || list.size()<1){
			resultInfo.setCode(0);
			resultInfo.setMsg("没有数据类型");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setResult(list);
		return resultInfo;
		
	}

	public ResultInfo<NoteType> checkTypeName(int uid, String typeName, String typeId) {
		// TODO Auto-generated method stub
		ResultInfo<NoteType> resultInfo =new ResultInfo<>();
		
		//判断typename是否为空
		if(StringUtil.isEmpty(typeName)){
			resultInfo.setCode(0);
			resultInfo.setMsg("不能为空");
			return resultInfo;
		}
		//调用dao层的方法

			NoteType noteType=typeDao.checkTypeName(uid,typeName,typeId);

		//判断返回值是否为空

		if(noteType!=null){
			resultInfo.setCode(0);
			resultInfo.setMsg("该类型已存在");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("输入正确");
		return resultInfo;

	}

	public ResultInfo<NoteType> delete(String typeid) {
		// TODO Auto-generated method stub
		ResultInfo<NoteType> resultInfo =new ResultInfo<>();
		//判断参数是否存在
		if(StringUtil.isEmpty(typeid)){
			resultInfo.setCode(0);
			resultInfo.setMsg("typeid 不能为空");
			return resultInfo;
		}
		//调用dao层的方法，返回受影响的行数
		int row =typeDao.delete(typeid);
		//判断row的值来判断数据库删除是否成功;
		if(row<=0){
			resultInfo.setCode(0);
			resultInfo.setMsg("删除失败，请再尝试");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("删除成功");
		return resultInfo;
	}

	public ResultInfo<NoteType> addtype(int uid, String typeName, String typeid) {
		// TODO Auto-generated method stub
		ResultInfo<NoteType> resultInfo=new ResultInfo<>();
		//判断TypeName是否为空
		if(StringUtil.isEmpty(typeName)){
			resultInfo.setCode(0);
			resultInfo.setMsg("类型名不能为空");
			return resultInfo;
		}
		
		//调用dao层的方法，返回受影响的行数
		int row=typeDao.addtype(uid,typeName,typeid);
		//根据行数判断操作数据库是否成功
		if(row>0){
			resultInfo.setCode(1);
			resultInfo.setMsg("成功");
			NoteType noteType =typeDao.selectType(uid,typeName);
			resultInfo.setResult(noteType);
			return resultInfo;
		}
		else{
			resultInfo.setCode(0);
			resultInfo.setMsg("发生了未知的错误");
		}
		return resultInfo;
	}

}
