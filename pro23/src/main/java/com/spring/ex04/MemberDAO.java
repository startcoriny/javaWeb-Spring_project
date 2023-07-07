package com.spring.ex04;

import java.io.Reader;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import com.spring.ex01.MemberVO;

public class MemberDAO {
	public static SqlSessionFactory sqlMapper = null;

	private static SqlSessionFactory getInstance() {
		if (sqlMapper == null) {
			try {
				String resource = "mybatis/SqlMapConfig.xml";
				Reader reader = Resources.getResourceAsReader(resource);
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				reader.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlMapper;
	}

	
	
	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memlist = null;
		memlist = session.selectList("mapper.member.selectAllMemberList");
		return memlist;
	}

	
	
	public List<MemberVO> selectMemberById(String id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> memberVO = session.selectList("mapper.member.selectMemberById", id);
		System.out.println("가져옴 memberVO : " + memberVO);
		return memberVO;
	}

	
	
	public List<MemberVO> selectMemberByPwd(String pwd) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> membersList = null;
		membersList = session.selectList("mapper.member.selectMemberByPwd", pwd);
		System.out.println(membersList);
		return membersList;
	}
	
	
	
	public List<MemberVO> selectMemberByname(String name) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> membersList = null;
		membersList = session.selectList("mapper.member.selectMemberByname", name);
		System.out.println(membersList);
		return membersList;
	}
	
	
	
	public List<MemberVO> selectMemberByemail(String email) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		List<MemberVO> membersList = null;
		membersList = session.selectList("mapper.member.selectMemberByemail", email);
		System.out.println(membersList);
		return membersList;
	}

	
	
	public int insertMember(MemberVO memberVO) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		result = session.insert("mapper.member.insertMember", memberVO);
		System.out.println("result결과값 : "+result);
		
		session.commit();
		return result;
	}
	
	
	
	public int insertMember2(Map<String,String> memberMap){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        int result= result=session.insert("mapper.member.insertMember2",memberMap);
        // 메서드로 전달된 HashMap을 sql문으로 다시 전달
        
        System.out.println("hashmap전달 result결과값 : "+result);
        session.commit();	
        return result;		
	}

	
	
	public int updateMember(MemberVO memberVO) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = session.update("mapper.member.updateMember", memberVO);
		// update문 호출시 SqlSession의 update()메서드를 이용.
		// SQL 매퍼 파일에서 정의된 UPDATE 문을 실행하고, 업데이트된 행의 수를 반환
		// session.update() 메서드는 실행된 UPDATE 문의 결과로 영향을 받은 행(row)의 수를 반환
		System.out.println("update실행문 result결과값 : "+result);
		session.commit();
		return result;
	}   

	
	
    public int deleteMember(String id) {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int result = 0;
		result = session.delete("mapper.member.deleteMember", id);
		// SqlSession의 delete()메서드 이용.
		// session.delete() 메서드는 실행된 DELETE 문의 결과로 영향을 받은 행(row)의 수를 반환
		// 이를 통해 삭제가 성공적으로 이루어졌는지를 확인
		
		System.out.println("delete실행문 result결과값 : "+result);
		session.commit();
		// 커밋을 반드시 해야하는 이유 - 트랜잭션 관리, 성능 개선, 에러 처리 등의 이유로 변경 사항을 데이터베이스에 커밋하는 과정이 필요
		/*
		 * 1. 트랜잭션 관리
		 *	  -  MyBatis는 기본적으로 자동 커밋 모드(auto-commit mode)가 아닌 
		 *		 수동 커밋 모드(manual-commit mode)를 사용
		 * 	  -  여러 개의 SQL 문장을 하나의 트랜잭션으로 묶어 처리할 수 있도록 함.
		 * 
		 * 	  -  session.commit()을 호출하여 트랜잭션을 커밋하면 변경된 데이터가 
		 * 		 영구적으로 데이터베이스에 적용되고, 롤백할 수 없게 됨.
		 * 
		 * 	  -  트랜잭션의 일관성과 안정성을 위해 커밋을 명시적으로 수행
		 * 
		 * 2. 성능 개선
		 * 	  -  커밋은 일반적으로 데이터베이스에 대한 작업을 일괄적으로 처리하는 시점을 나타냄 
		 * 	  -  변경 사항을 데이터베이스에 커밋하면, 
		 * 		 변경된 데이터가 데이터베이스에 실제로 적용되어 데이터베이스의 상태가 업데이트됨.
		 * 	  -  데이터베이스에 대한 I/O 작업이 최소화되고, 성능 향상을 기대
		 * 	  -  다른 세션 또는 트랜잭션에서 해당 데이터를 참조할 때 일관성 있는 데이터를 얻음.
		 * 
		 * 3. 에러 처리
		 * 	  -  커밋 시에 발생하는 에러는 데이터베이스 작업 중 발생하는 문제를 확인할 수 있는 중요한 정보
		 * 	  -  커밋 시 에러가 발생하면 해당 에러를 적절하게 처리하고 로그 등을 통해 문제를 추적
		 * */		
		
		
		
		return result;
    } 
    
    
    
    public List<MemberVO>  searchMember(MemberVO  memberVO){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        List list=session.selectList("mapper.member.searchMember",memberVO);
        // SQL 매퍼 파일에서 정의된 SELECT 문을 실행하고, 여러 개의 결과를 리스트(List) 형태로 반환
        // 실행된 SELECT 문의 결과로 여러 개의 행(row)을 반환
        // 반환되는 결과는 List 형태로, 각 행은 매핑된 객체 또는 맵(Map)으로 표현
        // 회원 검색창에서 전달된 이름과나이 값을 memberVO에 설정하여 sql문으로 전달.
        return list;		
    } 

    
    
    public List<MemberVO>  foreachSelect(List nameList){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        List list=session.selectList("mapper.member.foreachSelect",nameList);
        // 검색이름이 저장된 nameList를 SQL문으로 전달.
        System.out.println("받아온 list목록 : " + list);
        return list;		
    }
    
    
    
    public int  foreachInsert(List memList){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        int result = session.insert("mapper.member.foreachInsert",memList);
        session.commit();
        return result ;		
     }
    
    
    
    public List<MemberVO>  selectLike(String name){
        sqlMapper=getInstance();
        SqlSession session=sqlMapper.openSession();
        List list=session.selectList("mapper.member.selectLike",name);
        return list;		
    }
    
    public List<MemberVO>  selectallLike(String name){
    	sqlMapper=getInstance();
    	SqlSession session=sqlMapper.openSession();
    	List list=session.selectList("mapper.member.selectallLike",name);
    	return list;		
    }
	
}
