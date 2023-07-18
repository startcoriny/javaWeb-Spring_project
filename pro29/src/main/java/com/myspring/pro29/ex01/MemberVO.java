package com.myspring.pro29.ex01;


public class MemberVO {
	private String id;
	private String pwd;
	private String name;
	private String email;
	
	public MemberVO() {
		
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
	
	@Override
	public String toString() {
		// 자바의 모든 클래스가 상속받는 java.lang.Object 클래스에 정의된 기본 toString() 메서드를 오버라이드한 것
		// 객체의 속성값을 json형태로 전달되어 출력
		// 회원 속성 정보를 출력
		String info = id+", "+ pwd+", "+ name+", "+ email;
		return info;
	}

}
