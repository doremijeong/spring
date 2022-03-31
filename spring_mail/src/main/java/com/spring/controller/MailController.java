package com.spring.controller;

import java.io.File;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.spring.command.MailRequestCommand;
import com.spring.mail.MimeAttachNotifier;

@Controller
@RequestMapping("/mail")
public class MailController {

	@Autowired
	private MimeAttachNotifier notifier;
	
	@RequestMapping(value="/form",method=RequestMethod.GET)
	public void mailGet() throws Exception{}
	
	
	@RequestMapping(value="/send", method=RequestMethod.POST)
	public ModelAndView mailPost( MailRequestCommand mailReq,
								  HttpServletRequest request, ModelAndView mnv) throws Exception{
		String url = "mail/mail_success";
		
		MultipartFile attach=mailReq.getFile();
		//배포 폴더 만들기(임시저장)
		String uploadPath = request.getSession().getServletContext().getRealPath("resources/mail_attach");
		
		//파일 유무
		if(attach!=null && !attach.isEmpty()) {
			//파일 크기
			if(attach.getSize()<1024*1024*5) {
				//파일 저장
				File file = new File(uploadPath,attach.getOriginalFilename());
				file.mkdirs();
				
				attach.transferTo(file);
				
				//메일 메세지 보내기
				try {
					notifier.sendMail(mailReq, uploadPath);
				} catch (Exception e) {
					e.printStackTrace();
					url="mail/mail_fail";
					mnv.addObject("message","메일 보내기를 실패했습니다..");
				}
				
			}else {
				url="mail/mail_fail";
				mnv.addObject("message","첨부파일이 용량초과 입니다.");
			}
		}
		
		mnv.addObject("uploadPath",uploadPath);
		mnv.addObject("mailRequest",mailReq);
		
		//화면 설정
		mnv.setViewName(url);
		return mnv;
	}
}
