package com.spring.ex01;

import java.io.Reader;
import java.util.List;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

public class MemberDAO {
	public static SqlSessionFactory sqlMapper = null;

	private static SqlSessionFactory getInstance() {
		// SqlSessionFactory 인스턴스를 생성하여 반환하는 메서드
		// 처음 호출될 때 한 번만 실행되고, 이후에는 이미 생성된 SqlSessionFactory 인스턴스를 반환
		
		if (sqlMapper == null) {
			
			try {
				String resource = "mybatis/SqlMapConfig.xml";
				// MemberDAO의 각 메서드 호출 시 src/mybatis/SqlMapConfig.xml에서
				// 설정 정보를 읽은 후 데이터 베이스와의 연동 준비
				
				Reader reader = Resources.getResourceAsReader(resource);
				
				sqlMapper = new SqlSessionFactoryBuilder().build(reader);
				// mybatis를 이용하는 sqlMapper 객체를 가져옴
				// mybatis/SqlMapConfig.xml 파일을 읽어와 설정 정보를 기반으로 SqlSessionFactory를 생성
				
				reader.close();
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return sqlMapper;

	}

	public List<MemberVO> selectAllMemberList() {
		sqlMapper = getInstance();
		// SqlSessionFactory 인스턴스를 가져옴
		
		SqlSession session = sqlMapper.openSession();
		System.out.println("open세션에서 받아온 세션은 : "+session);
		// openSession() 메서드는 SqlSessionFactory 인스턴스에서 세션(SqlSession)을 생성하여 반환
		// openSession() 메서드를 통해 SqlSession 객체를 얻음
		// 실제 member.xml의 SQL문을 호출하는데 사용되는 SqlSession객체를 가져옴
		
		List<MemberVO> memlist = null;
		
		memlist = session.selectList("mapper.member.selectAllMemberList");
		// selectList() 메서드는 MyBatis에서 제공하는 SqlSession 인터페이스의 메서드
		// SqlSession 객체를 사용하여 SQL 문을 실행하고, 여러 개의 결과를 리스트 형태로 반환
		// 여러개의 레코드를 조회하므로 selectList()메서드에 실행하고자 하는 SQL문의 id를 인자로 전달.
		
		System.out.println("memlist의 정보 :"+ memlist );
		return memlist;
	}
	
//	 public List<HashMap<String, String>> selectAllMemberList() { 
//		sqlMapper = getInstance(); 
//     	SqlSession session = sqlMapper.openSession();
//		List<HashMap<String, String>> memlist = null; 
//   	memlist = session.selectList("mapper.member.selectAllMemberList"); 
//		return memlist; 
//	 }
	
}
