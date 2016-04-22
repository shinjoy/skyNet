package kr.nomad.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.google.gson.Gson;

public class Response {
	boolean result = false;	
	String msg = "";
	
	public void setResponse(boolean result, String msg) {
		this.result = result;
		this.msg = msg;
	}
	
	public boolean isResult() {
		return result;
	}
	public void setResult(boolean result) {
		this.result = result;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	

	public static void responseWrite(HttpServletResponse response, String msg) {
		PrintWriter pw;
		try {
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.println(msg);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void responseWrite(HttpServletResponse response, JSONObject jsonObject) {
		PrintWriter pw;
		try {
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.println(outputStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void responseWrite(HttpServletResponse response, Map map) {
		PrintWriter pw;
		try {
		    JSONObject jsonObject = JSONObject.fromObject(map);
			Gson gson = new Gson();
			String outputStr = gson.toJson(jsonObject);
			response.setCharacterEncoding("UTF-8");
			pw = response.getWriter();
			pw.println(outputStr);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
