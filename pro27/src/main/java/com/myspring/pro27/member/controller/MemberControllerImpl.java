package com.myspring.pro27.member.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.myspring.pro27.member.service.MemberService;
import com.myspring.pro27.member.vo.MemberVO;


@Controller("memberController")
// @Controller를 이용해 MemberControllerImpl 클래스에 대해
// id가 memberController인 빈을 자동생성.
// @Controller 애노테이션을 지정한 클래스는 HTTP 요청을 처리하기 위해 사용되는 컨트롤러로 동작
// @RequestMapping 애노테이션과 함께 사용하여 특정 URL 패턴에 대한 요청을 처리할 메서드를 지정
// @Controller 애노테이션을 지정한 클래스의 메서드는 ModelAndView 객체를 반환하거나, Model 객체에 데이터를 설정하여 뷰와의 상호작용을 구현
// 컨트롤러에서 생성한 데이터를 뷰로 전달하고, 뷰에서 클라이언트에게 보여줄 화면을 생성


public class MemberControllerImpl extends MultiActionController implements MemberController {
	
	private static final Logger logger =  LoggerFactory.getLogger(MemberControllerImpl.class);
	// LoggerFactory 클래스를 이용해 Logger클래스 객체를 가져옴
	
	@Autowired
	// @Autowired를 이용해 id가 memberService인 빈을 자동 주입.
	private MemberService memberService;

	
//	@Autowired
//	// @Autowired를 이용해 id가 memberVO인 빈을 자동 주입.
	private MemberVO memberVO;


	
	@Override
	@RequestMapping(value = "/member/listMembers.do", method = {RequestMethod.GET , RequestMethod.POST} )
	// 두 단계(get,post)로 요청 시 바로 해당 메서드(listMembers)를 호출하도록 매핑
	
	public ModelAndView listMembers(HttpServletRequest request, HttpServletResponse response) throws Exception {
		// ModelAndView는 스프링 MVC에서 컨트롤러 메서드의 처리 결과를 담고 뷰로 전달하는 데 사용되는 클래스
		// ModelAndView 객체를 생성하고 해당 뷰의 이름을 설정하여 반환하면, 스프링 MVC는 해당 뷰를 찾아 처리 결과를 전달
		
		String viewName = getViewName(request);
		logger.info("info 레벨 : viewname = "+viewName);
//		// Logger클래스의 info()메서드로 로그 메시지 레벨을 info로 설정
		
		logger.debug("debug 레벨 : viewname = "+viewName);
		// Logger클래스의 debug()메서드로 로그 메시지 레벨을 debug로 설정
		
		List membersList = memberService.listMembers();
		ModelAndView mav = new ModelAndView(viewName);
		// viewName이 definition태그에 설정한 뷰이름과 일치
		mav.addObject("membersList", membersList);
		// 조회한 회원 정보를 ModelAndView의 addObject()메서드를 이용해 바인딩
		
		return mav;
		// modelandview 객체에 설정한 뷰 이름을 타일즈 뷰리졸버로반환.
	}

	
	
	@Override
	@RequestMapping(value = "/member/addMember.do" , method = RequestMethod.POST)
	public ModelAndView addMember(@ModelAttribute("member") MemberVO member,
						// MemberVO member는 해당 객체를 받아올 변수 이름
						// 회원 가입창에서 전송된 회원정보를 바로 MemberVO객체에 설정
						// member라는 이름을 사용하여 요청 파라미터들을 MemberVO 객체의 속성과 매핑
						// @ModelAttribute("member")는 MemberVO의 member를 가리킴.
			
			HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int result = 0;
		
		result = memberService.addMember(member);
		// 설정된 memberVO 객체를 SQL문으로 전달해 회원등록.
		System.out.println("정상 작동 addmember result" + result);
		//이런식으로 println 메서드 사용하면 유지 보수 시 불편함!!!
		
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		// 회원 정보 추가 후 ModelAndView 클래스의 redirect속성을 이용해 /member/listMembers.do로 리다이렉트
		
		return mav;
	}
	
	
	
	@Override
	@RequestMapping(value = "/member/removeMember.do", method = RequestMethod.GET)
	public ModelAndView removeMember(@RequestParam("id") String id, HttpServletRequest request, HttpServletResponse response) throws Exception{
							// 전송된 ID를 변수 id에 설정
		
		memberService.removeMember(id);
		// 받아온 id를 SQL문으로 전달해 회원삭제.
		
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		// 회원 정보를 삭제하고 회원 목록창으로 리다이렉트함.
		
		return mav;
	}
	
	
	
