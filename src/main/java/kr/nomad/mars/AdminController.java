package kr.nomad.mars;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;


import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;

import kr.nomad.mars.dao.BbsCommentDao;
import kr.nomad.mars.dao.BbsDao;
import kr.nomad.mars.dao.BbsFilesDao;
import kr.nomad.mars.dao.CompanyDao;
import kr.nomad.mars.dao.DataDao;
import kr.nomad.mars.dao.FormDataDao;
import kr.nomad.mars.dao.ModuleDao;
import kr.nomad.mars.dao.ProjectAdminDao;
import kr.nomad.mars.dao.ProjectCommentDao;
import kr.nomad.mars.dao.ProjectDao;
import kr.nomad.mars.dao.TodoDao;
import kr.nomad.mars.dao.UserDao;
import kr.nomad.mars.dto.Bbs;
import kr.nomad.mars.dto.BbsComment;
import kr.nomad.mars.dto.BbsFiles;
import kr.nomad.mars.dto.Company;
import kr.nomad.mars.dto.Data;
import kr.nomad.mars.dto.FormData;
import kr.nomad.mars.dto.Module;
import kr.nomad.mars.dto.Project;
import kr.nomad.mars.dto.ProjectAdmin;
import kr.nomad.mars.dto.ProjectComment;
import kr.nomad.mars.dto.Todo;
import kr.nomad.mars.dto.User;
import kr.nomad.util.F;
import kr.nomad.util.FileMime;
import kr.nomad.util.FileWrite;
import kr.nomad.util.ImageUtil;
import kr.nomad.util.Paging;
import kr.nomad.util.Response;
import kr.nomad.util.T;
import kr.nomad.util.encrypt.CryptoNew;
import kr.nomad.util.encrypt.CryptoSeedData;
import kr.nomad.util.file.UniqFileRenamePolicy;
import net.sf.json.JSONObject;


@Controller
public class AdminController {
	
	@Autowired UserDao userDao;
	@Autowired ProjectDao projectDao;
	@Autowired BbsDao bbsDao;
	@Autowired BbsCommentDao bbsCommentDao;
	@Autowired BbsFilesDao bbsfilesDao;
	@Autowired DataDao dataDao;
	@Autowired ProjectAdminDao projectAdminDao;
	@Autowired CompanyDao companyDao;
	@Autowired FormDataDao formDataDao;
	@Autowired ModuleDao moduleDao;
	@Autowired TodoDao todoDao;
	@Autowired ProjectCommentDao projectCommentDao;
	// 페이지당 아이템 갯수
	@Value("#{config['page.itemCountPerPage']}")
	int ITEM_COUNT_PER_PAGE;

	// 페이징당 페이지 갯수
	@Value("#{config['page.pageCountPerPaging']}")
	int PAGE_COUNT_PER_PAGING;

	// 파일 루트
	@Value("#{config['file.root']}")
	String FILE_ROOT;

	String FILE_PATH = "";
	String FILE_LOCAL_PATH = "";

	// 파일 최대크기(Mb)
	@Value("#{config['file.maxSize']}")
	int FILE_MAX_SIZE;
	
/*	
	@RequestMapping("/api_view.go")
	public String login(
			HttpServletRequest request,
			
			HttpServletResponse res, Model model	
	) {
	
		return "api_view";
	}
	*/

	@RequestMapping("/admin/menu.go")
	public String login(
			HttpServletRequest request,
			
			HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List projectlist = projectDao.getProjectListNotPaging(0,user.getUserId(),user.getUserLevel());
		map.put("projectList", projectlist);
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}


