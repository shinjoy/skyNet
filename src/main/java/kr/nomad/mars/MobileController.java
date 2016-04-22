package kr.nomad.mars;


import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.nomad.mars.dao.AdminBbsCommentDao;
import kr.nomad.mars.dao.AdminBbsDao;
import kr.nomad.mars.dao.AdminFilesDao;
import kr.nomad.mars.dao.BbsCommentDao;
import kr.nomad.mars.dao.BbsDao;
import kr.nomad.mars.dao.BbsFilesDao;
import kr.nomad.mars.dao.DataDao;
import kr.nomad.mars.dao.ProjectAdminDao;
import kr.nomad.mars.dao.ProjectCommentDao;
import kr.nomad.mars.dao.ProjectDao;
import kr.nomad.mars.dao.TodoDao;
import kr.nomad.mars.dao.UserDao;
import kr.nomad.mars.dao.WeekBbsCommentDao;
import kr.nomad.mars.dao.WeekBbsDao;
import kr.nomad.mars.dao.WeekFilesDao;
import kr.nomad.mars.dto.AdminBbs;
import kr.nomad.mars.dto.AdminBbsComment;
import kr.nomad.mars.dto.AdminFiles;
import kr.nomad.mars.dto.Bbs;
import kr.nomad.mars.dto.BbsComment;
import kr.nomad.mars.dto.BbsFiles;
import kr.nomad.mars.dto.Data;
import kr.nomad.mars.dto.Project;
import kr.nomad.mars.dto.ProjectAdmin;
import kr.nomad.mars.dto.ProjectComment;
import kr.nomad.mars.dto.Todo;
import kr.nomad.mars.dto.User;
import kr.nomad.mars.dto.WeekBbs;
import kr.nomad.mars.dto.WeekBbsComment;
import kr.nomad.mars.dto.WeekFiles;
import kr.nomad.util.F;
import kr.nomad.util.FileMime;
import kr.nomad.util.FileWrite;
import kr.nomad.util.ImageUtil;
import kr.nomad.util.Paging;
import kr.nomad.util.Response;
import kr.nomad.util.T;
import kr.nomad.util.encrypt.CryptoNew;
import kr.nomad.util.file.MovieConverter;
import kr.nomad.util.file.UniqFileRenamePolicy;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;


@Controller
public class MobileController {
	
	@Autowired UserDao userDao;
	@Autowired ProjectDao projectDao;
	@Autowired BbsDao bbsDao;
	@Autowired BbsCommentDao bbsCommentDao;
	@Autowired BbsFilesDao bbsfilesDao;
	@Autowired DataDao dataDao;
	@Autowired ProjectAdminDao projectAdminDao;
	@Autowired WeekBbsDao weekBbsDao;
	@Autowired WeekBbsCommentDao weekBbsCommentDao;
	@Autowired WeekFilesDao weekFilesDao;
	@Autowired AdminBbsDao adminBbsDao;
	@Autowired AdminBbsCommentDao adminBbsCommentDao;
	@Autowired AdminFilesDao adminFilesDao;
	@Autowired TodoDao todoDao;
	@Autowired ProjectCommentDao  projectCommentDao;
	
	
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
	
	// FFMPEG
	@Value("#{config['ffmpeg.path']}")
	String FFMPEG_PATH;
	
	
	//모바일 로그인
	@RequestMapping("/api_view.go")
	public String login(
			HttpServletRequest request,
			
			HttpServletResponse res, Model model	
	) {
	
		return "api_view";
	}
	
