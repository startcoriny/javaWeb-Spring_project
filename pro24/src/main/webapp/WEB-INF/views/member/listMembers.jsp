<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" 
    isELIgnored="false"  %>
 <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"  %>
<c:set var="contextPath"  value="${pageContext.request.contextPath}"  />

<%
  request.setCharacterEncoding("UTF-8");
%>    


<html>
<head>
<meta charset=UTF-8">
<title>회원 정보 출력창</title>
</head>
<body>
 <form align="center" action="${pageContext.request.contextPath}/member/searchMember.do">
   입력 : <input  type="text" name="value" />
   <select name="action">
     <option value="all" >전체</option>
     <option value="id" >아이디</option>
     <option  value="pwd">비밀번호</option>
     <option  value="name">이름</option>
     <option  value="email">이메일</option>
   </select>
   <input type="submit" value="검색" name = "pwd" />
</form>  
<table border="1"  align="center"  width="80%">
    <tr align="center"   bgcolor="lightgreen">
      <td ><b>아이디</b></td>
      <td><b>비밀번호</b></td>
      <td><b>이름</b></td>
      <td><b>이메일</b></td>
      <td><b>가입일</b></td>
      <td><b>수정</b></td>
      <td><b>삭제</b></td>
   </tr>
   
 <c:forEach var="member" items="${membersList}" >     
   <tr align="center">
      <td>${member.id}</td>
      <td>${member.pwd}</td>
      <td>${member.name}</td>
      <td>${member.email}</td>
      <td>${member.joinDate}</td>
      <td><a href="${contextPath}/member/removeMember.do?id=${member.id }">삭제하기</a></td>
      <td><a href="${contextPath}/member/modMember.do?id=${member.id }">수정하기</a></td>
      <!-- 삭제하기 클릭시 /member/removeMember.do로 요청 -->
    </tr>
  </c:forEach>   
  
</table>
<a  href="${contextPath}/member/memberForm.do"><h1 style="text-align:center">회원가입</h1></a>
<!-- /member/memberForm.do로 요청 -->
</body>
</html>
