package kr.nomad.mars.dto;

import kr.nomad.util.T;

public class User {
	
	String userId = "";
	String userPw = "";
	String userName = "";
	String userGroup = "";
	String userPosition = "";
	String userPhone = "";
	int userLevel = 0;
	int companySeq = 0;
	String userEmail = "";
	String userEct = "";
	int userType = 0;
	

	//View
	
	String companyName = "";
	String companyComment = "";
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getCompanyComment() {
		return companyComment;
	}
	public void setCompanyComment(String companyComment) {
		this.companyComment = companyComment;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getUserPw() {
		return userPw;
	}
	public void setUserPw(String userPw) {
		this.userPw = userPw;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public String getUserGroup() {
		return userGroup;
	}
	public void setUserGroup(String userGroup) {
		this.userGroup = userGroup;
	}
	public String getUserPosition() {
		return userPosition;
	}
	public void setUserPosition(String userPosition) {
		this.userPosition = userPosition;
	}
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public int getUserLevel() {
		return userLevel;
	}
	public void setUserLevel(int userLevel) {
		this.userLevel = userLevel;
	}
	
	public int getCompanySeq() {
		return companySeq;
	}
	public void setCompanySeq(int companySeq) {
		this.companySeq = companySeq;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getUserEct() {
		return userEct;
	}
	public void setUserEct(String userEct) {
		this.userEct = userEct;
	}
	public int getUserType() {
		return userType;
	}
	public void setUserType(int userType) {
		this.userType = userType;
	}

	

	
	

}
