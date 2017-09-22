/*
 * @(#)PrintEncrypt.java     V1.0.0      @2015-3-20
 *
 * Project: 
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    pengxuan       2015-3-20     Create this file
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
package com.common.util.encryDncry;


import com.common.annotation.AnnoEncrypt;
import com.common.constants.Constants;
import com.common.util.bytes.ByteUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Map.Entry;

/**
 * 部分日志加密打印工具类
 * @author pengxuan
 */
public class PrintEncrypt {
	private static Logger logger = LoggerFactory.getLogger(PrintEncrypt.class);

	/**
	 * 加密打印byte数组
	 * @param bytes 待加密打印的byte数组
	 * @return 当byte数组为null或长度为0时，返回空字符串<br>
	 * 当系统加密总开关开启时，打印加密后的hex字符串<br>
	 * 当系统加密总开关关闭时，打印byte数组转换出的hex字符串
	 */
	public static String printBytes(byte[] bytes) {
		return printBytes(true, bytes);
	}
	
	/**
	 * 根据isEncrypt条件，加密打印byte数组
	 * @param isEncrypt 是否加密打印
	 * @param bytes 待加密打印的byte数组
	 * @return 当byte数组为null或长度为0时，返回空字符串<br>
	 * 当系统加密总开关开启时，根据isEncrypt定义进行加密，打印加密后的hex字符串<br>
	 * 当系统加密总开关关闭时，打印byte数组转换出的hex字符串
	 */
	public static String printBytes(boolean isEncrypt, byte[] bytes) {
		if(null == bytes || 0 == bytes.length){
			return "";
		}
		isEncrypt = Constants.LOG_ENCRYPT_FLAG?isEncrypt:false;
		if(isEncrypt) {
//			return Encrypt.getDESEncryptByte(Constants.LOG_ENCRYPT_KEY, bytes);
		} else {
			return ByteUtils.bytes2HexString(bytes);
		}
		return null;
	}
	
	/**
	 * 加密打印字符串
	 * @param origStr 待加密打印的字符串
	 * @return 当字符串为null或空字符串时，返回空字符串<br>
	 * 当系统加密总开关开启时，打印加密后结果hex字符串<br>
	 * 当系统加密总开关关闭时，打印原字符串
	 */
	public static String printStr(String origStr) {
		return printStr(true, origStr);
	}
	
	/**
	 * 根据isEncrypt条件，加密打印字符串
	 * @param isEncrypt 是否加密打印
	 * @param origStr 待加密打印的字符串
	 * @return 当字符串为null或空字符串时，返回空字符串<br>
	 * 当系统加密总开关开启时，根据isEncrypt定义进行加密，打印加密后结果hex字符串<br>
	 * 当系统加密总开关关闭时，打印原字符串
	 */
	public static String printStr(boolean isEncrypt, String origStr) {
//		if(null == origStr || origStr.isEmpty()) {
//			return "";
//		}
//		isEncrypt = FAPConstants.LOG_ENCRYPT_FLAG?isEncrypt:false;
		if(isEncrypt) {
//			return Encrypt.getDESEncryptString(FAPConstants.LOG_ENCRYPT_KEY, origStr);
		} else {
			return origStr;
		}
		return null;
	}
	
