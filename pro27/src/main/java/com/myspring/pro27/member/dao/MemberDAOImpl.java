package com.myspring.pro27.member.dao;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Repository;

import com.myspring.pro27.member.vo.MemberVO;

@Repository("memberDAO")
// @Repository 애노테이션은 데이터 액세스 계층(DAO)의 구현 클래스에 지정
// id가 memberDAO인 빈을 자동 주입

public class MemberDAOImpl implements MemberDAO {
	
	@Autowired
	private SqlSession sqlSession;
	// XML설정 파일에서 생성한 id가 sqlSession인 빈을 자동 주입
	
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
	
	
	
	@Override
	public int deleteMember(String id) throws DataAccessException {
		
		int result =  sqlSession.delete("mapper.member.deleteMember", id);
		// 주입된 sqlSession빈으로 delete() 메서드를 호출하면서 SQL문의 id와 회원 ID를 전달.
		
		return result;
		
	}

	
	
	@Override
	public int updateMember(MemberVO memberVO) {
		
		int result = sqlSession.update("mapper.member.updateMember", memberVO);
		
		System.out.println("update실행문 result결과값 : "+result);
		
		return result;
	}   


	@Override
	public List<MemberVO> searchMembers(MemberVO vo) {

		List<MemberVO> memberVO = sqlSession.selectList("mapper.member.searchMembers", vo);
		System.out.println("가져옴 memberVO : " + memberVO);
		return memberVO;
	}



	@Override
	public MemberVO loginById(MemberVO memberVO) {
		MemberVO vo = sqlSession.selectOne("mapper.member.loginById",memberVO);
		// 메서드 호출 시 전달된 memberVO를 SQL문으로 전달해 ID와 비밀번호에 대한 회원 정보를 MemberVO객체로 반환
		return vo;
	}




	
	



}
