package com.common.util.encryDncry;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

public class DESDemo3 {
	//KeyGenerator提供对称密钥生成器的功能，支持各种算法
	private KeyGenerator keygen;
	//SecretKey负责保存对称密钥
	private SecretKey deskey;
	//Cipher负责完成加密或解密工作
	private Cipher c;
	
	public DESDemo3()throws Exception{
		//实例化支持3DES算法的密钥生成器，算法名称用DESede
		keygen = KeyGenerator.getInstance("DESede");
		//生成密钥
		deskey = keygen.generateKey();
		//生成Cipher对象，指定􀝊支持3DES算法
		c = Cipher.getInstance("DESede");
	}
	public byte[] createEncrypt(String msg)throws Exception{
		//根据密钥，对Cipher对象进行初始化,ENCRYPT_MODE表示加密模式
		c.init(Cipher.ENCRYPT_MODE, deskey);
		byte[] src = msg.getBytes();
		//加密，结果保存进enc
		byte[] enc = c.doFinal(src);
		return enc;
	}
	//对enc 进行解密
	public byte[] createDecrypt(byte[] enc)throws Exception{
		//根据密钥，对Cipher对象进行初始化,DECRYPT_MODE表示解密模式
		c.init(Cipher.DECRYPT_MODE, deskey);
		//解密，结果保存进dec
		byte[] dec = c.doFinal(enc);
		return dec;
	}
	public static void main(String[] args) throws Exception{
		
		DESDemo3 d = new DESDemo3();
		String msg = "郭克华_安全编程技术";
		System.out.println("明文是：" + msg);
		byte[] enc = d.createEncrypt(msg);
		System.out.println("密文是:" + new String(enc));
		
		byte[] dec = d.createDecrypt(enc);
		System.out.println("解密后的结果是：" + new String(dec));
		
	}
}
