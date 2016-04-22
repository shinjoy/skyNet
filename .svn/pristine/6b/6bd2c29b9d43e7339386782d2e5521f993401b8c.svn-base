/**
 * [출처] http://blog.naver.com/PostView.nhn?blogId=gigar&logNo=60103979416
 
  1. 아래와 같이 viewResolver를 설정한다.
		 <!-- FileDownloadView클래스의 bean객체 설정 -->
		 <!-- id는 쓰일 이름을 적어주고 class에는 실제 FileDownloadView의 정보를 써주면 된다 -->
		 <bean id="fileDownloadView" class="test.veiw.FileDownloadView" />
		 
		 <!-- 따로 만든 커스텀 view를 쓰기 위한 viewResolver 설정 -->
		 <bean id="beanNameViewResolver"
		         class="org.springframework.web.servlet.view.BeanNameViewResolver" >
		   <property name="order"><value>0</value></property>
		 </bean>
		 
  2. 컨트롤러에서 다음과 같이 사용한다.
		@RequestMapping("/fileDownload.go")
		public String fileDownloadController(@RequestParam String fileDir, @RequestParam String fileName, Model model) {
			File file = new File(filePath +"/"+ fileDir +"/"+ fileName);
			
			model.addAttribute("file", file);
			return "fileDownloadView";
		}
  3. 다운로드 링크는 다음과 같이 연결한다.
		<a type="button" href="/fileDownload.go?fileDir=${rl.fileDir}&fileName=${rl.fileName}" class="button round green">다운로드</a>
 */

package kr.nomad.util.file;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.AbstractView;

public class FileDownloadView extends AbstractView {

	private Logger log = null;
	 
	public FileDownloadView(){
		super.setContentType("application/octet-stream");
		log = Logger.getLogger(this.getClass());
	}

	@Override
	protected void renderMergedOutputModel(Map model, HttpServletRequest request, HttpServletResponse response) throws Exception {
		// Controller에서 넘어온 파일 정보를 추출한다
		// 여기서 Map인 model객체는 Controller에서 ModelAndView객체에 addObject하여 넘어온 정보다.
		File file = (File)model.get("file");
		
		response.setContentType(getContentType());
		response.setContentLength((int)file.length());
		response.setHeader("Content-Transfer-Encoding", "binary");
		response.setHeader("Content-Disposition", "attachment;fileName=\""+file.getName()+"\";");
		        
		OutputStream out = response.getOutputStream();
		FileInputStream fis = null;
		try{
			fis = new FileInputStream(file);
			FileCopyUtils.copy(fis,out);
		} catch(java.io.IOException ioe) {
			ioe.printStackTrace();
		} finally {
			if(fis != null) fis.close();
		}
		out.flush();
	}

}
