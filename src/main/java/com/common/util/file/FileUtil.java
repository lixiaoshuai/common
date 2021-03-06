
package com.common.util.file;

import java.io.BufferedReader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.List;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;



/**
 * @author sxl
 * 文件操作了
 */
public class FileUtil {

	static Log logger = LogFactory.getLog(FileUtil.class.getName());
	
	/**
	 * 创建文件.
	 * @param strPath 路径名.
	 * @param strFile 文件名.
	 * @param strMode 创建模式。
	 *		"0":若文件存在，清空；"1":若文件存在，保留原内容
	 * @return 成功返回FILE类
	 */
	public static File createFile(String strPath, String strFile, String strMode){
		File fp = new File(strPath, strFile);
		try{
			//判断文件是否存在被清空
			if (strMode.equals("0")) {
				fp.delete();
			}
			//判断路径是否存在,不存在创建
			createPath(strPath);

			//判断文件是否存在,不存在创建
			fp.createNewFile();
			
		}catch(IOException exp){
			logger.warn("访问、创建、删除文件无权限(路径名:"+strPath+")(文件名:"+strFile+")");
		}
		return fp;
	}

    /**
     * 读取文件
     * @param pvFileName
     * @return byte[]
     * @throws IOException
     */
    public static byte[] read(String pvFileName) throws IOException {
    	
        if(pvFileName == null || pvFileName.trim().equals("")) {
			return null;
		}
        //去掉文件名的前后空格，空格是非法的
        String lvFileName = pvFileName.trim();

        File lvFile = new File(lvFileName);
        byte[] lvBuffer = new byte[(int)(lvFile.length())];
        FileInputStream lvFIS= new FileInputStream(lvFile);
        lvFIS.read(lvBuffer);
        lvFIS.close();
        return lvBuffer;
    }

	/**
	 * 创建目录.
	 * @param strPath 路径名.
	 * @return成功返回0；否则返回-1
	 */
	public static int createPath(String strPath){
		File fp = new File(strPath);
		//判断路径是否存在,不存在创建
		if (!fp.exists()) {
			fp.mkdirs();
		}
		return 0;
	}

	/**
	 * 删除文件或目录.
	 * @param strPathFile 路径名/文件名.
	 * @return成功返回0；否则返回-1
	 */
	public static int deleteFile(String strPathFile){
		File fp = new File(strPathFile);
		//判断路径是否存在,存在删除
		if (fp.exists()) {
			fp.delete();
		}
		return 0;
	}

	/**
	 * 判断文件或目录是否存在.
	 * @param strPathFile 路径名/文件名.
	 * @return 存在返回True;否则false
	 */
	public static boolean isExists(String strPathFile){
		File fp = new File(strPathFile);
		if (fp.exists()) {
			return true;
		}
		return false;
	}

    /**
     * 创建文件内容，清空原文件内容，如文件不存在则创建新文件
     * @param strFilePath 路径.
     * @param strFileName 文件名.
     * @param strMsg 文件内容.
     * @throws IOException
     * @throws Exception
     */
    public static void  writeFile(String strFilePath, String strFileName, byte[]  strMsg){
        try{
            File file = createFile(strFilePath, strFileName,"0");
            FileOutputStream out=new FileOutputStream(file);
            out.write(strMsg);
            out.close();
        }catch (IOException ie){
            
        }
    }

    /**
     * 修改文件内容，保留原文件内容，新内容更新至文件末尾
     * @param strFilePath 路径.
     * @param strFileName 文件名.
     * @param strMsg 文件内容.
     * @throws IOException
     * @throws Exception
     *
     */
    public static void  writeUpdateFile(String strFilePath, String strFileName, byte[] strMsg){
        try{
            File file = createFile(strFilePath, strFileName,"1");
            FileOutputStream out=new FileOutputStream(file);
            out.write(strMsg);
            out.close();
        }catch (IOException ie){
            
        }
    }

    /**  
     *  复制单个文件  
     *  @param  oldPath  String  原文件路径  如：c:/fqf.txt  
     *  @param  newPath  String  复制后路径  如：f:/fqf.txt  
     *  @return  boolean  
     */  
   public static void copyFile(String oldPath, String newPath)  {  
       try  {  
           int  bytesum  =  0;  
           int  byteread  =  0;  
           File  oldfile  =  new  File(oldPath);
		   //文件存在时
           if  (oldfile.exists())  {
			   //读入原文件
               InputStream  inStream  =  new  FileInputStream(oldPath);
               @SuppressWarnings("resource")
			FileOutputStream  fs  =  new  FileOutputStream(newPath);  
               byte[]  buffer  =  new  byte[1444];
               while  (  (byteread  =  inStream.read(buffer))  !=  -1)  {
				   //字节数  文件大小
                   bytesum  +=  byteread;
                   System.out.println(bytesum);  
                   fs.write(buffer,  0,  byteread);  
               }  
               inStream.close();  
           }  
	   }catch (IOException ie){
	       
	   }	 
   }  
   
