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
public class SkynetController {
	
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

	@RequestMapping("/skynet/login.go")
	public String skynetLogin(
			HttpServletRequest request, HttpServletResponse res, Model model,HttpSession ss	
	) {
		
		return "/skynet/login";
	}

	@RequestMapping("/skynet/main.go")
	public String skynetMain(
			HttpServletRequest request, HttpServletResponse res, Model model,HttpSession session	
	) {
		User user = userDao.getUser(session.getAttribute("USER_ID").toString());
		List projectlist = projectDao.getProjectListNotPaging(0,user.getUserId(),user.getUserLevel());
		model.addAttribute("projectlist", projectlist);
		return "/skynet/main";
	}

}
	

