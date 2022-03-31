package kr.or.ddit.dao.spring;

import java.sql.SQLException;
import java.util.List;

import com.jsp.command.SearchCriteria;
import com.jsp.dto.BoardVO;

public interface BoardDAOBean {

List<BoardVO> selectBoardCriteria(SearchCriteria cri) throws SQLException;
	
	int selectBoardCriteriaTotalCount(SearchCriteria cri) throws SQLException;
	
	BoardVO selectBoardByBno(int bno) throws SQLException;
	
	void insertBoard(BoardVO baord) throws SQLException;
	
	void updateBoard(BoardVO board) throws SQLException;
	
	void deleteBoard(int bno) throws SQLException;
	
	//viewcnt증가
	void increaseViewCnt(int bno) throws SQLException;
	
	//board_seq.nextval가져오기
	int selectBoardSeqNext() throws SQLException;
	
	BoardVO selectBoardByImage(String imageFile) throws SQLException;

}