	// 프로젝트 리스트
	@RequestMapping("/admin/project_list.go")
	public String projectList(HttpServletRequest request,
	
		@RequestParam(value = "page", required = false, defaultValue = "1") int page,
		@RequestParam(value = "sort", required = false, defaultValue = "") String sort,
		@RequestParam(value = "colName", required = false, defaultValue = "") String colName,
		@RequestParam(value = "startDay", required = false, defaultValue = "") String startDay,
		@RequestParam(value = "endDay", required = false, defaultValue = "") String endDay,
		@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
		@RequestParam(value = "status", required = false, defaultValue = "") String status,
		@RequestParam(value = "companySeq", required = false, defaultValue = "0") int companySeq,

	HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List<Project> list = null;
		int count = 0;
		if (user != null) {

			if (user.getUserType() == 1) { // 내부직원
				list = projectDao.getProjectList(status, companySeq, sort, colName, startDay, endDay, keyword, page,
						ITEM_COUNT_PER_PAGE);
				count = projectDao.getProjectCount(status, companySeq, startDay, endDay, keyword);

			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
			String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE, PAGE_COUNT_PER_PAGING,"projectAdmin.getList","dataForm");

			map.put("paging", paging);
			map.put("currentPage", page);
			map.put("list", list);
			map.put("result", true);

		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}
	
	// 업체 리스트
	@RequestMapping("/admin/company_list.go")
		public String CompanyList(
			HttpServletRequest request,
			@RequestParam(value = "page", required = false, defaultValue="1") int page,
			@RequestParam(value = "colName", required = false, defaultValue="") String colName,
			@RequestParam(value = "sort", required = false, defaultValue="") String sort,
			@RequestParam(value = "keyword", required = false, defaultValue="") String keyword,
			HttpServletResponse res, Model model,HttpSession ss	
	) {
				Map<String, Object> map = new HashMap<String, Object>();
				
				User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
				
				List<Company>list=new ArrayList<Company>();
		
				int count =0;
				if(user!=null){
					
					if(user.getUserType()==1){ //내부직원
						
						list= companyDao.getCompanyList(keyword,colName,sort,page, ITEM_COUNT_PER_PAGE);
						count = companyDao.getCompanyCount(keyword);
					}else{// 이외
						map.put("result", false);
						map.put("message", "권한이 없습니다.");
					}
					
					String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE, PAGE_COUNT_PER_PAGING,"company.getList","dataForm");
					map.put("paging", paging);
					map.put("list", list);
					map.put("count", count);
					map.put("currentPage", page);
					map.put("ITEM_COUNT_PER_PAGE", ITEM_COUNT_PER_PAGE);
					map.put("result", true);
				
					
				}else{
					map.put("result", false);
					map.put("message", "해당 ID 정보가 존재하지 않습니다.");
				}
				
				JSONObject jsonObject = JSONObject.fromObject(map);
				Response.responseWrite(res, jsonObject);
				return null;
				
			}
		
	// 업체 상세화면
	@RequestMapping("/admin/company_view.go")
		public String CompanyView(
			HttpServletRequest request,
			
			@RequestParam(value = "page", required = false, defaultValue="1") int page,
			@RequestParam(value = "companySeq", required = false, defaultValue="1") int companySeq,
			@RequestParam(value = "colName", required = false, defaultValue="") String colName,
			@RequestParam(value = "sort", required = false, defaultValue="") String sort,
		
			HttpServletResponse res, Model model,HttpSession ss	
		) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			List<User>list=null;
			List<Project>prolist=null;
			Company cp = null;
			int count =0;
			if(user!=null){
				
				if(user.getUserType()==1){ //내부직원
					
					cp=companyDao.getCompany(companySeq);
					list= userDao.getUserList(colName,sort,companySeq,page, ITEM_COUNT_PER_PAGE); //업체 담당자 리스트
					count=userDao.getUserCount(companySeq);
					prolist = projectDao.getProjectListNotPaging(companySeq,"",0);
					
					
					
				}else{// 이외
					map.put("result", false);
					map.put("message", "권한이 없습니다.");
				}
				String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING,"company.getView","dataForm");
				
