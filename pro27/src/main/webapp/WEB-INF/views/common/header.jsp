<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"
    isELIgnored="false" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%
  request.setCharacterEncoding("UTF-8");
%> 
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
<title>헤더</title>
</head>
<body>
<table border=0  width="100%">
  <tr>
     <td>
		<a href="${contextPath}/main.do">
			<img src="${contextPath}/resources/image/don.png" width="100px" height="100px" />
		</a>
     </td>
     <td>
       <h1><font size=30>스프링실습 홈페이지!!</font></h1>
     </td>
     
     <td>
       <%-- <a href="#"><h3>로그인</h3></a> --%>
       <c:choose>
       <%-- isLogon 속성 값을 체크하여 로그인 상태 시 로그아웃이 표시되게함. --%>
          <c:when test="${isLogOn == true  && member!= null}">
            <h3>환영합니다. ${member.name }님!</h3>
            <a href="${contextPath}/member/logout.do"><h3>로그아웃</h3></a>
            <%-- 로그 아웃 링크를 클릭하면 로그인을 표시 --%>
          </c:when>
          <c:otherwise>
	        <a href="${contextPath}/member/loginForm.do"><h3>로그인</h3></a>
	        <%-- 로그인 링크를 클릭하면 로그인 창을 요청 --%>
	      </c:otherwise>
	   </c:choose>     
	   
     </td>
  </tr>
</table>


</body>
</html>