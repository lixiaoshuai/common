package com.sxl.common.util.http;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownHostException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLServerSocket;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

public class HttpUtil {

	private SSLSocket socket = null;

	/**
	 * 向指定URL发送GET方法的请求
	 * 
	 * @param url
	 *            发送请求的URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return URL 所代表远程资源的响应结果
	 */
	public static String sendGet(String url, String param) {
		String result = "";
		BufferedReader in = null;
		try {
			String urlNameString = url + "?" + param;
			URL realUrl = new URL(urlNameString);
			// 打开和URL之间的连接
			URLConnection connection = realUrl.openConnection();
			// 设置通用的请求属性
			connection.setRequestProperty("accept", "*/*");
			connection.setRequestProperty("connection", "Keep-Alive");
			connection.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 建立实际的连接
			connection.connect();
			// 获取所有响应头字段
			Map<String, List<String>> map = connection.getHeaderFields();
			// 遍历所有的响应头字段
			for (String key : map.keySet()) {
				System.out.println(key + "--->" + map.get(key));
			}
			// 定义 BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送GET请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
		finally {
			try {
				if (in != null) {
					in.close();
				}
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 向指定 URL 发送POST方法的请求
	 * 
	 * @param url
	 *            发送请求的 URL
	 * @param param
	 *            请求参数，请求参数应该是 name1=value1&name2=value2 的形式。
	 * @return 所代表远程资源的响应结果
	 */
	public static String sendPost(String url, String param) {
		PrintWriter out = null;
		BufferedReader in = null;
		String result = "";
		try {
			URL realUrl = new URL(url);
			// 打开和URL之间的连接
			URLConnection conn = realUrl.openConnection();
			// 设置通用的请求属性
			conn.setRequestProperty("accept", "*/*");
			conn.setRequestProperty("connection", "Keep-Alive");
			conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
			// 发送POST请求必须设置如下两行
			conn.setDoOutput(true);
			conn.setDoInput(true);
			// 获取URLConnection对象对应的输出流
			out = new PrintWriter(conn.getOutputStream());
			// 发送请求参数
			out.print(param);
			// flush输出流的缓冲
			out.flush();
			// 定义BufferedReader输入流来读取URL的响应
			in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}
		} catch (Exception e) {
			System.out.println("发送 POST 请求出现异常！" + e);
			e.printStackTrace();
		}
		// 使用finally块来关闭输出流、输入流
		finally {
			try {
				if (out != null) {
					out.close();
				}
				if (in != null) {
					in.close();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
		return result;
	}

	public void connect() throws UnknownHostException, IOException {
		// 通过套接字工厂，获取一个客户端套接字
		SSLSocketFactory socketFactory = (SSLSocketFactory) SSLSocketFactory.getDefault();
		socket = (SSLSocket) socketFactory.createSocket("localhost", 7070);
		try {
			// 获取客户端套接字输出流
			PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
			// 将用户名和密码通过输出流发送到服务器端
			String userName = "principal";
			output.println(userName);
			String password = "credential";
			output.println(password);
			output.flush();

			// 获取客户端套接字输入流
			BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			// 从输入流中读取服务器端传送的数据内容，并打印出来
			String response = input.readLine();
			response += "\n " + input.readLine();
			System.out.println(response);

			// 关闭流资源和套接字资源
			output.close();
			input.close();
			socket.close();
		} catch (IOException ioException) {
			ioException.printStackTrace();
		} finally {
			System.exit(0);
		}
	}
	
	
	 // 服务器端授权的用户名和密码
	 private static final String USER_NAME = "principal"; 
	 private static final String PASSWORD = "credential"; 
	 // 服务器端保密内容
	 private static final String SECRET_CONTENT = 
	"This is confidential content from server X, for your eye!"; 

	 private SSLServerSocket serverSocket = null; 

	
	
	

	 public void runServer() throws IOException { 
		 // 通过套接字工厂，获取一个服务器端套接字
		 SSLServerSocketFactory socketFactory = (SSLServerSocketFactory) 
	 SSLServerSocketFactory.getDefault(); 
		 serverSocket = (SSLServerSocket)socketFactory.createServerSocket(7070); 
		 while (true) { 
			 try { 
				 System.out.println("Waiting for connection..."); 
				 // 服务器端套接字进入阻塞状态，等待来自客户端的连接请求
				 SSLSocket socket = (SSLSocket) serverSocket.accept(); 
				
				 // 获取服务器端套接字输入流
				 BufferedReader input = new BufferedReader( 
				        new InputStreamReader(socket.getInputStream())); 
			 // 从输入流中读取客户端用户名和密码
				 String userName = input.readLine(); 
				 String password = input.readLine(); 
				
				 // 获取服务器端套接字输出流
				 PrintWriter output = new PrintWriter( 
				        new OutputStreamWriter(socket.getOutputStream())); 

			 // 对请求进行认证，如果通过则将保密内容发送给客户端
				 if (userName.equals(USER_NAME) && password.equals(PASSWORD)) { 
					 output.println("Welcome, " + userName); 
					 output.println(SECRET_CONTENT); 
				 } else { 
					 output.println("Authentication failed, you have no	 access to server X..."); 
				 } 
			
			 // 关闭流资源和套接字资源
				 output.close(); 
				 input.close(); 
				 socket.close(); 

			 } catch (IOException ioException) { 
				 ioException.printStackTrace(); 
			 } 
		 } 
	 } 
public static void main(String[] args) throws IOException {
	
	 
	
}

}
