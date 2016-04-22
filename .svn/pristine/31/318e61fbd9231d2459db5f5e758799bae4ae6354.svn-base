package kr.nomad.util;

import java.io.File;

import org.apache.tika.Tika;

public class FileMime {
	
	public static String getMimeType(File file) {

		String mimeType = "";
		
		try {
			Tika tika = new Tika();
			mimeType = tika.detect(file);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mimeType;
	}
}
