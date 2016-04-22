package kr.nomad.mars;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.nomad.util.F;
import kr.nomad.util.Response;
import net.sf.json.JSONObject;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

public class RequestInitializeInterceptor extends HandlerInterceptorAdapter {
	
	// Interceptor 예외
	String[] interceptorPass = {
		"/login.go",
		"/admin/login.go"
	};
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		HttpSession session = request.getSession();
		String userId = "";
		if (session != null) {
			userId = F.nullCheck(session.getAttribute("USER_ID"));
		}
		String uri = request.getRequestURI();		
		
		if (uri.substring(0, 2).equals("m_") || uri.substring(0, 3).equals("/m_")) {
			return true;	// 통과
		}
		
		if (uri.substring(0, 2).equals("i_") || uri.substring(0, 3).equals("/i_")) {
			return true;	// 통과
		}
		
		// 예외 페이지는 그냥 통과
		for (int i=0; i<interceptorPass.length; i++) {
			if (uri.equals(interceptorPass[i])) {
				return true;	// 통과
			}
		}
		
		// 어드민 접속일때 아이디 없거나 권한 없으면 로그인 페이지로 이동.
		if (userId.equals("") == false) {
			return true;	// 통과
		} else {
		    response.sendRedirect("/logoutTop.jsp");	// top frame에서 로그인 페이지를 연다.
			//response.sendRedirect("/logout_do.go");	// top frame에서 로그인 페이지를 연다.
			return false;	// 딱걸렸어!!
		}
	}

}
