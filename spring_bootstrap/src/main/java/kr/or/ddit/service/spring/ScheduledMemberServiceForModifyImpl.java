package kr.or.ddit.service.spring;

import java.sql.SQLException;

import com.jsp.dto.MemberVO;

import kr.or.ddit.dao.spring.ScheduledMemberDAOBean;

public class ScheduledMemberServiceForModifyImpl extends MemberServiceImpl
				implements ScheduledMemberServiceForModify{

	private ScheduledMemberDAOBean scheduledMemberDAO;
	public void setScheduledMemberDAO(ScheduledMemberDAOBean scheduledMemberDAO) {
		this.scheduledMemberDAO = scheduledMemberDAO;
	}
	
	@Override
	public MemberVO getMemberByPicture(String picture) throws SQLException {
		MemberVO member = scheduledMemberDAO.selectMemberByPicture(picture);
		return member;
	}

}
