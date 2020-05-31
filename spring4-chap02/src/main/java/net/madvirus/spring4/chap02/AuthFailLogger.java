package net.madvirus.spring4.chap02;

public class AuthFailLogger {

	private int threshold;		//3으로 주입
	private int failCounts;
	
	public void insertBadPw(String userId, String inputPw) {
		System.out.printf("AuthFail[type=badpw, userid=%s, pw=%s]\n", userId, inputPw);
		failCounts++;
		if(threshold > 0 && failCounts>threshold) {
			notifyTooManyFail();
			failCounts=0;
		}
	}
	
	public void notifyTooManyFail() {
		System.out.println("너무 많은 로그인 시도 실패");
	}

	public void setThreshold(int threshold) {
		this.threshold = threshold;
	}
	
}
