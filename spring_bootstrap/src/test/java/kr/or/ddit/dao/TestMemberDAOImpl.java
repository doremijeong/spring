package kr.or.ddit.dao;

import java.sql.SQLException;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import com.jsp.dao.MemberDAO;
import com.jsp.dto.MemberVO;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:kr/or/ddit/context/root-context.xml")
@Transactional
public class TestMemberDAOImpl {
   
   @Autowired
   private MemberDAO memberDAO;
   //의존주입
   
   @Autowired
   private SqlSession sqlSession;
   //의존주입
   
   @Test
   public void testSelectMember() throws SQLException
   {
      String id="mimi";
      
      MemberVO member=memberDAO.selectMemberById(sqlSession, id);
      
      Assert.assertEquals(id, member.getId());
   }
   
   @Test
   @Rollback
   public void testInsertMember() throws Exception
   {
      MemberVO testMember=new MemberVO();
      
      testMember.setId("kaka");
      testMember.setPwd("123");
      testMember.setEmail("abc@naver.com");
      testMember.setPicture("no Image");
      testMember.setName("kaka");
      testMember.setPhone("010-0000-0000");
      testMember.setAddress("asdf");
      testMember.setAuthority("asdf");
      
      memberDAO.insertMember(sqlSession, testMember);
      
      MemberVO result=memberDAO.selectMemberById(sqlSession, testMember.getId());
      
      Assert.assertEquals(testMember.getId(), result.getId());
   }
   
}