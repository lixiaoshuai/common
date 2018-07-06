/*
 * @(#)FTPConnectionManager.java     V1.0.0      @2014年12月24日
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
package com.common.util.ftp;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelSftp;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.Session;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.net.SocketException;
import java.util.Properties;

/**
 * @author gmc
 * ftp或sftp文件上传、下载服务类
 */
public class FTPConnectionManager {

	Logger logger = LoggerFactory.getLogger(FTPConnectionManager.class);
	
	FTPClient ftpClient;
	
	ChannelSftp sftpClient;

	/**
	 * 上传文件
	 * @param path FTP服务器保存目录
	 * @param localFile 要上传到FTP服务器上的本地文件(路径及文件名)
	 * @param ftpFile   上传到FTP服务器上的文件名 
	 * @return 成功返回true，否则返回false
	 * */
	public boolean uploadFileByFTP(String path, String localFile, String ftpFile){
		// 初始表示上传失败
		boolean success = false;
		try {
			FileInputStream in = new FileInputStream(new File(localFile));
			String[] paths = path.substring(1).split("/");
			String pathTemp = "";
			logger.info("path"+path);
			logger.info("ftpFile"+ftpFile);
			for(String p : paths) {
				pathTemp += "/" + p;
				boolean flag = ftpClient.changeWorkingDirectory(pathTemp);
				if(!flag) {
					ftpClient.makeDirectory(pathTemp);
					ftpClient.changeWorkingDirectory(pathTemp);
				}
			}
			success = ftpClient.storeFile(ftpFile, in);
			in.close();
			logger.debug("upload success!");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("upload fail!", e);
			return false;
		} finally {
			closeConnectForFTP();
		}
		return success;
	}
	
	/**
	 *
	 * @param dir FTP文件夹路径
	 * @return
	 */
	public boolean createDirFtp(String dir){
		boolean success = false;
		try {

			String[] paths = dir.substring(1).split("/");
			String pathTemp = "";
			for(String p : paths) {
				pathTemp += "/" + p;
				boolean flag = ftpClient.changeWorkingDirectory(pathTemp);
				if(!flag) {
					ftpClient.makeDirectory(pathTemp);
					ftpClient.changeWorkingDirectory(pathTemp);
				}
			}
			success = true;
		} catch (Exception e) {
			logger.error("make dir false");
			return false;
		} finally {
			closeConnectForFTP();
		}
		logger.info("make dir success");
		return success;
	}  
	
	/**
	 * 下载文件
	 * @param ftpFile 要下载FTP服务器上的文件   (1.txt或者 20180110)
	 * @param localFile   下载到本地的文件(路径) 如为空，则自动创建该文件
	 * @return 成功返回true，否则返回false
	 * */
	public boolean downFileByFTP( String ftpFile, String localFile){
		// 初始表示上传失败
		boolean success = false;
		FTPFile[] fs;
		try{
			fs = ftpClient.listFiles();
			boolean isFind = false;
			logger.info("ftp文件名：" + ftpFile);
			for (FTPFile ff : fs) {


				if (ff.getName().contains(ftpFile)) {
					logger.info("文件名 ,{}",ff.getName());

					String locaPath = localFile+ ff.getName();
					isFind=true;
					logger.info("下载本地文件路径：" + locaPath);
					String localDir;
					try {
						localDir = locaPath.substring(0, locaPath.lastIndexOf("/"));
					} catch (StringIndexOutOfBoundsException e) {
						localDir = locaPath.substring(0, locaPath.lastIndexOf("\\"));
					}
					File file = new File(localDir);
					if (!file.exists()) {
						file.mkdirs();
					}
					File lf = new File(locaPath);
					if(!lf.exists()){
						lf.createNewFile();
					}
					FileOutputStream is = new FileOutputStream(lf);
					ftpClient.retrieveFile(ff.getName(), is);
					is.close();
					success = true;
					
				}
			}
			if(!isFind){
				logger.info("未发现ftp文件,提示:是否您已正确配置ftp路径和ftp文件名称[ftp路径必须为文件所在根路径,ftp文件名称必须为不包含任何路径的纯文件名称]");
			}
		}catch(Exception ex){
			logger.error("download fail!", ex);
			return success;
		}finally{
			// 关闭连接
			closeConnectForFTP();
		}
		if(success) {
			logger.debug("download success!");
		} else {
			logger.error("download fail!");
		}
		return success;
	}
	
