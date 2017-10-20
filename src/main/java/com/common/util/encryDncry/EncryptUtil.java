package com.common.util.encryDncry;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.security.Key;
import java.security.KeyPair;
import java.security.MessageDigest;
import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;


import com.common.util.bytes.ByteUtil;

import com.common.util.base64.Base64Util;
import org.apache.commons.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 功能简述: 加密解密工具类，对MD5/BASE64/DES/RSA等算法提供了包装.
 *
 * @author sxl
 * @version 1.0
 */
public class EncryptUtil {

    private static Logger logger = LoggerFactory.getLogger(EncryptUtil.class);

    private static final int ENCRYPT = 1;    //   加密
    private static final int DNCRYPT = 2;    //   解密
    private static final int KEY_SIZE = 1024;
    private static final String MD5_ALGORITHM = "md5";
    private static final String DES_ALGORITHM = "DES";
    private static final String DES3_ALGORITHM = "DESede";
    private static final String RSA_ALGORITHM = "RSA";
    private static final String ALGORITHM = "DESede/ECB/PKCS5Padding";
    private static final String SIGNATURE_ALGORITHM = "MD5withRSA";

    private static final String SECRET_KEY = "8FC72A454C7FDC3DE3B50D2958C4E6205B8CF7EA7C7C861A";   //秘钥 24位
    private static final String INTEKEYFILEPATH = "e:/key.txt";   //保存地址

    private static MessageDigest md5;

    private static SecureRandom random;
    private static KeyPair keyPair;


