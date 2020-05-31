package net.madvirus.spring4.chap02;

public class User {

	private String id;
	private String password;

	//유저 객체 생성시 id,비밀번호를 받아 생성
	public User(String id, String password) {
		this.id = id;
		this.password = password;
	}


	public String getId() {
		return id;
	}

	//파라미터로 받은 비밀번호와 객체의 비밀번호 비교
	public boolean matchPassword(String inputPasswd) {
		return password.equals(inputPasswd);
	}

	//파라미터로 받은 비밀번호를 체크하여 새로운 비밀번호로 변경
	public void changePassword(String oldPassword, String newPassword) {
		if (!matchPassword(oldPassword))
			throw new IllegalArgumentException("illegal password");
		password = newPassword;
	}

}