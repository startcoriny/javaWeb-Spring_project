package com.spring.member.service;

import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.spring.member.dao.MemberDAO;
import com.spring.member.vo.MemberVO;

/*@Transactional(propagation=Propagation.REQUIRED) */
public class MemberServiceImpl  implements MemberService{
	
	   private MemberDAO memberDAO;
	   
	   
	   
	   public void setMemberDAO(MemberDAO memberDAO){
		 // 속성memberDAO에 memberDAO빈을 주입하기 위해 setter를 구현
		   
	      this.memberDAO = memberDAO;
	      
	   }

	   
	   
	   @Override
	   public List listMembers() throws DataAccessException {
		   
	      List membersList = null;
	      
	      membersList = memberDAO.selectAllMemberList();
	      
	      return membersList;
	      
	   }

	   
	   
	   @Override
	   public int addMember(MemberVO memberVO) throws DataAccessException {
		   
	     return memberDAO.insertMember(memberVO);
	     
	   }


	   
	   @Override
	   public int removeMember(String id) throws DataAccessException {
		   
	      return memberDAO.deleteMember(id);
	      
	   }



	@Override
	public int updateMember(MemberVO memberVO) throws DataAccessException {
		
		return memberDAO.updateMember(memberVO);
	}





	@Override
	public List<MemberVO> searchMembers(MemberVO vo) throws DataAccessException {
		List list = memberDAO.searchMembers(vo);
		return list;
	}




}
