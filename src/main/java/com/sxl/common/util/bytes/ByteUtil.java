package com.sxl.common.util.bytes;

import org.apache.commons.lang3.ArrayUtils;

/**
 * 
 * Project :	Common
 * classFile :	com.sxl.common.util.bytes
 *
 * Modify Information
 *		Author		Date		
 *		=========   =============
 * 		sxl		    2017年3月17日
 * 
 * Description :
 * 			byte 工具类
 *	
 */
public class ByteUtil {
	
	/**
	 * byte转String
	 * @param bytes		bytes数组
	 * @return 字符串	String
	 */
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
	
	/**
	 * byte转String
	 * @param hexString 字符串	String 
	 * @return 		bytes数组
	 */
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

