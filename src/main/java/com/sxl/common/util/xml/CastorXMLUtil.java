/*
 * @(#)CastorXMLUtil.java     V1.0.0      @2015-1-14
 *
 * Project: 
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    pengxuan       2015-1-14     Create this file
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
package com.sxl.common.util.xml;

import java.io.StringReader;
import java.io.StringWriter;

import org.exolab.castor.xml.Marshaller;
import org.exolab.castor.xml.Unmarshaller;



/**
 * 
 * <pre>
 * xml工具
 * </pre>
 * @author sxl
 * @since 2015年1月20日 上午10:37:51
 * @version V1.0.0 描述 : 创建文件MarshallerUtil
 * 
 *         
 *
 */
public class CastorXMLUtil {
	/**
	 * xml转成obj
	 * @param strXml
	 * @param clazz     对象类型
	 * @return		对象(object)
	 */
	@SuppressWarnings("unchecked")
	public static <T> T xml2Obj(Class<T> clazz, String strXml)  {
		StringReader reader = new StringReader(strXml); // 解析xml使用
		T obj = null;
		
		try{
			// 将xml字符串转换为object
			obj = (T) Unmarshaller.unmarshal(clazz, reader);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return obj;
	}
	/**
	 * 对象转xml
	 * @param obj
	 * @return
	 */
	public static String obj2Xml(Object obj, String encoding) {
		StringWriter writer = new StringWriter(); // 生成xml使用
		Marshaller marshaller = null;
		
		try {
			// 将object转换为xml字符串
			marshaller = new Marshaller(writer);
			marshaller.setEncoding(encoding);
			marshaller.marshal(obj);
		} catch (Exception e) {
			
		}
		return writer.toString();
	}
	/**
	 * 对象转xml
	 * @param obj
	 * @param 是否需要xml头信息
	 * @return
	 * @throws FapSysException
	 */
	public static String obj2Xml(Object obj, String encoding,boolean b)  {
		StringWriter writer = new StringWriter(); // 生成xml使用
		Marshaller marshaller = null;
		String xmlString="";
		try {
			// 将object转换为xml字符串
			marshaller = new Marshaller(writer);
			marshaller.setEncoding(encoding);
			marshaller.marshal(obj);
			xmlString= writer.toString();
			if(b){
				return xmlString;
			}else{
				String rex="<?xml version=\"1.0\" encoding=\""+encoding+"\"?>\n";
				String restr=xmlString.replace(rex, "");
				return restr;
			}
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	 
}
