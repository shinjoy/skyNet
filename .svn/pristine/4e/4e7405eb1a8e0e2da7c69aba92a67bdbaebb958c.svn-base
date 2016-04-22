package kr.nomad.mars;

import java.util.List;

import javax.servlet.http.HttpSession;

import kr.nomad.mars.dao.BbsCommentDao;
import kr.nomad.mars.dao.BbsDao;
import kr.nomad.mars.dao.BbsFilesDao;
import kr.nomad.mars.dao.CompanyDao;
import kr.nomad.mars.dao.DataDao;
import kr.nomad.mars.dao.FormDataDao;
import kr.nomad.mars.dao.ModuleDao;
import kr.nomad.mars.dao.ProjectAdminDao;
import kr.nomad.mars.dao.ProjectDao;
import kr.nomad.mars.dao.TodoDao;
import kr.nomad.mars.dao.UserDao;
import kr.nomad.mars.dto.User;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class DataController {
	
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
	
    @RequestMapping(value = "/data/project_menu.go", method = RequestMethod.GET)
    public @ResponseBody List<?> customers(
    		HttpSession session	) {
		User user = userDao.getUser(session.getAttribute("USER_ID").toString());
		List projectlist = projectDao.getProjectListNotPaging(0,user.getUserId(),user.getUserLevel());

        return projectlist;
    }
}
