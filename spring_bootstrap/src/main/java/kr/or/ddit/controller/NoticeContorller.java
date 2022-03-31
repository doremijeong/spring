package kr.or.ddit.controller;

import java.sql.SQLException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.josephoconnell.html.HTMLInputFilter;
import com.jsp.command.SearchCriteria;
import com.jsp.dto.NoticeVO;
import com.jsp.service.NoticeService;

import kr.or.ddit.utils.GetAttachesByMultipartFileAdapter;

@Controller
@RequestMapping("/notice")
public class NoticeContorller {

	@Autowired
	//@Resource(name="noticeService")
	private NoticeService noticeService;
	
	@RequestMapping("/main")
	public String main()throws Exception{
		String url = "notice/main.open";
		return url;
	}
	
	@RequestMapping("/list")
	public String list(SearchCriteria cri, Model model)throws Exception{
		String url = "notice/list.open";
		
		Map<String, Object> dataMap = noticeService.getNoticeList(cri);
		model.addAllAttributes(dataMap);//Resolver단 가서 우리는 통째로 보냈지만 하나씩 꺼내서 심어준다.
		
		return url;
	}
	
	@RequestMapping("/registForm")
	public String registForm(){
		String url = "notice/regist.open";
		return url;
	}
	
	@RequestMapping("/regist")
	public String regist(NoticeVO notice, HttpServletRequest request, RedirectAttributes rttr)throws Exception{
		String url = "redirect:/notice/list";
		
		//notice.setTitle(HTMLInputFilter.htmlSpecialChars(notice.getTitle()));
		//interceptor등록후
		notice.setTitle((String)request.getAttribute("XSStitle"));
		
		noticeService.regist(notice);
		
		rttr.addFlashAttribute("form","regist");
		
		return url;
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(int nno,
							  @RequestParam(defaultValue="")String from,
							  HttpServletRequest request,
							  ModelAndView mnv)throws SQLException{
	
		String url = "notice/detail.open";
		
		NoticeVO notice = null;
		
		if(!from.equals("list")) {
			notice = noticeService.getNoticeForModify(nno);
		}else {
			notice = noticeService.getNotice(nno);
			url = "redirect:/notice/detail.do?nno="+nno;
		}
		
		mnv.addObject("notice",notice);
		mnv.setViewName(url);
		
		return mnv;
		
	}
	
	@RequestMapping("/modifyForm")
	public ModelAndView modifyForm(int nno, ModelAndView mnv) throws Exception{
		String url="notice/modify.open";
		
		NoticeVO notice = noticeService.getNoticeForModify(nno);
		
		mnv.addObject("notice",notice);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPost(NoticeVO notice,
							 HttpServletRequest request,
							 RedirectAttributes rttr)throws Exception{
		String url = "redirect:/notice/detail.do";
		
		//notice.setTitle(HTMLInputFilter.htmlSpecialChars(notice.getTitle()));
		notice.setTitle((String)request.getAttribute("XSStitle"));
		
		noticeService.modify(notice);
		
		rttr.addAttribute("nno",notice.getNno());
		rttr.addAttribute("from","modify");
		//addFlashAttribute를 안하고 그냥 addAttribute를 하게되면 detail에서 꺼내올때 앞에 param.을 붙여서 사용해야한다.
		//그냥 attribute는 param을 쓰고 flash는 그냥 주고..
		
		return url;
	}
	
	@RequestMapping(value="/remove",method=RequestMethod.POST)//jsp에서form태그 action에 아무것도 안주면 get방식.
	public String remove(int nno,RedirectAttributes rttr)throws Exception{
		String url = "redirect:/notice/detail.do";
		
		noticeService.remove(nno);
		
		rttr.addFlashAttribute("from","remove");
		rttr.addAttribute("nno",nno);
		
		return url;
	}
	
}
