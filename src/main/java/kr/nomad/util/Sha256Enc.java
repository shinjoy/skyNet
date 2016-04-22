package kr.nomad.util;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Random;

import org.bouncycastle.util.encoders.Base64;


public class Sha256Enc {

	public static String getHeshKey(String input) {
        String result = "";
		try {
			MessageDigest md = MessageDigest.getInstance("SHA-256");
		
			md.update(input.getBytes());
	        byte byteData[] = md.digest();
	        
	        byteData = Base64.encode(byteData);	        
	        result = new String(byteData);
	        
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
        
        return result;
	}
	
	

	private static final char[] KEY_LIST = { 
		'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 
        'a', 'b','c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', 'l', 
        'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v', 'w','x','y','z'            
	};
	private static Random rnd = new Random();
	public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMddHHmmssSSS");		
	
	private static String getRandomStr(int size){
		String result = "";
		
		for (int i = 0 ; i < size ; i++) {
			result += KEY_LIST[rnd.nextInt(36)];
		}
			
		return result;		 		
	}
	
	public static String getRandomKey(int size) {
		String key = getRandomStr(size);
		return getHeshKey(key);
	}
}
