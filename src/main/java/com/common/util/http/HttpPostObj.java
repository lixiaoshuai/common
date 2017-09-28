/*
 * @(#)HttpPostObj.java     V1.0.0      @2014年12月24日
 *
 * Project: FAPCommon
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    gmc       2014年12月24日     Create this file
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
package com.common.util.http;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * @author gmc
 * 
 */
public class HttpPostObj {
	
	private String iKey;

    private String iValue;

    public HttpPostObj(String aKey, String aValue) {
        this.iKey = aKey;
        try {
			this.iValue = URLEncoder.encode(aValue, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    }

    public String getKey() {
        return iKey;
    }

    public void setKey(String aKey) {
        iKey = aKey;
    }

    public String getValue() {
        return iValue;
    }

    public void setValue(String aValue) {
        iValue = aValue;
    }
}
