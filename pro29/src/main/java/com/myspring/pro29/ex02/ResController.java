package com.myspring.pro29.ex02;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResController {
	// @RestController로 지정되지 않음
	
	
	@RequestMapping(value = "/res1")
	@ResponseBody
	// 핸들러 메서드가 반환하는 데이터를 HTTP 응답의 본문(body)에 포함시킬 때 사용
	// 메서드 호출시 데이터를 전송하도록 설정
	// 컨트롤러의 특정 메서드에 @ResponseBody를 적용하면 JSP가 아닌 텍스트나 JSON으로 결과를 전송할수 있음
	public Map<String, Object> res1() {
		// map데이터를 브라우저로 전송
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "hong");
		map.put("name", "홍길동");
		return map;
		// JSP를 통한 뷰를 렌더링하지 않고 직접 데이터를 반환
	}
	
	
	@RequestMapping(value = "/res2")
	// 메서드 호출 시 home.jsp를 브라우저로 전송
	public ModelAndView res2() {
		return new ModelAndView("home");
	}
	
}
