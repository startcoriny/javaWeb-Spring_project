package com.spring.ex04;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.spring.ex01.MemberVO;

@WebServlet("/mem4.do")
public class MemberServlet extends HttpServlet {
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doHandle(request, response);
	}

	private void doHandle(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		MemberDAO dao = new MemberDAO();
		MemberVO memberVO = new MemberVO();
		String action = request.getParameter("action");
		String nextPage = "";
		System.out.println("들어온 페이지 : " + nextPage);

		
		
		if (action == null || action.equals("listMembers")) {
// 주소를 직접 입력하여 웹 페이지에 접속할 때, <form> 태그의 action 속성은 기본적으로 null로 설정
			List<MemberVO> membersList = dao.selectAllMemberList();
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";

			
		} else if (action.equals("memberForm.do")) {

			
			nextPage = "test03/memberForm.jsp";
			
			
		} else if (action.equals("selectMemberById")) {

			String id = request.getParameter("value");
			System.out.println("가져온 아이디"+id);
			List<MemberVO>memlist = dao.selectMemberById(id);
			request.setAttribute("membersList", memlist);
			nextPage = "test03/listMembers.jsp";

			
			
		} else if (action.equals("selectMemberByPwd")) {

			String pwd = (request.getParameter("value"));
			List<MemberVO> membersList = dao.selectMemberByPwd(pwd);
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";
			
			
			
		} else if (action.equals("selectMemberByname")) {

			String name = (request.getParameter("value"));
			List<MemberVO> membersList = dao.selectMemberByname(name);
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";
			
			
			
		} else if (action.equals("selectMemberByemail")) {

			String email = (request.getParameter("value"));
			List<MemberVO> membersList = dao.selectMemberByemail(email);
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";
			
			
			
		} else if (action.equals("insertMember")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			memberVO.setId(id);
			memberVO.setPwd(pwd);
			memberVO.setName(name);
			memberVO.setEmail(email);
			dao.insertMember(memberVO);
			nextPage = "/mem4.do?action=listMembers";
			
			
			
		} else if (action.equals("insertMember2")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			Map<String, String> memberMap = new HashMap<String, String>();
			// 회원 가입창에서 전송된 회원 정보를 HashMap에 key, value로 저장한후
			// MemberDAO의 insertMember2()의 인자로 문자열 형태로 저장.
			
			memberMap.put("id", id);
			memberMap.put("pwd", pwd);
			memberMap.put("name", name);
			memberMap.put("email", email);
			dao.insertMember2(memberMap);
			nextPage = "/mem4.do?action=listMembers";
			
			
			
		} else if (action.equals("updateMember")) {
			String id = request.getParameter("id");
			String pwd = request.getParameter("pwd");
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			memberVO.setId(id);
			memberVO.setPwd(pwd);
			memberVO.setName(name);
			memberVO.setEmail(email);
			dao.updateMember(memberVO);
			// 회원 수정 창에서 전송된 회원 정보를 MemberVO의 속성에 설정한 후
			// updateMember()를 호출하면서 MemberVO객체를 전달.
			
			nextPage = "/mem4.do?action=listMembers";
			
			
			
		} else if (action.equals("deleteMember")) {
			String id = request.getParameter("id");
			// 회원 아이디 가져오기
			dao.deleteMember(id);
			// 회원목록창에서 전달된 id를 deleteMember()메서드를 호출하면서 SQL문으로 전달.
			nextPage = "/mem4.do?action=listMembers";
			
			
			
		} else if (action.equals("searchMember")) {
			String name = request.getParameter("name");
			String email = request.getParameter("email");
			// 입력받은 값 가져오기
			memberVO.setName(name);
			memberVO.setEmail(email);
			List<MemberVO> membersList = dao.searchMember(memberVO);
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";
			
			
			
		} else if (action.equals("foreachSelect")) {
			List<String> nameList = new ArrayList<String>();
			nameList.add("홍길동");
			nameList.add("차범근");
			nameList.add("이순신");
			List<MemberVO> membersList = dao.foreachSelect(nameList);
			// ArrayList에 검색할 이름을 저장한 후 SQL문으로 ArrayList를 전달.
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";
			
			
			
		} else if (action.equals("foreachInsert")) {
			List<MemberVO> memList = new ArrayList<MemberVO>();
			memList.add(new MemberVO("m1", "1234", "박길동", "m1@test.com"));
			memList.add(new MemberVO("m2", "1234", "이길동", "m2@test.com"));
			memList.add(new MemberVO("m3", "1234", "김길동", "m3@test.com"));
			int result = dao.foreachInsert(memList);
			nextPage = "/mem4.do?action=listMembers";
			
			
			
			
		} else if (action.equals("selectLike")) {
			String name = request.getParameter("value");
			List<MemberVO> membersList = dao.selectLike(name);
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";
			
			
			
		} else if (action.equals("selectallLike")) {
			String name = request.getParameter("value");
			List<MemberVO> membersList = dao.selectallLike(name);
			request.setAttribute("membersList", membersList);
			nextPage = "test03/listMembers.jsp";
		}

		System.out.println("최종 경로 페이지 : " + nextPage);
		RequestDispatcher dispatch = request.getRequestDispatcher(nextPage);
		dispatch.forward(request, response);

	}
}
