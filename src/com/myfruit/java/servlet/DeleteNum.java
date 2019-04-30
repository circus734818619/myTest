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


public class DeleteNum extends HttpServlet {
	private static final long serialVersionUID = 1L;
       

    public DeleteNum() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
//		response.getWriter().append("Served at: ").append(request.getContextPath());
		request.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		String k = request.getParameter("key");
		int key = Integer.parseInt(k);
		String uid = request.getParameter("useid");
		int useid = Integer.parseInt(uid);
		HttpSession hs = request.getSession();
		PrintWriter pw = response.getWriter();
		LoginService ls = new LoginService();
		if(key == 1) {
			String id = request.getParameter("fid");
			int fid = Integer.parseInt(id);
			ls.delete(useid, fid);
			System.out.println("删除完毕");
			pw.println("<script>location.href='shopping_cart.jsp';</script>");
		}
		if(key == 2) {
			ls.delete(useid);
			pw.println("<script>alert('购买成功！');location.href='shopping_cart.jsp';</script>");
		}//删除地址
		if(key == 3) {
			String add = request.getParameter("addNum");
			System.out.println(add);
			int addNum = Integer.parseInt(add);
			ls.deleteAdd(useid, addNum);
			pw.println("<script>alert('已删除！');location.href='user_address.jsp';</script>");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
