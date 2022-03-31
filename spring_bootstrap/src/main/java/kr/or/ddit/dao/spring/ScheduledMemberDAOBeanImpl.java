package kr.or.ddit.dao.spring;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;

import com.jsp.dto.MemberVO;

public class ScheduledMemberDAOBeanImpl extends MemberDAOImplTemplate implements ScheduledMemberDAOBean {

	private SqlSession sqlSession;
	public void setSqlSession(SqlSession sqlSession) {
		this.sqlSession = sqlSession;
	}
	
	//다 가져와서 비교하기에는 데이터양이 너무 많아서 이렇게 가져와서 비교하는게 효율적.
	@Override
	public MemberVO selectMemberByPicture(String picture) throws SQLException {
		MemberVO member = null;
		
		member = sqlSession.selectOne("Member-Mapper.selectMemberByPicture",picture);
		
		return member;
	}

}
