/*
 * @(#)ByteUtils333.java     V1.0.0      @2015-3-23
 *
 * Project: 
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    pengxuan       2015-3-23     Create this file
 * 
 * Copyright Notice:
 *     Copyright (c) 2009-2014 Unicompay Co., Ltd. 
 *     1002 Room, No. 133 North Street, Xi Dan, 
 *     Xicheng District, Beijing ,100032, China 
 *     All rights reserved.
 *
 *     This software is the confidential and proprietary information of
 *     Unicompay Co., Ltd. ("Confidential Information").
 *     You shall not disclose such Confidential Information and shall use 
 *     it only in accordance with the terms of the license agreement you 
 *     entered into with Unicompay.
 */
package com.common.util.bytes;

import org.apache.commons.lang3.ArrayUtils;

/**
 * @author pengxuan
 */
public class ByteUtils333 {
	
	public static String bytes2HexString(byte[] bytes){
		StringBuffer sb = new StringBuffer();
		for (byte b : bytes) {
			String hex = Integer.toHexString(b & 0xff);
			if (hex.length() == 1) {
				hex = '0' + hex;
			}
			hex = hex.toUpperCase();
			sb.append(hex);
		}
		return sb.toString();
	}
	
	public static byte[] hexString2bytes(String hexString){
		if (hexString == null || hexString.equals("")) {
			return null;
		}
		hexString = hexString.toUpperCase();
		int length = hexString.length() / 2;
		char[] hexChars = hexString.toCharArray();
		byte[] d = new byte[length];
		for (int i = 0; i < length; i++) {
			int pos = i * 2;
			d[i] = (byte) (char2byte(hexChars[pos]) << 4 | char2byte(hexChars[pos + 1]));
		}
		return d;
	}

	public static byte char2byte(char c){
		return (byte) "0123456789ABCDEF".indexOf(c);
	}
	
	public static byte[]  subarray( byte[] b, int beginIndex, int endIndex){
		if(beginIndex<0 || endIndex < beginIndex || endIndex>b.length){
			return b;
		}
		return ArrayUtils.subarray(b, beginIndex, endIndex);
	}
	
	public static byte[]  subarray( byte[] b, int beginIndex){
		return ArrayUtils.subarray(b, beginIndex, b.length);
	}
}
