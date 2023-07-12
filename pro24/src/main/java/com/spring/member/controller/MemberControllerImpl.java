package com.spring.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.spring.member.service.MemberService;
import com.spring.member.service.MemberServiceImpl;
import com.spring.member.vo.MemberVO;

public class MemberControllerImpl extends MultiActionController implements MemberController {
	private MemberService memberService;

	public void setMemberService(MemberServiceImpl memberService) {
		// memberService 빈을 주입하기 위해 setter를 구현
		
		this.memberService = memberService;
	
	}
	
	@Override
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		String viewName = getViewName(request);
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		mav.addObject("membersList", membersList);
		// 조회한 회원 정보를 ModelAndView의 addObject()메서드를 이용해 바인딩
		
		return mav;
	}

	
	
	@Override
	public ModelAndView addMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO = new MemberVO();
		/*
		String id=request.getParameter("id");
		String pwd=request.getParameter("pwd");
		String name=request.getParameter("name");
		String email = request.getParameter("email");
		memberVO.setId(id);
		memberVO.setPwd(pwd);
		memberVO.setName(name);
		memberVO.setEmail(email);
		 */
		// 수동으로 요청 파라미터를 가져와 memberVO 객체의 각 속성에 설정하는 방식
		
		bind(request, memberVO);
		// 회원 가입창에서 전송된 회원 정보를 bind()메서드를 이용해 memberVO해당 속성에 자동으로 설정.
		
		int result = 0;
		result = memberService.addMember(memberVO);
		System.out.println("정상 작동 addmember result" + result);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		// 회원 정보 추가 후 ModelAndView 클래스의 redirect속성을 이용해 /member/listMembers.do로 리다이렉트
		
		return mav;
	}
	
	
	
	@Override
	public ModelAndView removeMember(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("utf-8");
		String id=request.getParameter("id");
		memberService.removeMember(id);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		// 회원 정보를 삭제하고 회원 목록창으로 리다이렉트함.
		
		return mav;
	}
	
	
	@Override
	public ModelAndView updateMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("utf-8");
		MemberVO memberVO = new MemberVO();
		
		bind(request, memberVO);
		// 회원 가입창에서 전송된 회원 정보를 bind()메서드를 이용해 memberVO해당 속성에 자동으로 설정.
		
		int result = 0;
		result = memberService.updateMember(memberVO);
		System.out.println("정상 작동 updateMember result" + result);
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		// 회원 정보 추가 후 ModelAndView 클래스의 redirect속성을 이용해 /member/listMembers.do로 리다이렉트
		
		return mav;
		
		
	}	
	
	
	public ModelAndView form(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String viewName = getViewName(request);
		System.out.println("받아온viewname : " + viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	
	
	public ModelAndView searchMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
			
			request.setCharacterEncoding("utf-8");
			String act = request.getParameter("action");
			
			MemberVO vo = new MemberVO();
			
			String value = request.getParameter("value");
			
			System.out.println(act);
			System.out.println(value);
			
			if(act.equals("all")) {
				
				vo = new MemberVO(value, value, value, value);	
//				vo.setId(value);
//				vo.setPwd(value);
//				vo.setName(value);
//				vo.setEmail(value);
			}
			
			
			if(act.equals("id")) {
				
				vo.setId(value);
				
				
			}else if(act.equals("pwd")) {
				
				vo.setPwd(value);
				
				
			}else if(act.equals("name")) {
				
				vo.setName(value);
				
				
			}else if(act.equals("email")) {
				
				vo.setEmail(value);
				
			}
			
			System.out.println(vo.getId()+vo.getPwd()+vo.getName()+vo.getEmail());
			
			System.out.println("시작 vo 객체 값 : " + vo);
			List list = memberService.searchMembers(vo);
			System.out.println("마지막 vo객체 값 : " + list);
			ModelAndView mav = new ModelAndView("/listMembers");
			mav.addObject("membersList", list);
			
			return mav;

	}
	
	

	private String getViewName(HttpServletRequest request) throws Exception {
		
		String contextPath = request.getContextPath();
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		System.out.println("getViewName의 include uri : " + uri);
		
		
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
		}
		System.out.println("getViewName의 uri : " + uri);

		int begin = 0;
		
		if (!((contextPath == null) || ("".equals(contextPath)))) {
			begin = contextPath.length();
		}

		int end;
		
		if (uri.indexOf(";") != -1) {
			
			end = uri.indexOf(";");
			
		} else if (uri.indexOf("?") != -1) {
			
			end = uri.indexOf("?");
			
		} else {
			
			end = uri.length();
			
		}

		String fileName = uri.substring(begin, end);
		
		if (fileName.indexOf(".") != -1) {
			
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			
		}
		
		if (fileName.lastIndexOf("/") != -1) {
			
			fileName = fileName.substring(fileName.lastIndexOf("/"), fileName.length());
			
		}
		
		System.out.println("최종 fileName : " + fileName);
		return fileName;
	}



}
