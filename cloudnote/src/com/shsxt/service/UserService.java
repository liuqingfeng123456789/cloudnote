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

/*Service层：（业务逻辑处理）
1、判断参数 （返回的封装类ResultInfo  code状态码 0=失败，1=成功、msg提示信息、result返回的数据 泛型）
	如果参数为空，code=0，msg=用户名/密码不能为空，返回ResultInfo对象
2、调用Dao层的查询方法，得到user对象
3、判断user对象是否为空 
	如果为空，code=0，msg=用户不存在，返回ResultInfo对象
4、判断前台传过来的密码是否和数据库查询的密码一致
	将前台传过来的密码先加密，再与数据库比较  （加密工具类）
	如果为空，code=0，msg=用户密码不正确，返回ResultInfo对象
5、code=1，msg=登录成功，返回ResultInfo对象
*/
public class UserService {
	private UserDao userDao=new UserDao();
	public ResultInfo<User> login(String uname,String upwd){
		ResultInfo<User> resultInfo=new ResultInfo<>();
		//判断用户名是否为空，并将相应的信息写入resultInfo中
		if(StringUtil.isEmpty(uname)){
			resultInfo.setCode(0);
			resultInfo.setMsg("用户名不能为空");
			return resultInfo;
		}
		//判断密码是否为空,并将相应的信息写入resultInfo中
		if(StringUtil.isEmpty(upwd)){
			resultInfo.setCode(0);
			resultInfo.setMsg("密码不能为空");
			return resultInfo;
		}
		
		User user=userDao.findname(uname);
		
		//判断用户名是否存在
		if(user==null){
			resultInfo.setCode(0);
			resultInfo.setMsg("用户名不存在");
			return resultInfo;
			
		}
		//判断密码是否正确
		upwd=MD5util.md5(upwd) ;
		if(!upwd.equals(user.getUpwd())){
			resultInfo.setCode(0);
			resultInfo.setMsg("密码不正确");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("登录成功");
		resultInfo.setResult(user);
		return resultInfo;
		
	}



	public ResultInfo<User> nicknameservice(int uid, String nickname) {
		//
		ResultInfo<User> resultInfo=new ResultInfo<>();
		// TODO Auto-generated method stub
		//判断昵称是否为空
		if(StringUtil.isEmpty(nickname)){
			resultInfo.setCode(0);
			resultInfo.setMsg("nickname不能为空");
			return resultInfo;
		}
		//判断user是否为空
		User user=userDao.findnicknamedao(nickname,uid);
		if(user!=null){
			resultInfo.setCode(0);
			resultInfo.setMsg("该昵称已被使用，请重新尝试");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("昵称输入正确");
		return resultInfo;
	}



	public ResultInfo<User> updateinfo(HttpServletRequest request) throws IOException, ServletException {
		ResultInfo<User> resultInfo =new ResultInfo<>();
		//获取参数
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		String nickname=request.getParameter("nickname");
		String mood=request.getParameter("mood");
		String imagename=request.getParameter("myfile");
		//判断昵称是否为空
		if(StringUtil.isEmpty(nickname)){
			resultInfo.setCode(0);
			resultInfo.setMsg("昵称不能为空");
			return resultInfo;
		}
		//调用nicknameservice方法，查询昵称是否被占用
		resultInfo=nicknameservice(uid, nickname);
		if(resultInfo.getCode()==0){
			return resultInfo;
		}
		/* 5、文件上传
		1、得到part对象 Part part = request.getPart("img"); // getPart(name):前台file表单元素的name属性值
		
		2、得到文件存放的路径 path =   request.getServletContext().getRealPath("/WEB-INF/upload/");
		3、得到上传文件的名称 fileName =  part.getSubmitedFiledName()
		4、上传文件到指定路径  part.write(path + fileName);
	6、数据库修改 （修改用户信息  昵称、头像、心情、用户Id） 返回受影响的行数
	7、如果受影响的行数大于0，更新session作用域 
	8、返回resultInfo对象
		*/
		Part part= request.getPart("myfile");
		String path=request.getServletContext().getRealPath("/WEB-INF/upload/");
		String filename=part.getSubmittedFileName();
		part.write(path+filename);
		int row=userDao.updateinfo(nickname,filename,mood,uid);
		if(row<=0){
			resultInfo.setCode(0);
			resultInfo.setMsg("修改出现错误，请重新尝试");
			return resultInfo;
		}
		resultInfo.setCode(1);
		resultInfo.setMsg("修改成功");
		user.setNickname(nickname);
		user.setHead(filename);
		user.setMood(mood);
		request.getSession().setAttribute("user", user);
		return resultInfo;
		// TODO Auto-generated method stub
	
	}

	
}
