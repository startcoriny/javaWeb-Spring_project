package com.spring.ex02;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/mem2.do")
public class MemberServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)	throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO();
		String name = dao.selectName();
		// MemberDAO의 selectName()메서드를 호출
		
		int pwd = dao.selectPwd();
		// MemberDAO의 selectPwd()메서드를 호출
		PrintWriter pw = response.getWriter();
		pw.write("<script>");
		 pw.write("alert('이름 :" + name + "');");
		// 조회한 이름을 브라우저로 출력
		pw.write("alert(' 비밀번호"+ pwd+"');");
		pw.write("</script>");
	}
}
