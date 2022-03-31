package kr.or.ddit.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import com.jsp.dto.MemberVO;
import com.jsp.utils.GetUploadPath;

public class ExceptionLoggerHelper {

	//request는 저장경로받으려고... 사용자가 요청한  url필요, 사용자정보를 얻기위해서...
	//text를 만드는 과정
	//bean등록할거 아니고, 메서드만만들거고 
	//static으로 했다는거는 instance가 필요없다는것.
	
	public static void write(HttpServletRequest request, Exception e, Object obj) {
		//저장경로
		String savePath = GetUploadPath.getUploadPath("error.log").replace("/", File.separator);
		
		//이름
		String logFilePath=savePath+File.separator+"system_exception_log.csv";
		
		String url = request.getRequestURI().replace(request.getContextPath(), "");
		String date = new SimpleDateFormat("yyyy-MM-dd HH:mm").format(new Date());
		String loginUserName = ((MemberVO)request.getSession().getAttribute("loginUser")).getName();
		String exceptionType = e.getClass().getName();
		String happenObject = obj.getClass().getName();
		
		String log = "[Error : "+ exceptionType + "]" + url + "," + date + "," + loginUserName + "," + happenObject + "\n" + e.getMessage();
		
		//로그파일 생성.
		File file = new File(savePath);
		file.mkdirs();
		
		try {
			//이어쓰기
			BufferedWriter out = new BufferedWriter(new FileWriter(logFilePath,true));
			
			//로그들 기록
			out.write(log);
			out.newLine();//줄바꿈해놔야 또쓰지, 안그러면 옆으로 씀.
			
			out.close();
		} catch (IOException exception) {
			exception.printStackTrace();
			
		}
	}
}
