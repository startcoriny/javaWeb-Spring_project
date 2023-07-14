package com.myspring.pro28.ex03;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
@EnableAsync
public class MailController {
    @Autowired
    private MailService mailService;
 
    @RequestMapping(value = "/sendMail.do", method = RequestMethod.GET)
    public void sendSimpleMail(HttpServletRequest request, HttpServletResponse response) 
                                                          throws Exception{
    	request.setCharacterEncoding("utf-8");
    	response.setContentType("text/html;charset=utf-8");
        PrintWriter out = response.getWriter();
        mailService.sendMail("gjm0313@naver.com","테스트 메일","안녕하세요.보낸 메일 내용입니다.");
        // mailService의 sendMail()메서드로 메일관련 값(주소,제목,내용)을 전달
        mailService.sendPreConfiguredMail("테스트 메일입니다.");
        // mail.context.xml에 설정한 메일 주소로 내용을 보냄
        out.print("메일을 보냈습니다!!");
    }
}


