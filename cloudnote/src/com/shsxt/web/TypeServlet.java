package com.shsxt.web;

import java.io.Console;
import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.tomcat.util.buf.UEncoder;

import com.shsxt.po.Note;
import com.shsxt.po.NoteType;
import com.shsxt.po.User;
import com.shsxt.po.vo.ResultInfo;
import com.shsxt.service.TypeService;
import com.shsxt.util.JSONutil;
import com.sun.corba.se.impl.orbutil.threadpool.TimeoutException;

/**
 * Servlet implementation class TypeServlet
 */
@WebServlet("/type")
public class TypeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private TypeService typeservice=new TypeService();
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String actionName=request.getParameter("action");
		if("list".equals(actionName)){
			typelist(request,response);
		}else if("checkTypeName".equals(actionName)){
			checkTypeName(request,response);
		}else if("delete".equals(actionName)){
			delete(request, response);
		}else if("addtype".equals(actionName)){
				addtype(request,response);
		}
		else{
			
		}
	}
	private void addtype(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//获取uid
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		//接收参数typename
		String typeName=request.getParameter("typeName");
		//接收参数typeid
		String typeid=request.getParameter("typeId");
		//调用service层的方法
		ResultInfo<NoteType> resultInfo=typeservice.addtype(uid,typeName,typeid);;
		//将resultinfo存到request作用域中
		request.setAttribute("resultinfo", resultInfo);
		//将result对象返回给前台
		JSONutil.toJson(resultInfo, response);
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//获取typeid参数值
		String typeid=request.getParameter("typeId");
		//调用service层的方法
		ResultInfo<NoteType> resultInfo=typeservice.delete(typeid);
		//返回resultinfo对象给给客户端
		JSONutil.toJson(resultInfo, response);
		
	}

	private void checkTypeName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//查找userid
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		//获取typename和typeid
		String typeName=request.getParameter("typeName");
		String typeId=request.getParameter("typeId");
		//调用service层的方法,返回resultinfo的对象
		ResultInfo<NoteType> resultInfo= typeservice.checkTypeName(uid,typeName,typeId);
		//将resultinfo返回给客户端
		JSONutil.toJson(resultInfo, response);
	}
	private void typelist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//通过session中的用户名获得UID，因为后面再查询type类型的时候会用到UID
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid(); 
		//将resultinfo存在session作用域中
		ResultInfo<List<NoteType>> resultInfo=typeservice.typelist(uid);
		System.out.println(resultInfo.getCode());
		System.out.println(resultInfo.getResult());
		//设置页面包含type/list.jsp
		request.setAttribute("changepage", "type/list.jsp");
		//请求转发到index的jsp
		request.setAttribute("resultinfo", resultInfo);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
