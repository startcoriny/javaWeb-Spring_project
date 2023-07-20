<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<%
  request.setCharacterEncoding("UTF-8");
%>    

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>회원 정보 수정창</title>
<style>
   .text_center{
     text-align:center;
   }
</style>
</head>
<body>
	<form method="post"   action="${contextPath}/member/modMember.do?id=${param.id }">
	<!-- 제출시 updateMember서블릿에 전달. -->
	<!-- id를 수정하기버튼을 누르면 바로 가져오게 param으로 가져옴 -->
	<!-- 가져온 param값을 disabled로 가두면 수정및 전송도 안되기때문에 전송할때 직접 전송을 해주어야함.  -->
	<h1  class="text_center">회원 정보 수정창</h1>
	<table  align="center">
	   <tr>
	      <td width="200"><p align="right">아이디</td>
	      <td width="400"><input type="text" name="id" value="${param.id } " disabled="disabled" ></td>
	      <!-- param.id : 클라이언트가 수정하기 버튼을 클릭했을 때 쿼리 스트링으로 전달된 id 파라미터의 값을 나타냄 -->
	      <!-- 클라이언트가 폼을 제출하면 param.id는 쿼리 스트링으로 다시 서버에 전달되어 해당 값이 컨트롤러의 @RequestMapping에 매핑된 메서드의 파라미터로 전달 -->
	      <!-- 따라서 컨트롤러에서 따로 저장하지 않아도 다른 form에서 param.id를 사용하여 받아와서 활용 -->
	   </tr>
	   <tr>
	      <td width="200"><p align="right">비밀번호</td>
	      <td width="400"><input type="password" name="pwd" ></td>
	    </tr>
	    <tr>
	       <td width="200"><p align="right">이름</td>
	       <td width="400"><p><input type="text" name="name" ></td>
	    </tr>
	    <tr>
	       <td width="200"><p align="right">이메일</td>
	       <td width="400"><p><input type="text" name="email"></td>
	    </tr>
	    <tr>
	       <td width="200"><p>&nbsp;</p></td>
	       <td width="400"><input type="submit" value="수정하기"><input type="reset" value="다시입력"></td>
	    </tr>
	</table>
	</form>
</body>
</html>