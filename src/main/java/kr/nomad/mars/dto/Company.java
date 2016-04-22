package kr.nomad.mars.dto;

public class Company {
	
	int companySeq = 0;
	String companyName = "";
	String companyComment = "";
	public int getCompanySeq() {
		return companySeq;
	}
	public void setCompanySeq(int companySeq) {
		this.companySeq = companySeq;
	}
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

	

}
