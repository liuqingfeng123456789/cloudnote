package com.shsxt.service;


import com.shsxt.util.MD5util;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import com.shsxt.dao.UserDao;
import com.shsxt.po.User;
import com.shsxt.po.vo.ResultInfo;
import com.shsxt.util.StringUtil;
import javax.servlet.http.Part;

/*Service�㣺��ҵ���߼�����
1���жϲ��� �����صķ�װ��ResultInfo  code״̬�� 0=ʧ�ܣ�1=�ɹ���msg��ʾ��Ϣ��result���ص����� ���ͣ�
	�������Ϊ�գ�code=0��msg=�û���/���벻��Ϊ�գ�����ResultInfo����
2������Dao��Ĳ�ѯ�������õ�user����
3���ж�user�����Ƿ�Ϊ�� 
	���Ϊ�գ�code=0��msg=�û������ڣ�����ResultInfo����
4���ж�ǰ̨�������������Ƿ�����ݿ��ѯ������һ��
	��ǰ̨�������������ȼ��ܣ��������ݿ�Ƚ�  �����ܹ����ࣩ
	���Ϊ�գ�code=0��msg=�û����벻��ȷ������ResultInfo����
5��code=1��msg=��¼�ɹ�������ResultInfo����
*/
public class UserService {
	private UserDao userDao=new UserDao();
	public ResultInfo<User> login(String uname,String upwd){
		ResultInfo<User> resultInfo=new ResultInfo<>();
		//�ж��û����Ƿ�Ϊ�գ�������Ӧ����Ϣд��resultInfo��
		if(StringUtil.isEmpty(uname)){
			resultInfo.setCode(0);
			resultInfo.setMsg("�û�������Ϊ��");
			return resultInfo;
		}
		//�ж������Ƿ�Ϊ��,������Ӧ����Ϣд��resultInfo��
		if(StringUtil.isEmpty(upwd)){
			resultInfo.setCode(0);
			resultInfo.setMsg("���벻��Ϊ��");
			return resultInfo;
		}
		
		User user=userDao.findname(uname);
		
		//�ж��û����Ƿ����
		if(user==null){
			resultInfo.setCode(0);
			resultInfo.setMsg("�û���������");
			return resultInfo;
			
		}
		//�ж������Ƿ���ȷ
		upwd=MD5util.md5(upwd) ;
		if(!upwd.equals(user.getUpwd())){
			resultInfo.setCode(0);
			resultInfo.setMsg("���벻��ȷ");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("��¼�ɹ�");
		resultInfo.setResult(user);
		return resultInfo;
		
	}



	public ResultInfo<User> nicknameservice(int uid, String nickname) {
		//
		ResultInfo<User> resultInfo=new ResultInfo<>();
		// TODO Auto-generated method stub
		//�ж��ǳ��Ƿ�Ϊ��
		if(StringUtil.isEmpty(nickname)){
			resultInfo.setCode(0);
			resultInfo.setMsg("nickname����Ϊ��");
			return resultInfo;
		}
		//�ж�user�Ƿ�Ϊ��
		User user=userDao.findnicknamedao(nickname,uid);
		if(user!=null){
			resultInfo.setCode(0);
			resultInfo.setMsg("���ǳ��ѱ�ʹ�ã������³���");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("�ǳ�������ȷ");
		return resultInfo;
	}



	public ResultInfo<User> updateinfo(HttpServletRequest request) throws IOException, ServletException {
		ResultInfo<User> resultInfo =new ResultInfo<>();
		//��ȡ����
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		String nickname=request.getParameter("nickname");
		String mood=request.getParameter("mood");
		String imagename=request.getParameter("myfile");
		//�ж��ǳ��Ƿ�Ϊ��
		if(StringUtil.isEmpty(nickname)){
			resultInfo.setCode(0);
			resultInfo.setMsg("�ǳƲ���Ϊ��");
			return resultInfo;
		}
		//����nicknameservice��������ѯ�ǳ��Ƿ�ռ��
		resultInfo=nicknameservice(uid, nickname);
		if(resultInfo.getCode()==0){
			return resultInfo;
		}
		/* 5���ļ��ϴ�
		1���õ�part���� Part part = request.getPart("img"); // getPart(name):ǰ̨file��Ԫ�ص�name����ֵ
		
		2���õ��ļ���ŵ�·�� path =   request.getServletContext().getRealPath("/WEB-INF/upload/");
		3���õ��ϴ��ļ������� fileName =  part.getSubmitedFiledName()
		4���ϴ��ļ���ָ��·��  part.write(path + fileName);
	6�����ݿ��޸� ���޸��û���Ϣ  �ǳơ�ͷ�����顢�û�Id�� ������Ӱ�������
	7�������Ӱ�����������0������session������ 
	8������resultInfo����
		*/
		Part part= request.getPart("myfile");
		String path=request.getServletContext().getRealPath("/WEB-INF/upload/");
		String filename=part.getSubmittedFileName();
		part.write(path+filename);
		int row=userDao.updateinfo(nickname,filename,mood,uid);
		if(row<=0){
			resultInfo.setCode(0);
			resultInfo.setMsg("�޸ĳ��ִ��������³���");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("�޸ĳɹ�");
		user.setNickname(nickname);
		user.setHead(filename);
		user.setMood(mood);
		request.getSession().setAttribute("user", user);
		return resultInfo;
		// TODO Auto-generated method stub
	
	}

	
}
