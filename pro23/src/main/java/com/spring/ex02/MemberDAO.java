package com.spring.ex02;

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

	public String  selectName() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		String name = session.selectOne("mapper.member.selectName");
		// selectOne()메서드로 인자로 지정한 SQL문을 실행한 후 한 개의 데이터(문자열)를 반환
		// selectOne() 메서드는 주어진 SQL 문을 실행하여 결과를 단일 객체로 반환
		// 반환되는 객체의 타입은 T로 지정되어 있으며, 실제로 조회된 레코드를 매핑한 객체
		// T의 타입은 매퍼 파일에서 resultType 속성이나 resultMap을 통해 명시적으로 지정
		return name;
	} 
		
	public int  selectPwd() {
		sqlMapper = getInstance();
		SqlSession session = sqlMapper.openSession();
		int pwd = session.selectOne("mapper.member.selectPwd");
		// selectOne()메서드로 인자로 지정한 SQL문을 실행한 후 한 개의 데이터(정수)를 반환
		return pwd;
	}
	
}
