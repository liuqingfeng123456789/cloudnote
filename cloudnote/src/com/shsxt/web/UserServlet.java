package com.shsxt.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.FileUtils;

import com.alibaba.fastjson.JSON;
import com.shsxt.po.User;
import com.shsxt.po.vo.ResultInfo;
import com.shsxt.service.UserService;
import com.shsxt.util.JSONutil;
import com.shsxt.util.StringUtil;




/**
 * Servlet implementation class UserServlet
 */
@WebServlet("/user")
@MultipartConfig
public class UserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private UserService userService =new UserService();
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

/*		Servlet层：
		1、接收参数
		2、调用Service层，返回ResultInfo对象
		3、判断code是否为1，登录成功
			将用户信息存到session作用域中
			判断是否记住密码，如果是，存cookie
		4、将ResultInfo对象转换成JSON字符串，响应给ajax的回调函数*/
		
		String actionName=request.getParameter("action");
		if("login".equals(actionName)){
			login(request,response);
		}else if("logout".equals(actionName)){
			logout(request,response);
		}else if("autologin".equals(actionName)){
			autologin(request,response);
		}else if("usercenter".equals(actionName)){
			usercenter(request,response);
		}else if("userheader".equals(actionName)){
			userheader(request,response);
		}else if("updatenickname".equals(actionName)){
			updatenickname(request,response);
		}else if("updateinfo".equals(actionName)){
			updateinfo(request,response);
		}
		else{
			response.sendRedirect("login.jsp");
		}
		
	}

	private void updateinfo(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	/*	User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		String nickname=request.getParameter("nickname");
		String mood=request.getParameter("mood");
		String imagename=request.getParameter("myfile");*/
		//调用service层的方法，返回resultinfo信息
		ResultInfo<User> resultInfo=userService.updateinfo(request);
		//将resultInfo对象存到request作用域中(为什么要设置resultinfo)
		request.setAttribute("resultInfo", resultInfo);
		//设置页面包含值
		request.setAttribute("changepage", "user/info.jsp");
		request.getRequestDispatcher("user?action=usercenter").forward(request, response);
	}

	private void updatenickname(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//在session中获取用户对象，拿到用户id，传到service层的方法中
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		//接收参数
		String nickname=request.getParameter("nickname");
		//调用service层的方法,返回resultinfo对象
		ResultInfo resultInfo= userService.nicknameservice(uid, nickname);
		//将对象转为json字符串返回给ajax
		JSONutil.toJson(resultInfo,response);
	}

	private void userheader(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//获取图片的名称
		String imagename=request.getParameter("imagename");
		if(imagename==null){
			return;
		}
		//得到文件的真实路径+图片名称
		String filepath=request.getServletContext().getRealPath("/WEB-INF/upload/")+imagename;
		System.out.println(filepath);
		File file=new File(filepath);
		//当文件存在，并且是一个文件的时候
		if(!file.exists() && !file.isFile()){
			return ;
		}
			//通过截取字符串，获取图片后缀名
			String format=imagename.substring(imagename.lastIndexOf("."));
			//通过图片的后缀名来设置图片的 响应类型
			if("png".equals(format)){
				response.setContentType("image/png");
			}else if("jpg".equals(format) || "jpeg".equals(format)){
				response.setContentType("image/jpeg");
			}else{
				response.setContentType("image/gif");
			}
			FileUtils.copyFile(file, response.getOutputStream());
		
		
		
	}

	private void usercenter(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setAttribute("changepage", "user/info.jsp");
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

	private void autologin(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		// TODO Auto-generated method stub
		ResultInfo<User> resultInfo = loginInfo(request, response);
		//判断用户密码是否正确
		if(resultInfo.getCode()==1){
			
			request.getRequestDispatcher("index").forward(request, response);
		}else{
			response.sendRedirect("login.jsp");
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//删除session
		request.getSession().invalidate();
		Cookie cookie=new Cookie("user", null);
		//删除cookie
		cookie.setMaxAge(0);
		//给客户端发送清除cookie的消息
		response.addCookie(cookie);
		//重定向到登录页面
		response.sendRedirect("login.jsp");
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
			//为了获取resultinfo
			ResultInfo<User> resultInfo=loginInfo(request, response);
			//获取用户名，密码，是否记住 我三个参数
			String rem=request.getParameter("rem");
			String uname=request.getParameter("uname");
			String upwd=request.getParameter("upwd");
			System.out.println("rem---->"+rem);
			//判断输入的用户名和密码是否正确
			if(resultInfo.getCode()==1){
				request.getSession().setAttribute("user", resultInfo.getResult());
				//判断记住我是否等于1
				if(rem.equals("1")){
					Cookie cookie=new Cookie("user", uname+"-"+upwd);
					//设置cookie的过期时间
					cookie.setMaxAge(3*24*60*60);
					//
					response.addCookie(cookie);
				}
			}
			//将对象转为json字符串
			String json=JSON.toJSONString(resultInfo);
			//发送给ajax;
			PrintWriter out=response.getWriter();
			out.write(json);
			out.close();
		
	}
	
	public ResultInfo<User> loginInfo(HttpServletRequest request, HttpServletResponse response) throws IOException {
		String uname=request.getParameter("uname");
		String upwd=request.getParameter("upwd");

	
		
		ResultInfo<User> resultInfo=userService.login(uname,upwd);

		return resultInfo;
	}
	
	
	
}
