package com.shsxt.filter;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import javax.servlet.http.HttpServletResponse;


/**
 * �����ַ�����Ĺ�����
 * 
 * 		Tomcat8������  
 * 			GET����     ����Ҫ����
 * 			POST����	��Ҫ����
 * 					���ý������ݵı���	request.setCharacterEncoding("UTF-8");
 * 					������Ӧ���ݵı���	response.setContentType("text/html;charset=UTF-8");
 * 
 * 		Tomcat7������
 * 			GET����	��Ҫ����
 * 					���ý��ղ����ı���	new String(request.getParameter("name").getBytes("ISO-8859-1"),"UTF-8");
 * 					������Ӧ���ݵı���	response.setContentType("text/html;charset=UTF-8");
 * 			POST����	��Ҫ����
 * 					���ý������ݵı���	request.setCharacterEncoding("UTF-8");
 * 					������Ӧ���ݵı���	response.setContentType("text/html;charset=UTF-8");
 * 
 * @author Lisa Li
 *
 */

@WebFilter("/*")
public class EncodeFilter implements Filter {

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		System.out.println("init...");
		
	}

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		
		// ����HTTP
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse)resp;
		
		// ���ý��պ���Ӧ���ַ�����
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		// �õ�����ʽ
		String  method = request.getMethod();  // get  GET  post POST
		
		// �ж��Ƿ���GET����
		if ("GET".equals(method.toUpperCase())) { // GET����
			// �õ��������İ汾   Apache Tomcat/8.0.45
			String serverInfo = request.getServletContext().getServerInfo();
			System.out.println(serverInfo);
			// �õ��������İ汾��
			String versionStr = serverInfo.substring(serverInfo.indexOf("/")+1, serverInfo.indexOf("/")+2);
			// System.out.println(versionStr);
			Integer version = Integer.parseInt(versionStr);
			// ���Tomcat�İ汾��8���£���Ҫ��������
			if (version < 8) {
				// ��дgetParameter()��������GET����
				MyWapper myWrapper = new MyWapper(request);
				// ����
				chain.doFilter(myWrapper, response);
				return;
			}
			
		}
		
		
		// ���� 
		chain.doFilter(request, response);
		
	}
	
	
	/**
	 * �����ڲ��࣬�̳�HttpServletRequest�İ�װ��HttpServletRequestWapper����дgetParameter(String name)����
	 * 	ע��㣺
	 * 		��request���������÷�Χ����
	 * @author Lisa Li
	 *
	 */
	class MyWapper extends HttpServletRequestWrapper{

		private HttpServletRequest request;
		public MyWapper(HttpServletRequest request) {
			super(request);
			this.request = request;
		}

		@Override
		public String getParameter(String name) {
			String value = "";
			try {
				// ���get����
				value = new String(request.getParameter(name).getBytes("ISO-8859-1"), "UTF-8");
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
			return value;
		}
		
	}

	@Override
	public void destroy() {
		System.out.println("destory...");
		
	}

}
