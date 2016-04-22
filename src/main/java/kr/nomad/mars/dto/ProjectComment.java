package kr.nomad.mars.dto;

public class ProjectComment {
	
	int commentSeq = 0;
	int projectSeq = 0;
	String designComment = "";
	String iosComment = "";
	String andComment = "";
	String webComment = "";
	String serverComment = "";
	String pcComment = "";
	String regDate = "";
	public int getCommentSeq() {
		return commentSeq;
	}
	public void setCommentSeq(int commentSeq) {
		this.commentSeq = commentSeq;
	}
	public int getProjectSeq() {
		return projectSeq;
	}
	public void setProjectSeq(int projectSeq) {
		this.projectSeq = projectSeq;
	}
	public String getDesignComment() {
		return designComment;
	}
	public void setDesignComment(String designComment) {
		this.designComment = designComment;
	}
	public String getIosComment() {
		return iosComment;
	}
	public void setIosComment(String iosComment) {
		this.iosComment = iosComment;
	}
	public String getAndComment() {
		return andComment;
	}
	public void setAndComment(String andComment) {
		this.andComment = andComment;
	}
	public String getWebComment() {
		return webComment;
	}
	public void setWebComment(String webComment) {
		this.webComment = webComment;
	}
	public String getServerComment() {
		return serverComment;
	}
	public void setServerComment(String serverComment) {
		this.serverComment = serverComment;
	}
	public String getPcComment() {
		return pcComment;
	}
	public void setPcComment(String pcComment) {
		this.pcComment = pcComment;
	}
	public String getRegDate() {
		return regDate;
	}
	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	

}
