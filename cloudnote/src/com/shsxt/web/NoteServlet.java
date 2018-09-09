package com.shsxt.web;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shsxt.po.NoteType;
import com.shsxt.po.User;
import com.shsxt.po.vo.ResultInfo;
import com.shsxt.service.NoteService;
import com.shsxt.util.StringUtil;
import com.sun.glass.ui.View;

/**
 * Servlet implementation class NoteServlet
 */
@WebServlet("/note")
public class NoteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private NoteService noteService=new NoteService();
	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String actionName=request.getParameter("action");
		if("view".equals(actionName)){
			view(request,response);
		}else{
			
		}
	}
	private void view(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//��ȡuid����ѯ���͵������
		User user=(User)request.getSession().getAttribute("user");
		int uid=user.getUid();
		//����service�㷽�����Ż�resultinfo
		ResultInfo<List<NoteType>> resultInfo= noteService.noteview(uid);
		//���б����ʹ���request����������
		request.setAttribute("typeList", resultInfo.getResult());
		//����ҳ�涯ֵ̬
		request.setAttribute("changepage", "note/view.jsp");
		//����ת����index.jspҳ��
		request.getRequestDispatcher("index.jsp").forward(request, response);
	}

}
