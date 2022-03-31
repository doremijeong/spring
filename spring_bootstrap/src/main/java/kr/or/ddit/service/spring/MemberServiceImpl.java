package kr.or.ddit.service.spring;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.jsp.command.Criteria;
import com.jsp.command.PageMaker;
import com.jsp.dto.MemberVO;
import com.jsp.exception.InvalidPasswordException;
import com.jsp.exception.NotFoundIDException;
import com.jsp.service.MemberServiceForModify;

import kr.or.ddit.dao.spring.MemberDAOBean;

public class MemberServiceImpl implements MemberServiceForModify {
//extends한다는것은 기존의 것을 다시 재정의 하겠다는것.
//extends MemberServiceForModifyImpl을해도 소용없다. 왜냐 가지고있는 interface가 다르기때이다.
//구현체를 extends하는건 DAO의 계보가 다르기때문에 사용불가. 

	private MemberDAOBean memberDAOBean;// interface의 부분은 정말 신중하게생각하고 만들어야한다.

	public void setMemberDAOBean(MemberDAOBean memberDAOBean) {
		this.memberDAOBean = memberDAOBean;
	}

	@Override
	public List<MemberVO> getMemberList() throws Exception {
		List<MemberVO> memberList = memberDAOBean.selectMemberList();
		return memberList;
	}

	@Override
	public List<MemberVO> getMemberList(Criteria cri) throws Exception {
		List<MemberVO> memberList = memberDAOBean.selectMemberList(cri);
		return memberList;
	}

	@Override
	public Map<String, Object> getMemberListPage(Criteria cri) throws Exception {

		List<MemberVO> memberList = memberDAOBean.selectMemberList(cri);
		PageMaker pageMaker = new PageMaker();
		pageMaker.setCri(cri);
		pageMaker.setTotalCount(memberDAOBean.selectMemberListCount());

		Map<String, Object> dataMap = new HashMap<String, Object>();
		dataMap.put("memberList", memberList);
		dataMap.put("pageMaker", pageMaker);

		return dataMap;
	}

	@Override
	public MemberVO getMember(String id) throws Exception {

		MemberVO member = memberDAOBean.selectMemberById(id);
		return member;
	}

	@Override
	public void regist(MemberVO member) throws Exception {
		memberDAOBean.insertMember(member);
	}

	@Override
	public void modify(MemberVO member) throws Exception {
		memberDAOBean.updateMember(member);
	}

	@Override
	public void remove(String id) throws Exception {
		memberDAOBean.deleteMember(id);
	}

	@Override
	public void enabled(String id, int enabled) throws Exception {
		memberDAOBean.enabledMember(id, enabled);

	}

	@Override
	public void login(String id, String pwd) throws NotFoundIDException, InvalidPasswordException, Exception {

		MemberVO member = memberDAOBean.selectMemberById(id);
		if (member == null)
			throw new NotFoundIDException();
		if (!pwd.equals(member.getPwd()))
			throw new InvalidPasswordException();
	}

}
