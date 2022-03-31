package kr.or.ddit.view;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.MediaType;
import org.springframework.web.servlet.View;

import com.jsp.utils.MakeFileName;

//이거는 View니까 servlet-context.xml에 bean등록한다.
public class FileDownloadView implements View {

	
	private String contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;
	
	//set메스드는 줄수도 있다~ 해서 만들어주지만, 파일다운로드하기위해 만든거기때문에 set메서드를 쓸일이 거의 없다. set으로 세팅 get으로 가져오면된다.
	//view의 성격이 이미 정해져있어서 쓸일이 거의 없을거야~
	public void setContentType(String contentType) {
		this.contentType = contentType;
	}
	
	@Override
	public String getContentType() {
		//우리가 줬떤 text/html;charset=utf-8이렇게 줬는데 이건 파일을 가지고오는 클래스니까 타입이 무엇이냐..
		return this.contentType;
	}

	@Override
	public void render(Map<String, ?> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		
		//req, res모두 갖다준다.
		//리플렉션 할때 어떤타입이 들어올지모르니까 썻던 제네릭에 와일드카드 사용.니가준거 그거 그타입으로 온다. 어차피 모델이 안이 map으로 되어있기에 타입이 map이다.
		//모델에 있는 맵을 그대로 꺼내서 준단말이야 addAttributes했던거를 그대로 map에서 resolver가 꺼내준단말이야.
		//하나씩 심어야하니까 request필요
		
		//이렇게했다는건 컨트롤러에서 이렇게 심었다는거
		//컨트롤러에서 심는과정이 있음.
		String savePath = (String) model.get("savePath");
		String fileName = (String) model.get("fileName");
		
		//보낼 파일 설정.
		File downloadFile = new File(savePath + File.separator + fileName);
		FileInputStream inStream = new FileInputStream(downloadFile);
		
		//파일 포맷으로MIME를 결정한다. - 이 로직이 없으면 무조건 다운로드한다.(Mime설정하는부분)
		//이걸 사용하면 다운로드하지않고 바로 열리고 저장버튼이 따로 생긴다. 우리는 모르고 context.getMemiType한테 파일명만 주면 알아서 type을 바꿔준다.
		ServletContext context = request.getServletContext();
		//getServletContext()가 에러가나는이유는 getSession()을 해서 가져와서 context를 뽑아와야한다.
		//우리가 가져온 jar가 3.0이아니고 2버전이여서 그렇다. 그럼 pom.xml에서 servlet버전을 바꿔줘야됨.
		String mimeType = context.getMimeType(downloadFile.getName());//getMimeType에다가 파일명을 넣어주면 확장자에 맞게 mime파일이 만들어짐 그럼 pdf랑 image가 열림
																	  //다운로드되는게아니라 링크 누르면 pdf파일이 바로 열림.이미지도 마찬가지로, mime type으로 안하면 다운로드가 됨.
		if(mimeType != null) {
			this.contentType = mimeType;
		}
		
		//response수정.
		response.setContentType(mimeType);
		response.setContentLength((int)downloadFile.length());
		
		String headerKey = "content-Disposition";
		
		//한글깨짐 방지 : ISO-8859-1
		String sendFileName = MakeFileName.parseFileNameFromUUID(downloadFile.getName(), "\\$\\$");
		
		String header = request.getHeader("User-Agent");
		if(header.contains("MSIE")) {
			sendFileName = URLEncoder.encode(sendFileName,"UTF-8");
		}else {
			sendFileName = new String(sendFileName.getBytes("utf-8"),"ISO-8859-1");
		}
		String headerValue = String.format("attachment; filename=\"%s\"", sendFileName);
		response.setHeader(headerKey, headerValue);
		
		//파일 내보내기
		OutputStream outStream = response.getOutputStream();
		byte[] buffer = new byte[4096];
		int bytesRead = -1;
		
		while((bytesRead = inStream.read(buffer)) != -1) {
			outStream.write(buffer,0,bytesRead);
		}
		
		inStream.close();
		outStream.close();
	}

}
