package com.common.util.encryDncry;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.SecureRandom;
import java.security.Signature;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.SecretKeySpec;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.common.util.base64.Base64Util;  
  
/** 
 * 功能简述: 加密解密工具类，对MD5/BASE64/DES/RSA等算法提供了包装. 
 * @author sxl
 * @version 1.0 
 */  
public class EncryptUtil {  
    private static Log logger = LogFactory.getLog(EncryptUtil.class);  
    
    private static final int KEY_SIZE = 1024;  
    private static final String  MD5_ALGORITHM= "md5";  
    private static final String  DES_ALGORITHM= "DES";
    private static final String  DES3_ALGORITHM = "DESede";
    private static final String  RSA_ALGORITHM= "RSA";
    private static final String  SIGNATURE_ALGORITHM= "MD5withRSA";  
      
    private static MessageDigest md5;  

    private static SecureRandom random;  
    private static KeyPair keyPair;  

    private static final String DES3_KEY = "123456789abcdefg!@#$%^&*";   //秘钥 24位
    /** 
     * 功能简述: 使用md5进行单向加密. 
     */  
    public static String encryptMD5(String plainText) {  
        byte[] cipherData = md5.digest(plainText.getBytes());  
        StringBuilder builder = new StringBuilder();  
        for(byte cipher : cipherData) {  
            String toHexStr = Integer.toHexString(cipher & 0xff);  
            builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);  
        }  
        return builder.toString();  
    }
    /**
     * 功能简述: 使用BASE64进行加密.
     * @param plainData Byte 明文数据
     * @return String 加密之后的文本内容
     */
    public static String BASE64Encrypt(String plainData) {
        return BASE64Encrypt(plainData.getBytes());
    }
    /** 
     * 功能简述: 使用BASE64进行加密. 
     * @param plainData Byte 明文数据
     * @return String 加密之后的文本内容
     */  
    public static String BASE64Encrypt(byte[] plainData) {
        return Base64Util.encode(plainData);  
    }  
      
    /** 
     * 功能简述: 使用BASE64进行解密. 
     * @param cipherText String 密文文本
     * @return Byte 解密之后的数据
     */  
    public static byte[] BASE64DecryptStr(String cipherText) {
        byte[] plainData = null;

            plainData =  Base64Util.decode(cipherText);
        return plainData;
    }

    /**
     *  生成秘钥
     * @return
     */
    public static String CreateSecretKey(){

        return null;
    }

    /**
     *  3DES  加密
     * @param msg
     * @return
     * @throws Exception
     */
    public static byte[] DES3Encrypt(String msg)throws Exception{


         SecretKey deskey = new SecretKeySpec(DES3_KEY.getBytes(),DES3_ALGORITHM);   //SecretKey负责保存对称密钥

         Cipher c = Cipher.getInstance(DES3_ALGORITHM);//Cipher负责完成加密或解密工作

        c.init(Cipher.ENCRYPT_MODE, deskey); //根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
        byte[] src = msg.getBytes();
        //加密，结果保存进enc
        byte[] enc = c.doFinal(src);
        return enc;
    }


    /**
     *  3DES 解密
     * @param enc   byte 密文
     * @return  byte  解密后的byte明文
     * @throws Exception
     */
    public static byte[] DES3Decrypt(byte[] enc)throws Exception{

        SecretKey deskey = new SecretKeySpec(DES3_KEY.getBytes(),DES3_ALGORITHM) ;
        Cipher c = Cipher.getInstance(DES3_ALGORITHM);

        c.init(Cipher.DECRYPT_MODE, deskey);//根据密钥，对Cipher对象进行初始化,DECRYPT_MODE表示解密模式

        return  c.doFinal(enc);   //解密，结果保存进dec

    }


    public static void main(String[] args)throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance(DES3_ALGORITHM);
        SecretKey deskey = keygen.generateKey();
        byte[] b = deskey.getEncoded();
        System.out.println(new String(b,"utf-8"));
    }

}  