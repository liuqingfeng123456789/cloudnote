package com.shsxt.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.shsxt.po.User;

/**
 * Servlet Filter implementation class LoginFilter
 */
@WebFilter("/*")
public class LoginFilter implements Filter {

    /**
     * Default constructor. 
     */
    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
		System.out.println("loginfiter is init......");
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// TODO Auto-generated method stub
		// place your code here
		HttpServletRequest req = (HttpServletRequest)request;
		HttpServletResponse resp=(HttpServletResponse)response;
		String uri=req.getRequestURI();
		String actionName=req.getParameter("action");
		//ÅÐ¶ÏÊÇ·ñ°üº¬¾²Ì¬×ÊÔ´
		if(uri.contains("statics")){
			chain.doFilter(req, resp);
			return;
		}
		//ÅÐ¶ÏÊÇ·ñÓÐµÇÂ¼
		if(uri.indexOf("/login.jsp")!=-1){
			chain.doFilter(req, resp);
			return;
		}
		//ÅÐ¶ÏÊÇ·ñ×¢²á
		if(uri.contains("register.jsp")){
			chain.doFilter(req, resp);
			return;
		}
		//ÅÐ¶ÏÊÇ·ñÓÐµÇÂ¼¶¯×÷
		if(uri.contains("user")){
			if("login".equals(actionName) || "autologin".equals(actionName)){
				chain.doFilter(req, resp);
				return ;
			}
			
		}
		//ÅÐ¶ÏsessionÊÇ·ñ´æÔÚ
		User user=(User)req.getSession().getAttribute("user");
		if(user!=null){
			chain.doFilter(req, resp);
			return ;
		}

		 Cookie[] cookies=req.getCookies();
		 if(cookies != null && cookies.length>0){
			 for(Cookie cookie:cookies){
				 String name=cookie.getName();
				 String info=cookie.getValue();
			 
				 if("user".equals(name)){
					 String uname=info.split("-")[0];
					 String upwd=info.split("-")[1];
					 req.setAttribute("uname",uname);
					 req.setAttribute("upwd", upwd);
					 req.getRequestDispatcher("user?action=autologin").forward(req, resp);
					 return ;
				 }
			 }
		 }
		
		resp.sendRedirect("login.jsp");

		
	

	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
		System.out.println("loginfilter is init....");
	}

}
