package com.spring.account;

import org.apache.ibatis.session.SqlSession;
import org.springframework.dao.DataAccessException;

public class AccountDAO {
	private SqlSession sqlSession;

	public void setSqlSession(SqlSession sqlSession) {
		// 속성 sqlSession에 빈을 주입하기 위해 setter를 구현
		this.sqlSession = sqlSession;
	}

	public void updateBalance1() throws DataAccessException {
		sqlSession.update("mapper.account.updateBalance1");
	}
	
	public void updateBalance2() throws DataAccessException {
		sqlSession.update("mapper.account.updateBalance2");
	}
	

}
