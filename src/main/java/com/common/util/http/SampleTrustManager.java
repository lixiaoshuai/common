/*
 * @(#)SampleTrustManager.java     V1.0.0      @2014年12月24日
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

import javax.net.ssl.X509TrustManager;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;

/**
 * @author gmc
 * 实现 X509TrustManager 接口，默认信任所有的服务器证书。
 */
public class SampleTrustManager implements X509TrustManager {

	/* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#getAcceptedIssuers()
     */
    public X509Certificate[] getAcceptedIssuers() {
        //System.out.println("SampleTrustManager::getAcceptedIssuers()");
        return null;
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkClientTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkClientTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        //System.out.println("SampleTrustManager::checkClientTrusted()");
    }

    /* (non-Javadoc)
     * @see javax.net.ssl.X509TrustManager#checkServerTrusted(java.security.cert.X509Certificate[], java.lang.String)
     */
    public void checkServerTrusted(X509Certificate[] arg0, String arg1) throws CertificateException {
        //System.out.println("SampleTrustManager::checkServerTrusted()");
        //System.out.println(arg0.length);
        //System.out.println(arg0[0].toString());
        //System.out.println(arg0[1].toString());
        //System.out.println(arg0[2].toString());
    }
}
