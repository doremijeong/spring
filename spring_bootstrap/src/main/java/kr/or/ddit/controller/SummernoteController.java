package kr.or.ddit.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.io.IOUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.jsp.utils.MakeFileName;

@RestController
public class SummernoteController {

	//이름으로 구별해야하기때문에 resource사용
	//이미지를 저장시키는작업.
	//비동기이기때문에 multipartfile로 받음.
	
	@Resource(name="imgPath")
	private String imgPath;
	
	@RequestMapping("/uploadImg")
	public ResponseEntity<String> uploadImg(MultipartFile file,HttpServletRequest request) throws Exception{
		ResponseEntity<String> result = null;
		
		int fileSize = 5 * 1024 * 1024; //5MB
		
		if(file.getSize() > fileSize) {
			return new ResponseEntity<String>("용량초과입니다.",HttpStatus.BAD_REQUEST);
		}
		
		String savePath = imgPath.replace("/", File.separator);
		String fileFormat = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf(".")+1);
		String fileName = MakeFileName.toUUIDFileName("."+fileFormat, "");
		
		File saveFile = new File(savePath,fileName);
		
		saveFile.mkdirs();
		
		try {
			file.transferTo(saveFile);
			result = new ResponseEntity<String>(request.getContextPath()
						+"/getImg.do?fileName=" + fileName,HttpStatus.OK);
		} catch (IOException e) {
			result = new ResponseEntity<String>(HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return result;
	}
	
	//다운로드할때에는 이런방식으로하면 안됨.
	//내보내는 파일이 
	//다운로드안되는이유는 헤더세팅이 없기때문에 브라우저가 인식하지못함. 파일이 바이너리로 열어버림.
	//이미지태그가 src로 받는건 전혀 문제가되지않는다.
	//그래서 지금 해당 이미지를 바로 바이너리로 보내서 바로 이미지가 보여야하는경우에 이런 간단한 방법을 사용함.
	//대신에 다운로드가안됨.(url클릭하면 그냥 text로 보여짐)
	//파일을 내보내는건 toByteArray
	@RequestMapping("/getImg")
	public ResponseEntity<byte[]> getImg(String fileName, HttpServletRequest request)throws Exception{
		ResponseEntity<byte[]> entity = null;
		
		String savePath = imgPath.replace("/", File.separator);
		File sendFile = new File(savePath,fileName);
		
		InputStream in = null;
		try {
			in = new FileInputStream(sendFile);
			
			entity = new ResponseEntity<byte[]>(IOUtils.toByteArray(in), HttpStatus.CREATED);
		} catch (IOException e) {
			e.printStackTrace();
			entity = new ResponseEntity<byte[]>(HttpStatus.INTERNAL_SERVER_ERROR);
		} finally {
			in.close();
		}
		return entity;
	}
	
	@RequestMapping("/deleteImg")
	public ResponseEntity<String> deleteImg(@RequestBody Map<String, String> data) throws Exception{
		
		ResponseEntity<String> result = null;
		
		String savePath = imgPath.replace("/", File.separator);
		File target = new File(savePath, data.get("fileName"));
		
		if(!target.exists()) {
			result = new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
		}else {
			try {
				target.delete();
				result = new ResponseEntity<String>("SUCCESS",HttpStatus.OK);
			} catch (Exception e) {
				result = new ResponseEntity<String>("Fail", HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return result;
	}
	
	
	
}
