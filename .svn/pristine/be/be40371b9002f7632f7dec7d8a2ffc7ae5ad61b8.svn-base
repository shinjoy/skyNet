package kr.nomad.util;

import java.io.FileOutputStream;
import java.util.UUID;

import org.apache.log4j.Logger;
import org.springframework.web.multipart.MultipartFile;

public class FileWrite {

	Logger logger = Logger.getLogger(getClass());
	
	private FileOutputStream fos;
	
	public String writeFile(MultipartFile file, String path, String fileName){
		String rtnFileName = null;
		try{
			byte fileData[] = file.getBytes();
			
			 UUID randomeUUID = UUID.randomUUID();//중복 파일명 방지
			 rtnFileName =  randomeUUID.getLeastSignificantBits()+"_"+fileName;
			fos = new FileOutputStream(path + "\\" + rtnFileName);
			fos.write(fileData);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if( fos != null){
				try{
					fos.close();
				}catch(Exception e)
				{
					
				}
			}
		}
		return rtnFileName;
	}
	public String writeFileTempName(MultipartFile file, String path, String fileName){
		String rtnFileName = null;
		try{
			byte fileData[] = file.getBytes();
			
			String fileExt = fileName.split("[.]")[fileName.split("[.]").length-1];
			
			UUID randomeUUID = UUID.randomUUID();//중복 파일명 방지
			rtnFileName =  Math.abs(randomeUUID.getLeastSignificantBits())+"."+fileExt;
			fos = new FileOutputStream(path + "\\" + rtnFileName);
			fos.write(fileData);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			if( fos != null){
				try{
					fos.close();
				}catch(Exception e)
				{
					
				}
			}
		}
		return rtnFileName;
	}
}
