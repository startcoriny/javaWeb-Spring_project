<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"   isELIgnored="false"  %>    
    <!-- isELIgnored는 jsp페이지에서 el(expression Language)을 사용할수 있는지 설정하는 속성  -->
    <!-- 기본적으로 jsp페이지에서는 el을 사용할수 있도록 설정되어있지만 el을 사용하지 못하도록 설정해야할때는 true로 설정하여 비활성화 시킴 -->  
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>


<%
  request.setCharacterEncoding("UTF-8");
%>    


<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>메인 페이지</title>
  <script src="http://code.jquery.com/jquery-latest.js"></script>
</head>
<body>
   <h1>메인 페이지입니다!!</h1>
</body>
</html>