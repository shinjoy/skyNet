package kr.nomad.mars.dto;

public class FormData {
	
	int formSeq = 0;
	String formType = "";
	String formTitle = "";
	String userId = "";
	String formRegDate = "";
	String formFileName = "";
	
	
	//View
	String userName="";
	
	
	
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getFormSeq() {
		return formSeq;
	}
	public void setFormSeq(int formSeq) {
		this.formSeq = formSeq;
	}
	public String getFormType() {
		return formType;
	}
	public void setFormType(String formType) {
		this.formType = formType;
	}
	public String getFormTitle() {
		return formTitle;
	}
	public void setFormTitle(String formTitle) {
		this.formTitle = formTitle;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getFormRegDate() {
		return formRegDate;
	}
	public void setFormRegDate(String formRegDate) {
		this.formRegDate = formRegDate;
	}
	public String getFormFileName() {
		return formFileName;
	}
	public void setFormFileName(String formFileName) {
		this.formFileName = formFileName;
	}

	
	

}
