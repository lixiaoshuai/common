package com.common.util.encryDncry;



import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
public class RSADemo {
	public RSADemo() {
	}
	//得要公钥和私钥
	public static void generateKey() {
		try {
			//KeyPairGenerator  类用于生成公钥和私钥对
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			//	初始化确定密钥大小的密钥对生成器
			kpg.initialize(1024);
			//简单的密钥对（公钥和私钥）持有者
			KeyPair kp = kpg.genKeyPair();
			// 共有密钥
			PublicKey pbkey = kp.getPublic();
			// 私有密钥
			PrivateKey prkey = kp.getPrivate();
			// 保存公钥
			FileOutputStream f1 = new FileOutputStream("pubkey.dat");
			ObjectOutputStream b1 = new ObjectOutputStream(f1);
			b1.writeObject(pbkey);
			b1.close();
			// 保存私钥
			FileOutputStream f2 = new FileOutputStream("privatekey.dat");
			ObjectOutputStream b2 = new ObjectOutputStream(f2);
			b2.writeObject(prkey);
			b2.close();
		} catch (Exception e) {
			
		}
	}
	public static void generateKey1(){
		try {
			KeyPairGenerator kpg = KeyPairGenerator.getInstance("RSA");
			kpg.initialize(1024);
			KeyPair kp = kpg.generateKeyPair();
			PrivateKey prk = kp.getPrivate();
			PublicKey pbk = kp.getPublic();
			
			ObjectOutputStream b1 = new ObjectOutputStream(new FileOutputStream("prikey.dat"));
			b1.writeObject(prk);
			b1.close();
			ObjectOutputStream b2 = new ObjectOutputStream(new FileOutputStream("pubkey.dat"));
			b2.writeObject(pbk);
			b2.close();
		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//根据公钥生成密文
	public static void encrypt() throws Exception {
		String s = "Hello World!";
		// 获取公钥及参数e,n
		FileInputStream f = new FileInputStream("pubkey.dat");
		ObjectInputStream b = new ObjectInputStream(f);
		RSAPublicKey pbk = (RSAPublicKey) b.readObject();
		BigInteger e = pbk.getPublicExponent();//返回此公钥的指数
		BigInteger n = pbk.getModulus(); //返回此公钥的模
		System.out.println("e= " + e);
		System.out.println("n= " + n);
		// 获取明文m
		byte ptext[] = s.getBytes("UTF-8");
		BigInteger m = new BigInteger(ptext); 
		// 计算密文c
		BigInteger c = m.modPow(e, n);//生成密文
		System.out.println("c= " + c);//打印密文
		// 保存密文
		String cs = c.toString();
		BufferedWriter out =
			new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream("encrypt.dat")));
		out.write(cs, 0, cs.length());
		b.close();
		out.close();
	}
	
	public static void encrypt1() throws Exception{
		String s = "welcome to China";
		ObjectInputStream in = new ObjectInputStream(new FileInputStream("pubkey.dat"));
		RSAPublicKey pbk = (RSAPublicKey)in.readObject();
		BigInteger e = pbk.getPublicExponent();
		BigInteger n = pbk.getModulus();
		byte ptext[] = s.getBytes("UTF-8");
		BigInteger m = new BigInteger(ptext);
		
		BigInteger c = m.modPow(e, n);
		String cs = c.toString();
		
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream("encrypt.dat")));
		out.write(cs,0,cs.length());
		in.close();
		out.close();
	}

	//根据私钥破解密文
	public static void decrypt() throws Exception {
		// 读取密文
		BufferedReader in =
			new BufferedReader(
				new InputStreamReader(new FileInputStream("encrypt.dat")));
		String ctext = in.readLine();
		BigInteger c = new BigInteger(ctext);
		// 读取私钥
		FileInputStream f = new FileInputStream("privatekey.dat");
		ObjectInputStream b = new ObjectInputStream(f);
		RSAPrivateKey prk = (RSAPrivateKey) b.readObject(); 
		BigInteger d = prk.getPrivateExponent();  //返回此私钥的指数
		// 获取私钥参数及解密
		BigInteger n = prk.getModulus(); //返回此私钥的模  
		System.out.println("d= " + d);
		System.out.println("n= " + n);
		BigInteger m = c.modPow(d, n);  //进行解密操作
		// 显示解密结果
		System.out.println("m= " + m);
		byte[] mt = m.toByteArray();
		System.out.println("PlainText is ");
		for (int i = 0; i < mt.length; i++) {
			System.out.print((char) mt[i]);
		}
		in.close();
		b.close();
	}
	public static void decrypt1()throws Exception {
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream("encrypt.dat")));
		String ctext = in.readLine();
		BigInteger c = new BigInteger(ctext);
		
		ObjectInputStream b = new ObjectInputStream(new FileInputStream("prikey.dat"));
		RSAPrivateKey prk = (RSAPrivateKey)b.readObject();
		BigInteger d = prk.getPrivateExponent();
		BigInteger n = prk.getModulus();
		BigInteger m = c.modPow(d, n);
		byte[] mt = m.toByteArray();
		for (int i = 0; i < mt.length; i++) {
			System.out.print((char)mt[i]);
		}
		in.close();
		b.close();
	}
	public static void main(String args[]) {
		try {
			generateKey1();
			encrypt1();
			decrypt1();
		
		} catch (Exception e) {
			System.out.println(e.toString());
		}
	}
}
