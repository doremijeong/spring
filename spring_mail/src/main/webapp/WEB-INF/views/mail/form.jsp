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
	<form action="send" method="post" enctype="multipart/form-data">
		받는사람 : <input type="email" name="receiver"><br/>
		보내는 사람 : <input type="email" name="sender"><br/>
		제목 : <input type="text" name="title" /><br/>
		내용 : <textarea name="content" rows="10" cols="50"></textarea><br/>
		첨부파일 : <input type="file" name="file" /><br/>
		<input type="submit" value="메일 보내기" />
	</form>
</body>
</html> 