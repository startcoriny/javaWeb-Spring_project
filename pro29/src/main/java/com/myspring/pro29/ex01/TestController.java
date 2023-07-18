package com.myspring.pro29.ex01;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController 
// 컨트롤러 클래스에 @RestController 어노테이션을 사용하여 JSON 응답을 생성하는 RESTful 컨트롤러로 선언
// @RestController는 @Controller와 @ResponseBody를 조합한 단축어로, 모든 핸들러 메서드의 반환 값은 JSON으로 변환되어 응답으로 전송
@RequestMapping("/test/*")
public class TestController {
  static Logger logger = LoggerFactory.getLogger(TestController.class);
	
  @RequestMapping("/hello")
  // /hello 로 요청시 브라우저로 문자열을 전송
  
  public String hello() {
	return "Hello REST!!";
  } 
  
  
  @RequestMapping("/member")
  public MemberVO member() {
	  // MemberVO 객체의 속성 값을 저장한 후 JSON으로 전송
	  
    MemberVO vo = new MemberVO();
    vo.setId("hong");
    vo.setPwd("1234");
    vo.setName("홍길동");
    vo.setEmail("hong@test.com");
    return vo;
  } 	
  
  @RequestMapping("/membersList")
  public List<MemberVO> listMembers () {
    List<MemberVO> list = new ArrayList<MemberVO>();
    // MemberVO객체를 저장할 ArrayList 객체를 생성
	for (int i = 0; i < 10; i++) {
		//MemberVO객체 10개를 생성해 arrayList객체에 저장.
	  MemberVO vo = new MemberVO();
	  vo.setId("hong"+i);
	  vo.setPwd("123"+i);
	  vo.setName("홍길동"+i);
	  vo.setEmail("hong"+i+"@test.com");
	  list.add(vo);
	}
    return list; 
    // arrayList를 json으로 브라우저에 전송
  }	
  
  
  @RequestMapping("/membersMap")
  public Map<Integer, MemberVO> membersMap() {
    Map<Integer, MemberVO> map = new HashMap<Integer, MemberVO>();
    // MemberVO객체를 저장할 HashMap객체를 생성
    for (int i = 0; i < 10; i++) {
    	// MemberVO객체를 HashMap에 저장
      MemberVO vo = new MemberVO();
      vo.setId("hong" + i);
      vo.setPwd("123"+i);
      vo.setName("홍길동" + i);
      vo.setEmail("hong"+i+"@test.com");
      map.put(i, vo); 
    }
    return map; 
    // HashMap객체를 브라우저로 전송
  } 	
  
  
  @RequestMapping(value= "/notice/{num}" , method = RequestMethod.GET)
  // 브라우저에서 요청시 num부분의 값이 @PathVariable로 지정
  
  public int notice(@PathVariable("num") int num ) throws Exception {
	  // PathVariable을 사용하면 브라우저에서 요청 URL로 전달된 매개변수를 가져올수 있음
	  // 동적인 URL을 처리하고, 경로에 포함된 데이터를 활용하여 로직을 수행
	  // URL 경로에서 변수 값을 추출하는데 사용
	  // 주로 RESTful 웹 서비스에서 경로 변수를 처리할 때 사용
	  // 요청 URL에서 지정된 값이 num에 자동으로 할당
	  return num;
  }	

  
  
  @RequestMapping(value= "/info", method = RequestMethod.POST)
  public void modify(@RequestBody MemberVO vo ){
	  // requestBody를 사용하면 브라우저에서 전달되는 JSON 데이터를 객체로 자동 변환해줌
	  // JSON으로 전송된 MemberVO객체의 속성에 자동으로 설정해줌.
	  // HTTP 요청의 본문(body)에 포함된 데이터를 매개변수로 전달받을 때 사용
	  // 주로 POST 또는 PUT 요청과 함께 전송되는 데이터를 처리할 때 사용
	  
    logger.info(vo.toString());
  }
  
  @RequestMapping("/membersList2")
  public  ResponseEntity<List<MemberVO>> listMembers2() {
	  // responseEntity로 응답
	  
	List<MemberVO> list = new ArrayList<MemberVO>();
	for (int i = 0; i < 10; i++) {
	  MemberVO vo = new MemberVO();
	  vo.setId("lee" + i);
	  vo.setPwd("123"+i);
	  vo.setName("이순신" + i);
      vo.setEmail("lee"+i+"@test.com");
	  list.add(vo);
	}
    return new ResponseEntity(list,HttpStatus.INTERNAL_SERVER_ERROR);
    // 오류코드 500으로 응답
    
  }	
  
  
	@RequestMapping(value = "/res3")
	public ResponseEntity res3() {
		HttpHeaders responseHeaders = new HttpHeaders();
	    responseHeaders.add("Content-Type", "text/html; charset=utf-8");
	    // 전송할 데이터의 종류와 인코딩을 설정

	    String message = "<script>";
		// 전송할 자바스크립트 코드를 문자열로 작성
	    message += " alert('새 회원을 등록합니다.');";
		message += " location.href='/pro29/test/membersList2'; ";
		message += " </script>";
		return  new ResponseEntity(message, responseHeaders, HttpStatus.CREATED);
		// responseEntity를 이용해 HTML형식으로 전송
		// 201 Created를 나타냄
		// HTTP POST 요청이 성공적으로 처리되어 새로운 리소스가 생성되었음을 나타내는 상태 코드
	}
	
}
