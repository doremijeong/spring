package kr.or.ddit.security;

import javax.management.BadAttributeValueExpException;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;

import com.jsp.dto.MemberVO;
import com.jsp.service.MemberService;

public class CustomAuthenticationProvider implements AuthenticationProvider{

	private MemberService memberService;
	public void setMemberService(MemberService memberService) {
		this.memberService=memberService;
	}
	
	@Override
	public Authentication authenticate(Authentication auth) throws AuthenticationException {
		// 인증을 처리하는 메서드, 마지막 리턴할때 authentication을 줘야하는데, provider한테 어떤 인증객체를 줄건지 사인을 줘야한다. authentication의 종류가 너무많기때문에.
		//그래서 그걸 해주는역할이 밑에 supports이다.(class타입을 줄것)
		//Authentication의 자손객체가 너무 많아서 뭔지모름. 그래서 한정시켜주기위해서 supports에서 제네릭에 와일드카드 타입으로 정해진class타입을 넘겨줘야함.

		String login_id = (String)auth.getPrincipal(); //로그인 시도한ID를 가져온다.
		String login_pwd = (String)auth.getCredentials();//로그인 시도한 Password를 가져온다.
		
		
		MemberVO member = null;
		try {
			member = memberService.getMember(login_id);
		} catch (Exception e) {
			e.printStackTrace();
			throw new BadCredentialsException("서버 장애로 서비스가 불가합니다.");
			//무조건 login fail로만 취급한다. 서버 오작동이긴 하지만 fail로 취급한다. 
		}
		
		//이 단계에서는 로그인제한이지 어디서는 되고 regist에서는 되고 안되고를 지정해서 할 수는 없다.
		if(member != null) {
			if(login_pwd.equals(member.getPwd())) {//아이디 패스워드 일치
				UserDetails authUser = new User(member);
				boolean invalidCheck = authUser.isAccountNonExpired()
								&& authUser.isAccountNonExpired()
								&& authUser.isCredentialsNonExpired()
								&& authUser.isEnabled();
				if(invalidCheck) {
					//스프링 시큐리티 내부 클래스로 인증 토큰 생성한다.
					//authentication.getPrincipal() : id
					//authentication.getCredential() : pwd
					UsernamePasswordAuthenticationToken result = new UsernamePasswordAuthenticationToken(authUser.getUsername(),
																										 authUser.getPassword(),
																										 authUser.getAuthorities());
					//전달할 내용을 설정한 후
					result.setDetails(authUser);
					//리턴한다. successHandler로 전송된다.
					return result;
				}
				
				throw new BadCredentialsException("상태변경으로 로그인이 불가합니다.");
				
			}else {//패스워드 불일치
				throw new BadCredentialsException("패스워드가 일치하지 않습니다.");
			}
		}else {//존재하지 않는 아이디
			throw new BadCredentialsException("존재하지 않는 아이디입니다.");
		}
		
		
		
	}

	@Override
	public boolean supports(Class<?> auth) {
		return auth.equals(UsernamePasswordAuthenticationToken.class);
	}

	
}
