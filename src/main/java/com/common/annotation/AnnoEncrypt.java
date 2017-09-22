/*
 * @(#)AnnoEncrypt.java     V1.0.0      @2015-3-20
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
package com.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lixiaoshuai on 2017/9/19.
 *
 * 打印加密日志使用
 *
 * @mail sxlshuai@foxmail.com
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AnnoEncrypt {
	/**
	 * 定义字段是否需要加密打印
	 * @return 返回true为加密打印，返回false为不加密打印
	 */
	 boolean isEncrypt() default true;
	/**
	 * 定义字段是否需要打印<br>
	 * isIgnore的级别大于isEncrypt
	 * @return 返回true为不打印，返回false为打印
	 */
	 boolean isIgnore() default false;
	/**
	 * 将字段名称也进行加密（比如cvv和有效期字段，连名称也不要出现）
	 * @return
	 */
	 boolean nameEncrypt() default false;
}
