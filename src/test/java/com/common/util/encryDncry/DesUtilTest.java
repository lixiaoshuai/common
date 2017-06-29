package com.common.util.encryDncry;

import com.common.util.encryDncry.DesUtil;

public class DesUtilTest {

	public static void main(String[] args) throws Exception{
		DesUtil d = new DesUtil();
		String s = "我很好";
		System.out.println(s);
		//加密
		byte[] enc = d.createEncryptor(s);
		System.out.println(new String(enc,"utf-8"));
		//解密
		byte[] dec = d.createDecryptor(enc);
		
		System.out.println(new String(dec));
		
		
	}
}
