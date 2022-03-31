package kr.or.ddit.interceptor;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.jsp.dto.MemberVO;

//오버라이딩을 선택적으로 할 수 있고, 선택적으로 잡을 수 있다.
//예를들어 로그인했을때 로그인하는지 알려면 컨트롤러를 지나가야 알 수 있다.
//로그인 유저의 로그는 post쪽에 박히게된다.
//그럼 post는 실제로 로그인했는지안했는지, 확인하게된다.
//XSS를 컨트롤러가 등록되기전에 잡아야하기때문에 pre쪽에서 잡아야한다.왜냐, XSS로 먼저 바꿔줘야지 나중에 바꿔주면 소용없잖아..
public class LoginUserLogInterceptor extends HandlerInterceptorAdapter{

	/*@Override
	//controller들어가기 직전, true,false에 따라 controller의 실행여부를 여기서 판단한다. Object는 우리가 쓰는애가아니다. 필요없음. 우리는 주로 request를 사용함.
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// TODO Auto-generated method stub
		return super.preHandle(request, response, handler);
	}*/


	/*@Override //exception을 받아줌.
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		// TODO Auto-generated method stub
		super.afterCompletion(request, response, handler, ex);
	} */

	//로그데이터를 만들기위한 기본정보 세팅
	//xml에서 등록할때 바꾸기위한 set메서드.. bean등록할때
	private String savePath="c:\\log";
	private String saveFileName ="login_user_log.csv";
	
	public void setSavePath(String savePath) {
		this.savePath=savePath;
	}
	
	public void setSaveFileName(String saveFileName) {
		this.saveFileName = saveFileName;
	}
	
	@Override//controller가 끝나고 model이 받아줌. controller가 return 하는건 model이 다 받아준다.
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		
		MemberVO loginUser = (MemberVO)request.getSession().getAttribute("loginUser");
		if(loginUser==null)return;
		
		//로그인 정볼르 스트링으로 저장
		String tag = "[login:user]";
		String log = tag
					+ loginUser.getId()+","
					+ loginUser.getPhone()+","
					+ loginUser.getEmail()+","
					+ request.getRemoteAddr()+","
					+ new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		
		File file=new File(savePath);
		if(!file.exists()) {
			file.mkdirs();
		}
		String logFilePath = savePath+File.separator+saveFileName;
		BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath,true));
		
		//로그들 기록
		out.write(log);
		out.newLine();
		
		out.close();
		
		
		
		
	}
	
	//인터셉터는 부분부분 적용시키는게 목적이다.
	//패터닝 가능.(서블릿컨텍스트에서)
	//login.do잡을거 그래서 로그인했는지 확인할거다.
	//session에 로그인유저가 있는지 확인.
	//인터셉터를 왜쓰냐, 컨트롤러의 관리방법이지만 잘 활용하면 컨트롤러에서 하는걸 빼낼수있는 장치도 된다.
	//추가하고 빼내는데도 용이하다. web.xml에서 분리가 굉장히 쉽게할 수 있다. 용이나는 녀석.
	
	
}
