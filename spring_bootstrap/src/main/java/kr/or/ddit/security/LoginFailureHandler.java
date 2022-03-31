package kr.or.ddit.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler;

public class LoginFailureHandler extends SimpleUrlAuthenticationFailureHandler{

	@Override
	public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException exception) throws IOException, ServletException {

		String msg = exception.getMessage();
		
		response.setContentType("text/html;charset=urf-8");
		PrintWriter out = response.getWriter();
		
		out.println("<script>");
		out.println("alert('"+msg+".');");
		out.println("history.go(-1)");
		out.println("</script>");
		
		//밑에거를 사용하지않고 우리가 직접 핸들링할거임.
		//super.onAuthenticationFailure(request, response, exception);
	}

	
}
