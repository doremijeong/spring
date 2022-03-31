package kr.or.ddit.filter;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

import com.jsp.filter.CheckLoginUserFilter;

public class SpringCheckLoginUserFilter extends CheckLoginUserFilter{


	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpServletRequest httpReq = (HttpServletRequest)request;
		String from = httpReq.getParameter("from");
		
		if(from!=null && from.equals("remove")) {
			chain.doFilter(request, response);
			return;
		}
		
		super.doFilter(request, response, chain);
	}
}
