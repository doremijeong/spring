<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title></title>
</head>
<body>
	
	<ul>
		<li>받는 사람: ${mailRequest.receiver }</li>
		<li>보내는 사람 : ${mailReqeust.sender }</li>
		<li>제목 : ${mailRequest.title } </li>
		<li>내용 : ${mailRequest.content }</li>
		<li>첨부파일 : ${uploadPath }</li>
	</ul>
	
</body>
</html>