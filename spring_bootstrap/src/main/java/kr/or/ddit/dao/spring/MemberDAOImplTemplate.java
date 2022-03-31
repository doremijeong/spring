package kr.or.ddit.dao.spring;

import java.sql.SQLException;
import java.util.List;

import org.apache.ibatis.session.SqlSession;

import com.jsp.command.Criteria;
import com.jsp.command.SearchCriteria;
import com.jsp.dao.MemberDAO;
import com.jsp.dto.MemberVO;

public class MemberDAOImplTemplate implements MemberDAOBean{

	private SqlSession session;
	private MemberDAO memberDAO;
	
	public void setSession(SqlSession session) {
		this.session = session;
	}
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}
	
	
	@Override
	public List<MemberVO> selectMemberList() throws Exception {
		return memberDAO.selectMemberList(session);
	}

	@Override
	public List<MemberVO> selectMemberList(Criteria cri) throws Exception {
		return memberDAO.selectMemberList(session, cri);
	}

	@Override
	public List<MemberVO> selectMemberList(SearchCriteria cri) throws Exception {
		return memberDAO.selectMemberList(session, cri);
	}

	@Override
	public int selectMemberListCount() throws Exception {
		return memberDAO.selectMemberListCount(session);
	}

	@Override
	public int selectMemberListCount(SearchCriteria cri) throws Exception {
		return memberDAO.selectMemberListCount(session, cri);
	}

	@Override
	public MemberVO selectMemberById(String id) throws SQLException {
		return memberDAO.selectMemberById(session, id);
	}

	@Override
	public void insertMember(MemberVO member) throws Exception {
		memberDAO.insertMember(session, member);
	}

	@Override
	public void updateMember(MemberVO member) throws SQLException {
		memberDAO.updateMember(session, member);
		
	}

	@Override
	public void deleteMember(String id) throws SQLException {
		memberDAO.deleteMember(session, id);
		
	}

	@Override
	public void enabledMember(String id, int enabled) throws Exception {
		memberDAO.enabledMember(session, id, enabled);
		
	}

	
}
