package kr.nomad.mars;

import java.util.HashMap;
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

import kr.nomad.mars.dao.UserDao;
import kr.nomad.mars.dto.User;
import kr.nomad.util.Response;
import kr.nomad.util.encrypt.CryptoNew;
import kr.nomad.util.encrypt.CryptoSeedData;
import net.sf.json.JSONObject;



@Controller
public class LoginController {
	
	@Autowired
	UserDao userDao;
	
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
	
	
	// �α��� 
	@RequestMapping("/admin/login.go")
	public String login1() {
		return "/admin/login";
	}
	
	// 로그인 처리
	@RequestMapping("/login_do.go")
	public String loginProc(HttpServletRequest request,
			@RequestParam(value = "loginId", required = false) String userId,
			@RequestParam(value = "loginPw", required = false) String password,
		
			HttpServletResponse res, Model model) {

		Map<String, Object> map = new HashMap<String, Object>();

		String enPw = "";
		try {
			int userCheck = userDao.correctId(userId);
			if (userCheck > 0) {

				// enPw = Sha256Util.encryptPassword(password);
				enPw = CryptoNew.encrypt(password);

				int loginCheck = userDao.getUserChk(userId, enPw);

				
				if (loginCheck > 0) {

					User user = userDao.getUserCompany(userId);
					
					if(user.getUserType()==1){
						HttpSession session = request.getSession();
						session.setAttribute("USER_ID", user.getUserId());
						session.setAttribute("USER_NAME", user.getUserName());
						session.setAttribute("USER_TYPE", user.getUserType());
						
	
						map.put("message", "Login Success");
						map.put("result", true);
						map.put("userType", user.getUserType());
					}else{
						map.put("message", "해당 아이디는 접속 권한이 없습니다.");
						map.put("result", false);
						map.put("userType", 0);
					}

				} else {
					map.put("message", "비밀번호가 일치하지 않습니다.");
					map.put("result", false);
					map.put("userType", 0);
				}
				
				

			} else {
				map.put("message", "해당 ID가 존재하지 않습니다.");
				map.put("result", false);
				map.put("userType", 0);
			}
		} catch (Exception e) {
			map.put("message", e.getMessage());
			map.put("result", false);
			map.put("userType", 0);
		}

		JSONObject jsonObject = JSONObject.fromObject(map);
		Response.responseWrite(res, jsonObject);
		return null;
	}
			
			/**
			 * 로그아웃 처리<br>
			 * "/logout_do.go"
			 * 
			 * @return "redirect:/login.go"
			 */

			// 로그아웃
			@RequestMapping("/logout_top.go")
			public String logout(HttpServletRequest request) {

				HttpSession session = request.getSession();
				session.removeAttribute("USER_ID");
				session.removeAttribute("USER_NAME");
				session.removeAttribute("USER_TYPE");

				return "logoutTop";
			}

			// 로그아웃
			@RequestMapping("/logout_do.go")
			public String logoutDoController(HttpServletRequest request) {

				HttpSession session = request.getSession();
				session.removeAttribute("USER_ID");
				session.removeAttribute("USER_NAME");
				session.removeAttribute("USER_TYPE");

				return "redirect:/admin/login.go";
			}
			/**
			 * 관리자 비밀번호 변경
			 * @param userId
			 * @param model
			 * @return
			 */
			@RequestMapping("/admin/user_edit_password.go")
			public String adminPasswordEditController(@RequestParam(value = "userId", required = false) String userId, Model model, HttpSession session) {

				User user = userDao.getUser(userId);

				model.addAttribute("user", user);
				return "admin/user_edit_password";
			}
			

			/**
			 * 관리자 비밀번호 변경 처리
			 * @param userId
			 * @param newPassword
			 * @param model
			 * @return
			 */
			@RequestMapping("/admin/user_edit_password_do.go")
			public String adminEditPasswordDoController(
					@RequestParam(value = "userId", required = false) String userId, 
					@RequestParam String oldPassword, 
					@RequestParam String newPassword, Model model) {
				
				try {
					
					//String enPw = Sha256Util.encryptPassword(oldPassword);
					String enPw = CryptoNew.encrypt(oldPassword);
					int loginCheck = userDao.getUserChk(userId, enPw);
					if (loginCheck > 0) {
						int userCheck = userDao.correctId(userId);
						if (userCheck > 0) {
							
							//String Pw = Sha256Util.encryptPassword(newPassword);
							String Pw = CryptoNew.encrypt(newPassword);
							//userDao.updateUserPassword(userId, Pw);
							model.addAttribute("msg", "비밀번호가 변경되었습니다.");
							return "/admin/main";
						} else {
							model.addAttribute("msg", "회원 계정을 확인할 수 없습니다.");
							return "/admin/user_edit_password";
						}
					} else {
						model.addAttribute("msg", "기존 비밀번호가 올바르지 않습니다.");
						return "/admin/user_edit_password";
					}
					
				
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				
				return null;
				
			}
		
	
}
