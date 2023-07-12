package com.spring.ex02;
// com.spring 하위 패키지에 클래스가 위치해야 애너테이션이 적용
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller("loginController")
// 컨트롤러빈을 자동으로 생성

public class LoginController {

	@RequestMapping(value = { "/test/loginForm.do", "/test/loginForm2.do" }, method = { RequestMethod.GET })
	// /test/loginForm.do와 /test/loginform2.do로 요청시 loginForm()을 호출
	
	public ModelAndView loginForm(HttpServletRequest request, HttpServletResponse response) throws Exception {

		ModelAndView mav = new ModelAndView();
		mav.setViewName("loginForm");
		return mav;
	}
	
 
	
	@RequestMapping(value = "/test/login.do", method={RequestMethod.GET,RequestMethod.POST})
	//GET 방식과 POST방식 요청시 모두 처리
	
	public ModelAndView login(HttpServletRequest request, HttpServletResponse response) throws Exception {
		
		
		request.setCharacterEncoding("utf-8");
		ModelAndView mav = new ModelAndView();
		mav.setViewName("result");
		String userID = request.getParameter("userID");
		String userName = request.getParameter("userName");
		mav.addObject("userID", userID);
		mav.addObject("userName", userName);

		return mav;
	}

//	@RequestMapping(value = "/test/login2.do", method = { RequestMethod.GET, RequestMethod.POST })
//	public ModelAndView login2(@RequestParam("userID") String userID, 
//								// @RequestParam을 이용해 매개변수가 userID이면 그 값을 변수 userID에 자동으로 설정
//			                  @RequestParam("userName") String userName,
//			                  // @RequestParam을 이용해 매개변수가 userName이면 그 값을 변수 userName자동으로 설정
//			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
//		request.setCharacterEncoding("utf-8");
//		ModelAndView mav = new ModelAndView();
//		mav.setViewName("result");
//		
//		// String userID = request.getParameter("userID");
//		// String userName = request.getParameter("userName");
//		// ※ getParameter()메서드를 이용할 필요가 없음.
//		
//		System.out.println("userID: "+userID);
//		System.out.println("userName: "+userName);
//		mav.addObject("userID", userID);
//		mav.addObject("userName", userName);
//
//		return mav;
//		
//	}
	

	@RequestMapping(value = "/test/login2.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login2(@RequestParam("userID") String userID, 
						// Required의 속성을 생략하면 required의 기본값은 true
			
                               @RequestParam(value="userName", required=true) String userName,    
                        // required속성을 명시적으로 true로 설정
                               
			                   @RequestParam(value="email", required=false) String email,    
			            // required속성을 명시적으로 false로 설정
			                  HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("result");
		
		// String userID = request.getParameter("userID");
		// String userName = request.getParameter("userName");
		
		System.out.println("userID: "+userID);
		System.out.println("userName: "+userName);
		System.out.println("email: "+ email);
		mav.addObject("userID", userID);
		mav.addObject("userName", userName);
		return mav;
	}
	

	@RequestMapping(value = "/test/login3.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login3(@RequestParam Map<String, String> info,
			// @RequestParam을 이용해 Map에 전송된 매개변수 이름을 key, 값을 value로 저장.
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		
		String userID = info.get("userID");
		String userName = info.get("userName");
		// Map에 저장된 매개변수의 이름으로 전달된 값을 가져옴.
		
		System.out.println("userID: "+userID);
		System.out.println("userName: "+userName);
		
		mav.addObject("info", info);
		// @RequestParam에서 설정한 Map 이름으로 바인딩.
		
		mav.setViewName("result");
		return mav;
	}
	
	
	// @ModelAttribute사용하여 vo에 매개변수값 설정하기
	@RequestMapping(value = "/test/login4.do", method = { RequestMethod.GET, RequestMethod.POST })
	public ModelAndView login4(@ModelAttribute("info") LoginVO loginVO,
			//@ModelAttribute를 이용해 전달되는 매개변수 값을 LoginVO클래스와 이름이 같은 속성에 자동으로 설정
			// addObject()를 이용할 필요없이 info를 이용해 바로 JSP에서 LoginVO속성에 접근할수 있음.
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		ModelAndView mav = new ModelAndView();
		System.out.println("userID: "+loginVO.getUserID());
		System.out.println("userName: "+loginVO.getUserName());
		mav.setViewName("result");
		return mav;
	}
	   
	
	@RequestMapping(value = "/test/login5.do", method = { RequestMethod.GET, RequestMethod.POST })
	public String login5(Model model, @ModelAttribute("info") LoginVO loginVO,
			// model객체를 사용하면 String값을 뷰 이름으로 간주하고
			// InternalResourceViewResolver 빈을 통해
			// 뷰의 경로와 확장자 등을 설정한 후, 해당 뷰로 데이터를 전달하여 렌더링
			
			//@ModelAttribute를 이용해 전달되는 매개변수 값을 LoginVO클래스와 이름이 같은 속성에 자동으로 설정
			// addObject()를 이용할 필요없이 info를 이용해 바로 JSP에서 LoginVO속성에 접근할수 있음.
			
			                   HttpServletRequest request, HttpServletResponse response) throws Exception {
		System.out.println("userID: "+loginVO.getUserID());
		System.out.println("userName: "+loginVO.getUserName());
		return "result";
	}	
}
