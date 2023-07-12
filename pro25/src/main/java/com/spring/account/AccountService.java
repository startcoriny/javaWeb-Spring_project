package com.spring.account;

import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Transactional(propagation=Propagation.REQUIRED)
// @Transactional을 이용해 AccountService클래스의 모든 메서드에 트랜잭션을 적용
// Spring Framework에서 제공하는 애노테이션을 사용하여 트랜잭션의 동작 방식을 설정하는 것을 의미
// propagation 속성은 트랜잭션의 전파 방식을 지정하는 데 사용
 
 /* Propagation.REQUIRED는 트랜잭션 전파 옵션 중 하나로 
  * 만약 현재 실행 중인 메서드가 이미 트랜잭션 내에서 실행 중인 경우, 해당 트랜잭션을 계속 사용 
  * 즉, 현재 트랜잭션이 존재하는 경우에는 그 트랜잭션에 참여
  * 현재 실행 중인 메서드가 트랜잭션 없이 실행 중인 경우, 새로운 트랜잭션을 시작. 
  * 따라서 메서드 실행 도중에 예외가 발생하면 롤백이 수행됩니다.
  * 
  * Propagation.REQUIRED는
  * 메서드를 호출하는 방식에 따라 트랜잭션을 시작하거나 참여할 수 있으므로, 
  * 메서드 간에 동일한 트랜잭션을 공유하거나 새로운 트랜잭션을 시작
  * */

public class AccountService {
	private AccountDAO accDAO;

	public void setAccDAO(AccountDAO accDAO) {
		// 속성accDAO에 빈을 주입하기 위해 setter를 구현
		this.accDAO = accDAO;
	}

	public void sendMoney() throws Exception {
		// sendMoney()메서드 호출시 accDAO의 두개의 SQL문을 실행
		accDAO.updateBalance1();
		accDAO.updateBalance2();
	}
}


