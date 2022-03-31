package kr.or.ddit.command;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.jsp.dto.AttachVO;
import com.jsp.dto.PdsVO;

public class PdsRegistCommand {
//등록화면에 있는 그대로를 짜맞추기
	
	private String title;
	private String content;
	private String writer;
	private List<MultipartFile> uploadFile; //이렇게하면 파일이 몇개던지 받을수있기때문에 command를 이용해 여러개의 파일을 받을 수있도록 만들어준다.
											//저장을 해야 만들어지기때문에 하나씩 뽑아서 만들어놓고 저장하는 방식
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public List<MultipartFile> getUploadFile() {
		return uploadFile;
	}
	public void setUploadFile(List<MultipartFile> uploadFile) {
		this.uploadFile = uploadFile;
	}

	//attachVO는 바로 만들지 못한다.
	public PdsVO toPdsVO() {

		PdsVO pds = new PdsVO();
		pds.setTitle(this.title);
		pds.setContent(this.content);
		pds.setWriter(this.writer);
		
		return pds;
	}
	
}
