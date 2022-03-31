package kr.or.ddit.dao.spring;

import java.sql.SQLException;
import java.util.List;

import com.jsp.command.Criteria;
import com.jsp.command.SearchCriteria;
import com.jsp.dto.MemberVO;

public interface MemberDAOBean {
	
	//회원 리스트 조회
	public List<MemberVO> selectMemberList() throws Exception;
	public List<MemberVO> selectMemberList(Criteria cri) throws Exception;
	public List<MemberVO> selectMemberList(SearchCriteria cri) throws Exception;
	
	
	//일반 리스트 전체 개수
	int selectMemberListCount() throws Exception;
	//검색 결과의 전체 리스트 개수
	int selectMemberListCount(SearchCriteria cri) throws Exception;
	//회원 정보 조회
	MemberVO selectMemberById(String id) throws SQLException;
	//회원추가
	public void insertMember(MemberVO member) throws Exception;
	//회원수정
	public void updateMember(MemberVO member) throws SQLException;
	
	//회원 삭제
	public void deleteMember(String id) throws SQLException;
	
	//회원 활성화
	public void enabledMember(String id, int enabled) throws Exception;
}
