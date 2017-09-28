/*
 * @(#)SpringContextHolder.java     V1.0.0      @2014-12-8
 *
 * Project: 
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    pengxuan       2014-12-8     Create this file
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

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

/** 
 * 
 */
@Component
@Lazy(value=false)
public class SpringContextHolder implements ApplicationContextAware {

	public static ApplicationContext context;
	
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		SpringContextHolder.context = applicationContext;
	}

	public ApplicationContext getContext(){
		return context;
	}
	
	public static Object getBean(String beanName){
		return context.getBean(beanName);
	}
	
	public static boolean containBean(String beanName) {
		return context.containsBean(beanName);
	}
}
