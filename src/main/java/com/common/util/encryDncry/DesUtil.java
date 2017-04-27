package com.common.util.encryDncry;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DesUtil {

	// KeyGenerator提供对称密钥生成器的功能，支持各种算法
	private KeyGenerator keygen;
	// SecretKey负责保存对称密钥
	private SecretKey deskey;
	// Cipher负责完成加密或解密工作
	private Cipher c;


	public void InitializeDes() throws Exception {
		// 实例化支持DES算法的密钥生成器(算法名称命名需按规定，否则抛出异常)
		keygen = KeyGenerator.getInstance("DES");
		// 生成密钥
		deskey = keygen.generateKey();
		// 生成Cipher对象，指定其支持des算法
		c = Cipher.getInstance("DES");
	}

	public static void main(String[] args) throws Exception {
		DesUtil d = new DesUtil();
		d.InitializeDes();
		String s = "我很好";
		System.out.println(s);
		// 加密
		byte[] enc = d.createEncryptor(s);
		System.out.println(new String(enc));
		// 解密
		byte[] dec = d.createDecryptor(enc);

		System.out.println(new String(dec));

	}

	// 对字符串str加密
	public byte[] createEncryptor(String str) throws Exception {
		// 根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		// 加密，结果保存进cipherByte
		return c.doFinal(str.getBytes());
	}

	/* 对字节数组buff解密 */
	public byte[] createDecryptor(byte[] buff) throws Exception {
		// 根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示解密模式
		c.init(Cipher.DECRYPT_MODE, deskey);
		// 得到明文，存入cipherByte字符数组
		return c.doFinal(buff);
	}

}
