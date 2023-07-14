package com.myspring.pro28.ex05;

import java.util.Locale;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import org.springframework.web.servlet.i18n.SessionLocaleResolver;

public class LocaleInterceptor extends  HandlerInterceptorAdapter{
	// 사용자 정의 인터셉터는 반드시 HandlerInterCeptorAdapter를 상속받아야함
	   @Override
	   public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler){
	     // 컨트롤러 실행전 호출
		   
		   HttpSession session=request.getSession();
		   
	      String locale=request.getParameter("locale");
	      // 브라우저에서 전달한 locale 정보를 가져옴
	      System.out.println("locale 값 : "+locale);
	      if(locale==null)
	         locale="ko";
	      // 최초 요청시 locale을 한국어로 설정
	      session.setAttribute("org.springframework.web.servlet.i18n.SessionLocaleResolver.LOCALE",new Locale(locale));
	      // Locale속성 값을 세션에 저장해 SessionLocaleResolver가 사용할수 있게 함.
	      return true;
	   }

	   @Override
	   public void postHandle(HttpServletRequest request, HttpServletResponse response,
	                           Object handler, ModelAndView modelAndView) throws Exception {
		   // 컨트롤러 실행 후 호출
	   }

	   @Override
	   public void afterCompletion(HttpServletRequest request, HttpServletResponse response,
	                                    Object handler, Exception ex)    throws  Exception {
	   }// 뷰(jsp)를 수행한 후 호출
	}
