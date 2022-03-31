package kr.or.ddit.service.spring;

import java.sql.SQLException;

import com.jsp.dto.BoardVO;
import com.jsp.service.BoardService;

public interface ScheduledBoardService extends BoardService {

	public BoardVO getBoardByImage(String imageFile)throws SQLException;
}
