<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>
<body>
<script>
 	alert("등록되었습니다. \n 회원 리스트 페이지로 이동");
 	//window.opener.location.href="list?page=1&perPageNum=10";
 	//window.opener.location.href="list?page=1";
 	//window.opener.list_go(1);
 	
 	window.opener.location.href="<%=request.getContextPath()%>/member/list.do";
 	window.close();
 	
</script>
</body>