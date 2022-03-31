<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page trimDirectiveWhitespaces="true"%>

<!-- 302를 주지않으면 200에러가 뜬다. 왜냐하면,  -->
<% response.setStatus(302);%>

<script>
	alert("${message}");
	if(window.opener){
		window.opener.parent.location.reload();
	}else{
		window.location.href="<%= request.getContextPath()%>";
	}
	window.close();
</script>