	/**
	 * 加密打印Object对象，不打印object中变量值为null的内容
	 * @param object 待加密打印对象
	 * @return 当object对象为null时，返回空字符串<br>
	 * 当系统加密总开关开启时，根据isEncrypt判断结果，打印内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	public static String printObj(Object object) {
		return printObj(false, object);
	}
	
	/**
	 * 根据isEncrypt条件，加密打印Object对象，不打印object中变量值为null的内容
	 * @param isEncrypt 是否全部加密，true为全部加密，false为根据内部定义进行加密
	 * @param object 待加密打印对象
	 * @return 当object对象为null时，返回空字符串<br>
	 * 当系统加密总开关开启时，根据isEncrypt判断结果，打印内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	public static String printObj(boolean isEncrypt, Object object) {

		StringBuffer sb = new StringBuffer();
		StringBuffer retSb = new StringBuffer();
		boolean objIsEncrypt, nameIsEncrypt;
		Object value;
		
		try {
			if(null == object) {
				return "";
			}
			retSb = handleValue(isEncrypt, object);
			if (retSb.length() != 0) {
				return retSb.toString();
			}
			Class<?> cls = object.getClass();
			sb.append("{");
			Field[] fields = cls.getDeclaredFields();
			for(Field field : fields){
				nameIsEncrypt = false;
				objIsEncrypt = isEncrypt;
				field.setAccessible(true);
				value = field.get(object);
				// 不打印值为null的内容
				if(null == value){
					continue;
				}
				if(field.isAnnotationPresent(AnnoEncrypt.class)){
					AnnoEncrypt annoEncrypt = field.getAnnotation(AnnoEncrypt.class);
					if(annoEncrypt.isIgnore()){
						continue;
					} else {
						// 对象加密总开关开启时，所有内容都进行加密
						// 对象加密总开关关闭时，根据对象内部定义进行加密
						objIsEncrypt = isEncrypt?true:annoEncrypt.isEncrypt();
						nameIsEncrypt = annoEncrypt.nameEncrypt();
					}
				}
				sb.append(printStr(nameIsEncrypt, field.getName()));
				retSb = handleValue(objIsEncrypt, value);
				if (retSb.length() != 0) {
					sb.append(retSb.toString());
				} else {
					sb.append(" : ").append(printObj(objIsEncrypt, value));
				}
				sb.append(", ");
				field.setAccessible(false);
			}
			
			if(sb.length() > 1) {
				sb.delete(sb.length() - 2, sb.length());
			}
			sb.append("}");
		} catch (Exception e) {
			logger.error("[printObj]Exception : ", e);
		}
		
		return sb.toString();
	}
	
	/**
	 * 加密打印Map对象
	 * @param map 待加密打印的map对象
	 * @param args map中需要加密打印的部分，该参数为变长参数，可不填
	 * @return 当map为空或null时，返回空字符串<br>
	 * 当系统加密总开关开启时，根据isEncrypt判断结果，打印内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	@SuppressWarnings("rawtypes")
	public static String printMap(Map map, String... args) {
		return printMap(false, map, args);
	}
	
	/**
	 * 根据isEncrypt条件，加密打印Map对象
	 * @param isEncrypt 是否全部加密，true时全部加密，false时加密答应args定义的内容
	 * @param map 待加密打印的map对象
	 * @param args map中需要加密打印的部分，该参数为变长参数，可不填
	 * @return 当map为空或null时，返回空字符串<br>
	 * 当系统加密总开关开启时，根据isEncrypt判断结果，打印内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static String printMap(boolean isEncrypt, Map map, String... args) {
		StringBuffer sb = new StringBuffer();
		StringBuffer retSb = new StringBuffer();
		boolean objIsEncrypt;
		
		try {
			// 当map为空或null时，返回空字符串
			if(null == map || map.isEmpty()){
				return "";
			}
			sb.append("{");
			Set<Entry> set = map.entrySet();
			for(Entry entry : set){
				objIsEncrypt = isEncrypt;
				Object value = entry.getValue();
				if(null == value){
					continue;
				}
				/*
				if(args != null && StringUtil.searchInArray((String)entry.getKey(), args)){
					objIsEncrypt = true;
				}*/
				sb.append(entry.getKey());
				retSb = handleValue(objIsEncrypt, value);
				if (retSb.length() != 0) {
					sb.append(retSb.toString());
				} else {
					sb.append(" : ").append(printObj(objIsEncrypt, value));
				}
				sb.append(", ");
			}
			
			if(sb.length() > 1) {
				sb.delete(sb.length() - 2, sb.length());
			}
			sb.append("}");
		} catch (Exception e) {
			logger.error("[printMap]Exception : ", e);
		}
		
		return sb.toString();
	}

	/**
	 * 加密打印数组
	 * @param array 待加密打印的数组内容
	 * @return 当array为空或null或传入的值不为数组时，返回空字符串<br>
	 * 当系统加密总开关开启时，打印加密内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	public static String printArray(Object array) {
		return printArray(false, array);
	}
	
	/**
	 * 根据isEncrypt条件，加密打印数组
	 * @param isEncrypt 是否全部加密，true为全部加密，false为不加密
	 * @param array 待加密打印的数组内容
	 * @return 当array为空或null或传入的值不为数组时，返回空字符串<br>
	 * 当系统加密总开关开启时，根据isEncrypt判断结果，打印内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	public static String printArray(boolean isEncrypt, Object array) {
		StringBuffer sb = new StringBuffer();
		StringBuffer retSb = new StringBuffer();
		
		try {
			if(!array.getClass().isArray() || null == array || 0 == Array.getLength(array)){
				return "";
			}
			int len = Array.getLength(array);
			for(int i = 0; i < len; i++) {
				Object value = Array.get(array, i);
				retSb = handleValue(isEncrypt, value);
				if (retSb.length() != 0) {
					sb.append(retSb.toString());
				} else {
					sb.append(printObj(isEncrypt, value));
				}
			}

			if(sb.length() > 1) {
				sb.delete(sb.length() - 2, sb.length());
			}
		} catch (Exception e) {
			logger.error("[printArray]Exception : ", e);
		}
		
		return sb.toString();
	}
	
	/**
	 * 加密打印Vector内容
	 * @param vector 待加密打印的vector内容
	 * @return 当vector为空或null时，返回空字符串<br>
	 * 当系统加密总开关开启时，打印加密内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	public static String printVector(Vector<?> vector) {
		return printVector(false, vector);
	}
	
	/**
	 * 根据isEncrypt条件，加密打印Vector内容
	 * @param isEncrypt 是否全部加密，true为全部加密，false为不加密
	 * @param vector 待加密打印的vector内容
	 * @return 当vector为空或null时，返回空字符串<br>
	 * 当系统加密总开关开启时，根据isEncrypt判断结果，打印内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	public static String printVector(boolean isEncrypt, Vector<?> vector) {
		StringBuffer sb = new StringBuffer();
		StringBuffer retSb = new StringBuffer();
		
		try {
			if(null == vector || vector.isEmpty()) {
				return "";
			}
			int size = vector.size();
			for(int i = 0; i < size; i++) {
				Object value = vector.get(i);
				retSb = handleValue(isEncrypt, value);
				if (retSb.length() != 0) {
					sb.append(retSb.toString());
				} else {
					sb.append(printObj(isEncrypt, value));
				}
				sb.append(", ");
			}
			
			if(sb.length() > 1) {
				sb.delete(sb.length() - 2, sb.length());
			}
		} catch (Exception e) {
			logger.error("[printVector]Exception : ", e);
		}
		
		return sb.toString();
	}
	
	/**
	 * 加密打印List
	 * @param list 待加密打印的list内容
	 * @return 当list为空或null时，返回空字符串<br>
	 * 当系统加密总开关开启时，打印加密内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	public static String printList(List<?> list) {
		return printList(false, list);
	}
	
	/**
	 * 根据isEncrypt条件，加密打印List
	 * @param isEncrypt 是否全部加密，true为全部加密，false为不加密
	 * @param list 待加密打印的list内容
	 * @return 当list为空或null时，返回空字符串<br>
	 * 当系统加密总开关开启时，根据isEncrypt判断结果，打印内容<br>
	 * 当系统加密总开关关闭时，打印原内容
	 */
	public static String printList(boolean isEncrypt, List<?> list) {
		StringBuffer sb = new StringBuffer();
		StringBuffer retSb = new StringBuffer();
		
		try {
			if(null == list || list.isEmpty()) {
				return "";
			}
			int size = list.size();
			for(int i = 0; i < size; i++) {
				Object value = list.get(i);
				retSb = handleValue(isEncrypt, value);
				if (retSb.length() != 0) {
					sb.append(retSb.toString());
				} else {
					sb.append(printObj(isEncrypt, value));
				}
				sb.append(", ");
			}
			
			if(sb.length() > 1) {
				sb.delete(sb.length() - 2, sb.length());
			}
		} catch (Exception e) {
			logger.error("[printList]Exception : ", e);
		}
		
		return sb.toString();
	}
	
	/**
	 * 
	 * @param isEncrypt
	 * @param value
	 * @return
	 */
	private static StringBuffer handleValue(boolean isEncrypt, Object value) {
		StringBuffer sb = new StringBuffer();
		
		if(value instanceof String
				|| value instanceof Character) {
			sb.append(" = ").append("\"").append(printStr(isEncrypt, value.toString())).append("\"");
		} else if(value instanceof Integer
				|| value instanceof Long
				|| value instanceof Double
				|| value instanceof BigDecimal
				|| value instanceof Boolean
				|| value instanceof Float
				|| value instanceof Short
				|| value.getClass() == int.class) {
			sb.append(" = ").append(printStr(isEncrypt, value.toString()));
		} else if(value instanceof Class) {
			sb.append(" = ").append(printStr(isEncrypt, value.toString()));
		} else if (value instanceof Date) {
			sb.append(" = ").append(printStr(isEncrypt, new SimpleDateFormat("yyyy-MM-dd").format(value)));
		} else if (value instanceof Timestamp) {
			sb.append(" = ").append(printStr(isEncrypt, new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(value)));
		} else if(value instanceof byte[]){
			sb.append(" = ").append(printBytes(isEncrypt, (byte[])value));
		} else if(value instanceof Vector) { // 打印Vector
			sb.append(" : [").append(printVector(isEncrypt, (Vector<?>)value)).append("]");
		} else if(value instanceof List) { // 打印List
			sb.append(" : [").append(printList(isEncrypt, (List<?>)value)).append("]");
		} else if(value.getClass().isArray()) { // 打印Array
			sb.append(" : [").append(printArray(isEncrypt, value)).append("]");
		} else if(value instanceof Map) { // 打印Map
			sb.append(" : ").append(printMap(isEncrypt, (Map<?, ?>)value));
		}
		return sb;
	}
}
