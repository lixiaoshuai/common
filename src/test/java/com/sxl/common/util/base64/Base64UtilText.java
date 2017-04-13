package com.sxl.common.util.base64;

import java.io.UnsupportedEncodingException;


public class Base64UtilText {

	public static void main(String[] args) throws UnsupportedEncodingException {

		
		String strEn = Base64Util.encode("hollo word".getBytes());
		System.out.println(strEn);
		
		byte[] byteDn = Base64Util.decode(strEn); 
		
		String strDn = new String(byteDn,"utf-8");
		
		System.out.println(strDn);
	
		

	}

}
