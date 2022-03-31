package kr.or.ddit.dao.spring;

import java.sql.SQLException;
import java.util.List;

import com.jsp.command.SearchCriteria;
import com.jsp.dto.NoticeVO;

public interface NoticeDAOBean {
	List<NoticeVO> selectSearchNoticeList(SearchCriteria cri) throws SQLException;
	
	int selectSearchNoticeListCount(SearchCriteria cri) throws SQLException;
	
	NoticeVO selectNoticeByNno(int nno) throws SQLException;
	
	//viewcnt증가
	void increaseViewCount(int nno) throws SQLException;
	
	//Notice_seq.nextval가져오기
	int selectNoticeSequenceNextValue() throws SQLException;
	
	void insertNotice(NoticeVO Notice) throws SQLException;
	
	void updateNotice(NoticeVO Notice) throws SQLException;
	
	void deleteNotice(int nno) throws SQLException;	
	
	NoticeVO selectNoticeByImage(String imageFile)throws SQLException;
}