    /**
     * 生成秘钥
     *
     * @return Sstring
     */
    public static String initKeyDES3() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance(DES3_ALGORITHM);
        SecretKey deskey = keygen.generateKey();
        return ByteUtil.byteArrayToHexStr(deskey.getEncoded());
    }

    /**
     * 生成秘钥
     *
     * @return byte[]
     */
    public static byte[] initKeyReturnByteDES3() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance(DES3_ALGORITHM);
        SecretKey deskey = keygen.generateKey();
        return deskey.getEncoded();

    }

    /**
     * 生成秘钥
     *
     * @return Key
     */
    public static Key initKeyReturnKeyDES3() throws Exception {
        DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(initKeyReturnByteDES3());
        SecretKeyFactory secretKeyFactory = SecretKeyFactory.getInstance("DESede");
        return secretKeyFactory.generateSecret(deSedeKeySpec);
    }

    /**
     * 生成秘钥 保存文件中
     *
     * @return
     */
    public static void initKeySaveFile() throws Exception {
        initKeySaveFile(null);

    }

    /**
     * @param path 路径
     * @throws Exception
     */
    public static void initKeySaveFile(String path) throws Exception {
        if (path == null) {
            path = INTEKEYFILEPATH;
        }
        File file = new File(path);
        OutputStream outputStream = new FileOutputStream(file);
        outputStream.write(initKeyDES3().getBytes());
        outputStream.close();
        logger.info("key秘钥生成路径：{}", path);
    }

    /**
     * 功能简述: 使用md5进行单向加密.
     */
    public static String encryptMD5(String plainText) {
        byte[] cipherData = md5.digest(plainText.getBytes());
        StringBuilder builder = new StringBuilder();
        for (byte cipher : cipherData) {
            String toHexStr = Integer.toHexString(cipher & 0xff);
            builder.append(toHexStr.length() == 1 ? "0" + toHexStr : toHexStr);
        }
        return builder.toString();
    }

    /**
     * 功能简述: 使用BASE64进行加密.
     *
     * @param plainData Byte 明文数据
     * @return String 加密之后的文本内容
     */
    public static String BASE64Encrypt(String plainData) {
        return BASE64Encrypt(plainData.getBytes());
    }

    /**
     * 功能简述: 使用BASE64进行加密.
     *
     * @param plainData Byte 明文数据
     * @return String 加密之后的文本内容
     */
    public static String BASE64Encrypt(byte[] plainData) {
        return Base64Util.encode(plainData);
    }

    /**
     * 功能简述: 使用BASE64进行解密.
     *
     * @param cipherText String 密文文本
     * @return Byte 解密之后的数据
     */
    public static byte[] BASE64DecryptStr(String cipherText) {
        return Base64Util.decode(cipherText);
    }

    /**
     * 3DES  加密
     *
     * @param msg 加密对象
     * @return
     * @throws Exception
     */
    public static byte[] DES3Encrypt(String msg) throws Exception {
        return DES3Encrypt(msg,null);
    }

    /**
     * 3DES 解密
     *
     * @param enc byte[] 解密数据
     * @return byte  解密后的byte明文
     * @throws Exception
     */
    public static byte[] DES3Decrypt(byte[] enc) throws Exception {
        return DES3Decrypt(enc,null);
    }
    /**
     * 3DES  解密
     *
     * @param msg   byte[] 解密数据
     * @param key   秘钥
     * @return      解密后的byte明文
     * @throws Exception
     */
    public static byte[] DES3Decrypt(byte[] msg,String key) throws Exception {
        key = (key == null)?SECRET_KEY:key;
        return encryptDncryptUtil(msg, DNCRYPT, key, DES3_ALGORITHM);
    }
    /**
     * 3DES  加密
     *
     * @param msg 加密对象
     * @return
     * @throws Exception
     */
    public static byte[] DES3Encrypt(String msg,String key) throws Exception {
        key = (key == null)?SECRET_KEY:key;
        return encryptDncryptUtil(msg.getBytes(), ENCRYPT, key, DES3_ALGORITHM);
    }




    /**
     * DES  加密
     *
     * @param msg 加密对象
     * @return
     * @throws Exception
     */
    public static byte[] DESEncrypt(String msg) throws Exception {
        return DESEncrypt(msg,null);
    }

    /**
     * DES 解密
     *
     * @param enc byte[] 解密数据
     * @return byte  解密后的byte明文
     * @throws Exception
     */
    public static byte[] DESDecrypt(byte[] enc) throws Exception {
        return DESDecrypt(enc,null);
    }
    /**
     * DES  解密
     *
     * @param msg   byte[] 解密数据
     * @param key   秘钥
     * @return      解密后的byte明文
     * @throws Exception
     */
    public static byte[] DESDecrypt(byte[] msg,String key) throws Exception {
        key = (key == null)?SECRET_KEY:key;
        return encryptDncryptUtil(msg, ENCRYPT, key, DES_ALGORITHM);
    }
    /**
     * DES  加密
     *
     * @param msg 加密对象
     * @return
     * @throws Exception
     */
    public static byte[] DESEncrypt(String msg,String key) throws Exception {
        key = (key == null)?SECRET_KEY:key;
        return encryptDncryptUtil(msg.getBytes(), ENCRYPT, key, DES_ALGORITHM);
    }



    /**
     * 加解密工具类
     *
     * @param msg       加密对象
     * @param cipher    加解密标识   1 加密， 2 解密
     * @param key       秘钥
     * @param algorithm 加密方式   DES,DES3,AES
     * @return
     * @throws Exception
     */
    public static byte[] encryptDncryptUtil(byte[] msg, int cipher, String key, String algorithm) throws Exception {
        SecretKey deskey = new SecretKeySpec(ByteUtil.hexStrToByteArray(key), algorithm);   //SecretKey负责保存对称密钥
        Cipher c = Cipher.getInstance(ALGORITHM);//Cipher负责完成加密或解密工作
        c.init(cipher, deskey); //根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
        byte[] dec = c.doFinal(msg);
        return dec;
    }




    /**
     * 日志加密使用，进行DES加密并返回Hex字符串
     * @param key
     * @param value
     * @return
     */
    public static byte[] getDESEncryptByte(String key, byte[] value) {
        try{
            if(null == value || 0 == value.length){
                return null;
            }
            DESKeySpec dks = new DESKeySpec(Base64.decodeBase64(key));
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(DES_ALGORITHM);
            Key k = keyFactory.generateSecret(dks);
            Cipher cipher = Cipher.getInstance(ALGORITHM);
            cipher.init(Cipher.ENCRYPT_MODE, k);
            return cipher.doFinal(value);
        }catch(Exception e){
            logger.error("Exception :", e);
        }
        return null;
    }













    public static void main(String[] args) throws Exception {

        initKeySaveFile();
    }

}