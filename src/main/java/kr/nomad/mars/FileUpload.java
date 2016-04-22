package kr.nomad.mars;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import kr.nomad.util.F;
import kr.nomad.util.FileWrite;
import kr.nomad.util.ImageUtil;
import kr.nomad.util.Response;
import kr.nomad.util.file.UniqFileRenamePolicy;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import com.google.gson.Gson;
import com.oreilly.servlet.MultipartRequest;
import com.oreilly.servlet.multipart.DefaultFileRenamePolicy;

@Controller
public class FileUpload {
	

	// 파일 경로
	@Value("#{config['file.root']}")
	String FILE_ROOT;
	
	// 파일 최대크기(Mb)
	@Value("#{config['file.maxSize']}")
	int FILE_MAX_SIZE;
	
	String FILE_BBS = "";
	
	
	@RequestMapping("/file_upload.go")
	public String fileUploadController(@RequestParam("filePath") String filePath, HttpServletRequest req, HttpServletResponse res, Model model) {
		
		Map<String, Object> map = new HashMap<String, Object>();
		boolean result = false;
		int fileMaxBiteSize = FILE_MAX_SIZE * 1024 * 1024;
		
		String message = "";
		String fileName = "";
		String uploadFullPath = FILE_ROOT +"/" + filePath +"/";
		File file = null;
		int seq = 0;

		try {
			req.setCharacterEncoding("UTF-8");
			res.setCharacterEncoding("UTF-8");
			
			File folder = new File(uploadFullPath);
			if(folder.exists() == false && folder.mkdirs() == false) {
				return null;
			}
			String encoding = "UTF-8";      		
			MultipartRequest multi = new MultipartRequest(req, uploadFullPath, fileMaxBiteSize, encoding, new DefaultFileRenamePolicy());
			
 			// 업로드한 파일들을 Enumeration 타입으로 반환.
			// Enumeration형은 데이터를 뽑아올때 유용한 인터페이스
			Enumeration files = multi.getFileNames();
			
			while (files.hasMoreElements()) {
				String elementName = (String)files.nextElement();
				file = multi.getFile(elementName);
				if (file != null) {
					fileName = file.getName();
				}
			}
			/* 파일이름 재설정*/
			if (fileName.contains(".")==false) {
				fileName += ".jpg";
				File reNameFile = new File(uploadFullPath + fileName);
				file.renameTo(reNameFile);
			}
			
			result = true;
			message = "success";
			
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
			message = e1.getMessage();
		} catch (IOException e) {
			e.printStackTrace();
			message = e.getMessage();
		}    

		map.put("result", result);
		map.put("message", message);
		map.put("filePath", filePath);
		map.put("fileName", fileName);
		
		JSONObject jsonObject = JSONObject.fromObject(map);
		
		Gson gson = new Gson();
	    String outputstr = gson.toJson(jsonObject);
		Response.responseWrite(res, outputstr);

		return null;
	}
	
	
	