	/**
	 * 创建连接
	 * @param url FTP服务器hostname
	 * @param port FTP服务器端口
	 * @param username FTP登录账号
	 * @param password FTP登录密码
	 * @param path FTP服务器保存目录
	 * */
	public boolean connectServerForFTP(String url, int port, String username, String password, String path)throws Exception {
		ftpClient = null;
		if (ftpClient == null) {
			int reply;
			try {
				ftpClient = new FTPClient();
				ftpClient.setDefaultPort(port);
				ftpClient.connect(url);
				ftpClient.login(username, password);
				ftpClient.enterLocalPassiveMode();
				if(path != null && !path.equals("")) {
					boolean flag = ftpClient.changeWorkingDirectory(path);
					if(!flag) {
						logger.error("error ftp path");
						throw new Exception("FTP服务器路径错误");
					}
				}
				reply = ftpClient.getReplyCode();
				ftpClient.setDataTimeout(120000);
				if (!FTPReply.isPositiveCompletion(reply)) {
					ftpClient.disconnect();
				}
				
			} catch (SocketException e) {
				e.printStackTrace();
				logger.info("connect " + url + " fail,timeout！");
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				logger.info("connect " + url + " fail,server not open！");
				return false;
			}
		}
		logger.info("connect " + url + " success");
		return true;
	}
	
