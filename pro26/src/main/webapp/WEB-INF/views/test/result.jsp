<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
 


<html>
<head>
<meta charset=UTF-8">
<title>결과창</title>
</head>
<body>
<%-- <h1>아이디 : ${userID }</h1>
<h1>이름   : ${userName }</h1> --%>
<h1>아이디 : ${info.userID }</h1>
<!-- Map에 key로 접근하여 값을 출력 -->
<h1>이름   : ${info.userName }</h1>
<!-- @ModelAttribute('info')에 지정한 이름으로 속성에 접근. -->
</body>
</html>
