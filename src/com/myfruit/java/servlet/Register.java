package com.myfruit.java.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.myfruit.java.service.LoginService;


public class Register extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Register() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		
		String k = request.getParameter("k");
		int key = Integer.parseInt(k);
		String tip = request.getParameter("tip");
		if(key==1 && tip.equals("true")) {
			String useidNum = request.getParameter("useid");
			int useid = Integer.parseInt(useidNum);
			String phone = request.getParameter("phone");
			String uname = request.getParameter("uname");
			String pass = request.getParameter("pass");
			if(null != useidNum&&null != phone&&null != uname&&null != pass) {
				LoginService ls = new LoginService();
				PrintWriter pw = response.getWriter();
				HttpSession hs = request.getSession();
				hs.setAttribute("NNAME", uname);
				hs.setAttribute("USEID", useid);
				ls.register(useid, uname, phone, pass);
				pw.println("<script>alert('注册成功！！请完善资料');location.href='user_info.jsp?useid="+useid+"';</script>");
			}
		}
//		else {//跳转页面到用户信息页面，进行进一步完善用户表资料
//			response.sendRedirect("registered.jsp");//这一步跳转有问题
//		}
		if(key==2) {//从uesr_info页面传过来的参数，进行用户资料完善
			String useidNum = request.getParameter("useid");
			int useid = Integer.parseInt(useidNum);
			String phone = request.getParameter("phone");
			String uname = request.getParameter("uname");
			String pass = request.getParameter("pass");
			System.out.println("密码："+pass);
			String realname = request.getParameter("realname");
			String sex = request.getParameter("RadioGroup1");
			System.out.println("是一是2："+sex);
			if(sex.equals("1")) {
				sex = "男";
			}if(sex.equals("2")) {
				sex = "女";
			}
			System.out.println("是男是女："+sex);
			String email = request.getParameter("email");
			System.out.println("伊美尔："+email);
			LoginService ls = new LoginService();
			PrintWriter pw = response.getWriter();
			HttpSession hs = request.getSession();
			hs.setAttribute("NNAME", uname);
			hs.setAttribute("USEID", useid);
			hs.setAttribute("EMAIL", email);
			//需要一个修改用户信息表的方法
			ls.registered(uname, phone, pass, realname, sex, email, useid);
			System.out.println("get it");
			pw.println("<script>alert('请完善您的收货地址信息');location.href='user_address.jsp?useid="+useid+"';</script>");
		}
		//设置一个K值，代表，注册跟完善两种情况
		//先判断用户id是否重复，不重复注册，重复，给足提示，重新注册
//		if(ls.isExsit(useid)) {
//			ls.register(useid, uname, phone, pass);//注册开始
//			System.out.println("搞定");
//			response.sendRedirect("user_info.jsp");
//		}else {
//			pw.println("<script>alert('该用户id已存在，请重新注册');location.href='registered.jsp';</script>");
//			response.sendRedirect("registerd.jsp");
//		}
//		request.getRequestDispatcher("user_info.jsp").forward(request,response);
//		response.sendRedirect("user_info.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
