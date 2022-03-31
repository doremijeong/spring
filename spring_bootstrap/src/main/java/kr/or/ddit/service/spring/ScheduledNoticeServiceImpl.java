package kr.or.ddit.service.spring;

import java.sql.SQLException;

import com.jsp.dto.NoticeVO;

import kr.or.ddit.dao.spring.NoticeDAOBean;

public class ScheduledNoticeServiceImpl extends NoticeServiceImpl implements ScheduledNoticeService{

	private NoticeDAOBean noticeDAOBeanChild;
	public void setNoticeDAOBeanChild(NoticeDAOBean noticeDAOBeanChild) {
		this.noticeDAOBeanChild = noticeDAOBeanChild;
	}
	
	@Override
	public NoticeVO getNoticeByImage(String imageFile) throws SQLException {
		NoticeVO notice = noticeDAOBeanChild.selectNoticeByImage(imageFile);
		return notice;
	}

}