	@Override
	@RequestMapping(value = { "/member/loginForm1.do", "/member/memberForm.do","/member/modMemberForm.do" }, method =  RequestMethod.GET)
	public ModelAndView form( HttpServletRequest request, HttpServletResponse response) throws Exception {
				//로그인창에서 요청시 매개변수 result가 전송되면 변수 result에 값을 저장. 최초로 로그인창을 요청할때는 매개변수 result가 전송되지 않으므로 뮤시
		String viewName = getViewName(request);
		System.out.println("받아온viewname : " + viewName);
		ModelAndView mav = new ModelAndView();
		mav.setViewName(viewName);
		return mav;
	}

	
	
	@Override
	@RequestMapping(value = "/member/modMember.do" , method =  {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView updateMember(@ModelAttribute("member") MemberVO member,
				HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		int result = 0;
		
		result = memberService.updateMember(member);
		
		System.out.println("정상 작동 updateMember result" + result);
		
		ModelAndView mav = new ModelAndView("redirect:/member/listMembers.do");
		// 회원 정보 추가 후 ModelAndView 클래스의 redirect속성을 이용해 /member/listMembers.do로 리다이렉트
		
		return mav;
		
	}	
	
	
	
	@Override
	@RequestMapping( value = "/member/searchMember.do", method =  {RequestMethod.GET, RequestMethod.POST})
	public ModelAndView searchMember(HttpServletRequest request, HttpServletResponse response) throws Exception {
			

			String act = request.getParameter("action");
			
			MemberVO vo = new MemberVO();
			
			String value = request.getParameter("value");
			
			System.out.println(act);
			System.out.println(value);
			
			if(act.equals("all")) {
				
				vo = new MemberVO(value, value, value, value);	
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
			ModelAndView mav = new ModelAndView("/member/listMembers");
			mav.addObject("membersList", list);
			
			return mav;

	}
	
	
	@Override
	@RequestMapping(value = "/member/login.do", method = RequestMethod.POST)
	public ModelAndView login(@ModelAttribute("member") MemberVO member,
			// 로그인창에서 전송되 id와 비밀번호를 MemberVO객체인 member에 저장
				              RedirectAttributes rAttr,
				              // RedirectAttributes 클래스를 이용해 로그인 실패 시 다시 로그인 창으로 리다이렉트하여 실패 메시지 전달
		                       HttpServletRequest request, HttpServletResponse response) throws Exception {
	ModelAndView mav = new ModelAndView();
	memberVO = memberService.login(member);
	// login()메서드를 호출하면서 로그인 정보를 전달
	
	if(memberVO != null) {
		    HttpSession session = request.getSession();
		    session.setAttribute("member", memberVO);
		    // 세션에 회원 정보를 저장
		    session.setAttribute("isLogOn", true);
		    // 세션에 로그인 상태를 true로 설정
		    mav.setViewName("redirect:/member/listMembers.do");
		    // memberVO로 반환된 값이 있으면 세션을 이용해 로그인 상태를 true로 함
	}else {
		    rAttr.addAttribute("result","loginFailed");
		    // 로그인 실패시 실패 메시지를 로그인 창으로 전달
		    mav.setViewName("redirect:/member/loginForm.do");
		    // 로그인 실패시 다시 로그인 창으로 리다이렉트
	}
	return mav;
	}
	
	@Override
	@RequestMapping(value = "/member/logout.do", method =  RequestMethod.GET)
	public ModelAndView logout(HttpServletRequest request, HttpServletResponse response) throws Exception {
		HttpSession session = request.getSession();
		session.removeAttribute("member");
		session.removeAttribute("isLogOn");
		// 로그아웃 요청 시 세션에 저장된 로그인 정보와 회원 정보를 삭제
		ModelAndView mav = new ModelAndView();
		mav.setViewName("redirect:/member/listMembers.do");
		return mav;
	}
	
	
	@RequestMapping(value = "/member/*Form.do", method =  RequestMethod.GET)
	private ModelAndView form(@RequestParam(value= "result", required=false) String result,
						       HttpServletRequest request, 
						       HttpServletResponse response) throws Exception {
		//String viewName = getViewName(request);
		String viewName = (String)request.getAttribute("viewName");
		// 인터셉터에서 바인딩된 뷰이름을 가져옴
		
		ModelAndView mav = new ModelAndView();
		mav.addObject("result",result);
		mav.setViewName(viewName);
		System.out.println("*Form.do에서 받은 viewName : "+viewName);
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
		System.out.println("fileName : " + fileName);
		if (fileName.indexOf(".") != -1) {
			
			fileName = fileName.substring(0, fileName.lastIndexOf("."));
			
		}
		
		if (fileName.lastIndexOf("/") != -1) {
			
			fileName = fileName.substring(fileName.lastIndexOf("/",1), fileName.length());
			// /member/listMembers.do로 요청할 경우 member/listMember를 파일이름으로 가져옴.
		}
		
		System.out.println("최종 fileName : " + fileName);
		return fileName;
	}



}
