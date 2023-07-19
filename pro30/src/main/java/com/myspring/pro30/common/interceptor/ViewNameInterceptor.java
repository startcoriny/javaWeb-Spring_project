package com.myspring.pro30.common.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class ViewNameInterceptor extends HandlerInterceptorAdapter {
//	(preHandle, postHandle, afterCompletion)는 HandlerInterceptorAdapter 클래스를 상속받아서 오버라이드
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
		// 컨트롤러가 요청을 처리하기 전에 실행되는 메서드
		// 요청을 가로채서 전/후 처리 작업을 수행
		// 메서드의 반환값이 true이면 요청을 계속 진행하고, false이면 요청을 중단
		
		
		try {
			String viewName = getViewName(request);
			// getViewName()메서드를 이용해 브라우저의 요청명에서 뷰이름을 가져옴
			request.setAttribute("viewName", viewName);
			// 뷰이름을 request에 바인딩
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			// 컨트롤러가 요청을 처리한 후에 실행되는 메서드
			// 컨트롤러의 핸들러 메서드가 실행된 이후에 추가적인 작업을 수행
			// 요청 처리 결과를 가지고 추가적인 로직을 수행하는데 사용
			
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			// 뷰 렌더링이 완료된 후에 실행되는 메서드
			// 뷰를 통해 응답을 생성한 후에 추가적인 작업을 수행
			// 예외 처리나 리소스 정리 등을 수행하는데 사용
			throws Exception {
	}

	private String getViewName(HttpServletRequest request) throws Exception {
		// 요청명에서 뷰이름을 반환
		
		String contextPath = request.getContextPath();
		// 현재 웹 애플리케이션의 경로를 문자열로 가져옴
		System.out.println(contextPath + " : 인터페이스의 컨텍스트path");
		
		String uri = (String) request.getAttribute("javax.servlet.include.request_uri");
		if (uri == null || uri.trim().equals("")) {
			uri = request.getRequestURI();
			System.out.println(uri + " : 인터페이스에서 리퀘스트로 가져온 URI");
		}

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
			fileName = fileName.substring(fileName.lastIndexOf("/", 1), fileName.length());
		}
		System.out.println(fileName + " : 잘리는 과정을 거친 인터페이스의 filename");
		return fileName;
	}
}
