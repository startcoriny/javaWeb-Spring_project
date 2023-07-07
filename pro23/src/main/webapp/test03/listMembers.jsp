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

<%-- <form action="${contextPath}/mem4.do">
   <input  type="hidden" name="action" value="searchMember" />
   이름 : <input  type="text" name="name" /><br>
   이메일 : <input  type="text" name="email" /><br>
   <input type="submit" value="검색"  />
</form>  --%>

 <form align="center" action="${pageContext.request.contextPath}/mem4.do">
   입력 : <input  type="text" name="value" />
   <select name="action">
     <option value="selectallLike" >전체</option>
     <option value="selectMemberById" >아이디</option>
     <option  value="selectMemberByPwd">비밀번호</option>
     <option  value="selectLike">이름</option>
     <option  value="selectMemberByemail">이메일</option>
   </select>
   <input type="submit" value="검색"  />
</form>  

<table border="1"  align="center"  width="80%">
    <tr align="center"   bgcolor="lightgreen">
      <td ><b>아이디</b></td>
      <td><b>비밀번호</b></td>
      <td><b>이름</b></td>
      <td><b>이메일</b></td>
      <td><b>가입일</b></td>
      <td><b>삭제</b></td>
      <td><b>수정</b></td>
   </tr>
   
 <c:forEach var="member" items="${membersList}" >     
   <tr align="center">
      <td>${member.id}</td>
      <td>${member.pwd}</td>
      <td>${member.name}</td>
      <td>${member.email}</td>
      <td>${member.joinDate}</td>
      <td><a href="${contextPath}/mem4.do?action=deleteMember&id=${member.id }">삭제하기</a></td>
      <td><a href="${contextPath}/test03/modMember.jsp?id=${member.id }">수정하기</a></td>
      
    </tr>
  </c:forEach>   
  
  
  
</table>
<a  href="${contextPath}/mem4.do?action=memberForm.do"><h1 style="text-align:center">회원가입</h1></a>
</body>
</html>
