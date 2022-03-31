package kr.or.ddit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.dto.MemberVO;
import com.jsp.service.MemberServiceForModify;
import com.jsp.utils.MakeFileName;

import kr.or.ddit.utils.ExceptionLoggerHelper;


@RestController
@RequestMapping("/member")
public class RestMemberController {
	@Autowired
	private MemberServiceForModify memberService;
	
	//비동기의 시작은 forwarding부터 가고 그다음에 REST가 온다. forwarding 작업없이 데이터가 와도 받아주는이가 없기때문에..
	//비동기는 DB에 들어가지않아서 service가 필요없다는게 무슨뜻인지...?
	
	//이미지 저장 비동기 방식
	@Resource(name = "picturePath")
	private String picturePath;
	
	@RequestMapping(value="/picture",method=RequestMethod.POST, produces = "text/plain;charset=utf-8")
	//파일명은 헤더로오기때문에 인코딩처리해도 소용이없어서 produces로 처리해줘야 된다.
	public ResponseEntity<String> picture(@RequestParam("pictureFile") MultipartFile multi, String oldPicture) throws IOException{
		
		ResponseEntity<String> entity = null;
		
		String result = savePicture(oldPicture,multi);
		
		entity = new ResponseEntity<String>(result, HttpStatus.OK);
		
		return entity;
	}
	
	//비동기의 파일업로드
	public String savePicture(String oldPicture, MultipartFile multi) throws IOException{
		
		String fileName = null;
		
		/*파일유무 확인*/
		if(!(multi == null || multi.isEmpty() || multi.getSize() > 1024*1204*5)) {
			
			/*파일저장폴더설정*/
			String uploadPath = picturePath;
			fileName = MakeFileName.toUUIDFileName(multi.getOriginalFilename(), "$$");
			File storeFile = new File(uploadPath, fileName);
			
			storeFile.mkdirs();
			
			//local HDD에 저장
			multi.transferTo(storeFile);
			
			if(oldPicture != null && !oldPicture.isEmpty()) {
				File oldFile = new File(uploadPath, oldPicture);
				if(oldFile.exists()) {
					oldFile.delete();
				}
			}
		}
		return fileName;
	}
	
	@RequestMapping(value="/getPicture",produces = "text/plain;charset=utf-8")
	public ResponseEntity<byte[]> getPicture(String picture) throws IOException{
		InputStream in = null;
		ResponseEntity<byte[]> entity = null;
		String imgPath = this.picturePath;
		try {
			
			in = new FileInputStream(new File(imgPath,picture));
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in),HttpStatus.CREATED);
		} finally {
			if(in!=null) in.close();
		}
		return entity;
	}
	
	@RequestMapping(value="/getPictureById.do/{id}",method=RequestMethod.GET,produces = "text/plain;charset=utf-8")
	public ResponseEntity<byte[]>getPictureById(@PathVariable("id") String id, HttpServletRequest request) 
																				throws Exception{
		
		String picture = null;
		
		try {
			picture = memberService.getMember(id).getPicture();
		}catch(Exception e) {
			e.printStackTrace();
			ExceptionLoggerHelper.write(request, e, memberService);
			throw e;
		}
		
		return getPicture(picture);
	}
	
	@RequestMapping("/idCheck")
	public ResponseEntity<String> idCheck(String id) throws Exception{
		ResponseEntity<String> entity=null;
		
		MemberVO member = memberService.getMember(id);
		
		if(member!=null) {
			entity = new ResponseEntity<String>("duplicated",HttpStatus.OK);
		}else {
			entity = new ResponseEntity<String>("",HttpStatus.OK);
		}
		
		return entity;
	}
}

