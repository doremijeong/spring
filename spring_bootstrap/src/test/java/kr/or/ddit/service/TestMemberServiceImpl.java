package kr.or.ddit.service;

import java.util.List;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.jsp.command.SearchCriteria;
import com.jsp.dto.MemberVO;
import com.jsp.service.MemberServiceForModify;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:kr/or/ddit/context/root-context.xml")
public class TestMemberServiceImpl {
	
	@Autowired
	private MemberServiceForModify service;
	
	@Before
	public void init() {
		//service.setMemberDAO(new MockMemberDAO());
	}
	
	@Test
	public void testGetList() throws Exception{
		SearchCriteria cri = new SearchCriteria();
		
		List<MemberVO> memberList = (List<MemberVO>)service.getMemberListPage(cri).get("memberList");
		
		Assert.assertEquals(6,  memberList.size());
	}
}
