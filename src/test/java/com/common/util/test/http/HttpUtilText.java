package com.common.util.test.http;

import java.io.IOException;
import java.net.UnknownHostException;

import org.junit.Test;

import com.common.util.http.HttpUtil;

public class HttpUtilText {

	@Test
	public void sendGet(){
		//发送 GET 请求
        String s=HttpUtil.sendGet("http://localhost:6144/Home/RequestString", "key=123&v=456");
        System.out.println(s);
	}
	
	@Test
	public void sendPost(){
		//发送 GET 请求
        String s=HttpUtil.sendGet("http://localhost:6144/Home/RequestString", "key=123&v=456");
        System.out.println(s);
	}
}
