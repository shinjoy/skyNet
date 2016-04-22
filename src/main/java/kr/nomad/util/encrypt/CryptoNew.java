package kr.nomad.util.encrypt;

import java.io.UnsupportedEncodingException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import javax.xml.bind.DatatypeConverter;

public class CryptoNew {
	private static byte[] getRawKeyBytes() {
		String key = "98hytgfmxra3205l";		
		
		byte[] keyBytes = new byte[16];
		byte[] b = key.getBytes();
		int len = b.length;
		if (len > keyBytes.length)
			len = keyBytes.length;
		System.arraycopy(b, 0, keyBytes, 0, len);

		return keyBytes;
	}

	public static byte[] encrypt(byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//

		byte[] keyBytes = getRawKeyBytes();
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);

		cipher.init(Cipher.ENCRYPT_MODE, keySpec, ivSpec);

		return cipher.doFinal(data);
	}

	public static byte[] decrypt(byte[] data) throws Exception {
		Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");

		byte[] keyBytes = getRawKeyBytes();

		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "AES");
		IvParameterSpec ivSpec = new IvParameterSpec(keyBytes);
		cipher.init(Cipher.DECRYPT_MODE, keySpec, ivSpec);

		return cipher.doFinal(data);
	}
	
	public static String encrypt(String value) {		
		String result = "";
		try {
			byte pbData[] = null;
			try {
				pbData = value.getBytes("UTF-8");
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				
			}
			
		    byte[] defaultCipherText = encrypt(pbData);	    
		    result =  DatatypeConverter.printBase64Binary(defaultCipherText);
		    //result = Base64.encodeToString(defaultCipherText, Base64.DEFAULT).trim();
		} catch (Exception e) {
			
			
		}
		return result;
	}
	
	
	public static String decrypt(String value) {		
		String result = "";
		try {
			if(value != null) {
			    byte[] defaultCipherText = DatatypeConverter.parseBase64Binary(value);	    	    
				//byte[] defaultCipherText = Base64.decode(value, Base64.DEFAULT);	    	    
			    byte[] PPPPP = decrypt(defaultCipherText);
			    
				try {
					result = new String(PPPPP, 0, PPPPP.length, "UTF-8");
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					
				}
			}
		} catch (Exception e) {
			
			
		}
	    return result;
	}	
}
