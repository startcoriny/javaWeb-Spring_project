package com.spring.ex01;
// 에너테이션이 적용되도록 하려면 해당 클래스가 반드시 component-scan에서 설정한 패키지나
// 하위 패키지에 존재해야함.
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

@Controller("mainController")
// @Controller 애너테이션을 이용해 MainController 클래스를 빈으로 자동 변환.
// 빈 id는 mainController

@RequestMapping("/test")
// @RequestMapping을 이용해 첫번째 단계의 URL요청이 /test이면 mainController빈을 요청
public class MainController {
	
   @RequestMapping(value="/main1.do" ,method=RequestMethod.GET)
   // @RequestMapping을 이용해 두번째 단계의 URL요청이 /main1.do이면 mainController빈의
   // main1()메서드에게 요청.
   // method=RequestMethod.GET으로 지정하면 GET방식으로 요청 시 해당 메서드가 호출

   public ModelAndView main1(HttpServletRequest request, HttpServletResponse response)  throws Exception{
      ModelAndView mav=new ModelAndView();
      mav.addObject("msg","곽지민1");
      mav.setViewName("main");
      return mav;
   }

   
   
   @RequestMapping(value="/main2.do" ,method = RequestMethod.GET)
   // @RequestMapping을 이용해 두 번재 단계의 URL요청이 /main2.do이면 mainController빈의
   //  main2()메서드에게 요청
   // method=RequestMethod.GET으로 지정하면 GET방식으로 요청시 해당 메서드가 호출.
   
   public ModelAndView main2(HttpServletRequest request, HttpServletResponse response) throws Exception{
      ModelAndView mav=new ModelAndView();
      mav.addObject("msg","곽지민2");
      mav.setViewName("main");
      return mav;
   }
   
}
