package kr.nomad.mars.dto;

public class AdminBbsComment {
	
	int bbsCommentSeq = 0;
	int bbsSeq = 0;
	String userId = "";
	String bbsContents = "";
	String files = "";
	String linkUrl = "";
	String regDate = "";
	int rLevel = 0;
	int rCommentSeq = 0;
	String rRegDate = "";
	int rAnswerStatus=0;
	String rAnswerStatusTxt="";
	//View
	String userName="";
	String comapanyName="";
	
	
	
	
	
	public String getrAnswerStatusTxt() {
		String txt="";
		if(rAnswerStatus==1){
			txt="진행";
		}
		if(rAnswerStatus==2){
			txt="보류";
		}
		if(rAnswerStatus==3){
			txt="완료";
		}
		return txt;
	}
	public int getrAnswerStatus() {
		return rAnswerStatus;
	}
	public void setrAnswerStatus(int rAnswerStatus) {
		this.rAnswerStatus = rAnswerStatus;
	}
	public String getComapanyName() {
		return comapanyName;
	}
	public void setComapanyName(String comapanyName) {
		this.comapanyName = comapanyName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getBbsCommentSeq() {
		return bbsCommentSeq;
	}
	public void setBbsCommentSeq(int bbsCommentSeq) {
		this.bbsCommentSeq = bbsCommentSeq;
	}
	public int getBbsSeq() {
		return bbsSeq;
	}
	public void setBbsSeq(int bbsSeq) {
		this.bbsSeq = bbsSeq;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBbsContents() {
		return bbsContents;
	}
	public void setBbsContents(String bbsContents) {
		this.bbsContents = bbsContents;
	}
	public String getFiles() {
		return files;
	}
	public void setFiles(String files) {
		this.files = files;
	}
	public String getLinkUrl() {
		return linkUrl;
	}
	public void setLinkUrl(String linkUrl) {
		this.linkUrl = linkUrl;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}
	public int getrLevel() {
		return rLevel;
	}
	public void setrLevel(int rLevel) {
		this.rLevel = rLevel;
	}
	public int getrCommentSeq() {
		return rCommentSeq;
	}
	public void setrCommentSeq(int rCommentSeq) {
		this.rCommentSeq = rCommentSeq;
	}
	public String getrRegDate() {
		return rRegDate;
	}
	public void setrRegDate(String rRegDate) {
		this.rRegDate = rRegDate;
	}
	
	

}
