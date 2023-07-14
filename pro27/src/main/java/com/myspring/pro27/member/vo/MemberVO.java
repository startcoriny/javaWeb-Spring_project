package com.myspring.pro27.member.vo;

import java.sql.Date;

import org.springframework.stereotype.Component;

@Component("memberVO")
// 해당 클래스를 컴포넌트로 등록하고, 컨테이너 내에서 해당 빈을 참조할 때 사용되는 이름을 지정하는 역할
// 컴포넌트 스캔 기능을 위한 애노테이션
// 다른 클래스에서 해당 컴포넌트를 필요로 할 때, 주입(Dependency Injection)을 통해 해당 빈을 가져와 사용
//  다른 클래스에서 MemberVO를 사용하기 위해 @Autowired를 사용하여 주입받을 수 있음

public class MemberVO {
	private String id;
	private String pwd;
	private String name;
	private String email;
	private Date joinDate;

	public MemberVO() {
		
	}

	public MemberVO(String id, String pwd, String name, String email) {
		this.id = id;
		this.pwd = pwd;
		this.name = name;
		this.email = email;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getPwd() {
		return pwd;
	}

	public void setPwd(String pwd) {
		this.pwd = pwd;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public Date getJoinDate() {
		return joinDate;
	}

	public void setJoinDate(Date joinDate) {
		this.joinDate = joinDate;
	}

}