				map.put("paging", paging);
				map.put("currentPage", page);
				map.put("company", cp);
				map.put("list", list);
				map.put("prolist", prolist);
				map.put("result", true);
			
				
			}else{
				map.put("result", false);
				map.put("message", "해당 ID 정보가 존재하지 않습니다.");
			}
			
			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;
			
		}
	
	// 업체 담당/직원  등록
	@RequestMapping("/admin/user_edit_do.go")
	public String CompanyUserEdit(HttpServletRequest request,
			@RequestParam(value = "type", required = false, defaultValue="0") int type,
			User inputuser,
			HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		
		int count = 0;
		if (user != null) {

			if (user.getUserType() == 1) { // 내부직원
				
				
				User uu = userDao.getUser(inputuser.getUserId());
				
				if(type==1){ //수정
					userDao.updateUser(inputuser);
					map.put("result", true);
					map.put("message", "수정되었습니다.");
				}else{//등록
					int Idcheck= userDao.correctId(inputuser.getUserId());
					if(Idcheck>0){
						map.put("result", false);
						map.put("message", "중복 된 아이디가 있습니다.");
					}else{

						String enPw = CryptoNew.encrypt(inputuser.getUserPw());
						inputuser.setUserPw(enPw);
						userDao.addUser(inputuser);
						map.put("result", true);
						map.put("message", "등록되었습니다.");
					}
				}
				
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}
	
	// 업체 담당/직원  삭제
	@RequestMapping("/admin/user_delete_do.go")
	public String CompanyUserDelete(HttpServletRequest request,
			@RequestParam(value = "userId", required = false, defaultValue = "") String userId,
		
		
			HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		
		int count = 0;
		if (user != null) {

			if (user.getUserType() == 1) { // 내부직원
				
				
				User uu = userDao.getUser(userId);
				
				if(uu.getUserType()==1){
					//내부직원일때 상황 별 동작 .
					
					
				}
				
				userDao.deleteUser(userId);
				
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}
	
	// 업체 등록
	@RequestMapping("/admin/company_edit_do.go")
	public String CompanyEditDo(HttpServletRequest request,
			Company cc,
			HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		
		int count = 0;
		if (user != null) {

			if (user.getUserType() == 1) { // 내부직원
				
				
				if(cc.getCompanySeq()==0){//등록
					companyDao.addCompany(cc);
					map.put("result", true);
					map.put("message", "등록되었습니다.");
					
				}else{//수정
					companyDao.updateCompany(cc);
					map.put("result", true);
					map.put("message", "수정되었습니다.");
				}
				
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}
	
	// 업체   삭제
	@RequestMapping("/admin/company_delete_do.go")
	public String CompanyDelete(HttpServletRequest request,
		
			@RequestParam(value = "companySeq", required = false, defaultValue = "0") int companySeq,
		
			HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		
		int count = 0;
		if (user != null) {

			if (user.getUserType() == 1 && user.getUserLevel()==1) { // 내부직원
				companyDao.deleteCompany(companySeq);
				//삭제시 필요한 내용.
				
				
				map.put("result", true);
				map.put("message", "삭제되었습니다.");
				
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}
	
	///파일 업로드
	

	@RequestMapping("/m/photo_upload.go")
	public String proUploadController(
			HttpServletRequest req, HttpServletResponse res, Model model
	) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		

		String FILE_PATH = "/files/temp/";
		String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
		//String userId = "";
		String photo = "";
		String fileName = "";
		String photoPre = "";
		String path = "";
		String repath ="";
		String org = "";
		
		int fileSize = FILE_MAX_SIZE * 1024 * 1024;

		try {
			req.setCharacterEncoding("utf-8");

			File file = null;
			try {
				MultipartRequest multi = new MultipartRequest(req, FILE_LOCAL_PATH, fileSize, "UTF-8", new UniqFileRenamePolicy());

				// 폼에서 입력한 값을 가져옴
				String userId="";
				path = F.nullCheck(multi.getParameter("path"), "");
			
				Enumeration files = multi.getFileNames();
				while (files.hasMoreElements()) {
					String elementName = (String) files.nextElement();
					file = multi.getFile(elementName);
					if (file != null) {
						org = file.getName();
						photo =org;
						
					}
				}
				
				fileName = photo;
				String fullpath ="";
				
			
				fullpath =FILE_ROOT +"/files/"+ path +"/";
				repath = "/files/"+ path +"/";
				
				
				
				String FILE_RESIZE_PATH = FILE_ROOT +repath;
			
			
				File copyFolder = new File(fullpath);
				if (!copyFolder.exists()) {
						copyFolder.mkdirs();
				}
				
				
				// 파일 복사
				FileInputStream fis = new FileInputStream(FILE_LOCAL_PATH + fileName);
				FileOutputStream fos = new FileOutputStream(fullpath+"/"+fileName);
				int data = 0;
				while((data=fis.read())!=-1) {
					fos.write(data);
				}
				fis.close();
				fos.close();
				
				
				
				File newFile = new File(FILE_RESIZE_PATH + fileName);
				
				// 썸네일이미지 저장
				File thumbFile = new File(FILE_RESIZE_PATH+"thumb/"+fileName);
				if (!thumbFile.exists()) {
					thumbFile.mkdirs();
				}
				try {
					ImageUtil.resize(newFile, thumbFile, 200, 0, 0);
					result = true;
				} catch (IOException e) {
					e.printStackTrace();
				}
				
				//복사한뒤 원본파일을 삭제함
				file.delete();
				map.put("photo", fileName);
				map.put("path", repath);
				map.put("result", true);
				map.put("message", "사진이 등록되었습니다.");
			} catch (Exception e) {
				e.getMessage();
			}
			
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "fail! \n"+e.getMessage());
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);

		Gson gson = new Gson();
		String outputstr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputstr);

		return null;

	}
// 파일삭제
		@RequestMapping("/m/file_delete.go")
		public String file_deleteController(
				@RequestParam(value = "photo", required = false, defaultValue = "") String photo,
				Model model, HttpServletResponse res) {

			Map<String, Object> map = new HashMap<String, Object>();
			
			filedelNow(photo);
			
			map.put("result", true);
			
			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			
			return null;
	}
		// Todo 등록/수정
		@RequestMapping("/admin/project_todo_edit.go")
		public String ProjectTodoEdit(HttpServletRequest request,
			Todo todo,
			HttpServletResponse res, Model model,HttpSession ss) {
			Map<String, Object> map = new HashMap<String, Object>();

			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			
			int count = 0;
			if (user != null) {

				if (user.getUserType() == 1 /*&& user.getUserLevel()==1*/) { // 내부직원
					
					if(todo.getTodoSeq()==0){
						todoDao.addTodo(todo);
						
					}else{
						todoDao.updateTodo(todo);
						System.out.println(todo.getTodoComment());
					}
					
					
					map.put("result", true);
					map.put("message", "저장되었습니다.");
				} else {// 이외
					map.put("result", false);
					map.put("message", "권한이 없습니다.");
				}
			} else {
				map.put("result", false);
				map.put("message", "해당 ID 정보가 존재하지 않습니다.");
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;

		}	
		// Todo 삭제
		@RequestMapping("/admin/project_todo_delete.go")
		public String ProjectTodoDelete(HttpServletRequest request,
				@RequestParam(value = "todoSeq", required = false, defaultValue = "0") int todoSeq,
			HttpServletResponse res, Model model,HttpSession ss) {
			Map<String, Object> map = new HashMap<String, Object>();

			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			
			int count = 0;
			if (user != null) {

				if (user.getUserType() == 1 /*&& user.getUserLevel()==1*/) { // 내부직원
					
					
					todoDao.deleteTodo(todoSeq);
					
					map.put("result", true);
					map.put("message", "삭제되었습니다.");
				} else {// 이외
					map.put("result", false);
					map.put("message", "권한이 없습니다.");
				}
			} else {
				map.put("result", false);
				map.put("message", "해당 ID 정보가 존재하지 않습니다.");
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;

		}	

		// 프로젝트 등록/수정
		@RequestMapping("/admin/project_edit.go")
		public String ProjectEdit(HttpServletRequest request,
				@RequestParam(value = "projectSeq", required = false, defaultValue = "0") int projectSeq,
				@RequestParam(value = "companySeq", required = false, defaultValue = "0") int companySeq,
				@RequestParam(value = "page", required = false, defaultValue = "1") int page,
			HttpServletResponse res, Model model,HttpSession ss) {
			Map<String, Object> map = new HashMap<String, Object>();

			User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
			
			int count = 0;
			if (user != null) {

				if (user.getUserType() == 1) { // 내부직원
					
					Project pro =projectDao.getProject(projectSeq);
					List<ProjectAdmin> adminList = projectAdminDao.getProjectAdminList(projectSeq);
					List<User>userList=userDao.getUserAdminList();
					List<Company>companyList =companyDao.getCompanyList();
					List<Todo>todolist=todoDao.getTodoList(projectSeq, page, ITEM_COUNT_PER_PAGE);
					count = todoDao.getTodoCount(projectSeq);
					
					String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING,"projectAdmin.projectEdit","dataForm");
					
					map.put("paging", paging);
					map.put("currentPage", page);
					
					
					map.put("result", true);
					map.put("project", pro);
					map.put("companySeq", companySeq);
					map.put("adminList", adminList);
					map.put("todoList", todolist);
					map.put("userList", userList);
					map.put("companyList", companyList);
				} else {// 이외
					map.put("result", false);
					map.put("message", "권한이 없습니다.");
				}
			} else {
				map.put("result", false);
				map.put("message", "해당 ID 정보가 존재하지 않습니다.");
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;

		}		
	
	
	// 프로젝트 등록/수정
	@RequestMapping("/admin/project_edit_do.go")
	public String ProjectEditDo(HttpServletRequest request,
		Project pr,
		HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
		
		int count = 0;
		int seq=0;
		if (user != null) {

			if (user.getUserType() == 1 /*&& user.getUserLevel()==1*/) { // 내부직원
				if(pr.getProjectSeq()==0){
					
					seq=projectDao.addProject(pr);
					
					
				}else{
					
					Project beforepr= projectDao.getProject(pr.getProjectSeq());
					if(!beforepr.getProjectFileName().equals("")&&!beforepr.getProjectFileName().equals(pr.getProjectFileName())){
						File file = new File(FILE_ROOT + beforepr.getProjectFileName());
						file.delete();
					}
					projectDao.updateProject(pr);
					seq=pr.getProjectSeq();
				}
				map.put("seq", seq);
				map.put("result", true);
				map.put("message", "저장되었습니다.");
				
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}
	// 프로젝트 상세보기
	@RequestMapping("/admin/project_view.go")
	public String ProjectView(HttpServletRequest request,
		
			@RequestParam(value = "projectSeq", required = false, defaultValue = "0") int projectSeq,
		
			HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
		Project pp=null;
		List<ProjectAdmin>list = null;
		int count = 0;
		if (user != null) {

			if (user.getUserType() == 1 ) {
				pp=projectDao.getProject(projectSeq);
				list= projectAdminDao.getProjectAdminList(projectSeq);
				map.put("result", true);
				map.put("project", pp);
				map.put("list", list);
				
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}	
	// 프로젝트 담당 관리자 등록/수정 
	@RequestMapping("/admin/project_admin_edit_do.go")
	public String ProjectAdminEdit(HttpServletRequest request,
			ProjectAdmin pa,
		
			HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
	
	
		if (user != null) {

			if (user.getUserType() == 1 ) {
				
				if(pa.getProjectAdminSeq()==0){
					
					projectAdminDao.addProjectAdmin(pa);
					map.put("result", true);
					map.put("message", "등록되었습니다.");
				
				
					
				}else{
					projectAdminDao.updateProjectAdmin(pa);
					map.put("result", true);
					map.put("message", "수정되었습니다.");
				}
				
				
				
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}	
	
	// 프로젝트  삭제
		@RequestMapping("/admin/project_delete.go")
		public String ProjectDelete(HttpServletRequest request,
				@RequestParam(value = "projectSeq", required = false, defaultValue = "0") int projectSeq,
		HttpServletResponse res, Model model,HttpSession ss) {
			Map<String, Object> map = new HashMap<String, Object>();

			User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
		
		
			if (user != null) {

				if (user.getUserType() == 1 /*&& user.getUserLevel()==1*/ ) {
					
					
						projectDao.deleteProject(projectSeq);
						projectAdminDao.deleteProjectAdminProject(projectSeq);
						map.put("result", true);
						map.put("message", "삭제 되었습니다.");
				} else {// 이외
					map.put("result", false);
					map.put("message", "권한이 없습니다.");
				}
			} else {
				map.put("result", false);
				map.put("message", "해당 ID 정보가 존재하지 않습니다.");
			}

			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;

		}	
	// 프로젝트 담당 관리자 삭제
	@RequestMapping("/admin/project_admin_delete.go")
	public String ProjectAdminDelete(HttpServletRequest request,
			@RequestParam(value = "projectAdminSeq", required = false, defaultValue = "0") int projectAdminSeq,
			
			HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
	
	
		if (user != null) {

			if (user.getUserType() == 1 && user.getUserLevel()==1) {
				
				projectAdminDao.deleteProjectAdmin(projectAdminSeq);
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}	
	
	// 게시판 상태변경
	@RequestMapping("/admin/bbs_status_edit.go")
	public String AdminStatusDelete(HttpServletRequest request,
			@RequestParam(value = "bbsSeq", required = false, defaultValue = "0") int bbsSeq,
			@RequestParam(value = "answerStatus", required = false, defaultValue = "0") int answerStatus,
			@RequestParam(value = "grade", required = false, defaultValue = "0") String grade,
			
			HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
	
	
		if (user != null) {

			if (user.getUserType() == 1) {
				Bbs bbs = bbsDao.getBbs(bbsSeq);
				bbs.setAnswerStatus(answerStatus);
				bbs.setGrade(grade);
				
				bbsDao.updateBbs(bbs);
				
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}	
	// 직원리스트
	@RequestMapping("/admin/admin_list.go")
	public String AdminList(HttpServletRequest request,
			@RequestParam(value = "page", required = false, defaultValue = "0") int page,
			@RequestParam(value = "colName", required = false, defaultValue = "") String colName,
			@RequestParam(value = "sort", required = false, defaultValue = "") String sort,
			@RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
	
		List<User>list = null;
		int count = 0;
		if (user != null) {

			if (user.getUserType() == 1 ) {
				list= userDao.getUserAdminList(keyword,colName ,sort,page, ITEM_COUNT_PER_PAGE);
				count=userDao.getUserAdminCount(keyword);
				String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);
				
				map.put("paging", paging);
				map.put("currentPage", page);
				map.put("result", true);
				map.put("count", count);
				map.put("list", list);
				
				
				
			} else {// 이외
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}	
	
	
	////////////////////////// 서식자료실
	
	//서식자료실 리스트
	@RequestMapping("/admin/form_list.go")
	public String dataRoomList(
		HttpServletRequest request,
	
		@RequestParam(value = "formType", required = false, defaultValue="") String formType,
		//1:기획서 2:체크리스트3:기능목록표4:버그리스트5:디자인시안
		@RequestParam(value = "keyword", required = false, defaultValue="") String keyword,
		@RequestParam(value = "page", required = false, defaultValue="") int page,

		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
		List<FormData>list=null;
		int count =0;
		if(user!=null){
		
			list=formDataDao.getFormDataList(keyword,formType,page, ITEM_COUNT_PER_PAGE);
			count=formDataDao.getFormDataCount(keyword,formType);
		
			
			
			String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);
			
			map.put("paging", paging);
			map.put("currentPage", page);
			map.put("list", list);

			map.put("result", true);
			map.put("message", user.getUserId());
			
			
		}else{
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
		
	}	
	//서식자료실 등록/수정
	@RequestMapping("/admin/form_edit_do.go")
	public String dataEditDo(
		HttpServletRequest request,
	
		@RequestParam(value = "formSeq", required = false, defaultValue="0") int formSeq,
	
		@RequestParam(value = "formType", required = false, defaultValue="") String formType,
		@RequestParam(value = "formTitle", required = false, defaultValue="") String formTitle,
	
		@RequestParam(value = "formFileName", required = false, defaultValue="") MultipartFile multipartFiles,
	
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
			boolean chk=true;
			String FILE_PATH = "/data/form/";
			String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
			
			if(user!=null){
				if(formSeq==0){//등록
					FormData fd = new FormData();
					fd.setFormTitle(formTitle);
					fd.setFormType(formType);
					fd.setUserId(user.getUserId());
				
				MultipartFile multipartFile = (MultipartFile)multipartFiles;
					String fileName = multipartFile.getOriginalFilename();
					if(!fileName.equals("")){
						FileWrite fileWrite = new FileWrite();
						String uploadFileName = fileWrite.writeFileTempName(multipartFile, FILE_LOCAL_PATH, fileName);
						uploadFileName = URLEncoder.encode(uploadFileName,"UTF-8");
						
						File file = new File(FILE_LOCAL_PATH + uploadFileName);
						
						fd.setFormFileName(FILE_PATH+uploadFileName);
					}
					formDataDao.addFormData(fd);
					
					map.put("result", true);
					map.put("message", "등록되었습니다.");
					
					
				}else{//수정
					FormData fd = formDataDao.getFormData(formSeq);
					if(fd.getUserId().equals(user.getUserId())){
						fd.setFormTitle(formTitle);
						fd.setFormType(formType);
						fd.setUserId(user.getUserId());
						
						if(!fd.getFormFileName().equals("")){
							File file = new File(FILE_ROOT + fd.getFormFileName());
							file.delete();
						}
						MultipartFile multipartFile = (MultipartFile)multipartFiles;
						String fileName = multipartFile.getOriginalFilename();
						if(!fileName.equals("")){
							FileWrite fileWrite = new FileWrite();
							String uploadFileName = fileWrite.writeFileTempName(multipartFile, FILE_LOCAL_PATH, fileName);
							uploadFileName = URLEncoder.encode(uploadFileName,"UTF-8");
							
							File file = new File(FILE_LOCAL_PATH + uploadFileName);

							fd.setFormFileName(FILE_PATH+uploadFileName);
						}
					
						formDataDao.updateFormData(fd);
					}else{
						chk=false;
						map.put("result", false);
						map.put("message", "수정 권한이 없습니다.");
					}
				
					
					
				}
			
				
			}else{
				map.put("result", false);
				map.put("message", "해당 ID 정보가 존재하지 않습니다.");
			}
		}catch(Exception e){
			map.put("result", false);
			map.put("message", e.getMessage());
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
		
	}

	@RequestMapping("/admin/form_delete.go")
	public String dataDeleteController(
			
			@RequestParam(value="formSeq", required=false, defaultValue="0") int formSeq,
		
			HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
		if(user!=null){
			FormData fd = formDataDao.getFormData(formSeq);
			if(fd.getUserId().equals(user.getUserId())||user.getUserType()==1){
				// 파일 삭제
					
						
					String FILE_PATH = "/data/form/";
					String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
					if(!fd.getFormFileName().equals("")){
						String fileName = fd.getFormFileName();
						File file = new File(FILE_ROOT + fileName);
						file.delete();
					}
					formDataDao.deleteFormData(formSeq);
				
					
		
				map.put("result", true);
				map.put("message", "게시물이 삭제되었습니다.");
			}else{
				map.put("result", false);
				map.put("message", "삭제권한이 없습니다.");
			}
			
		}else{
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}
		
		
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	
	
	////////////////모듈
	
	@RequestMapping("/admin/module_List.go")
	public String moduleListController(
			@RequestParam(value="page", required=false, defaultValue="1") int page,	
		HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
		List<Module>list=null;
		int count=0;
		if(user!=null){
			
			if(user.getUserType()==1){
				
				list=moduleDao.getModuleList("",page, ITEM_COUNT_PER_PAGE);
				count=moduleDao.getModuleCount("");
				map.put("result", true);
				String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);
				
				map.put("paging", paging);
				map.put("currentPage", page);
				map.put("list", list);
				
			}else{
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
			
		}else{
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}
		
		
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	
	@RequestMapping("/admin/module_edit_do.go")
	public String moduleEditDoController(
			Module md,
		HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
		
		if(user!=null){
			
			if(user.getUserType()==1){
				if(md.getModuleSeq()==0){
					moduleDao.addModule(md);
				}else{
					moduleDao.updateModule(md);
				}
				map.put("result", true);
			
			}else{
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
			
		}else{
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}
		
		
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	@RequestMapping("/admin/module_delete.go")
	public String moduleDeleteController(
			@RequestParam(value="moduleSeq", required=false, defaultValue="0") int moduleSeq,	
		HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser((String)ss.getAttribute("USER_ID"));
		
		if(user!=null){
			
			if(user.getUserType()==1){
				moduleDao.deleteModule(moduleSeq);
				map.put("result", true);
			
			}else{
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
			
		}else{
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}
		
		
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	
	///////////////////todo
	
	@RequestMapping("/admin/todo_list.go")
	public String todoListController(
		@RequestParam(value="startDay", required=false, defaultValue="") String startday,	
		@RequestParam(value="endDay", required=false, defaultValue="") String endday,
		@RequestParam(value="keyword", required=false, defaultValue="") String keyword,
		HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List<Todo>list = null;
		List<Todo>beforeList=null;
	
		if(user!=null){
			
			if(user.getUserType()==1){
				String today= T.getToday();
				if(startday.equals("")||endday.equals("")){
					startday=today;
					endday=today;
				}
			
				
				list=todoDao.getTodoList(user.getUserId(),startday,endday,Todo.TODO_BE_DUE,keyword);
				beforeList=todoDao.getTodoList(user.getUserId(),"","",Todo.TODO_POSTPHONE,keyword);
			
				map.put("result", true);
				map.put("list", list);
				map.put("beforeList", beforeList);
				map.put("startDay", startday);
				map.put("endDay", endday);
				map.put("keyword", keyword);
			}else{
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
			
		}else{
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}
		
		
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	
	@RequestMapping("/admin/todo_edit.go")
	public String todoEditController(
		@RequestParam(value="todoSeq", required=false, defaultValue="0") int todoSeq,	

		HttpServletResponse res,HttpSession ss,Model model
		){
		
	
		Todo todo =todoDao.getTodo(todoSeq);
		
		model.addAttribute("todo",todo);
		
		
		return "admin/todo/todo_pop";
	}
	@RequestMapping("/admin/todo_edit_do.go")
	public String todoEditDoController(
		@RequestParam(value="todoSeq", required=false, defaultValue="0") int todoSeq,	
		@RequestParam(value="todoStatus", required=false, defaultValue="") String todoStatus,
		@RequestParam(value="todoComment", required=false, defaultValue="") String todoComment,
		@RequestParam(value="todoFinishday", required=false, defaultValue="") String todoFinishday,
		HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		
		if(user!=null){
			
			if(user.getUserType()==1){
				
				todoDao.updateTodoAdmin(todoSeq,todoStatus,todoComment,todoFinishday);
				
			
				map.put("result", true);
				map.put("message", "저장 되었습니다.");
			}else{
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
			
		}else{
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}
		
		
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	
	
	@RequestMapping("/admin/comment_edit_do.go")
	public String commentEditDoController(
		@RequestParam(value="commentSeq", required=false, defaultValue="0") int commentSeq,	
		@RequestParam(value="projectSeq", required=false, defaultValue="0") int projectSeq,
		@RequestParam(value="designComment", required=false, defaultValue="") String designComment,
		@RequestParam(value="iosComment", required=false, defaultValue="") String iosComment,
		@RequestParam(value="webComment", required=false, defaultValue="") String webComment,
		@RequestParam(value="serverComment", required=false, defaultValue="") String serverComment,
		@RequestParam(value="pcComment", required=false, defaultValue="") String pcComment,
		@RequestParam(value="andComment", required=false, defaultValue="") String andComment,
		HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		
		if(user!=null){
			
			if(user.getUserType()==1){
				
				if(commentSeq>0){
					ProjectComment pc = projectCommentDao.getProjectComment(commentSeq);
					pc.setAndComment(andComment);
					pc.setWebComment(webComment);
					pc.setServerComment(serverComment);
					pc.setIosComment(iosComment);
					pc.setPcComment(pcComment);
					pc.setDesignComment(designComment);
					pc.setProjectSeq(projectSeq);
					projectCommentDao.updateProjectComment(pc);
					
				}else{
					ProjectComment pc = new ProjectComment();
					pc.setAndComment(andComment);
					pc.setWebComment(webComment);
					pc.setServerComment(serverComment);
					pc.setIosComment(iosComment);
					pc.setPcComment(pcComment);
					pc.setDesignComment(designComment);
					pc.setProjectSeq(projectSeq);
					projectCommentDao.addProjectComment(pc);
					
				}
				
			
				map.put("result", true);
				map.put("message", "저장 되었습니다.");
			}else{
				map.put("result", false);
				map.put("message", "권한이 없습니다.");
			}
			
		}else{
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}
		
		
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
	//파일삭제
	public void filedelNow(String files){
		String [] arr = files.split(",");
		for(int i=0; i<arr.length ; i++){
			String fileName = arr[i];
			//본파일
			FILE_LOCAL_PATH = FILE_ROOT + fileName ;
			File file = new File(FILE_LOCAL_PATH);
			file.delete();
			//썸네일
			String  thumpath = fileName.substring(0,fileName.lastIndexOf("/"))+"/thumb"; //경로
			String tumbName = fileName.substring(fileName.lastIndexOf("/")); //사진이름
			String thumb = FILE_ROOT +thumpath + tumbName;
			File file2 = new File(thumb);
			file2.delete();
			
		}
	}
	
	void sendSmS(String num,String msg1,String userName) throws Exception{
		
		String getURL = "http://smmunja.nomadsoft.com/api/send.go";
		
		
		String curl =URLEncoder.encode(msg1, "UTF-8");
		getURL += "?clientId="+"nomad" ;
		getURL += "&authCode="+"12345" ;
		getURL += "&title="+URLEncoder.encode("NOMAD SOFT", "utf-8");
		getURL += "&msg="+ curl ;
		getURL += "&isReservation=0" ;
		getURL += "&receiverNumber="+num ;
		getURL += "&receiverName="+URLEncoder.encode(userName, "utf-8");
		getURL += "&sendType=sms" ;
		
		
		//String getURL = GET_URL + "?name=" + URLEncoder.encode("zhangshan", "utf-8");
		URL getUrl = new URL(getURL);
		//URL getUrl = new URL(URLEncoder.encode(getURL, "utf-8"));
		// 끌어모았어 URL을 열 따라 연결할 URL 형식은 따라 할 URL.openConnection 함수, 
		// 다시 다른 URLConnection 하위 클래스 대상, 여기 URL 한 http 때문에 실제 복귀 것은 HttpURLConnection
		HttpURLConnection connection = (HttpURLConnection) getUrl.openConnection();
		// 을 연결 하지만 실제로는 get request 반xp드시 다음 구의 connection.getInputStream () 함수 중 비로소 진정한 발 까지 서버
		connection.connect();
		// 확실한 입력 및 사용 Reader 읽기
		BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));// 인코딩 설정 을 함께 했 다., 그렇지 않으면, 중국어
		System.out.println("=============================");
		System.out.println("Contents of get request");
		System.out.println("=============================");
		String lines;
		String msg = "";
		while ((lines = reader.readLine()) != null) {
			String str = new String(lines.getBytes());
			msg += URLDecoder.decode(str, "UTF-8");
			System.out.println(lines);
		}
		reader.close();
		// 연결 끊기
		connection.disconnect();
	}
}
	

