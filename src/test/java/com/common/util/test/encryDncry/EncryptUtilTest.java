package com.common.util.test.encryDncry;

import com.common.util.bytes.ByteUtil;
import com.common.util.bytes.ByteUtil12;
import com.common.util.encryDncry.EncryptUtil;
import org.junit.Test;

/**
 * Created by lixiaoshuai on 2017/9/22.
 *
 * @mail sxlshuai@foxmail.com
 */
public class EncryptUtilTest {

    private String base64 = "你好BASE64";

    @Test
    public void Base64()throws Exception{
        String strEn = EncryptUtil.BASE64Encrypt(base64);
        System.out.println(strEn);

        byte[] by = EncryptUtil.BASE64DecryptStr(EncryptUtil.BASE64Encrypt(base64));
        System.out.println(new String(by,"utf-8"));
    }


    @Test
    public void DES3ENDN()throws Exception{

        String msg = "郭克华_安全编程技术";
        System.out.println("明文是：" + msg);
        byte[] enc = EncryptUtil.DES3Encrypt(msg);
        System.out.println("密文是:" + ByteUtil.byteArrayToHexStr(enc));

        byte[] dec = EncryptUtil.DES3Decrypt(enc);
        System.out.println("解密后的结果是：" + new String(dec));
    }

}
