package kr.or.ddit.filter;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class SessiontExpiredCheckFilter implements Filter {
	
	public void destroy() {
		
	}
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse httpRes = (HttpServletResponse)response;
		
		String message = "중복 로그인이 확인되었습니다. \\n" + "다시 로그인하면 다른 장치에서 로그인은 취소됩니다.";
		//자바스크립트로 내보낼때는 \\ 두개줘야한다. 왜냐하면 나갈때 인지할수있게 해줘야하기때문에
		
		httpRes.setContentType("text/html;charset=utf-8");
		PrintWriter out = httpRes.getWriter();
		out.println("<script>");
		out.println("alert('"+message+"');");
		out.println("location.href='loginForm.do';");
		out.println("</script>");
		
		
		
		return;
		
	}
	
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
