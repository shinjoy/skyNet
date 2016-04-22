package kr.nomad.util.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.UUID;

public class MovieConverter {
	
	private String fileName, basePath, uploadPath;
	
	static final String FFMPEG_PATH = "C:/TEMP/webroot/ffmpeg/ffmpeg.exe";
	
	public MovieConverter(String fileName, String basePath, String uploadPath) {
		this.fileName = fileName;
		this.basePath = basePath;
		this.uploadPath = uploadPath;
	}
	
	public String convert() {

		//String ffmpegPath = "/data/movie/work/ffmpeg.exe"; 		// 예) /work/ffmpeg
		//String ffmpegPath = "D:/00_WorkSpace/LingTalk/SOURCE/WEB/HuuiM/src/main/webapp/data/movie/work/ffmpeg.exe"; 		// 예) /work/ffmpeg
		//String fOriginal = "/data/movie/upload/"+movieFileName; 			// 실시간으로 업로드되는 파일
		//String fResult = "/data/movie/"+movieFileName.substring(0, movieFileName.lastIndexOf("."))+".flv"; 			// 인코딩하고 저장 할 파일위치

		
		File fOriginal = new File(""+uploadPath+fileName);
		UUID randomeUUID = UUID.randomUUID();//중복 파일명 방지
		String outputName =  randomeUUID.getLeastSignificantBits()+".mp4";
		//String outputName = fileName.substring(0, fileName.indexOf("."))+ ".mp4";
		File fResult = new File(basePath+outputName);
		String ffmpegPath = FFMPEG_PATH;


		/*
		String[] cmdLine = new String[] { 
			ffmpegPath,			// 변환시킬 파일위치 
			"-i",
			fOriginal.getPath(),
			"-ar",
			"11025",
			"-f",
			"mp4", 				// mp4파일 형태로 출력
			fResult.getPath() 			// 저장하는 위치입니다.
		};
		*/ 
		
		String[] cmdLine = new String[] { 
				ffmpegPath,			// 변환시킬 파일위치 
				"-i",
				fOriginal.getPath(),
				"-ar",
				"11025",
				"-ab",
				"32",
				"-s",
				"500x300", 			// 화면 사이즈
				"-b",
				"768k", 			// 비트레이트
				"-r",
				"24", 				// 영상 프레임
				"-y",
				"-f",
				"mp4", 				// flv파일 형태로 출력
				fResult.getPath() 			// 저장하는 위치입니다.
			}; 
		
		
		 
		// 프로세스 속성을 관리하는 ProcessBuilder 생성.
		ProcessBuilder pb = new ProcessBuilder(cmdLine);
		pb.redirectErrorStream(true);
		Process p = null;
		 
		try {
			// 프로세스 작업을 실행함.
			p = pb.start();		 
		} catch (Exception e) {        
			e.printStackTrace();
			p.destroy();
			return null;
		}
		 
		exhaustInputStream(p.getInputStream());   // 자식 프로세스에서 발생되는 인풋 스트림 소비시킴;;
		 
		try {
			// p의 자식 프로세스의 작업이 완료될 동안 p를 대기시킴
			p.waitFor();
		} catch (InterruptedException e) {
			p.destroy();
		}
		 
		// 정상 종료가 되지 않았을 경우
		if (p.exitValue() != 0) {
			System.out.println("변환 중 에러 발생 : "+ p.exitValue());
			return null;
		}
		// 변환을 하는 중 에러가 발생하여 파일의 크기가 0일 경우
		if (fResult.length() == 0) {
			System.out.println("변환된 파일의 사이즈가 0임");
			return null;
		}
		System.out.println("변환 성공 ^^");
		
		// 섬네일 추출
		/*
		File movieFile = new File(basePath + outputName);
		File movieThumb = new File(basePath + "thumb/" + outputName.substring(0, outputName.indexOf("."))+ ".jpg");
		movieThumb = extractImage(movieFile, 1, movieThumb);
		*/
		
		fOriginal.delete(); // 원본 파일 삭제
		return outputName;
		
		/*
		String cmdLine = " "
				+ ffmpegPath +" "			// 변환시킬 파일위치 
				+ "-i "
				+ fOriginal +" "
				+ "-ar "
				+ "44100 "
				+ "-ab "
				+ "32 "
				+ "-s "
				+ "500x300 " 			// 화면 사이즈
				+ "-b "
				+ "768k "
				+ "-r "
				+ "24 "
				+ "-y "
				+ "-f "
				+ "flv "
				+ fResult;
		Runtime r = Runtime.getRuntime();
		Process p = null;
		try {
			p = r.exec(cmdLine); // 동영상 변환 명령어 실행시키고 부모 프로세스(자바) 를 얻는다.
			p.waitFor();  // 서브 프로세스 (ffmepg) 가 종료할 때 까지 메인 프로세스를 잠시 대기시킨다.
		} catch (InterruptedException e) {
			p.destroy(); // 서브 프로세스를 강제로 종료시킴.
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		 
		if (p.exitValue() != 0) {
		System.out.println("변환 중 에러 발생");
		// 정상 종료가 되지 않았을 경우 로직처리
		}
		 
		if (fResult.length() == 0) {
		System.out.println("변환된 파일의 사이즈가 0임");
		// 변환을 하는 중 에러가 발생하여 파일의 크기가 0일 경우 로직 처리
		}
		// 변환 성공 시 로직 처리
		System.out.println("변환 성공 ^^");
		
		p.destroy();

		String[] cmdLine = new String[] { 
			ffmpegPath,			// 변환시킬 파일위치 
			"-i",
			fOriginal,
			"-ar",
			"44100",
			"-ab",
			"32",
			"-s",
			"500x300", 			// 화면 사이즈
			"-b",
			"768k", 			// 비트레이트
			"-r",
			"24", 				// 영상 프레임
			"-y",
			"-f",
			"flv", 				// flv파일 형태로 출력
			fResult 			// 저장하는 위치입니다.
		}; 
		
		// 프로세스 속성을 관리하는 ProcessBuilder 생성.
		ProcessBuilder pb = new ProcessBuilder(cmdLine);
		pb.redirectErrorStream(true);
		Process p = null;
		try {
			// 프로세스 작업을 실행
			p = pb.start();
		} catch (Exception e) {
			e.printStackTrace();

			p.destroy();
			return null;
		}

		exhaustInputStream(p.getInputStream()); // 자식 프로세스에서 발생되는 inputstrem를 소비시켜야합니다.
		try {
			// p의 자식 프로세스의 작업이 완료될 동안 p를 대기시킴
			p.waitFor();
		} catch (InterruptedException e) {
			p.destroy();
		}

		// 정상 종료가 되지 않았을 경우
		if (p.exitValue() != 0) {
			System.out.println("변환 중 에러 발생");
			return null;
		}

		// 변환을 하는 중 에러가 발생하여 파일의 크기가 0일 경우
		if (fResult.length() == 0) {
			System.out.println("변환된 파일의 사이즈가 0임");
			return null;
		}
		
		fOriginal.delete(); // 원본 파일 삭제

		p.destroy();
		*/
		//return fResult;

	}

