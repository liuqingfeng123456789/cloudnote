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
			resultInfo.setMsg("�����˲��������Ĵ���");
			return resultInfo;
		}
		List<NoteType> list= typeDao.typelist(uid);
		
		if(list==null || list.size()<1){
			resultInfo.setCode(0);
			resultInfo.setMsg("û����������");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setResult(list);
		return resultInfo;
		
	}

	public ResultInfo<NoteType> checkTypeName(int uid, String typeName, String typeId) {
		// TODO Auto-generated method stub
		ResultInfo<NoteType> resultInfo =new ResultInfo<>();
		
		//�ж�typename�Ƿ�Ϊ��
		if(StringUtil.isEmpty(typeName)){
			resultInfo.setCode(0);
			resultInfo.setMsg("����Ϊ��");
			return resultInfo;
		}
		//����dao��ķ���

			NoteType noteType=typeDao.checkTypeName(uid,typeName,typeId);

		//�жϷ���ֵ�Ƿ�Ϊ��

		if(noteType!=null){
			resultInfo.setCode(0);
			resultInfo.setMsg("�������Ѵ���");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("������ȷ");
		return resultInfo;

	}

	public ResultInfo<NoteType> delete(String typeid) {
		// TODO Auto-generated method stub
		ResultInfo<NoteType> resultInfo =new ResultInfo<>();
		//�жϲ����Ƿ����
		if(StringUtil.isEmpty(typeid)){
			resultInfo.setCode(0);
			resultInfo.setMsg("typeid ����Ϊ��");
			return resultInfo;
		}
		//����dao��ķ�����������Ӱ�������
		int row =typeDao.delete(typeid);
		//�ж�row��ֵ���ж����ݿ�ɾ���Ƿ�ɹ�;
		if(row<=0){
			resultInfo.setCode(0);
			resultInfo.setMsg("ɾ��ʧ�ܣ����ٳ���");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("ɾ���ɹ�");
		return resultInfo;
	}

	public ResultInfo<NoteType> addtype(int uid, String typeName, String typeid) {
		// TODO Auto-generated method stub
		ResultInfo<NoteType> resultInfo=new ResultInfo<>();
		//�ж�TypeName�Ƿ�Ϊ��
		if(StringUtil.isEmpty(typeName)){
			resultInfo.setCode(0);
			resultInfo.setMsg("����������Ϊ��");
			return resultInfo;
		}
		
		//����dao��ķ�����������Ӱ�������
		int row=typeDao.addtype(uid,typeName,typeid);
		//���������жϲ������ݿ��Ƿ�ɹ�
		if(row>0){
			resultInfo.setCode(1);
			resultInfo.setMsg("�ɹ�");
			NoteType noteType =typeDao.selectType(uid,typeName);
			resultInfo.setResult(noteType);
			return resultInfo;
		}
		else{
			resultInfo.setCode(0);
			resultInfo.setMsg("������δ֪�Ĵ���");
		}
		return resultInfo;
	}

}
