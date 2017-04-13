package com.sxl.common.util.poi;

import java.io.File;
import java.io.FileInputStream;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 * 
 * <pre>
 * 			excel工具类
 * 			支持2003,2007(xls.xlsx)
 * <pre>
 *
 * Author:sxl 2017年4月13日
 */
public class PoiUtil {

	

	/**
	 * 获取excel信息
	 * 
	 * @param execelFile
	 *            Excel 文件路径
	 * @param line
	 *            从第几行开始
	 * @param page
	 *            从哪页开始
	 * @throws Exception
	 */
	public StringBuffer readExcel(String execelFile, int line, int page) throws Exception {
		Workbook book = null;
		String exceType = execelFile.substring(execelFile.lastIndexOf(".") + 1);
		FileInputStream inputStream = new FileInputStream(new File(execelFile));
		if ("xls".equals(exceType)) {
			book = new HSSFWorkbook(inputStream);
		} else if ("xlsx".equals(exceType)) {
			book = new XSSFWorkbook(inputStream);
		}
		return importExcel2003(execelFile, book, line, page);
	}
	/**
	 * 
	 * @param execelFile 	Excel 文件路径
	 * @param book		Workbook
	 * @param line		 从第几行开始
	 * @param page	 从哪页开始
	 * @return
	 */
	private  StringBuffer importExcel2003(String execelFile, Workbook book, int line, int page) {
		try {
			StringBuffer strBuffer = new StringBuffer();
			Sheet sheet = book.getSheetAt(page); // 从表格的第几页开始
			Row row;
		
			int totalRows = sheet.getLastRowNum(); // 总共有多少行,从0开始
			// 循环输出表格中的内容,首先循环取出行,再根据行循环取出列
			for (int i = 0; i <= totalRows; i++) {
				row = sheet.getRow(i);
				if (row == null) { // 处理空行
					continue;
				}
				int totalCells = row.getLastCellNum(); // 总共有多少列,从0开始
				for (int j = row.getFirstCellNum(); j < totalCells; j++) {
					if (row.getCell(j) == null) { // 处理空列
						continue;
					}
					strBuffer.append(getCellValue2Str(row.getCell(j)) + "||");
					
				}
				strBuffer.append("\n");
			}
			return strBuffer;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	@SuppressWarnings("deprecation")
	private  String getCellValue2Str(Cell cell) {
		String ret = "";
		if (null == cell)
			return ret;

		int cellType = cell.getCellType();
		switch (cellType) {

		case HSSFCell.CELL_TYPE_BOOLEAN:
			ret = String.valueOf(cell.getBooleanCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			ret = String.valueOf(cell.getErrorCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			
			ret = String.valueOf(parseFormula(cell.getCellFormula())).trim();
			System.out.println(ret);
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			ret = String.valueOf(cell.getNumericCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_STRING:
			ret = String.valueOf(cell.getRichStringCellValue()).trim();
			break;
		default:
			break;
		}
		return ret;
	}
	/**
	 * 
	 * @param strFormula
	 * @return
	 */
	private  String parseFormula(String strFormula) {
		final String strCstReplace = "ATTR(semiVolatile)";
		StringBuffer result = null;
		int index;

		result = new StringBuffer();
		index = strFormula.indexOf(strCstReplace);
		if (index >= 0) {
			result.append(strFormula.substring(0, index));
			result.append(strFormula.substring(index + strCstReplace.length()));
		} else {
			result.append(strFormula);
		}

		return result.toString();
	}
	
	/**
	 * 		生成excel
	 * 	
	 * @param expFilePath	文件路径
	 * @param sheetName		页码名称
	 * @param list		要写入的数据
	 */
	public static void writeToExcel(String expFilePath,String sheetName,List<String> list) {
		OutputStream os = null;
		Workbook book = null;
		try {
			// 输出流
			os = new FileOutputStream(expFilePath);
			// 创建工作区(97-2003)
			book = new HSSFWorkbook();
			// 创建第一个sheet页
			Sheet sheet = book.createSheet(sheetName);
			// 生成第一行
			Row row =null;
			for (int i = 0; i < list.size(); i++) {
				row =  sheet.createRow(i);
				String line[] = list.get(i).split(ConstantPoi.split_equals);
				for (int j = 0; j < line.length; j++) {
					row.createCell(j).setCellValue(line[j]);
				}
			}
			book.write(os);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				os.close();// 关闭输出流
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		List<String> list = new ArrayList<String>();
		
		for (int i = 0; i < 100; i++) {
			list.add("aaa="+i);
		}
		writeToExcel("d:/123.xls","1",list);
		System.out.println(1+"{}");
		System.out.println("sdf,{}");
		String str[] = "aaa=1".split("=");
		System.out.println(str[0]+"    "+str[1]);
	}
}