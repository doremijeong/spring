package kr.or.ddit.controller;

import java.sql.SQLException;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.jsp.command.BoardModifyCommand;
import com.jsp.command.SearchCriteria;
import com.jsp.controller.XSSHttpRequestParameterAdapter;
import com.jsp.dto.BoardVO;
import com.jsp.service.BoardService;

@Controller
@RequestMapping("/board") //board라는 1댑스가 있구나 라고 알고있음.
public class BoardController {

	
	@Autowired
	//@Resource(name="boardService")
	private BoardService boardService;
	
	@RequestMapping("/main")
	public String main()throws Exception{
		return "board/main";
	}
	
	@RequestMapping("/list")
	public ModelAndView list(SearchCriteria cri, ModelAndView mnv)throws SQLException{
		String url = "board/list.open";
		
		Map<String,Object> dataMap = boardService.getBoardList(cri);
		
		mnv.addObject("dataMap",dataMap);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping("/registForm")
	public String registForm() {
		String url = "board/regist.open";
		return url;
	}
	
	@RequestMapping("/regist")
	public String regist(HttpServletRequest request, //BoardVO board,(밑에 주석이랑 짝궁)
						RedirectAttributes rttr)throws Exception{
		String url = "redirect:/board/list.do";
		
		//board.setTitle(HTMLInputFilter.htmlSpecialChars(board.getTitle()));
		
		BoardVO board = (BoardVO)XSSHttpRequestParameterAdapter.execute(request, BoardVO.class, true);
		
		board.setContent(request.getParameter("content"));
		
		//board.setTitle((String)request.getAttribute("XSStitle"));//이거에러남. 왜?
		
		boardService.regist(board);
		
		//rttr.addAttribute("from","regist"); //주소줄로 가게됨.
		
		rttr.addFlashAttribute("from","regist");
		
		return url;
	}
	
	@RequestMapping("/detail")
	public ModelAndView detail(int bno,String from,ModelAndView mnv) throws SQLException{
		String url = "board/detail.open";
		
		BoardVO board = null;
		if(from!=null && from.equals("list")) {
			board=boardService.getBoard(bno);
			url="redirect:/board/detail.do?bno="+bno;//list화면에서 detail갈때 list에서올때만 조회수를 올리고싶었서 이작업 했음
		}else {
			board=boardService.getBoardForModify(bno);
		}
		
		mnv.addObject("board",board);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	//url이 같으면 method를 구분해줘야겠지만, 우리는 url를 다 갈라놓았기때문에 method를 구분하지 않았음.
	@RequestMapping("/modifyForm")
	public ModelAndView modifyForm(int bno,ModelAndView mnv) throws SQLException{
		String url="board/modify.open";
		
		BoardVO board = boardService.getBoardForModify(bno);
		
		mnv.addObject("board",board);
		mnv.setViewName(url);
		
		return mnv;
	}
	
	@RequestMapping(value="/modify",method=RequestMethod.POST)
	public String modifyPost(HttpServletRequest request, //BoardModifyCommand modifyReq,
							RedirectAttributes rttr) throws Exception{
		String url = "redirect:/board/detail.do";
		
		BoardModifyCommand modifyReq
		=(BoardModifyCommand)XSSHttpRequestParameterAdapter.execute(request, BoardModifyCommand.class, true);
		
		BoardVO board = modifyReq.toBoardVO();
		board.setContent(request.getParameter("content"));
		
		//board.setTitle(HTMLInputFilter.htmlSpecialChars(board.getTitle()));
		
		boardService.modify(board);
		
		rttr.addFlashAttribute("from","modify");
		rttr.addAttribute("bno",board.getBno());
		
		return url;
	}
	
	@RequestMapping("/remove")
	public String remove(int bno,RedirectAttributes rttr) throws Exception{
		String url = "redirect:/board/detail.do";
		boardService.remove(bno);
		
		rttr.addAttribute("bno",bno);
		rttr.addFlashAttribute("from","remove");
		return url;
		
	}
	
}
