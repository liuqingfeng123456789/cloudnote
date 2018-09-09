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
		//��ȡuid
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		//���ղ���typename
		String typeName=request.getParameter("typeName");
		//���ղ���typeid
		String typeid=request.getParameter("typeId");
		//����service��ķ���
		ResultInfo<NoteType> resultInfo=typeservice.addtype(uid,typeName,typeid);;
		//��resultinfo�浽request��������
		request.setAttribute("resultinfo", resultInfo);
		//��result���󷵻ظ�ǰ̨
		JSONutil.toJson(resultInfo, response);
	}
	
	private void delete(HttpServletRequest request, HttpServletResponse response) throws IOException {
		//��ȡtypeid����ֵ
		String typeid=request.getParameter("typeId");
		//����service��ķ���
		ResultInfo<NoteType> resultInfo=typeservice.delete(typeid);
		//����resultinfo��������ͻ���
		JSONutil.toJson(resultInfo, response);
		
	}

	private void checkTypeName(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		//����userid
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		//��ȡtypename��typeid
		String typeName=request.getParameter("typeName");
		String typeId=request.getParameter("typeId");
		//����service��ķ���,����resultinfo�Ķ���
		ResultInfo<NoteType> resultInfo= typeservice.checkTypeName(uid,typeName,typeId);
		//��resultinfo���ظ��ͻ���
		JSONutil.toJson(resultInfo, response);
	}
	private void typelist(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//ͨ��session�е��û������UID����Ϊ�����ٲ�ѯtype���͵�ʱ����õ�UID
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid(); 
		//��resultinfo����session��������
		ResultInfo<List<NoteType>> resultInfo=typeservice.typelist(uid);
		System.out.println(resultInfo.getCode());
		System.out.println(resultInfo.getResult());
		//����ҳ�����type/list.jsp
		request.setAttribute("changepage", "type/list.jsp");
		//����ת����index��jsp
		request.setAttribute("resultinfo", resultInfo);
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
