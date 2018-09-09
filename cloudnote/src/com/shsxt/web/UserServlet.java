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

/*		Servlet�㣺
		1�����ղ���
		2������Service�㣬����ResultInfo����
		3���ж�code�Ƿ�Ϊ1����¼�ɹ�
			���û���Ϣ�浽session��������
			�ж��Ƿ��ס���룬����ǣ���cookie
		4����ResultInfo����ת����JSON�ַ�������Ӧ��ajax�Ļص�����*/
		
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
		//����service��ķ���������resultinfo��Ϣ
		ResultInfo<User> resultInfo=userService.updateinfo(request);
		//��resultInfo����浽request��������(ΪʲôҪ����resultinfo)
		request.setAttribute("resultInfo", resultInfo);
		//����ҳ�����ֵ
		request.setAttribute("changepage", "user/info.jsp");
		request.getRequestDispatcher("user?action=usercenter").forward(request, response);
	}

	private void updatenickname(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//��session�л�ȡ�û������õ��û�id������service��ķ�����
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		//���ղ���
		String nickname=request.getParameter("nickname");
		//����service��ķ���,����resultinfo����
		ResultInfo resultInfo= userService.nicknameservice(uid, nickname);
		//������תΪjson�ַ������ظ�ajax
		JSONutil.toJson(resultInfo,response);
	}

	private void userheader(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//��ȡͼƬ������
		String imagename=request.getParameter("imagename");
		if(imagename==null){
			return;
		}
		//�õ��ļ�����ʵ·��+ͼƬ����
		String filepath=request.getServletContext().getRealPath("/WEB-INF/upload/")+imagename;
		System.out.println(filepath);
		File file=new File(filepath);
		//���ļ����ڣ�������һ���ļ���ʱ��
		if(!file.exists() && !file.isFile()){
			return ;
		}
			//ͨ����ȡ�ַ�������ȡͼƬ��׺��
			String format=imagename.substring(imagename.lastIndexOf("."));
			//ͨ��ͼƬ�ĺ�׺��������ͼƬ�� ��Ӧ����
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
		//�ж��û������Ƿ���ȷ
		if(resultInfo.getCode()==1){
			
			request.getRequestDispatcher("index").forward(request, response);
		}else{
			response.sendRedirect("login.jsp");
		}
	}

	private void logout(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//ɾ��session
		request.getSession().invalidate();
		Cookie cookie=new Cookie("user", null);
		//ɾ��cookie
		cookie.setMaxAge(0);
		//���ͻ��˷������cookie����Ϣ
		response.addCookie(cookie);
		//�ض��򵽵�¼ҳ��
		response.sendRedirect("login.jsp");
	}

	private void login(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
			//Ϊ�˻�ȡresultinfo
			ResultInfo<User> resultInfo=loginInfo(request, response);
			//��ȡ�û��������룬�Ƿ��ס ����������
			String rem=request.getParameter("rem");
			String uname=request.getParameter("uname");
			String upwd=request.getParameter("upwd");
			System.out.println("rem---->"+rem);
			//�ж�������û����������Ƿ���ȷ
			if(resultInfo.getCode()==1){
				request.getSession().setAttribute("user", resultInfo.getResult());
				//�жϼ�ס���Ƿ����1
				if(rem.equals("1")){
					Cookie cookie=new Cookie("user", uname+"-"+upwd);
					//����cookie�Ĺ���ʱ��
					cookie.setMaxAge(3*24*60*60);
					//
					response.addCookie(cookie);
				}
			}
			//������תΪjson�ַ���
			String json=JSON.toJSONString(resultInfo);
			//���͸�ajax;
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