	//모바일 로그인
	@RequestMapping("/proc/login_do.go")
	public String login(
		HttpServletRequest request,
		@RequestParam(value = "userId", required = false, defaultValue="") String userId,
		@RequestParam(value = "userPw", required = false, defaultValue="") String userPw,
		HttpServletResponse res, Model model	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int Idchk =userDao.correctId(userId);
		
		if(Idchk>0){
			String enPw = CryptoNew.encrypt(userPw);
			int chk =userDao.getUserChk(userId,enPw);  //암호화 해야함.
			if(chk>0){
				
				User user = userDao.getUserCompany(userId);
				HttpSession ss =request.getSession();
				ss.setAttribute("USER_ID", user.getUserId());
				ss.setAttribute("USER_NAME", user.getUserName());
				ss.setAttribute("USER_TYPE", user.getUserType());
				ss.setAttribute("COMPANY_SEQ", user.getCompanySeq());
				ss.setAttribute("COMPANY_NAME", user.getCompanyName());
				map.put("result", true);
				
				
			}else{
				map.put("result", false);
				map.put("message", "비밀번호가 일치하지 않습니다.");
			}
		}else{
			map.put("result", false);
			map.put("message", "해당 ID가 존재하지 않습니다.");
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
		
	}
	//비밀번호 찾기 검사
	@RequestMapping("/proc/find_pw.go")
	public String findPw(
		HttpServletRequest request,
		@RequestParam(value = "userId", required = false, defaultValue="") String userId,
		@RequestParam(value = "userPhone", required = false, defaultValue="") String userPhone,
		HttpServletResponse res, Model model	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		int Idchk =userDao.correctIdPhone(userId,userPhone);
		
		if(Idchk>0){
		
			map.put("result", true);
		
		}else{
			map.put("result", false);
			map.put("message", "해당 ID가 존재하지 않습니다.");
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
		
	}	
	
	//비밀번호변경
	@RequestMapping("/proc/pw_change.go")
		public String pwChange(
			HttpServletRequest request,
			@RequestParam(value = "userId", required = false, defaultValue="") String userId,
			@RequestParam(value = "newpw", required = false, defaultValue="") String newpw,
			HttpServletResponse res, Model model	
		) {
			Map<String, Object> map = new HashMap<String, Object>();
			
			User user = userDao.getUser(userId);
			String enPw = CryptoNew.encrypt(newpw);
			user.setUserPw(enPw);
			userDao.updateUserPw(user);
			
			map.put("result", true);
			
			
			
			JSONObject jsonObject = JSONObject.fromObject(map);
			Response.responseWrite(res, jsonObject);
			return null;
	}	

	@RequestMapping("/fileDownload.go")
	public String fileDownloadController(
		
			@RequestParam(value = "fileName", required = false, defaultValue = "") String  fileName,
			Model model
		) {
	
	
	//	List<Data>list = dataDao.getDataListInseq(arrSeq);
		
			if(!fileName.equals("")){
				File file = new File(FILE_ROOT +"/"+ fileName);
				
				model.addAttribute("file", file);
				
			}
	
		return "fileDownloadView";
	}
	//아이디 찾기
	@RequestMapping("/proc/find_id.go")
	public String findId(
		HttpServletRequest request,
	
		@RequestParam(value = "userPhone", required = false, defaultValue="") String userPhone,
		HttpServletResponse res, Model model	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user=userDao.Phone(userPhone);
		
		if(user!=null){
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
	

	/**
	 * 이미지, 동영상, 파일을 업로드한다.
	 * 원본 파일명 유지 정책
	 * type : 저장 파일의 경로 (프로젝트 명을 사용)
	 * 이미지 - isThumb=1:섬네일 이미지만 저장
	 *        - addThumb=1:원본 저장 후 섬네일 이미지도 저장
	 * 동영상 - 저장 후 컨버팅 실행
	 * 기타파일 - 단순 저장
	 */
	// 파일 등록
	@RequestMapping("/proc/file_upload.go")
	public String proUpload_Controller(
			@RequestParam(value="userId", required = false, defaultValue = "") String userId,
			@RequestParam(value="type", required = false, defaultValue = "") String type,
			@RequestParam(value="isThumb", required = false, defaultValue = "0") int isThumb,
			@RequestParam(value="addThumb", required = false, defaultValue = "0") int addThumb,
			@RequestParam(value="photo", required=false, defaultValue="") MultipartFile photo,
			HttpServletRequest req, HttpServletResponse res, Model model

	) {

		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		

		String FILE_PATH = "/files/temp/";
		String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
		String photoPre = "";
		String repath ="";
		String org = "";
		
		int fileSize = FILE_MAX_SIZE * 1024 * 1024;

		try {
			req.setCharacterEncoding("utf-8");

			String originalFileName = photo.getOriginalFilename();
			String uploadFileName = "";
			
			String hwak = originalFileName.substring(originalFileName.lastIndexOf("."));
			
			if (hwak.equals(".jpg") || hwak.equals(".jpeg") || hwak.equals(".png")) {
				/* 이미지 파일일 경우 */

				// 파일을 저장하고 저장된 이름을 반환
				repath = "/img/"+type;
				String fileLocalPath = FILE_ROOT + "/img/"+type;
				String thumbLocalPath = FILE_ROOT + "/img/"+type+"/thumb";
				
				// 폴더가 없으면 만든다.
				File fileFolder = new File(fileLocalPath);
				if (!fileFolder.exists()) { fileFolder.mkdirs(); }
				File thumbFolder = new File(thumbLocalPath);
				if (!thumbFolder.exists()) { thumbFolder.mkdirs(); }
				
				if (isThumb == 1) {
					uploadFileName = ImageUtil.fileSave(thumbLocalPath, photo, originalFileName);
				} else {
					uploadFileName = ImageUtil.fileSave(fileLocalPath, photo, originalFileName);
					if (addThumb == 1) {
						// 썸네일이미지 저장
						File newFile = new File(fileLocalPath +"/"+ uploadFileName);
						File thumbFile = new File(thumbLocalPath +"/"+ uploadFileName);
						try {
							ImageUtil.resize(newFile, thumbFile, 200, 0, 0);
							result = true;
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}

				
			} else if (hwak.equals(".mov") || hwak.equals(".avi") || hwak.equals(".mp4")) {
				/* 동영상 파일일 경우 : 저장 후 컨버팅 */

				// 파일을 임시경로에 저장하고 저장된 이름을 반환
				repath = "/mov/"+type;
				String fileTempPath = FILE_ROOT + "/temp";
				String fileLocalPath = FILE_ROOT + "/mov/"+type;
				
				// 폴더가 없으면 만든다.
				File tempFolder = new File(fileTempPath);
				if (!tempFolder.exists()) { tempFolder.mkdirs(); }
				File fileFolder = new File(fileLocalPath);
				if (!fileFolder.exists()) { fileFolder.mkdirs(); }
				
				String uploadTempName = ImageUtil.fileSave(fileTempPath, photo, originalFileName);

				// mime이 동영상이면...
				File tmpFile = new File(fileTempPath+"/"+uploadTempName);		
				String mimeType = FileMime.getMimeType(tmpFile);
				if (mimeType != "" && mimeType.split("[/]")[0].equals("video")) {
					// 컨버팅하여 저장하여 저장된 이름을 반환
					MovieConverter converter  = new MovieConverter(FFMPEG_PATH, uploadTempName, fileLocalPath, fileTempPath);
					uploadFileName = converter.convert();
				}
				
				// 임시 파일은 제거
				File file = new File(fileTempPath+"/"+uploadTempName);
				file.delete();

			} else {
				/* 기타 파일일 경우 */
				// 파일을 저장하고 저장된 이름을 반환
				repath = "/data/"+type;
				String fileLocalPath = FILE_ROOT + "/data/"+type;
				
				// 폴더가 없으면 만든다.
				File fileFolder = new File(fileLocalPath);
				if (!fileFolder.exists()) { fileFolder.mkdirs(); }

				uploadFileName = ImageUtil.fileSave(fileLocalPath, photo, originalFileName);
			}
			
			/*
			// 회전된 사진은 바로 세워 저장한다.
			//int degree = imageExifOrientation(FILE_LOCAL_PATH+"/"+org);		
			int degree = ImageUtil.GetExifOrientation(FILE_LOCAL_PATH+"/"+org);		
			int isLotated = 0;
			if (degree>0) {
				isLotated = 1;
				File oldFile = new File(FILE_LOCAL_PATH+"/"+org);
				File newFile = new File(FILE_LOCAL_PATH+"/"+org);
				BufferedImage oldImage = ImageIO.read(new FileInputStream(oldFile));
				BufferedImage newImage = null; 
				if (degree==90 || degree==270) {
					newImage = new BufferedImage(oldImage.getHeight(),oldImage.getWidth(), oldImage.getType());
					Graphics2D graphics = (Graphics2D) newImage.getGraphics();
					
					graphics.rotate(Math.toRadians(degree), newImage.getWidth() / 2, newImage.getHeight() / 2);
					graphics.translate((newImage.getWidth() - oldImage.getWidth()) / 2, (newImage.getHeight() - oldImage.getHeight()) / 2);
					graphics.drawImage(oldImage, 0, 0, newImage.getHeight(), newImage.getWidth(), null);
				} else {
					newImage = new BufferedImage(oldImage.getWidth(),oldImage.getHeight(),oldImage.getType());
					Graphics2D graphics = (Graphics2D) newImage.getGraphics();
					graphics.rotate(Math.toRadians(degree), newImage.getWidth() / 2, newImage.getHeight() / 2);
					graphics.translate((newImage.getWidth() - oldImage.getWidth()) / 2, (newImage.getHeight() - oldImage.getHeight()) / 2);
					graphics.drawImage(oldImage, 0, 0, newImage.getWidth(), newImage.getHeight(), null);
				}
				FileOutputStream fos = new FileOutputStream(newFile);
				try {
					ImageIO.write(newImage, "JPG", fos);
				} catch (Exception e) {
					// TODO: handle exception
				} finally {
					fos.close();
				}
			}
			*/
			
			map.put("originalFileName", originalFileName);
			map.put("fileName", uploadFileName);
			map.put("path", repath);
			map.put("result", true);
			map.put("message", "사진이 등록되었습니다.");
			
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
	

	
	@RequestMapping("/proc/photo_upload2.go")
	public String procPhotoUploadController(HttpServletRequest req, HttpServletResponse res, Model model) {

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
				
			
				String yearMonth = T.getTodayYear()+T.getTodayMonth();
					
				photoPre = yearMonth+"/";
				fullpath =FILE_ROOT + "/files/"+ path +"/"+photoPre;
				repath = "/files/"+ path +"/"+photoPre;
				
				
			
			

				File copyFolder = new File(fullpath);
				if (!copyFolder.exists()) {
						copyFolder.mkdirs();
				}
			
				

				// 파일 복사
				FileInputStream fis = new FileInputStream(FILE_LOCAL_PATH + org);
				FileOutputStream fos = new FileOutputStream(fullpath+"/"+fileName);
				int data = 0;
				while((data=fis.read())!=-1) {
					fos.write(data);
				}
				fis.close();
				fos.close();
				//복사한뒤 원본파일을 삭제함
				file.delete();
			} catch (Exception e) {
				e.getMessage();
			}
		
			
			map.put("photo", fileName);
			map.put("path", repath);
			map.put("result", true);
			map.put("message", "등록되었습니다.");
			
		} catch (Exception e) {
			map.put("result", false);
			map.put("message", "등록에 실패하였습니다.\n"+e.getMessage());
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);

		Gson gson = new Gson();
		String outputstr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputstr);

		return null;
	}
	
	////////////////////////////////////////////////////////////프로젝트 리스트
	@RequestMapping("/proc/project_list.go")
	public String projectList(
			@RequestParam(value = "page", required = false, defaultValue="1") int page,
			HttpServletResponse res, Model model,HttpSession session	
		) {
		
			Map<String, Object> map = new HashMap<String, Object>();
			
			User user = userDao.getUser(session.getAttribute("USER_ID").toString());
			List<Project>list=null;
			int count =0;
			if(user!=null){
				
				if(user.getUserType()==1){ //내부직원
					list=projectDao.getProjectList("",0,"","","","","",page,ITEM_COUNT_PER_PAGE);
					count=projectDao.getProjectCount("",0,"","","");
					
				}else{//고객
					list=projectDao.getProjectList("",user.getCompanySeq(),"","","","","",page,ITEM_COUNT_PER_PAGE);
					count=projectDao.getProjectCount("",user.getCompanySeq(),"","","");
				}
				String paging = Paging.getPaging(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING);
				
				map.put("paging", paging);
				map.put("currentPage", page);
				map.put("list", list);
				map.put("result", true);
			
			
		}else{
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
		
	}
	
	// 프로젝트 상세
	@RequestMapping("/proc/project_view.go")
	public String projectView(HttpServletRequest request,
		
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq,
			HttpServletResponse res,HttpSession ss,
			Model model) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		
		int count = 0;
		if (user != null) {
			Project project = projectDao.getProject(projectSeq);
			List<ProjectAdmin>list= projectAdminDao.getProjectAdminList(projectSeq);
			int requireCount=bbsDao.getBbsCount("",projectSeq, Bbs.BBS_TYPE_REQUIRE, "0",Bbs.BBS_STATUS_NEW);
			int requirefinishCount=bbsDao.getBbsCount("",projectSeq, Bbs.BBS_TYPE_REQUIRE, "0",Bbs.BBS_STATUS_FINISH);
			int requireingCount=bbsDao.getBbsCount("",projectSeq, Bbs.BBS_TYPE_REQUIRE, "0",Bbs.BBS_STATUS_ING);
			int bugCount=bbsDao.getBbsCount("",projectSeq, Bbs.BBS_TYPE_BUG, "0",Bbs.BBS_STATUS_NEW);
			int bugfinishCount=bbsDao.getBbsCount("",projectSeq, Bbs.BBS_TYPE_BUG, "0",Bbs.BBS_STATUS_FINISH);
			int bugingCount=bbsDao.getBbsCount("",projectSeq, Bbs.BBS_TYPE_BUG, "0",Bbs.BBS_STATUS_ING);
			
			int noticeCount=adminBbsDao.getBbsCount("", projectSeq, 0);
			int newCount=weekBbsDao.getnewBbsCount(projectSeq);
			
			ProjectComment pc = projectCommentDao.getProjectCommentByTop(projectSeq);
			
			map.put("projectComment",pc);
			map.put("project",project);
			map.put("list",list);
			map.put("requireCount",requireCount);
			map.put("requirefinishCount",requirefinishCount);
			map.put("requireingCount",requireingCount);
			map.put("bugCount",bugCount);
			map.put("bugfinishCount",bugfinishCount);
			map.put("bugingCount",bugingCount);
			map.put("userType", ss.getAttribute("USER_TYPE").toString());
			map.put("noticeCount",noticeCount);
			map.put("newCount",newCount);
		} else {
			map.put("result", false);
			map.put("message", "해당 ID 정보가 존재하지 않습니다.");
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;

	}
	
	
	////////////////////////////////////게시판
	//게시판 리스트
	@RequestMapping("/proc/bbs_list.go")
	public String bbsList(
		HttpServletRequest request,
		
		@RequestParam(value = "bbsType", required = false, defaultValue="") String bbsType,//1요청2버그
		@RequestParam(value = "bbsType2", required = false, defaultValue="0") String bbsType2,//빈값 둘다,0전체 1내부공개
		@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq,
		@RequestParam(value = "page", required = false, defaultValue="") int page,
		@RequestParam(value = "sort", required = false, defaultValue="") String sort,
		@RequestParam(value = "colName", required = false, defaultValue="") String colName,
		@RequestParam(value = "keyword", required = false, defaultValue="") String keyword,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List<Bbs>list=null;
		int count =0;
		if(user!=null){
			
			Project project = projectDao.getProject(projectSeq);

			if(user.getUserType()==1){ //내부직원
				list=bbsDao.getBbsList(keyword,sort,colName,projectSeq,bbsType,bbsType2,page,ITEM_COUNT_PER_PAGE);
				count=bbsDao.getBbsCount(keyword,projectSeq,bbsType,bbsType2,0);
			}else{
				list=bbsDao.getBbsList(keyword,sort,colName,projectSeq,bbsType,"0",page,ITEM_COUNT_PER_PAGE);
				count=bbsDao.getBbsCount(keyword,projectSeq,bbsType,"0",0);
			}
			
			String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING,"projectMenu.getList","searchForm");
			
			map.put("paging", paging);
		
			map.put("list", list);
			map.put("bbsType", bbsType);
			map.put("project", project);
			map.put("projectSeq", projectSeq);
			//총카운트 갯수
			map.put("count", count);
			map.put("currentPage", page);
			map.put("PERPAGE", ITEM_COUNT_PER_PAGE);
			
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
	//게시판 상세화면
	@RequestMapping("/proc/bbs_view.go")
	public String bbsList(
		HttpServletRequest request,
		@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
		@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
		//@RequestParam(value = "page", required = false, defaultValue="") int page,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List<BbsComment>list=null;
		int count =0;
		if(user!=null){
			
			Project project = projectDao.getProject(projectSeq);

			Bbs bbs =bbsDao.getBbs(bbsSeq);
			list=bbsCommentDao.getBbsCommentList(bbsSeq);
		//	count=bbsCommentDao.getBbsCommentCount(bbsSeq);
			List<BbsFiles>fileList= bbsfilesDao.getBbsFilesList(bbsSeq);
			
		//	String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING,"bbs.view","searchForm");
		
			map.put("project", project);
			map.put("projectSeq", projectSeq);
			map.put("bbs", bbs);
		//	map.put("paging", paging);
	//		map.put("currentPage", page);
			map.put("bbsCommentSeq", 0);
			map.put("list", list);
			map.put("fileList", fileList);
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
	
	//게시판 등록 수정화면
	@RequestMapping("/proc/bbs_edit.go")
	public String bbsEdit(
			HttpServletRequest request,
			@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
			HttpServletResponse res, Model model,HttpSession ss	
		) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//User user = userDao.getUser(/*ss.getAttribute("USER_ID").toString()*/"jo");
		Project project = projectDao.getProject(projectSeq);
		User user = new User();
		Bbs bbs = null;
		try {
			user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
		List<BbsComment>list=null;
		int count =0;

		bbs = bbsDao.getBbs(bbsSeq);
		
		List<BbsFiles>fileList= bbsfilesDao.getBbsFilesList(bbsSeq);
		
		map.put("bbs", bbs);
		map.put("fileList", fileList);
		map.put("project", project);
		map.put("result", true);

		if(user!=null){
			
			//Bbs bbs =bbsDao.getBbs(bbsSeq);
		
			//List<BbsFiles>fileList= bbsfilesDao.getBbsFilesList(bbsSeq);
			
			map.put("bbs", bbs);
			map.put("bbsType", 0);
			map.put("fileList", fileList);
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
	
	//게시판 등록
	@RequestMapping("/proc/bbs_edit_do.go")
	public String bbsEditDo(
		HttpServletRequest request,
		@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
		@RequestParam(value = "projectSeq", required = false, defaultValue="") int projectSeq,
		@RequestParam(value = "bbsType", required = false, defaultValue="1") String bbsType,//1요청2버그
		@RequestParam(value = "bbsType2", required = false, defaultValue="0") String bbsType2,//0전체 1내부공개
		@RequestParam(value = "bbsTitle", required = false, defaultValue="") String bbsTitle,
		@RequestParam(value = "bbsContents", required = false, defaultValue="") String bbsContents,
		@RequestParam(value = "fileName", required = false, defaultValue="") String[] fileName,
		@RequestParam(value = "sendSms", required = false, defaultValue="0") int sendSms,
		@RequestParam(value = "grade", required = false, defaultValue="0") String grade,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			boolean chk=true;
			
			if(user!=null){
				if(bbsSeq==0){//등록
					Bbs bbs = new Bbs();
					bbs.setProjectSeq(projectSeq);
					bbs.setBbsType(bbsType);
					bbs.setBbsType2(bbsType2);
					bbs.setBbsTitle(bbsTitle);
					bbs.setBbsContents(bbsContents);
					bbs.setSendSms(sendSms);
					bbs.setGrade(grade);
					bbs.setUserId(user.getUserId());
					bbsSeq =bbsDao.addBbs(bbs);
					Project pp =projectDao.getProject(projectSeq);
					map.put("result", true);
					map.put("message", "등록되었습니다.");
					if(user.getUserType()!=1){ //고객이 등록한 글이면,
						List<ProjectAdmin>list =projectAdminDao.getProjectAdminList(projectSeq);
						for(int i=0;i<list.size();i++){
							ProjectAdmin pa = list.get(i);
							String msg =user.getUserName()+"("+pp.getProjectName()+")님의 문의글이 등록되었습니다."; 
							sendSmS(pa.getUserPhone(),msg,pa.getUserName());
						}
					}
				}else{//수정
					Bbs bbs = bbsDao.getBbs(bbsSeq);
					if(bbs.getUserId().equals(user.getUserId())){
						bbs.setProjectSeq(projectSeq);
						bbs.setBbsType(bbsType);
						bbs.setBbsType2(bbsType2);
						bbs.setBbsTitle(bbsTitle);
						bbs.setBbsContents(bbsContents);
						bbs.setSendSms(sendSms);
						bbs.setGrade(grade);
						List <BbsFiles>list =bbsfilesDao.getBbsFilesList(bbsSeq);
						
						for(int i=0;i<list.size();i++){
							BbsFiles bf=list.get(i);
							File file = new File(FILE_ROOT + bf.getFileName());
							file.delete();
						}
						
						bbsfilesDao.deleteBbsFiles(bbsSeq);
						bbsDao.updateBbs(bbs);
						map.put("result", true);
						map.put("message", "수정 되었습니다.");
					}else{
						chk=false;
						map.put("result", false);
						map.put("message", "수정 권한이 없습니다.");
					}
				}
				if(chk){
					for (int i=0; i<fileName.length; i++) {
						if(!fileName[i].equals("")){
							String filename=fileName[i];
							File file = new File(FILE_ROOT + filename);
							String type = filename.substring(filename.lastIndexOf("."));
							if(type.equals(".jpg")||type.equals(".png")||type.equals(".jpeg")||type.equals(".bmp")){
								type ="img";
							}else{
								type ="text";
							}
							
							BbsFiles bf = new BbsFiles();
							bf.setBbsSeq(bbsSeq);
							bf.setFileName(filename);
							bf.setFileExt(type);
							bbsfilesDao.addBbsFiles(bf);
						}
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
	@RequestMapping("/proc/file_delete.go")
	public String fileDeleteDoController(
			@RequestParam(value="bbsSeq", required=false, defaultValue="0") int bbsSeq,
			@RequestParam(value="fileName", required=false, defaultValue="") String fileName,
			HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		if(user!=null){
			Bbs bbs =bbsDao.getBbs(bbsSeq);
		
			if(bbsSeq==0||bbs.getUserId().equals(user.getUserId())||user.getUserType()==1){
		
				// 파일 삭제
				File file = new File(FILE_ROOT + fileName);
				file.delete();
				bbsfilesDao.deleteBbsFiles(bbsSeq, fileName);
			
				map.put("result", true);
				map.put("message", "파일이 삭제되었습니다.");
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
		
	
	
	
	@RequestMapping("/proc/bbs_delete.go")
	public String bbsDeleteDoController(
			
			@RequestParam(value="bbsSeq", required=false, defaultValue="0") int bbsSeq,
			@RequestParam(value="bbsCommentSeq", required=false, defaultValue="0") int bbsCommentSeq,
			HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		if(user!=null){
			Bbs bbs =bbsDao.getBbs(bbsSeq);
			BbsComment bc =bbsCommentDao.getBbsComment(bbsCommentSeq);
			if(bbs.getUserId().equals(user.getUserId())||bc.getUserId().equals(user.getUserId())){
				if(bbsCommentSeq==0){ ///본글삭제
					
					// 파일 삭제
					List <BbsFiles>list =bbsfilesDao.getBbsFilesList(bbsSeq);
					int fileDeleteCount = 0;		
					String FILE_PATH = "/data/bbs/";
					String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
					for(int i=0; i<list.size(); i++) {
						BbsFiles bf = list.get(i);
						File file = new File(FILE_ROOT + bf.getFileName());
						file.delete();
					}
					bbsfilesDao.deleteBbsFiles(bbsSeq);
					bbsCommentDao.deleteBbsCommentBbsSeq(bbsSeq);
					bbsDao.deleteBbs(bbsSeq);
					
				}else{
					bbsCommentDao.deleteBbsComment(bbsCommentSeq);
					int count = bbsCommentDao.getBbsCommentCount(bbsSeq);
					bc =bbsCommentDao.getTopBbsComment(bbsSeq);
					bbsDao.updateStatusBbs(bbsSeq,bc.getrAnswerStatus(),count);	
				}
			
				
		
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
	
	//댓글 등록
	@RequestMapping("/proc/bbs_comment_edit_do.go")
	public String bbsCommentEditDo(
		HttpServletRequest request,
		
		@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
		@RequestParam(value = "bbsCommentSeq", required = false, defaultValue="0") int bbsCommentSeq,
		@RequestParam(value = "bbsContents", required = false, defaultValue="") String bbsContents,
		@RequestParam(value = "commentStatus", required = false, defaultValue="1") int commentStatus,
		@RequestParam(value = "dateSet", required = false, defaultValue="") String dateSet,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try{
			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			int count = bbsCommentDao.getBbsCommentCount(bbsSeq);
			if(user!=null){
				if(bbsCommentSeq==0){
					BbsComment bc = new BbsComment();
					bc.setBbsSeq(bbsSeq);
					bc.setBbsContents(bbsContents);
					bc.setrAnswerStatus(commentStatus);
					bc.setUserId(user.getUserId());
					bbsCommentDao.addBbsComment(bc);
					count =count+1;
				
					map.put("result", true);
					map.put("message", "등록 되었습니다.");
					
					Bbs bbs=bbsDao.getBbs(bbsSeq);
					if(!user.getUserId().equals(bbs.getUserId())){
						sendSmS(bbs.getUserPhone(), "문의하신 글에 댓글이 등록되었습니다.", bbs.getUserName());
					}
					
				}else{
					BbsComment bc = bbsCommentDao.getBbsComment(bbsCommentSeq);
					if(bc.getUserId().equals(user.getUserId())){
						bc.setBbsSeq(bbsSeq);
						bc.setBbsContents(bbsContents);
						bc.setrAnswerStatus(commentStatus);
						bc.setUserId(user.getUserId());
						bbsCommentDao.updateBbsComment(bc);
						map.put("result", true);
						map.put("message", "수정 되었습니다.");
					}else{
						map.put("result", false);
						map.put("message", "수정 권한이 없습니다.");
					}
					
				}
	
				BbsComment bc = bbsCommentDao.getTopBbsComment(bbsSeq);
				bbsDao.updateStatusBbs(bbsSeq,bc.getrAnswerStatus(),count);
				
				if(commentStatus==Bbs.BBS_STATUS){//접수
					bbsDao.updateDateBbs("bbs_startday",dateSet,bbsSeq);
					
				}else if(commentStatus==Bbs.BBS_STATUS_ING){//진행
					bbsDao.updateDateBbs("bbs_endday",dateSet,bbsSeq);
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

	// Todo 등록/수정
	@RequestMapping("/proc/project_todo_edit.go")
	public String mProjectTodoEdit(HttpServletRequest request,
			@RequestParam(value = "todo", required = false, defaultValue="") String todo,
			@RequestParam(value = "todoPart", required = false, defaultValue="") String todoPart,
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq,
			@RequestParam(value = "userId", required = false, defaultValue="") String userId,
			@RequestParam(value = "dateSet", required = false, defaultValue="") String dateSet,
		HttpServletResponse res, Model model,HttpSession ss) {
		Map<String, Object> map = new HashMap<String, Object>();

		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		
		int count = 0;
		if (user != null) {

			if (user.getUserType() == 1 ) { // 내부직원
				
			
				Todo td= new Todo();
				td.setTodo(todo);
				td.setUserId(userId);
				td.setTodoStartday(dateSet);
				td.setTodoEndday(T.getDateAdd(dateSet, 3));
				td.setTodoPart(todoPart);
				td.setProjectSeq(projectSeq);
				td.setTodoStatus("0");
				todoDao.addTodo(td);
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
	
	////////////////////////// 자료실
	
	//자료실 리스트
	@RequestMapping("/proc/data_room.go")
	public String dataRoomList(
		HttpServletRequest request,
		@RequestParam(value = "dataType", required = false, defaultValue="") String dataType,
		//1:기획서 2:체크리스트3:기능목록표4:버그리스트5:디자인시안
		@RequestParam(value = "keyword", required = false, defaultValue="") String keyword,
		@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq,
		@RequestParam(value = "page", required = false, defaultValue="") int page,

		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List<Data>list=null;
		int count =0;
		if(user!=null){
		
			Project project = projectDao.getProject(projectSeq);

			list=dataDao.getDataList(dataType,projectSeq,keyword,page ,ITEM_COUNT_PER_PAGE);
			count=dataDao.getDataCount(dataType,projectSeq,keyword);
		
			String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING,"projectMenu.getFileList","searchForm");
			
			
			map.put("dataType", dataType);
			map.put("keyword", keyword);
			map.put("projectSeq", projectSeq);
			map.put("paging", paging);
			map.put("currentPage", page);
			map.put("count", count);
			
			map.put("PERPAGE", ITEM_COUNT_PER_PAGE);
			map.put("list", list);
			map.put("project", project);
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
	//자료실 상세화면
	@RequestMapping("/proc/data_room_view.go")
	public String dataRoomView(
		HttpServletRequest request,
		@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq,
		@RequestParam(value = "dataSeq", required = false, defaultValue="0") int dataSeq,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
	
		if(user!=null){
			Project project = projectDao.getProject(projectSeq);
			Data data =dataDao.getData(dataSeq);
			
			map.put("data", data);
			map.put("project", project);
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
	//자료실 상세화면
	@RequestMapping("/proc/file_edit.go")
	public String fileEdit(
		HttpServletRequest request,
		@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq,
		@RequestParam(value = "dataSeq", required = false, defaultValue="0") int dataSeq,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
	
		if(user!=null){
			Project project = projectDao.getProject(projectSeq);
			Data data =dataDao.getData(dataSeq);
			
			map.put("data", data);
			map.put("project", project);
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
			
	//자료실 등록
	@RequestMapping("/proc/data_edit_do.go")
	public String dataEditDo(
		HttpServletRequest request,

		@RequestParam(value = "dataSeq", required = false, defaultValue="0") int dataSeq,
		@RequestParam(value = "projectSeq", required = false, defaultValue="") int projectSeq,
		@RequestParam(value = "dataContents", required = false, defaultValue="") String dataContents,
		@RequestParam(value = "fileName", required = false, defaultValue="") String fileName,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		try{
			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			boolean chk=true;
			
			if(user!=null){
				if(dataSeq==0){//등록
					Data data = new Data();
					data.setDataContents(dataContents);
					data.setProjectSeq(projectSeq);
					data.setUserId(user.getUserId());
					data.setDataFileName(fileName);
					dataDao.addData(data);
					
					map.put("result", true);
					map.put("message", "등록되었습니다.");
					
					
				}else{//수정
					Data data = dataDao.getData(dataSeq);
					if(data.getUserId().equals(user.getUserId())){
						data.setDataContents(dataContents);
						data.setProjectSeq(projectSeq);
						if(!data.getDataFileName().equals("")){
							File file = new File(FILE_LOCAL_PATH + data.getDataFileName());
							file.delete();
						}
						data.setDataFileName(fileName);
						dataDao.updateData(data);
						map.put("result", true);
						map.put("message", "수정 되었습니다.");
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
	
	
	@RequestMapping("/proc/data_file_delete.go")
	public String dataFileDeleteController(

			@RequestParam(value="dataSeq", required=false, defaultValue="0") int dataSeq,
			@RequestParam(value="fileName", required=false, defaultValue="") String fileName,
			HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		if(user!=null){
			Data data = dataDao.getData(dataSeq);
			if(data.getUserId().equals(user.getUserId())||user.getUserType()==1){
				// 파일 삭제
					
					File file = new File(FILE_ROOT + fileName);
					file.delete();
					data.setDataFileName("");
					dataDao.updateData(data);
					
		
				map.put("result", true);
				map.put("message", "파일이 삭제되었습니다.");
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
	
	@RequestMapping("/proc/data_delete.go")
	public String dataDeleteController(

			@RequestParam(value="dataSeq", required=false, defaultValue="0") int dataSeq,
		
			HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		if(user!=null){
			Data data = dataDao.getData(dataSeq);
			if(data.getUserId().equals(user.getUserId())||user.getUserType()==1){
				// 파일 삭제
					
						
					String FILE_PATH = "/data/data/";
					String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
					if(!data.getDataFileName().equals("")){
						String fileName = data.getDataFileName();
						File file = new File(FILE_ROOT + fileName);
						file.delete();
					}
					dataDao.deleteData(dataSeq);
				
					
		
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
	/////////////////////진행사항 보고
	
	@RequestMapping("/proc/week_bbs_list.go")
	public String weekbbsList(
		HttpServletRequest request,
		
	
		@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq,
		@RequestParam(value = "page", required = false, defaultValue="") int page,
		@RequestParam(value = "sort", required = false, defaultValue="") String sort,
		@RequestParam(value = "colName", required = false, defaultValue="") String colName,
		@RequestParam(value = "keyword", required = false, defaultValue="") String keyword,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List<WeekBbs>list=null;
		int count =0;
		if(user!=null){
			
			Project project = projectDao.getProject(projectSeq);

			
			list=weekBbsDao.getBbsList(keyword,sort,colName,projectSeq/*,bbsType,"0"*/,page,ITEM_COUNT_PER_PAGE);
			count=weekBbsDao.getBbsCount(keyword,projectSeq/*,bbsType,"0"*/,0);
			
			
			String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING,"weekBbs.getList","searchForm");
			
			map.put("paging", paging);
		
			map.put("list", list);
			//map.put("bbsType", bbsType);
			map.put("project", project);
			map.put("projectSeq", projectSeq);
			//총카운트 갯수
			map.put("count", count);
			map.put("currentPage", page);
			map.put("PERPAGE", ITEM_COUNT_PER_PAGE);
			
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
	
	
	@RequestMapping("/proc/week_bbs_view.go")
	public String weekbbsview(
		HttpServletRequest request,
		@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
		@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
		//@RequestParam(value = "page", required = false, defaultValue="") int page,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List<WeekBbsComment>list=null;
		int count =0;
		if(user!=null){
			
			Project project = projectDao.getProject(projectSeq);

			WeekBbs bbs =weekBbsDao.getBbs(bbsSeq);
			list=weekBbsCommentDao.getBbsCommentList(bbsSeq);
		//	count=bbsCommentDao.getBbsCommentCount(bbsSeq);
			List<WeekFiles>fileList= weekFilesDao.getBbsFilesList(bbsSeq);
			
		//	String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING,"bbs.view","searchForm");
			
			map.put("project", project);
			map.put("projectSeq", projectSeq);
			map.put("bbs", bbs);
		//	map.put("paging", paging);
	//		map.put("currentPage", page);
			map.put("bbsCommentSeq", 0);
			map.put("list", list);
			map.put("fileList", fileList);
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
	

	@RequestMapping("/proc/week_bbs_edit.go")
	public String weekbbsEdit(
			HttpServletRequest request,
			@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
			HttpServletResponse res, Model model,HttpSession ss	
		) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//User user = userDao.getUser(/*ss.getAttribute("USER_ID").toString()*/"jo");
		Project project = projectDao.getProject(projectSeq);
		User user = new User();
		WeekBbs bbs = null;
		try {
			user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		int count =0;

		bbs = weekBbsDao.getBbs(bbsSeq);
		
		List<WeekFiles>fileList= weekFilesDao.getBbsFilesList(bbsSeq);
		
		map.put("bbs", bbs);
		map.put("fileList", fileList);
		map.put("project", project);
		map.put("result", true);

		if(user!=null){
			
			//Bbs bbs =bbsDao.getBbs(bbsSeq);
		
			//List<BbsFiles>fileList= bbsfilesDao.getBbsFilesList(bbsSeq);
			
			map.put("bbs", bbs);
			//map.put("bbsType", 0);
			map.put("fileList", fileList);
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
	
	
	@RequestMapping("/proc/week_bbs_edit_do.go")
	public String WeekbbsEditDo(
		HttpServletRequest request,
		@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
		@RequestParam(value = "projectSeq", required = false, defaultValue="") int projectSeq,
	//	@RequestParam(value = "bbsType", required = false, defaultValue="1") String bbsType,//1요청2버그
	//	@RequestParam(value = "bbsType2", required = false, defaultValue="0") String bbsType2,//0전체 1내부공개
		@RequestParam(value = "bbsTitle", required = false, defaultValue="") String bbsTitle,
		@RequestParam(value = "bbsContents", required = false, defaultValue="") String bbsContents,
		@RequestParam(value = "fileName", required = false, defaultValue="") String[] fileName,
		@RequestParam(value = "sendSms", required = false, defaultValue="0") int sendSms,
		@RequestParam(value = "grade", required = false, defaultValue="0") String grade,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			boolean chk=true;
			
			if(user!=null){
				if(bbsSeq==0){//등록
					WeekBbs bbs = new WeekBbs();
					bbs.setProjectSeq(projectSeq);
					//bbs.setBbsType(bbsType);
					//bbs.setBbsType2(bbsType2);
					bbs.setBbsTitle(bbsTitle);
					bbs.setBbsContents(bbsContents);
					bbs.setSendSms(sendSms);
					bbs.setGrade(grade);
					bbs.setUserId(user.getUserId());
					bbsSeq =weekBbsDao.addBbs(bbs);
					Project pp =projectDao.getProject(projectSeq);
					map.put("result", true);
					map.put("message", "등록되었습니다.");
					//업체 문자발송
					List<User>list =userDao.getUserListNoPaging(pp.getCompanySeq());
						for(int i=0;i<list.size();i++){
							User uu = list.get(i);
							String msg =""+pp.getProjectName()+"의 금주 진행사항이 등록되었습니다."; 
							sendSmS(uu.getUserPhone(),msg,uu.getUserName());
						}
					
				}else{//수정
					WeekBbs bbs = weekBbsDao.getBbs(bbsSeq);
					if(bbs.getUserId().equals(user.getUserId())){
						bbs.setProjectSeq(projectSeq);
						//bbs.setBbsType(bbsType);
						//bbs.setBbsType2(bbsType2);
						bbs.setBbsTitle(bbsTitle);
						bbs.setBbsContents(bbsContents);
						bbs.setSendSms(sendSms);
						bbs.setGrade(grade);
						List <WeekFiles>list =weekFilesDao.getBbsFilesList(bbsSeq);
						
						for(int i=0;i<list.size();i++){
							WeekFiles bf=list.get(i);
							File file = new File(FILE_ROOT + bf.getFileName());
							file.delete();
						}
						
						weekFilesDao.deleteBbsFiles(bbsSeq);
						weekBbsDao.updateBbs(bbs);
						map.put("result", true);
						map.put("message", "수정 되었습니다.");
					}else{
						chk=false;
						map.put("result", false);
						map.put("message", "수정 권한이 없습니다.");
					}
				}
				if(chk){
					for (int i=0; i<fileName.length; i++) {
						if(!fileName[i].equals("")){
							String filename=fileName[i];
							File file = new File(FILE_ROOT + filename);
							String type = filename.substring(filename.lastIndexOf("."));
							if(type.equals(".jpg")||type.equals(".png")||type.equals(".jpeg")||type.equals(".bmp")){
								type ="img";
							}else{
								type ="text";
							}
							
							WeekFiles bf = new WeekFiles();
							bf.setBbsSeq(bbsSeq);
							bf.setFileName(filename);
							bf.setFileExt(type);
							weekFilesDao.addBbsFiles(bf);
						}
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
	@RequestMapping("/proc/week_file_delete.go")
	public String weekfileDeleteDoController(
			@RequestParam(value="bbsSeq", required=false, defaultValue="0") int bbsSeq,
			@RequestParam(value="fileName", required=false, defaultValue="") String fileName,
			HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		if(user!=null){
			WeekBbs bbs =weekBbsDao.getBbs(bbsSeq);
		
			if(bbsSeq==0||bbs.getUserId().equals(user.getUserId())||user.getUserType()==1){
		
				// 파일 삭제
				File file = new File(FILE_ROOT + fileName);
				file.delete();
				weekFilesDao.deleteBbsFiles(bbsSeq, fileName);
			
				map.put("result", true);
				map.put("message", "파일이 삭제되었습니다.");
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
		
	
	
	
	@RequestMapping("/proc/week_bbs_delete.go")
	public String weekbbsDeleteDoController(
			
			@RequestParam(value="bbsSeq", required=false, defaultValue="0") int bbsSeq,
			@RequestParam(value="bbsCommentSeq", required=false, defaultValue="0") int bbsCommentSeq,
			HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		if(user!=null){
			WeekBbs bbs =weekBbsDao.getBbs(bbsSeq);
			WeekBbsComment bc =weekBbsCommentDao.getBbsComment(bbsCommentSeq);
			if(bbs.getUserId().equals(user.getUserId())||bc.getUserId().equals(user.getUserId())){
				if(bbsCommentSeq==0){ ///본글삭제
					
					// 파일 삭제
					List <WeekFiles>list =weekFilesDao.getBbsFilesList(bbsSeq);
					int fileDeleteCount = 0;		
					String FILE_PATH = "/data/bbs/";
					String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
					for(int i=0; i<list.size(); i++) {
						WeekFiles bf = list.get(i);
						File file = new File(FILE_ROOT + bf.getFileName());
						file.delete();
					}
					weekFilesDao.deleteBbsFiles(bbsSeq);
					weekBbsCommentDao.deleteBbsCommentBbsSeq(bbsSeq);
					weekBbsDao.deleteBbs(bbsSeq);
					
				}else{
					weekBbsCommentDao.deleteBbsComment(bbsCommentSeq);
					int count = weekBbsCommentDao.getBbsCommentCount(bbsSeq);
					
					weekBbsDao.updateStatusBbs(bbsSeq,count);	
				}
			
				
		
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
	
	
	@RequestMapping("/proc/week_bbs_comment_edit_do.go")
	public String weekbbsCommentEditDo(
		HttpServletRequest request,
		
		@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
		@RequestParam(value = "bbsCommentSeq", required = false, defaultValue="0") int bbsCommentSeq,
		@RequestParam(value = "bbsContents", required = false, defaultValue="") String bbsContents,
	

		HttpServletResponse res, Model model,HttpSession ss	
	) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try{
			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			int count = weekBbsCommentDao.getBbsCommentCount(bbsSeq);
			if(user!=null){
				if(bbsCommentSeq==0){
					WeekBbsComment bc = new WeekBbsComment();
					bc.setBbsSeq(bbsSeq);
					bc.setBbsContents(bbsContents);
				
					bc.setUserId(user.getUserId());
					weekBbsCommentDao.addBbsComment(bc);
					count =count+1;
				
					map.put("result", true);
					map.put("message", "등록 되었습니다.");
					
					WeekBbs bbs=weekBbsDao.getBbs(bbsSeq);
					if(!user.getUserId().equals(bbs.getUserId())){
						String msg = " "+user.getUserName()+"님이 진행 사항 게시판에 댓글을 등록하셨습니다.";
						sendSmS(bbs.getUserPhone(), msg, bbs.getUserName());
					}
					
				}else{
					WeekBbsComment bc = weekBbsCommentDao.getBbsComment(bbsCommentSeq);
					if(bc.getUserId().equals(user.getUserId())){
						bc.setBbsSeq(bbsSeq);
						bc.setBbsContents(bbsContents);
				
						bc.setUserId(user.getUserId());
						weekBbsCommentDao.updateBbsComment(bc);
						map.put("result", true);
						map.put("message", "수정 되었습니다.");
					}else{
						map.put("result", false);
						map.put("message", "수정 권한이 없습니다.");
					}
					
				}
	
				
				weekBbsDao.updateStatusBbs(bbsSeq,count);
				
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
	
	
	/////////////////////////////////////////공지게시판 (직원용)
	
	

	
	@RequestMapping("/proc/admin_bbs_list.go")
	public String adminbbsList(
		HttpServletRequest request,
		
	
		@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq,
		@RequestParam(value = "page", required = false, defaultValue="") int page,
		@RequestParam(value = "sort", required = false, defaultValue="") String sort,
		@RequestParam(value = "colName", required = false, defaultValue="") String colName,
		@RequestParam(value = "keyword", required = false, defaultValue="") String keyword,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List<AdminBbs>list=null;
		int count =0;
		if(user!=null){
			
			Project project = projectDao.getProject(projectSeq);

			
			list=adminBbsDao.getBbsList(keyword,sort,colName,projectSeq/*,bbsType,"0"*/,page,ITEM_COUNT_PER_PAGE);
			count=adminBbsDao.getBbsCount(keyword,projectSeq/*,bbsType,"0"*/,0);
			
			
			String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING,"adminBbs.getList","searchForm");
			
			map.put("paging", paging);
		
			map.put("list", list);
			//map.put("bbsType", bbsType);
			map.put("project", project);
			map.put("projectSeq", projectSeq);
			//총카운트 갯수
			map.put("count", count);
			map.put("currentPage", page);
			map.put("PERPAGE", ITEM_COUNT_PER_PAGE);
			
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
	
	
	@RequestMapping("/proc/admin_bbs_view.go")
	public String adminbbsview(
		HttpServletRequest request,
		@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
		@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
		//@RequestParam(value = "page", required = false, defaultValue="") int page,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		List<AdminBbsComment>list=null;
		int count =0;
		if(user!=null){
			
			Project project = projectDao.getProject(projectSeq);

			AdminBbs bbs =adminBbsDao.getBbs(bbsSeq);
			list=adminBbsCommentDao.getBbsCommentList(bbsSeq);
		//	count=bbsCommentDao.getBbsCommentCount(bbsSeq);
			List<AdminFiles>fileList= adminFilesDao.getBbsFilesList(bbsSeq);
			
		//	String paging = Paging.getPagingFunction(page, count, ITEM_COUNT_PER_PAGE,PAGE_COUNT_PER_PAGING,"bbs.view","searchForm");
			
			map.put("project", project);
			map.put("projectSeq", projectSeq);
			map.put("bbs", bbs);
		//	map.put("paging", paging);
	//		map.put("currentPage", page);
			map.put("bbsCommentSeq", 0);
			map.put("list", list);
			map.put("fileList", fileList);
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
	

	@RequestMapping("/proc/admin_bbs_edit.go")
	public String adminbbsEdit(
			HttpServletRequest request,
			@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
			@RequestParam(value = "projectSeq", required = false, defaultValue="0") int projectSeq, 
			HttpServletResponse res, Model model,HttpSession ss	
		) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		
		//User user = userDao.getUser(/*ss.getAttribute("USER_ID").toString()*/"jo");
		Project project = projectDao.getProject(projectSeq);
		User user = new User();
		AdminBbs bbs = null;
		try {
			user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		} catch (Exception e) {
			// TODO: handle exception
		}
	
		int count =0;

		bbs = adminBbsDao.getBbs(bbsSeq);
		
		List<AdminFiles>fileList= adminFilesDao.getBbsFilesList(bbsSeq);
		
		map.put("bbs", bbs);
		map.put("fileList", fileList);
		map.put("project", project);
		map.put("result", true);

		if(user!=null){
			
			//Bbs bbs =bbsDao.getBbs(bbsSeq);
		
			//List<BbsFiles>fileList= bbsfilesDao.getBbsFilesList(bbsSeq);
			
			map.put("bbs", bbs);
			//map.put("bbsType", 0);
			map.put("fileList", fileList);
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
	
	
	@RequestMapping("/proc/admin_bbs_edit_do.go")
	public String adminbbsEditDo(
		HttpServletRequest request,
		@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
		@RequestParam(value = "projectSeq", required = false, defaultValue="") int projectSeq,
	//	@RequestParam(value = "bbsType", required = false, defaultValue="1") String bbsType,//1요청2버그
	//	@RequestParam(value = "bbsType2", required = false, defaultValue="0") String bbsType2,//0전체 1내부공개
		@RequestParam(value = "bbsTitle", required = false, defaultValue="") String bbsTitle,
		@RequestParam(value = "bbsContents", required = false, defaultValue="") String bbsContents,
		@RequestParam(value = "fileName", required = false, defaultValue="") String[] fileName,
		@RequestParam(value = "sendSms", required = false, defaultValue="0") int sendSms,
		@RequestParam(value = "grade", required = false, defaultValue="0") String grade,
		HttpServletResponse res, Model model,HttpSession ss	
	) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			boolean chk=true;
			
			if(user!=null){
				if(bbsSeq==0){//등록
					AdminBbs bbs = new AdminBbs();
					bbs.setProjectSeq(projectSeq);
					//bbs.setBbsType(bbsType);
					//bbs.setBbsType2(bbsType2);
					bbs.setBbsTitle(bbsTitle);
					bbs.setBbsContents(bbsContents);
					bbs.setSendSms(sendSms);
					bbs.setGrade(grade);
					bbs.setUserId(user.getUserId());
					bbsSeq =adminBbsDao.addBbs(bbs);
					Project pp =projectDao.getProject(projectSeq);
					map.put("result", true);
					map.put("message", "등록되었습니다.");
					List<ProjectAdmin>list =projectAdminDao.getProjectAdminList(projectSeq);
					for(int i=0;i<list.size();i++){
						
						ProjectAdmin pa = list.get(i);
						if(!user.getUserId().equals(pa.getUserId())){
							String msg ="["+pp.getProjectName()+"] 새로운 공지 글이 등록되었습니다."; 
							sendSmS(pa.getUserPhone(),msg,pa.getUserName());
						}
					}
					
				}else{//수정
					AdminBbs bbs = adminBbsDao.getBbs(bbsSeq);
					if(bbs.getUserId().equals(user.getUserId())){
						bbs.setProjectSeq(projectSeq);
						//bbs.setBbsType(bbsType);
						//bbs.setBbsType2(bbsType2);
						bbs.setBbsTitle(bbsTitle);
						bbs.setBbsContents(bbsContents);
						bbs.setSendSms(sendSms);
						bbs.setGrade(grade);
						List <AdminFiles>list =adminFilesDao.getBbsFilesList(bbsSeq);
						
						for(int i=0;i<list.size();i++){
							AdminFiles bf=list.get(i);
							File file = new File(FILE_ROOT + bf.getFileName());
							file.delete();
						}
						
						adminFilesDao.deleteBbsFiles(bbsSeq);
						adminBbsDao.updateBbs(bbs);
						map.put("result", true);
						map.put("message", "수정 되었습니다.");
					}else{
						chk=false;
						map.put("result", false);
						map.put("message", "수정 권한이 없습니다.");
					}
				}
				if(chk){
					for (int i=0; i<fileName.length; i++) {
						if(!fileName[i].equals("")){
							String filename=fileName[i];
							File file = new File(FILE_ROOT + filename);
							String type = filename.substring(filename.lastIndexOf("."));
							if(type.equals(".jpg")||type.equals(".png")||type.equals(".jpeg")||type.equals(".bmp")){
								type ="img";
							}else{
								type ="text";
							}
							
							AdminFiles bf = new AdminFiles();
							bf.setBbsSeq(bbsSeq);
							bf.setFileName(filename);
							bf.setFileExt(type);
							adminFilesDao.addBbsFiles(bf);
						}
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
	@RequestMapping("/proc/admin_file_delete.go")
	public String adminfileDeleteDoController(
			@RequestParam(value="bbsSeq", required=false, defaultValue="0") int bbsSeq,
			@RequestParam(value="fileName", required=false, defaultValue="") String fileName,
			HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		if(user!=null){
			AdminBbs bbs =adminBbsDao.getBbs(bbsSeq);
		
			if(bbsSeq==0||bbs.getUserId().equals(user.getUserId())||user.getUserType()==1){
		
				// 파일 삭제
				File file = new File(FILE_ROOT + fileName);
				file.delete();
				adminFilesDao.deleteBbsFiles(bbsSeq, fileName);
			
				map.put("result", true);
				map.put("message", "파일이 삭제되었습니다.");
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
		
	
	
	
	@RequestMapping("/proc/admin_bbs_delete.go")
	public String adminbbsDeleteDoController(
			
			@RequestParam(value="bbsSeq", required=false, defaultValue="0") int bbsSeq,
			@RequestParam(value="bbsCommentSeq", required=false, defaultValue="0") int bbsCommentSeq,
			HttpServletResponse res,HttpSession ss
		){
		HashMap<String, Object> map = new HashMap<String, Object>();
		User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
		if(user!=null){
			AdminBbs bbs =adminBbsDao.getBbs(bbsSeq);
			AdminBbsComment bc =adminBbsCommentDao.getBbsComment(bbsCommentSeq);
			if(bbs.getUserId().equals(user.getUserId())||bc.getUserId().equals(user.getUserId())){
				if(bbsCommentSeq==0){ ///본글삭제
					
					// 파일 삭제
					List <AdminFiles>list =adminFilesDao.getBbsFilesList(bbsSeq);
					int fileDeleteCount = 0;		
					String FILE_PATH = "/data/bbs/";
					String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
					for(int i=0; i<list.size(); i++) {
						AdminFiles bf = list.get(i);
						File file = new File(FILE_ROOT + bf.getFileName());
						file.delete();
					}
					adminFilesDao.deleteBbsFiles(bbsSeq);
					adminBbsCommentDao.deleteBbsCommentBbsSeq(bbsSeq);
					adminBbsDao.deleteBbs(bbsSeq);
					
				}else{
					adminBbsCommentDao.deleteBbsComment(bbsCommentSeq);
					int count = adminBbsCommentDao.getBbsCommentCount(bbsSeq);
					
					adminBbsDao.updateStatusBbs(bbsSeq,count);	
				}
			
				
		
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
	
	
	@RequestMapping("/proc/admin_bbs_comment_edit_do.go")
	public String adminbbsCommentEditDo(
		HttpServletRequest request,
		
		@RequestParam(value = "bbsSeq", required = false, defaultValue="0") int bbsSeq,
		@RequestParam(value = "bbsCommentSeq", required = false, defaultValue="0") int bbsCommentSeq,
		@RequestParam(value = "bbsContents", required = false, defaultValue="") String bbsContents,
	

		HttpServletResponse res, Model model,HttpSession ss	
	) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		try{
			User user = userDao.getUser(ss.getAttribute("USER_ID").toString());
			int count = adminBbsCommentDao.getBbsCommentCount(bbsSeq);
			if(user!=null){
				if(bbsCommentSeq==0){
					AdminBbsComment bc = new AdminBbsComment();
					bc.setBbsSeq(bbsSeq);
					bc.setBbsContents(bbsContents);
				
					bc.setUserId(user.getUserId());
					adminBbsCommentDao.addBbsComment(bc);
					count =count+1;
				
					map.put("result", true);
					map.put("message", "등록 되었습니다.");
					
				
				
					
				}else{
					AdminBbsComment bc = adminBbsCommentDao.getBbsComment(bbsCommentSeq);
					if(bc.getUserId().equals(user.getUserId())){
						bc.setBbsSeq(bbsSeq);
						bc.setBbsContents(bbsContents);
				
						bc.setUserId(user.getUserId());
						adminBbsCommentDao.updateBbsComment(bc);
						map.put("result", true);
						map.put("message", "수정 되었습니다.");
					}else{
						map.put("result", false);
						map.put("message", "수정 권한이 없습니다.");
					}
					
				}
	
				
				adminBbsDao.updateStatusBbs(bbsSeq,count);
				
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
	
	
	
	
	
	
	/////////////////////////////////////////
	@RequestMapping("/m/sms.go")
	public String smsController(

			
		
			HttpServletResponse res,HttpSession ss
		){
		try {
			sendSmS("01020190889", "ddd", "신주연");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
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