   /**
    * 根据传递过来的对象列表生成记录的Excel文件。
    * @param aFilePath       生成文件的绝对路径
    * @param aFileName       生成文件的文件名
    * @param aSheetName      生成文件的表单名
    * @param aFileDetail     订单对象列表
    * @throws Exception      生成文件失败
    */
   public static void makeExcelFile(String aFilePath, String aFileName, String aSheetName, String aFileDetail) throws Exception {

       File tPath = new File(aFilePath);
       if (!tPath.exists()) {
		   tPath.mkdir();
	   }

       String tFilePrefix = aFileName;
       String tFileSuffix = ".xls";

       String tFileName = tFilePrefix + tFileSuffix;
       String tPathFileName = aFilePath + tFileName;
       if(logger.isDebugEnabled()) {
		   logger.debug("excel file path -[" + tPathFileName + "]");
	   }
       try {
           //生成相应的 Excel 文档
           OutputStreamWriter tOutputStream = new OutputStreamWriter(new FileOutputStream(tPathFileName), "GBK");

           tOutputStream.write("<html xmlns:o=\"urn:schemas-microsoft-com:office:office\" \n");
           tOutputStream.write("xmlns:x=\"urn:schemas-microsoft-com:office:excel\" \n");
           tOutputStream.write("xmlns=\"http://www.w3.org/TR/REC-html40\"> \n");
           tOutputStream.write("<head> <meta http-equiv=Content-Type content=\"text/html; charset=gb2312\"> \n");
           tOutputStream.write("           <style> <!--table {mso-displayed-decimal-separator:'\\.'; \n");
           tOutputStream.write("           mso-displayed-thousand-separator:'\\,';} --> </style> \n");
           tOutputStream.write("<!--[if gte mso 9]><xml> \n");
           tOutputStream.write("  <x:ExcelWorkbook> \n");
           tOutputStream.write("	<x:ExcelWorksheets> \n");
           tOutputStream.write("		<x:ExcelWorksheet> \n");
           tOutputStream.write("		  <x:Name>" + aSheetName + "</x:Name> \n");
           tOutputStream.write("			<x:WorksheetOptions> \n");
           tOutputStream.write("			  <x:ProtectContents>False</x:ProtectContents> \n");
           tOutputStream.write("			  <x:ProtectObjects>False</x:ProtectObjects> \n");
           tOutputStream.write("			  <x:ProtectScenarios>False</x:ProtectScenarios> \n");
           tOutputStream.write("			</x:WorksheetOptions> \n");
           tOutputStream.write("		  </x:ExcelWorksheet> \n");
           tOutputStream.write("		</x:ExcelWorksheets> \n");
           tOutputStream.write("    </x:ExcelWorkbook> \n");
           tOutputStream.write("</xml><![endif]--> \n");
           tOutputStream.write("</head> <body> \n");
           
           tOutputStream.write(aFileDetail);
           
           tOutputStream.write("</body> \n");
           tOutputStream.close();

       }
       catch (Exception e) {
           logger.error("Make Excel File Failure !", e);
       }
   }
   
   /**
	 * 获取txt文件，每行都装配到List中
	 * @param file
	 * @return
	 */
	public static List<String> readTxtFile(File file){
		return readTxtFile(file,"UTF-8");
	}
	
	/**
	 * 获取txt文件，每行都装配到List中
	 * @param file
	 * @return
	 */
	public static List<String> readTxtFile(File file, String encoding){
		List<String> resultList = new ArrayList<String>();
		try {
			// 判断文件是否存在
			if (file.isFile() && file.exists()) {
				// 考虑到编码格式
				InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
				BufferedReader bufferedReader = new BufferedReader(read);
				String lineTxt = null;
				while ((lineTxt = bufferedReader.readLine()) != null) {
					resultList.add(lineTxt);
				}
				read.close();
			} else {
				logger.error( "找不到指定的批量文件！");
			}
		}catch (IOException e){
			logger.error( "找不到指定的批量文件！"+ e.getMessage());
		}catch (Exception e) {
			logger.error( "读取文件内容出现未知错误！"+ e.getMessage());
		}
		return resultList;
	}
	
	/**
	 * 获取txt文件，每行都装配到List中
	 * @param filePath
	 * @return
	 */
	public static List<String> readTxtFile(String filePath) {
		return readTxtFile(new File(filePath));
	}

}
