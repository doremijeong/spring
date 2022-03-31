package kr.or.ddit.dao.spring;

import java.sql.SQLException;

import com.jsp.dto.MemberVO;

public interface ScheduledMemberDAOBean extends MemberDAOBean {

	public MemberVO selectMemberByPicture(String picture) throws SQLException;
}