	private void exhaustInputStream(final InputStream is) {

		// InputStream.read() 에서 블럭상태에 빠지기 때문에 따로 쓰레드를 구현하여 스트림을 소비한다
		try {
			BufferedReader br = new BufferedReader(new InputStreamReader(is));
			String cmd = null;
			while ((cmd = br.readLine()) != null) { // 읽어들일 라인이 없을때까지 계속 반복
				 System.out.println(cmd);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
	public File extractImage(File videoFile, int position,
			File creatingImageFile) {
		try {
			int seconds = position % 60;
			int minutes = (position - seconds) / 60;
			int hours = (position - minutes * 60 - seconds) / 60 / 60;

			String videoFilePath = videoFile.getAbsolutePath();
			String imageFilePath = creatingImageFile.getAbsolutePath();
			String ffmpegPath = basePath+"work/ffmpeg.exe";

			String[] commands = { ffmpegPath, "-ss",
					String.format("%02d:%02d:%02d", hours, minutes, seconds),
					"-i", videoFilePath, "-an", "-vframes", "1", "-y",
					imageFilePath };

			Process processor = Runtime.getRuntime().exec(commands);

			String line1 = null;
			BufferedReader error = new BufferedReader(new InputStreamReader(
					processor.getErrorStream()));
			while ((line1 = error.readLine()) != null) {
				//logger.debug(line1);
			}
			processor.waitFor();
			int exitValue = processor.exitValue();
			if (exitValue != 0) {
				throw new RuntimeException("exit code is not 0 [" + exitValue
						+ "]");
			}
			return creatingImageFile;
		} catch (IOException e) {
			throw new RuntimeException(e);
		} catch (InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