	/********************************************************************************
	 * Smart Editor용 파일 업로드
	 * @param req
	 * @param res
	 * @param session
	 * @param model
	 * @return
	 */
	@RequestMapping("/smart_file_upload.go")
	public String fileUploadController(
			HttpServletRequest req, 
			HttpServletResponse res, 
			Model model,
			HttpSession session
		) throws IllegalStateException, IOException {
		req.setCharacterEncoding("utf-8");
		
		String callback = "";
		String callback_func = "";
		String URL_IN = "";
		String FILE_PATH = "/files/imagefile/";
		String FILE_PATH_VIR = "/files/imagefile/";

		String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
		int fileMaxBiteSize = FILE_MAX_SIZE * 1024 * 1024;
		String uploadFileName = "";
		Object object = session.getAttribute("USER_ID");


		
		if(object != null && object.toString().equals("") == false) {
		
			
			File file = null;
			try {			
				//MultipartRequest multi = new MultipartRequest(req, FILE_LOCAL_PATH, fileMaxBiteSize, "UTF-8", new DefaultFileRenamePolicy()); //
				MultipartRequest multi = new MultipartRequest(req, FILE_LOCAL_PATH, fileMaxBiteSize, "UTF-8", new UniqFileRenamePolicy()); //

				// 폼에서 입력한 값을 가져옴
				callback = F.nullCheck(multi.getParameter("callback"),"");
				callback_func = F.nullCheck(multi.getParameter("callback_func"),"");
				URL_IN = F.nullCheck(multi.getParameter("urlHost"),"");
				 
				Enumeration files = multi.getFileNames();
				while (files.hasMoreElements()) {
					String elementName = (String)files.nextElement();
					file = multi.getFile(elementName);
					
					if (file != null) {
						uploadFileName = file.getName();
					}
				}
			} catch (Exception e) {
				e.getMessage();
			}
			
			/*
			String fileName = multipartFile.getOriginalFilename();
			FileWrite file = new FileWrite();
			String uploadFileName = file.writeFileTempName(multipartFile, FILE_LOCAL_PATH, fileName);
			uploadFileName = URLEncoder.encode(uploadFileName,"UTF-8");
			// 축소이미지 저장
			File newFile = new File(FILE_LOCAL_PATH + uploadFileName);
			File thumbFile = new File(FILE_LOCAL_PATH + uploadFileName);
			if (!thumbFile.exists()) {
				thumbFile.mkdirs();
			}
			try {
				ImageUtil.resize(newFile, thumbFile, 800, 0);
			} catch (IOException e) {
				e.printStackTrace();
			}
			 */
		}
		
		String errstr = "";
		if(uploadFileName.equals("")) {
			errstr = "파일 업로드에 실패 하였습니다.";
		}
		String result = "";
		String params = "";
		
		String bNewLine = "";
		String sFileName = "";
		String sFileURL = "";
		
		params = "&errstr=&bNewLine=true&sFileName="+uploadFileName+"&sFileURL="+ URL_IN + FILE_PATH_VIR + uploadFileName;
		bNewLine = "true";
		sFileName = uploadFileName;
		sFileURL = FILE_PATH_VIR + "/"+ uploadFileName;
		
		result = callback + "?callback_func="+callback_func+ params;
		//return result;
		model.addAttribute("callback", callback);
		model.addAttribute("callback_func", callback_func);
		model.addAttribute("errstr", errstr);
		model.addAttribute("bNewLine", bNewLine);
		model.addAttribute("sFileName", sFileName);
		model.addAttribute("sFileURL", sFileURL);
		model.addAttribute("domain", FILE_ROOT);
		model.addAttribute("params", "?callback_func="+callback_func+ params);
		return "smart_file_upload_callback";
	}
	
	
	
//	 * Smart Editor용 파일 업로드
//	 * @param req
//	 * @param res
//	 * @param session
//	 * @param model
//	 * @return
//	@RequestMapping("/smart_file_upload.go")
//	public String fileUploadController(
//			@RequestParam(value="callback", required=false, defaultValue="") String callback,
//			@RequestParam(value="callback_func", required=false, defaultValue="") String callback_func,
//			@RequestParam(value="urlHost", required=false, defaultValue="") String urlHost,
//			@RequestParam(value="filePath", required=false, defaultValue="") String filePath,
//			@RequestParam(value="Filedata", required=false) MultipartFile multipartFile,
//			HttpServletRequest req, 
//			HttpServletResponse res, 
//			Model model
//		) throws IllegalStateException, IOException {
//
//		req.setCharacterEncoding("utf-8");
//
//		String FILE_PATH = filePath;
//		String FILE_LOCAL_PATH = FILE_ROOT + FILE_PATH;
//		String fileName = multipartFile.getOriginalFilename();
//		FileWrite file = new FileWrite();
//		String uploadFileName = file.writeFileTempName(multipartFile, FILE_LOCAL_PATH, fileName);
//		uploadFileName = URLEncoder.encode(uploadFileName,"UTF-8");
//
//
//		// 축소이미지 저장
//		File newFile = new File(FILE_LOCAL_PATH + uploadFileName);
//		File thumbFile = new File(FILE_LOCAL_PATH + uploadFileName);
//		if (!thumbFile.exists()) {
//			thumbFile.mkdirs();
//		}
//		try {
//			ImageUtil.resize(newFile, thumbFile, 800, 0);
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
//
//		
//		String result = "";
//		String params = "";
//		String errstr = "";
//		String bNewLine = "";
//		String sFileName = "";
//		String sFileURL = "";
//		
//		params = "&errstr=&bNewLine=true&sFileName="+uploadFileName+"&sFileURL="+ urlHost + FILE_PATH + uploadFileName;
//		bNewLine = "true";
//		sFileName = uploadFileName;
//		sFileURL = FILE_PATH + "/"+ uploadFileName;
//		
//		result = callback + "?callback_func="+callback_func+ params;
//		//return result;
//		model.addAttribute("callback", callback);
//		model.addAttribute("callback_func", callback_func);
//		model.addAttribute("errstr", errstr);
//		model.addAttribute("bNewLine", bNewLine);
//		model.addAttribute("sFileName", sFileName);
//		model.addAttribute("sFileURL", sFileURL);
//		model.addAttribute("domain", FILE_ROOT);
//		model.addAttribute("params", "?callback_func="+callback_func+ params);
//		return "smart_file_upload_callback";
//	}
	 


}
