<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"  isELIgnored="false" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>

<c:set var="contextPath" value="${pageContext.request.contextPath}"  />
<!DOCTYPE html>
<html>
<head>
<title>JSONTest</title>
<script src="http://code.jquery.com/jquery-latest.js"></script>  
<script>
  $(function() {
      $("#checkJson").click(function() {
    	  // 회원 정보를 JSON으로 생성
      	var member = { id:"park", 
  			    name:"박지성",
  			    pwd:"1234", 
  			    email:"park@test.com" };
  	$.ajax({
        type:"post",
        url:"${contextPath}/test/info",
        // /test/info로 요청
        contentType: "application/json",
        data :JSON.stringify(member),
        // 회원정보를 json문자열로 변환.
     success:function (data,textStatus){
     },
     error:function(data,textStatus){
        alert("에러가 발생했습니다.");
     },
     complete:function(data,textStatus){
     }
  });  //end ajax	

   });
});
</script>
</head>
<body>
  <input type="button" id="checkJson" value="회원 정보 보내기"/><br><br>
  <div id="output"></div>
</body>
</html>