	/**
	 * 关闭连接
	 * */
	public void closeConnectForFTP(){
		logger.debug("close ftp server");
		try {
			if (ftpClient != null) {
				ftpClient.logout();
				ftpClient.disconnect();
				ftpClient = null;
				logger.debug("disconnect success!");
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("disconnect fail!", e);
		}
	}
	
	/**
	 * 上传文件
	 * @param localFile 要上传到SFTP服务器上的本地文件(路径及文件名)
	 * @param sftpFile   上传到SFTP服务器上的文件名 
	 * @return 成功返回true，否则返回false
	 * */
	public boolean uploadFileBySFTP( String localFile, String sftpFile){
		// 初始表示上传失败
		boolean success = false;
		try{
			logger.info("上传的文件名，{}",sftpFile);
			logger.info("上传的文件路径，{}",localFile);
			// 创建服务器连接
			// 上传文件
			File file = new File(localFile);
			FileInputStream fis = new FileInputStream(file);
			sftpClient.put(fis, sftpFile);
			logger.debug("upload success!");
			fis.close();
			success = true;
		}catch(Exception ex){
			logger.error("upload fail!", ex);
			return success;
		}finally{
			// 关闭连接
			closeConnectForSFTP();
		}
		
		return success;
	}
	
	/**
	 * 下载文件
	 * @param sftpFile 要下载SFTP服务器上的文件
	 * @param localFile   下载到本地的文件(路径及文件名) 
	 * @return 成功返回true，否则返回false
	 * */
	public boolean downFileBySFTP( String sftpFile, String localFile){
		// 初始表示上传失败
		boolean success = false;
		try{
			// 下载文件
			File file = new File(localFile);
			file.createNewFile();
			FileOutputStream fos = new FileOutputStream(file);
			System.out.println(sftpFile);
			sftpClient.get(sftpFile, fos);
			logger.debug("download success!");
			fos.close();
			success = true;
		}catch(Exception ex){
			logger.error("download fail!", ex);
			return success;
		}finally{
			// 关闭连接
			closeConnectForSFTP();
		}
		return success;
	}
	
	/**
	 * 创建连接
	 * @param url SFTP服务器hostname
	 * @param port SFTP服务器端口
	 * @param username SFTP登录账号
	 * @param password SFTP登录密码
	 * @param path SFTP服务器保存目录
	 * @throws Exception
	 */
	public void connectServerForSFTP(String url, int port, String username, String password, String path) throws Exception {
		// 创建ChannelSftp对象
		sftpClient = new ChannelSftp();
		JSch jsch = new JSch();
		// 创建连接session
		Session session = jsch.getSession(username, url, port);
		session.setPassword(password);
		Properties sshConfig = new Properties();
		sshConfig.put("StrictHostKeyChecking", "no");
		session.setConfig(sshConfig);
		session.connect();
		// 创建通道
		Channel channel = session.openChannel("sftp");
		channel.connect();
		sftpClient = (ChannelSftp) channel;

		// 转到指定目录
		sftpClient.cd(path);
	}
	
	/**
	 * 关闭连接
	 */
	public void closeConnectForSFTP(){
		try{
			sftpClient.disconnect();
			sftpClient.getSession().disconnect();
			logger.debug("disconnect success!");
		}catch(Exception ex){
			logger.error("disconnect fail!", ex);
		}
	}
	
	
	/**
	 * FTP迁移目录（支持多层级目录迁移）
	 * @param sourceFolderPath 源目录
	 * @param targetFolderPath 目标目录
	 * @return
	 */
	public boolean removeFolder(String sourceFolderPath, String targetFolderPath) {
		FTPFile[] fs;
		try{
			logger.info("源文件根路径：" + sourceFolderPath);
			fs = ftpClient.listFiles();
			for (FTPFile ff : fs) {
				logger.info("文件（夹）名：" + ff.getName() + "是文件，目标目录：" + targetFolderPath);
				if(!ftpClient.changeWorkingDirectory(targetFolderPath)) {
					boolean f = createFolder(targetFolderPath);
					if(f) {
						logger.info("创建文件夹" + targetFolderPath + "成功");
					} else {
						logger.info("创建文件夹" + targetFolderPath + "失败");
						return false;
					}
				}
				if(ff.isDirectory()) {
					removeFolder( sourceFolderPath + "/" + ff.getName(), targetFolderPath+ "/" + ff.getName());
				} else {
					boolean flag = ftpClient.rename(sourceFolderPath + "/" + ff.getName(), targetFolderPath + "/" + ff.getName());
					if(flag) {
						logger.info("迁移文件" + ff.getName() + "成功");
					} else {
						logger.info("迁移文件" + ff.getName() + "失败");
						return false;
					}
				}
			}
			ftpClient.removeDirectory(sourceFolderPath);
		} catch (Exception e) {
			logger.error("remove folder fail!", e);
			return false;
		} finally{
			// 关闭连接
			closeConnectForFTP();
		}
		return true;
	}
	
	/**
	 * FTP文件目录复制（支持多层级目录迁移）
	 * @param sourceFolderPath 源目录
	 * @param targetFolderPath 目标目录
	 * @return
	 */
	public boolean copyFolder(String sourceFolderPath, String targetFolderPath) {
        boolean copyFalg = false; 
        FTPFile[] filelist;
        try { 
        	// 创建服务器连接
			ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
            // 枚举当前from目录所有文件+子目录 
            filelist = ftpClient.listFiles(sourceFolderPath); 
            int length = filelist.length; 
            FTPFile ftpFile = null;
            String category = null;
            InputStream inputStream = null;
            source:
            for (int i = 0; i < length; i++) { 
                ftpFile = filelist[i]; 
                category = ftpFile.getName(); 
                if (ftpFile.isFile()) { 
                	
                	if(!ftpClient.changeWorkingDirectory(targetFolderPath)) {
                		boolean f = createFolder(targetFolderPath);
    					if(f) {
    						logger.info("创建文件夹" + targetFolderPath + "成功");
    					} else {
    						logger.info("创建文件夹" + targetFolderPath + "失败");
    						return false;
    					}
                	}
                	
                	FTPFile[] targetDirFiles = ftpClient.listFiles();
					for (FTPFile file : targetDirFiles) {
						if(file.getName().equals(category)) {//目标文件夹中有原文件夹中的同名文件则不复制
							continue source;
						}
					}
                	
                	
                    inputStream = ftpClient.retrieveFileStream(sourceFolderPath  + category); 
                    if (!ftpClient.completePendingCommand()) { 
                        copyFalg = false; 
                        return copyFalg; 
                    } 
                    // 如果读取的文件流不为空则复制文件 
                    if (inputStream != null) { 
                        copyFalg = ftpClient.storeFile(targetFolderPath + category, inputStream); 
                        // 关闭文件流 
                        inputStream.close(); 
                        if (!copyFalg) { 
                            return copyFalg; 
                        } 
                    } 

                } else if (ftpFile.isDirectory()) { 
                    // 如果是目录则先创建该目录 
                    copyFalg = createFolder(targetFolderPath + category); 
                    // 再进入子目录进行递归复制 
                    copyFolder( sourceFolderPath + category + "/", targetFolderPath + category + "/");
                } 
            } 
        } catch (Exception e) {
            logger.error("copy folder fail!" + e.getMessage(), e); 
            copyFalg = false; 
        } finally{
			// 关闭连接
			closeConnectForFTP();
		}
        return copyFalg; 
    }
	
	
	/**
	 * FTP文件复制
	 * @param sourceFilePath 源文件
	 * @param targetFilePath 目标文件
	 * @return
	 */
	public boolean copyFile( String sourceFilePath, String targetFilePath) {
        boolean copyFlag = false; 
        try {
			
			ftpClient.setBufferSize(1024); 
			ByteArrayOutputStream fos=new ByteArrayOutputStream();
			ftpClient.retrieveFile(sourceFilePath, fos);
			
            String targetFolderPath = targetFilePath.substring(0, targetFilePath.lastIndexOf("/"));
            logger.info("目标文件路径：" + targetFolderPath);
            if(!ftpClient.changeWorkingDirectory(targetFolderPath)) {
        		boolean f = createFolder(targetFolderPath);
				if(f) {
					logger.info("创建文件夹" + targetFolderPath + "成功");
				} else {
					logger.info("创建文件夹" + targetFolderPath + "失败");
					return false;
				}
        	} else{
        		 logger.info("切工作区成功");
        	}
            
            ByteArrayInputStream in=new ByteArrayInputStream(fos.toByteArray());
			ftpClient.storeFile(targetFilePath, in);
			fos.close();
			in.close();
			
			copyFlag = true;
        } catch (Exception e) {
            logger.error("copy folder fail!" + e.getMessage(), e); 
            copyFlag = false; 
        } finally{
			// 关闭连接
			closeConnectForFTP();
		}
        if(copyFlag) {
        	 logger.info("复制文件成功");
        }
        return copyFlag; 
    }
	
	
	/**
	 * 遍历创建目录
	 * @param path
	 * @return
	 * @throws IOException
	 */
	public boolean createFolder(String path) throws IOException {
		if(!ftpClient.changeWorkingDirectory(path)) {
			String[] paths = path.substring(1).split("/");
			String pathTemp = "";
			for(String p : paths) {
				pathTemp += "/" + p;
				if(!ftpClient.changeWorkingDirectory(pathTemp)) {
					if(!ftpClient.makeDirectory(pathTemp)) {
						return false;
					}
				}
			}
    	}
		return true;
	}

	/**
	 * 下载文件
	 * @param oldName 要替换的文件名
	 * @param newName   新名称
	 * @return 成功返回true，否则返回false
	 * */
	public boolean updFileBySFTP( String oldName, String newName){
		// 初始表示上传失败
		boolean success = false;
		try{
			// 下载文件
			sftpClient.rename(oldName,newName);

			logger.debug("update success!");
			success = true;
		}catch(Exception ex){
			logger.error("download fail!", ex);
			return success;
		}finally{
			// 关闭连接
			closeConnectForSFTP();
		}
		return success;
	}
	public boolean createDirSftp(String pathName){
		try{
			sftpClient.cd(pathName);

			return true;
		}catch (Exception e){
			logger.error("文件不存在，{}",e);
			try{
				sftpClient.rmdir(pathName);
			}catch (Exception e1){

			}
		}
		return false;
	}
	
	public static void main(String[] args) throws Exception{
		FTPConnectionManager manager = new FTPConnectionManager();
		//manager.uploadFileByFTP("192.168.8.33", 21, "issueinte", "&5Go1Qmew", "/app/lij/test/", "D:/ISUE_20150731.txt", "ISUE_20150731.txt");
		manager.connectServerForSFTP("",123,"","","");

		System.out.println(manager.copyFile( "/bus/ISUE/a.txt", "/batchPay/ISUE/a.txt"));
	}
	
}
