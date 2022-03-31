package kr.or.ddit.service.spring;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.command.PageMaker;
import com.jsp.command.SearchCriteria;
import com.jsp.dto.BoardVO;
import com.jsp.service.BoardService;

import kr.or.ddit.dao.spring.BoardDAOBean;
import kr.or.ddit.dao.spring.ReplyDAOBean;

public class BoardServiceImpl implements BoardService{

	private BoardDAOBean boardDAOBean;
	public void setBoardDAOBean(BoardDAOBean boardDAOBean) {
		this.boardDAOBean = boardDAOBean;
	}
	
	private ReplyDAOBean replyDAOBean;
	public void setReplyDAOBean(ReplyDAOBean replyDAOBean) {
		this.replyDAOBean = replyDAOBean;
	}

	@Override
	public BoardVO getBoardForModify(int bno) throws SQLException {
		BoardVO board = boardDAOBean.selectBoardByBno(bno);
		return board;
	}

	@Override
	public BoardVO getBoard(int bno) throws SQLException {
		BoardVO board = boardDAOBean.selectBoardByBno(bno);
		boardDAOBean.increaseViewCnt(bno);
		return board;
	}

	@Override
	public void regist(BoardVO board) throws SQLException {
		int bno = boardDAOBean.selectBoardSeqNext();
		board.setBno(bno);
		boardDAOBean.insertBoard(board);
	}

	@Override
	public void modify(BoardVO board) throws SQLException {
		boardDAOBean.updateBoard(board);
	}

	@Override
	public void remove(int bno) throws SQLException {
		boardDAOBean.deleteBoard(bno);
	}

	@Override
	public Map<String, Object> getBoardList(SearchCriteria cri) throws SQLException {
		Map<String, Object> dataMap = new HashMap<String, Object>();

		// 현재 page 번호에 맞는 리스트를 perPageNum 개수 만큼 가져오기.
		List<BoardVO> boardList = boardDAOBean.selectBoardCriteria(cri);
		// reply count 입력 :일단은 게시글에 댓글수는 나오지않는다.
		for (BoardVO board : boardList) {
			int replycnt = replyDAOBean.countReply(board.getBno());
			board.setReplycnt(replycnt);
		}
		// 전체 board 개수
		int totalCount = boardDAOBean.selectBoardCriteriaTotalCount(cri);

		// PageMaker 생성.
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(totalCount);

		dataMap.put("boardList", boardList);
		dataMap.put("pageMaker", pageMaker);

		return dataMap;

	}	
	
}
