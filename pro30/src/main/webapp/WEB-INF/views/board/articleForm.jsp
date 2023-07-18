<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<c:set var="contextPath" value="${pageContext.request.contextPath}" />
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>글쓰기창</title>
<script src="http://code.jquery.com/jquery-latest.min.js"></script>
<script type="text/javascript">
	function readURL(input) {
		if (input.files && input.files[0]) {
			var reader = new FileReader();
			reader.onload = function(e) {
				$('#preview').attr('src', e.target.result);
			}

			reader.readAsDataURL(input.files[0]);

		}

	}
	function backToList(obj) {
		obj.action = "${contextPath}/board/listArticles.do";
		obj.submit();

	}
	
	  var cnt=1;
	  function fn_addFile(){
		  $("#d_file").append("<br>"+"<input type='file' name='file"+cnt+"' />");
		  cnt++;
	  }  
</script>


</head>
<body>
	<h1 style="text-align:center">글쓰기</h1>
  	<form name="articleForm" method="post"   action="${contextPath}/board/addNewArticle.do"   enctype="multipart/form-data">
    	<table border="0" align="center">
      		<tr>
				<td align="right"> 작성자</td>
				<td colspan=2  align="left"><input type="text" size="20" maxlength="100"  value="${member.name }" readonly/> </td>
			</tr>
			<tr>
				<td align="right">글제목:</td>
				<td colspan="2"><input type="text" size="67" maxlength="500"
					name="title" /></td>
			</tr>
			<tr>
				<td align="right" valign="top"><br>글내용:</td>
				<td colspan=2><textarea name="content" rows="10" cols="65"
						maxlength="4000"></textarea></td>
			</tr>
			<tr>
				<td align="right">이미지파일 첨부:</td>
				<td><input type="file" name="imageFileName"
					onchange="readURL(this);" /></td>
				<!-- onchange - HTML의 이벤트 처리기(Attribute)
	     				 작성한 JS를 통해 변화가 일어났는지를 감지 -->
				<!-- readURL() - 주로 파일 업로드 기능과 관련하여 사용되며, 사용자가 파일 선택 대화 상자를 통해 파일을 선택하면 이벤트가 발생
	     				  선택한 파일의 정보를 읽거나 해당 파일을 처리.
	     				   -->
				<td><img id="preview" src="#" width=200 height=200 /></td>
				
				<td align="right">이미지파일 첨부</td>
				<td align="left"> <input type="button" value="파일 추가" onClick="fn_addFile()"/></td>
				
				
	  		</tr>
	  		 <tr>
	      		<td colspan="4"><div id="d_file"></div></td>
	   		</tr>
			<tr>
				<td align="right"></td>
				<td colspan="2"><input type="submit" value="글쓰기" /> <input
					type=button value="목록보기" onClick="backToList(this.form)" /> <!-- backToList() - * 프로그램이나 애플리케이션에서 이전으로 돌아가기 위해 사용되는 함수나 메서드
	       					   * 리스트나 메뉴와 같은 인터페이스 요소에서 사용.
	       					   * 현재 화면이나 상태를 유지하면서 이전 화면이나 이전 목록으로 돌아갈 수 있는 기능을 제공.
	       					   * 사용자가 이전으로 돌아가기를 원할 때 backToList() 함수를 호출하면, 이전에 방문한 목록이나 화면으로 이동
	       					   * backToList() 함수는 스택(Stack)이나 이전 상태를 저장하는 방식으로 구현
	       					   * 이전 목록이나 화면은 스택에 저장되며, backToList() 함수는 스택에서 가장 위에 있는 요소를 꺼내어 이동하는 역할
	       					   * 사용자는 여러 번의 backToList() 호출을 통해 여러 단계 이전으로 돌아갈수 있음--></td>
			</tr>
		</table>
	</form>
</body>
</html>