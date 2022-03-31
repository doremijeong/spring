package kr.or.ddit.service.spring;

import java.sql.SQLException;

import com.jsp.dto.MemberVO;
import com.jsp.service.MemberServiceForModify;

public interface ScheduledMemberServiceForModify extends MemberServiceForModify {

	public MemberVO getMemberByPicture(String picture)throws SQLException;
}
