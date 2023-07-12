package com.spring.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;

import com.spring.member.vo.MemberVO;


public class MemberDAOImpl implements MemberDAO {
	
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		// 속성 sqlSesion에 sqlSession 빈을 주입하기 위해 setter를 구현
		
		this.sqlSession = sqlSession;
		
	}

	
	
	@Override
	public List selectAllMemberList() throws DataAccessException {
		
		List<MemberVO> membersList = null;
		
		membersList = sqlSession.selectList("mapper.member.selectAllMemberList");
		// 주입된 sqlSession빈으로 selectList() 메서드를 호출하면서 SQL문의 id를 전달
		
		return membersList;
		
	}

	
	
	@Override
	public int insertMember(MemberVO memberVO) throws DataAccessException {
		
		int result = sqlSession.insert("mapper.member.insertMember", memberVO);
		// 주입된 sqlSession빈으로 insert()메서드를 호출 하면서 SQL문의 id와 memberVO를 전달
		
		return result;
		
	}

	
	public int updateMember(MemberVO memberVO) {

		int result = sqlSession.update("mapper.member.updateMember", memberVO);

		System.out.println("update실행문 result결과값 : "+result);

		return result;
	}   
	
	
	@Override
	public int deleteMember(String id) throws DataAccessException {
		
		int result =  sqlSession.delete("mapper.member.deleteMember", id);
		// 주입된 sqlSession빈으로 delete() 메서드를 호출하면서 SQL문의 id와 회원 ID를 전달.
		
		return result;
		
	}



	@Override
	public List<MemberVO> searchMembers(MemberVO vo) {

		List<MemberVO> memberVO = sqlSession.selectList("mapper.member.searchMembers", vo);
		System.out.println("가져옴 memberVO : " + memberVO);
		return memberVO;
	}




	
	



}
