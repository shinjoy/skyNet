package kr.nomad.mars.dto;

public class Todo {
	
	int todoSeq = 0;
	int projectSeq = 0;
	String module = "";
	String process = "";
	String todo = "";
	String todoPart = "";
	String todoStartday = "";
	String todoEndday = "";
	String todoFinishday = "";
	String userId = "";
	String todoStatus = "";
	String todoComment = "";
	String todoRegDate = "";
	String todoType="";
	String projectName ="";
	public static String TODO_BE_DUE="0";//진행예정
	public static String TODO_ING="1";//진행중
	public static String TODO_FINISH="2";//진행완료
	public static String TODO_POSTPHONE="3";//보류
	public static String TODO_DROP="4";//취소

	String todoStatusTxt="";
	String companyName="";
	
	
	
	
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getProjectName() {
		return projectName;
	}
	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}
	public String getTodoStatusTxt() {
		String txt="";
		if(this.todoStatus.equals("0")){
			txt="진행예정";
		}
		if(this.todoStatus.equals("1")){
			txt="진행중";	
		}
		if(this.todoStatus.equals("2")){
			txt="진행완료";
		}
		if(this.todoStatus.equals("3")){
			txt="보류";
		}
		if(this.todoStatus.equals("4")){
			txt="취소";
		}
		return txt;
	}
	public String getTodoType() {
		return todoType;
	}
	public void setTodoType(String todoType) {
		this.todoType = todoType;
	}
	public int getTodoSeq() {
		return todoSeq;
	}
	public void setTodoSeq(int todoSeq) {
		this.todoSeq = todoSeq;
	}
	public int getProjectSeq() {
		return projectSeq;
	}
	public void setProjectSeq(int projectSeq) {
		this.projectSeq = projectSeq;
	}
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getProcess() {
		return process;
	}
	public void setProcess(String process) {
		this.process = process;
	}
	public String getTodo() {
		return todo;
	}
	public void setTodo(String todo) {
		this.todo = todo;
	}
	public String getTodoPart() {
		return todoPart;
	}
	public void setTodoPart(String todoPart) {
		this.todoPart = todoPart;
	}
	public String getTodoStartday() {
		return todoStartday;
	}
	public void setTodoStartday(String todoStartday) {
		this.todoStartday = todoStartday;
	}
	public String getTodoEndday() {
		return todoEndday;
	}
	public void setTodoEndday(String todoEndday) {
		this.todoEndday = todoEndday;
	}
	public String getTodoFinishday() {
		return todoFinishday;
	}
	public void setTodoFinishday(String todoFinishday) {
		this.todoFinishday = todoFinishday;
	}
	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getTodoStatus() {
		return todoStatus;
	}
	public void setTodoStatus(String todoStatus) {
		this.todoStatus = todoStatus;
	}
	public String getTodoComment() {
		return todoComment;
	}
	public void setTodoComment(String todoComment) {
		this.todoComment = todoComment;
	}
	public String getTodoRegDate() {
		return todoRegDate;
	}
	public void setTodoRegDate(String todoRegDate) {
		this.todoRegDate = todoRegDate;
	}

	
	

}
