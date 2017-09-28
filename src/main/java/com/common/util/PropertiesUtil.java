/*
 * @(#)PropertiesUtil.java     V1.0.0      @2015-3-24
 *
 * Project: 
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    pengxuan       2015-3-24     Create this file
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
package com.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;

/**
 * 配置文件工具类
 * @author pengxuan
 */
public class PropertiesUtil {
	private static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	private static Properties properties;

	static {
		try {
			properties = (Properties)SpringContextHolder.getBean("configProperties");
			logger.info("资金能力业管 - 配置文件加载完毕！");
		} catch (Exception e) {
			logger.error("资金能力业管 - 初始化配置信息异常，异常原因为配置文件未找到！异常详情：", e);
		}
	}

	/**
	 * 获取参数值
	 * @param key
	 * @return
	 */
	public static String getValue(String key) {
		try {
			return properties.get(key).toString();
		} catch (NullPointerException e) {
			return "未发现该配置信息";
		}

	}
}