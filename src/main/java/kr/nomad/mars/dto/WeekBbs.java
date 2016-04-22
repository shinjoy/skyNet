package kr.nomad.mars.dto;

public class WeekBbs {
	
	int bbsSeq = 0;
	String bbsType = "1";
	String bbsType2 = "1";
	int projectSeq = 0;
	String bbsTitle = "";
	int answerStatus = 0;
	String bbsContents = "";
	int sendSms = 0;
	String userId = "";
	String bbsRegDate = "";
	String grade="";
	int commentCount=0;
	
	///View
	String userName="";
	String companyName="";
	String bbsTypeTxt="";
	String statusTxt="";
	public static String BBS_TYPE_REQUIRE="1";
	public static String BBS_TYPE_BUG="2";
	public static int BBS_STATUS=0;
	public static int BBS_STATUS_ING=1;
	public static int BBS_STATUS_DROP=2;
	public static int BBS_STATUS_FINISH=3;
	String userPhone="";
	
	
	
	
	public String getUserPhone() {
		return userPhone;
	}
	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}
	public String getStatusTxt() {
		String txt="";
		if(this.answerStatus==BBS_STATUS){
			txt="접수";
		}
		if(this.answerStatus==BBS_STATUS_ING){
			txt="진행";
		}
		if(this.answerStatus==BBS_STATUS_DROP){
			txt="보류";
		}
		if(this.answerStatus==BBS_STATUS_FINISH){
			txt="완료";
		}
		
		return txt;
	}
	public String getBbsTypeTxt() {
		String txt="";
		if(this.bbsType.equals(BBS_TYPE_REQUIRE)){
			txt="요청";
		}
		if(this.bbsType.equals(BBS_TYPE_BUG)){
			txt="버그";
		}
		return txt;
	}
	public int getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	public String getGrade() {
		return grade;
	}
	public void setGrade(String grade) {
		this.grade = grade;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getBbsSeq() {
		return bbsSeq;
	}
	public void setBbsSeq(int bbsSeq) {
		this.bbsSeq = bbsSeq;
	}
	public String getBbsType() {
		return bbsType;
	}
	public void setBbsType(String bbsType) {
		this.bbsType = bbsType;
	}
	public String getBbsType2() {
		return bbsType2;
	}
	public void setBbsType2(String bbsType2) {
		this.bbsType2 = bbsType2;
	}
	public int getProjectSeq() {
		return projectSeq;
	}
	public void setProjectSeq(int projectSeq) {
		this.projectSeq = projectSeq;
	}
	
	public String getBbsTitle() {
		return bbsTitle;
	}
	public void setBbsTitle(String bbsTitle) {
		this.bbsTitle = bbsTitle;
	}
	public int getAnswerStatus() {
		return answerStatus;
	}
	public void setAnswerStatus(int answerStatus) {
		this.answerStatus = answerStatus;
	}
	public String getBbsContents() {
		return bbsContents;
	}
	public void setBbsContents(String bbsContents) {
		this.bbsContents = bbsContents;
	}
	public int getSendSms() {
		return sendSms;
	}
	public void setSendSms(int sendSms) {
		this.sendSms = sendSms;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getBbsRegDate() {
		return bbsRegDate;
	}
	public void setBbsRegDate(String bbsRegDate) {
		this.bbsRegDate = bbsRegDate;
	}

	

}
