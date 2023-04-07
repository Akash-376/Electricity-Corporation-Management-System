package com.powerhouse.security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordHashing {
	
	public static String doHashing (String password) {
		try {
			MessageDigest md = MessageDigest.getInstance("SHA"); // "SHA" is an algorithm, we have another hashing algorithm is "MD-5"
			
			// getting byte Array
			byte [] passwordInBytes = password.getBytes();
			md.update(passwordInBytes);
			
			byte[] resultByteArray = md.digest(); // we can't convert it directly to string by using constructor provided by string. Because the byte array we are getting here is having some different format like hexaDecimal format.
			
			// taking StringBuilder to convert resultByteArray into string
			StringBuilder sb = new StringBuilder();
			for(byte b : resultByteArray) {
				sb.append(String.format("%02x", b)); // here "%02x" is used for hexaDecimal format
			}
			
			return sb.toString();
			
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		
			
		return ""; // if any exception occurred
		
	}

}
