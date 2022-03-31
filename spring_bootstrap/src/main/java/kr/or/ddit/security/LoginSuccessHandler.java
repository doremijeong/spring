package kr.or.ddit.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;

import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;
 
import kr.or.ddit.utils.ExceptionLoggerHelper;

public class LoginSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler{

	private MemberService memberService;
	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}
	
	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws ServletException, IOException {
		
		
		//provider를 custom했기때문에 안된다. principal을 가져오게되면 customAuthentication에서 준 authUser가 나옴. setDetail로 줬기때문에 getDetail로 가져와야한다.
		//UserDetails userDetail = (UserDetails)authentication.getPrincipal();
		//UserDetails userDetail = (UserDetails)authentication.getDetails();
		
		//String id = userDetail.getUsername();
		String id = (String)authentication.getPrincipal();
		MemberVO loginUser=null;
		
		try {
			loginUser = memberService.getMember(id);
		} catch (Exception e) {
			e.printStackTrace();
			ExceptionLoggerHelper.write(request, e, memberService);
			//response.getWriter() 메세지 전송(script)
		}
		
		HttpSession session = request.getSession();
		session.setAttribute("loginUser", loginUser);
		session.setMaxInactiveInterval(6*60);
		
		super.onAuthenticationSuccess(request, response, authentication);
	}

	
}
