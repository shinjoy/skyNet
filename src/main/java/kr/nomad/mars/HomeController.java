package kr.nomad.mars;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import javax.servlet.http.HttpSession;

import kr.nomad.mars.dao.CompanyDao;
import kr.nomad.mars.dao.ProjectAdminDao;
import kr.nomad.mars.dao.ProjectDao;
import kr.nomad.mars.dao.UserDao;
import kr.nomad.mars.dto.Company;
import kr.nomad.mars.dto.Project;
import kr.nomad.mars.dto.ProjectAdmin;
import kr.nomad.mars.dto.User;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	@Autowired ProjectDao projectDao;
	@Autowired UserDao userDao;
	@Autowired CompanyDao companyDao;
	@Autowired ProjectAdminDao projectAdminDao;

	// 로그인
	@RequestMapping("/m/login.go")
	public String mLoginController(
			Model model, HttpSession session
		) {

		return "m/login";
	}

	// 프로젝트 리스트
	@RequestMapping("/m/project_list.go")
	public String mProjectListController(
			Model model, HttpSession session
		) {

		return "m/project_list";
	}

	// 프로젝트 상세
	@RequestMapping("/m/project_view.go")
	public String mProjectViewController(
			@RequestParam(value="projectSeq", required=false, defaultValue="0") int projectSeq, 
			Model model, HttpSession session
		) {
		
		model.addAttribute("projectSeq", projectSeq);
		return "m/project_view";
	}
	
	// 게시판
	@RequestMapping("/{path}/bbs_list.go")
	public String mBbsListController(
			@PathVariable String path,
			@RequestParam(value="projectSeq", required=false, defaultValue="0") int projectSeq, 
			@RequestParam(value="bbsType", required=false, defaultValue="0") int bbsType, 
			Model model, HttpSession session
		) {

		model.addAttribute("projectSeq", projectSeq);
		model.addAttribute("bbsType", bbsType);
		if(path.equals("admin")){
			path="admin/projectMenu";
		}
		return path+"/bbs_list";
	}
	
	@RequestMapping("/{path}/bbs_view.go")
	public String mBbsViewController(
			@PathVariable String path,
			@RequestParam(value="projectSeq", required=false, defaultValue="0") int projectSeq, 
			@RequestParam(value="bbsSeq", required = false, defaultValue="0") int bbsSeq, 
			//@RequestParam(value="page", required = false, defaultValue="1") int page, 
			Model model, HttpSession session
		) {
		List<ProjectAdmin>list= projectAdminDao.getProjectAdminList(projectSeq);
		model.addAttribute("list", list);
		model.addAttribute("projectSeq", projectSeq);
		model.addAttribute("bbsSeq", bbsSeq);
	//	model.addAttribute("page", page);
		model.addAttribute("bbsCommentSeq", 0);
		if(path.equals("admin")){
			path="admin/projectMenu";
		}
		return path+"/bbs_view";
	}
	
	@RequestMapping("/{path}/bbs_edit.go")
	public String mBbsEditController(
			@PathVariable String path,
			@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq, 
			@RequestParam(value = "bbsType", required = false, defaultValue="0") int bbsType, 
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
			Model model, HttpSession session
		) {
		

		model.addAttribute("projectSeq", projectSeq);
		model.addAttribute("bbsSeq", bbsSeq);
		
		model.addAttribute("bbsType", bbsType);
		System.out.println(bbsType);
		if(path.equals("admin")){
			path="admin/projectMenu";
		}
		return path+"/bbs_edit";
	}
	
	@RequestMapping("/{path}/files_list.go")
	public String mFilesListController(
			@PathVariable String path,
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
			Model model, HttpSession session
		) {

		model.addAttribute("projectSeq", projectSeq);
		model.addAttribute("dataType", 0);
		if(path.equals("admin")){
			path="admin/projectMenu";
		}
		return path+"/files_list";
	}
	
	@RequestMapping("/{path}/files_view.go")
	public String mFilesViewController(
			@PathVariable String path,
			@RequestParam(value = "dataSeq", required = false, defaultValue="0") int dataSeq, 
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
			Model model, HttpSession session
		) {

		model.addAttribute("dataSeq", dataSeq);
		model.addAttribute("projectSeq", projectSeq);
		if(path.equals("admin")){
			path="admin/projectMenu";
		}
		return path+"/files_view";
	}
	
	@RequestMapping("/{path}/files_edit.go")
	public String mFilesEditController(
			@PathVariable String path,
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
			@RequestParam(value = "dataSeq", required = false, defaultValue="0") int dataSeq, 
			Model model, HttpSession session
		) {

		model.addAttribute("projectSeq", projectSeq);
		model.addAttribute("dataSeq", dataSeq);
		if(path.equals("admin")){
			path="admin/projectMenu";
		}
		return path+"/files_edit";
	}
	
	@RequestMapping("/admin/admin/project_list.go")
	public String AdminProjectListController(
			@RequestParam(value = "companySeq", required = false, defaultValue="0") int companySeq, 
		
			Model model, HttpSession session
		) {

		model.addAttribute("companySeq", companySeq);
	
		return "admin/project/project_list";
	}
	
	@RequestMapping("/admin/admin/company_view.go")
	public String AdminProjectViewController(
			@RequestParam(value = "companySeq", required = false, defaultValue="0") int companySeq, 
		
			Model model, HttpSession session
		) {

		model.addAttribute("companySeq", companySeq);
	
		return "admin/company/company_view";
	}
	
	@RequestMapping("/admin/admin/company_admin.go")
	public String AdmincompanyController(
			@RequestParam(value = "companySeq", required = false, defaultValue="0") int companySeq, 
			@RequestParam(value = "userId", required = false, defaultValue="") String userId, 
			Model model, HttpSession session
		) {
		
		
		User user = userDao.getUser(userId);
		if(user==null){
			user=new User();
		}
		model.addAttribute("companySeq", companySeq);
		model.addAttribute("user", user);
		return "admin/company/company_admin";
	}
	@RequestMapping("/admin/admin/company.go")
	public String companyController(
			@RequestParam(value = "companySeq", required = false, defaultValue="0") int companySeq, 
			
			Model model, HttpSession session
		) {
		
		Company cc = companyDao.getCompany(companySeq);
		
		model.addAttribute("companySeq", companySeq);
		model.addAttribute("company", cc);
		
		return "admin/company/company";
	}
	@RequestMapping("/admin/admin/company_list.go")
	public String companyListController(
		
			
			Model model, HttpSession session
		) {
		
	
		return "admin/company/company_list";
	}
	
	@RequestMapping("/admin/admin/user_list.go")
	public String AdminUserListController(
			
			Model model, HttpSession session
		) {

		model.addAttribute("companySeq", 1);
	
		return "admin/user/user_list";
	}
	@RequestMapping("/admin/admin/project_edit.go")
	public String projectEditController(
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
			@RequestParam(value = "companySeq", required = false, defaultValue="0") int companySeq, 
			Model model, HttpSession session
		) {

		model.addAttribute("projectSeq", projectSeq);
		model.addAttribute("companySeq", companySeq);
		return "admin/project/project_edit";
	}
	@RequestMapping("/admin/schedule/schedule.go")
	public String scheduleController(
			Model model, HttpSession session
		) {

		return "admin/schedule/schedule";
	}
	@RequestMapping("/admin/todo/todo.go")
	public String todoController(
			Model model, HttpSession session
		) {

		return "admin/todo/todo";
	}
	// 주간 업무 보고 게시판
	@RequestMapping("/{path}/week_bbs_list.go")
	public String weekBbsListController(
			@PathVariable String path,
			@RequestParam(value="projectSeq", required=false, defaultValue="0") int projectSeq, 
		
			Model model, HttpSession session
		) {

		model.addAttribute("projectSeq", projectSeq);
		if(path.equals("admin")){
			path="admin/projectMenu";
		}
		return path+"/week_bbs_list";
	}
	
	@RequestMapping("/{path}/week_bbs_view.go")
	public String weekBbsViewController(
			@PathVariable String path,
			@RequestParam(value="projectSeq", required=false, defaultValue="0") int projectSeq, 
			@RequestParam(value="bbsSeq", required = false, defaultValue="0") int bbsSeq, 
			//@RequestParam(value="page", required = false, defaultValue="1") int page, 
			Model model, HttpSession session
		) {
		
		model.addAttribute("projectSeq", projectSeq);
		model.addAttribute("bbsSeq", bbsSeq);
	//	model.addAttribute("page", page);
		model.addAttribute("bbsCommentSeq", 0);
		if(path.equals("admin")){
			path="admin/projectMenu";
		}
		return path+"/week_bbs_view";
	}
	
	@RequestMapping("/{path}/week_bbs_edit.go")
	public String weekBbsEditController(
			@PathVariable String path,
			@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq, 
		//	@RequestParam(value = "bbsType", required = false, defaultValue="0") int bbsType, 
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
			Model model, HttpSession session
		) {
		//model.addAttribute("bbsType", 1);

		model.addAttribute("projectSeq", projectSeq);
		model.addAttribute("bbsSeq", bbsSeq);
		
		if(path.equals("admin")){
			path="admin/projectMenu";
		}
		return path+"/week_bbs_edit";
	}
	
	// 공지 게시판
		@RequestMapping("/{path}/admin_bbs_list.go")
		public String adminBbsListController(
				@PathVariable String path,
				@RequestParam(value="projectSeq", required=false, defaultValue="0") int projectSeq, 
			
				Model model, HttpSession session
			) {

			model.addAttribute("projectSeq", projectSeq);

			if(path.equals("admin")){
				path="admin/projectMenu";
			}
			return path+"/admin_bbs_list";
		}
		
		@RequestMapping("/{path}/admin_bbs_view.go")
		public String adminBbsViewController(
				@PathVariable String path,
				@RequestParam(value="projectSeq", required=false, defaultValue="0") int projectSeq, 
				@RequestParam(value="bbsSeq", required = false, defaultValue="0") int bbsSeq, 
				//@RequestParam(value="page", required = false, defaultValue="1") int page, 
				Model model, HttpSession session
			) {
			
			model.addAttribute("projectSeq", projectSeq);
			model.addAttribute("bbsSeq", bbsSeq);
		//	model.addAttribute("page", page);
			model.addAttribute("bbsCommentSeq", 0);

			if(path.equals("admin")){
				path="admin/projectMenu";
			}
			return path+"/admin_bbs_view";
		}
		
		@RequestMapping("/{path}/admin_bbs_edit.go")
		public String adminBbsEditController(
				@PathVariable String path,
				@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq, 
			//	@RequestParam(value = "bbsType", required = false, defaultValue="0") int bbsType, 
				@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
				Model model, HttpSession session
			) {
			//model.addAttribute("bbsType", 1);

			model.addAttribute("projectSeq", projectSeq);
			model.addAttribute("bbsSeq", bbsSeq);
			if(path.equals("admin")){
				path="admin/projectMenu";
			}
			return path+"/admin_bbs_edit";
		}
		
		@RequestMapping("/admin/progress.go")
		public String adminProgressController(
		
				@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
				Model model, HttpSession session
			) {
		

			model.addAttribute("projectSeq", projectSeq);
		
			return "admin/projectMenu/progress";
		}
		

		
		
		
}
