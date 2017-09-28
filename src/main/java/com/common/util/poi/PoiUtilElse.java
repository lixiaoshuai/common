/*
 * @(#)NetworkRouteController.java     V1.0.0      @2014-8-25
 *
 * Project: 
 *
 * Modify Information:
 *    Author        Date        Description
 *    ============  ==========  =======================================
 *    pengxuan       2014-8-25     Create this file
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
package com.common.util.poi;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.security.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * excel工具类
 * @author pengxuan
 *
 */
public class PoiUtilElse {
	private static Logger logger = LoggerFactory.getLogger(PoiUtil.class);



	
	/**
	 * 读取excel文件数据到list中
	 * @param f
	 * @param iRowStart
	 * @param iColStart
	 * @param list
	 * @param strKeyArray
	 * @param cls
	 */
	@SuppressWarnings("unchecked")
	public static List getExcelToList(MultipartFile f, int iRowStart, int iColStart,
                                      List list, String strKeyArray[], Class<?> cls) throws Exception {
		boolean flag = false;
		InputStream fis = f.getInputStream();
		POIFSFileSystem pfs = new POIFSFileSystem(fis);
		HSSFWorkbook wb = new HSSFWorkbook(pfs);
		HSSFSheet sheet = wb.getSheetAt(0);
		for (int iRow = iRowStart;; iRow++) {
			HSSFRow row = sheet.getRow((short) iRow);
			Object dto = cls.newInstance();
			for (int iCol = iColStart; iCol < strKeyArray.length; iCol++) {
				if (null == row) {
					flag = true;
					break;
				}
				HSSFCell cell = row.getCell(iCol);
				if (!"".equals(strKeyArray[iCol])) {
					Field field = cls.getDeclaredField(strKeyArray[iCol]);
					field.setAccessible(true);
					field.set(dto, getCellValue2Str(cell));
				}
			}
			if (flag) {
				break;
			}
			list.add(dto);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	public static List getExcelToList(File f, int iRowStart, int iColStart,
                                      List list, String strKeyArray[], Class<?> cls) throws Exception {
		boolean flag = false;
		InputStream fis = new FileInputStream(f);
		POIFSFileSystem pfs = new POIFSFileSystem(fis);
		HSSFWorkbook wb = new HSSFWorkbook(pfs);
		HSSFSheet sheet = wb.getSheetAt(0);
		for (int iRow = iRowStart;; iRow++) {
			HSSFRow row = sheet.getRow((short) iRow);
			Object dto = cls.newInstance();
			for (int iCol = iColStart; iCol < strKeyArray.length; iCol++) {
				if (null == row) {
					flag = true;
					break;
				}
				HSSFCell cell = row.getCell(iCol);
				if (!"".equals(strKeyArray[iCol])) {
					Field field = cls.getDeclaredField(strKeyArray[iCol]);
					field.setAccessible(true);
					field.set(dto, getCellValue2Str(cell));
				}
			}
			if (flag) {
				break;
			}
			list.add(dto);
		}
		return list;
	}



	/**
	 * 
	 * @param strTemplate
	 *            模板文件路径
	 * @param iRowStart
	 *            数据开始行（从0开始）
	 * @param iColStart
	 *            数据开始列（从0开始）
	 * @param list
	 *            数据
	 * @param strKeyArray
	 *            显示列，与excel列号对应，列号从0开始。当数组值为空时，显示对象内所有值
	 * @param cls
	 * 	           list中对象的class
	 * @return
	 */
	public static HSSFWorkbook getListToExcel(String strTemplate, int iRowStart, int iColStart,
                                              List list, String strKeyArray[], Class<?> cls) throws Exception {
		HSSFWorkbook wb = null;
		HSSFSheet sheet = null;

		// 当数组为空时，把对象中所有名称复制到数据中
		if(null == strKeyArray){
			Field[] fields = cls.getDeclaredFields();
			strKeyArray = new String[fields.length];
			for(int i = 0; i < fields.length; i++){
				strKeyArray[i] = fields[i].getName();
			}
		}
		// 从文件路径中读取模板文件
		if(null == strTemplate){	//当路径为空时，创建新的文件
			wb = new HSSFWorkbook();
			sheet = wb.createSheet();
		} else {
			wb = inputTemplateFile(strTemplate);
		}
		if (wb != null) {
			if (list != null) {
				// 获取第一个sheet的数据
				sheet = wb.getSheetAt(0);
				int lastRowNum = sheet.getLastRowNum();
				if(lastRowNum > iRowStart){
					if (list.size() > 1) {
						// 拷贝iRowStart的格式，每次拷贝一行。拷贝从第0列到第strKeyArray.length长度的数据
						insertCopyCell(sheet, iRowStart, 1, iColStart, strKeyArray.length, list.size() - 1);
					}
				}
				for (int iRow = iRowStart; iRow < list.size() + iRowStart; iRow++) {
					Object obj = list.get(iRow - iRowStart);
					for (int iCol = 0; iCol < strKeyArray.length; iCol++) {
						Field field = cls.getDeclaredField(strKeyArray[iCol]);
						field.setAccessible(true);
						writeCell(sheet, iRow, iCol, field.getType(), field.get(obj));
					}
				}
			}
		}

		return wb;
	}
	
	/**
	 * 
	 * @param fromCell
	 * @return
	 */
	public static String getCellValue2Str(HSSFCell fromCell) {
		String ret = "";
		if (null == fromCell)
			return ret;

		int cellType = fromCell.getCellType();
		switch (cellType) {

		case HSSFCell.CELL_TYPE_BOOLEAN:
			ret = String.valueOf(fromCell.getBooleanCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_ERROR:
			ret = String.valueOf(fromCell.getErrorCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_FORMULA:
			ret = String.valueOf(parseFormula(fromCell.getCellFormula())).trim();
			break;
		case HSSFCell.CELL_TYPE_NUMERIC:
			ret = String.valueOf(fromCell.getNumericCellValue()).trim();
			break;
		case HSSFCell.CELL_TYPE_STRING:
			ret = String.valueOf(fromCell.getRichStringCellValue()).trim();
			break;
		default:
			break;
		}
		return ret;
	}

	/**
	 * 
	 * @param strTemplate
	 * @return
	 */
	private static HSSFWorkbook inputTemplateFile(String strTemplate) {
		File templateFile = null;
		FileInputStream inputStream = null;
		POIFSFileSystem templetIn = null;
		HSSFWorkbook wb = null;
		try {
			templateFile = new File(strTemplate);
			if (templateFile.exists()) {
				inputStream = new FileInputStream(templateFile);
				templetIn = new POIFSFileSystem(inputStream);
				wb = new HSSFWorkbook(templetIn);
				inputStream.close();
			} else {
				logger.info("文件不存在！");
			}
		} catch (Exception e) {
			logger.error("[资金能力业管][inputTemplateFile]Exception:", e);
		}
		return wb;
	}

	/**
	 * 
	 * @param wb
	 * @param targetName
	 * @return
	 */
	private static boolean outputTargetFile(HSSFWorkbook wb, String targetName) {
		FileOutputStream fileOut = null;
		try {
			fileOut = new FileOutputStream(new File(targetName));
			wb.write(fileOut);
			fileOut.close();
		} catch (Exception e) {
			logger.error("[资金能力业管][outputTargetFile]Exception:", e);
			return false;
		}
		return true;
	}

	/**
	 * 
	 * @param sheet
	 * @param iRow
	 * @param iCol
	 * @param clsType
	 * @param obj
	 */
	public static void writeCell(HSSFSheet sheet, int iRow, int iCol,
                                 Class<?> clsType, Object obj) {
		HSSFRow row = null;
		HSSFCell cell = null;
		if (obj != null) {
			if(null == sheet.getRow(iRow)){
				row = sheet.createRow(iRow);
				cell = row.createCell(iCol);
			} else {
				row = sheet.getRow(iRow);
				if(null == row.getCell(iCol)){
					cell = row.createCell(iCol);
				} else {
					cell = row.getCell(iCol);
				}
			}
			if (String.class == clsType) {
				cell.setCellValue(new HSSFRichTextString((String)obj));
			} else if (Date.class == clsType) {
				cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd").format(obj));
			} else if (BigDecimal.class == clsType) {
				cell.setCellValue(((BigDecimal)obj).doubleValue());
			} else if (Double.class == clsType || double.class == clsType) {
				cell.setCellValue((Double)obj);
			} else if (Long.class == clsType || long.class == clsType) {
				cell.setCellValue((Long)obj);
			} else if (Integer.class == clsType || int.class == clsType) {
				cell.setCellValue((Integer)obj);
			} else if (Timestamp.class == clsType){
				cell.setCellValue(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(obj));
			} else {
				cell.setCellValue(new HSSFRichTextString(String.valueOf(obj)));
			}
		}
	}

	/**
	 * 
	 * @param sheet
	 * @param iRowStart
	 * @param iRowCount
	 * @param iColStart
	 * @param iColCount
	 * @param iDataCount
	 */
	private static void insertCopyCell(HSSFSheet sheet, int iRowStart,
                                       int iRowCount, int iColStart, int iColCount, int iDataCount) {
		HSSFRow oldRow, newRow;
		HSSFCell oldCell, newCell;
		int cellType;
		int iRowTemp = iRowStart;

		sheet.shiftRows(iRowStart + iRowCount, sheet.getLastRowNum(), iRowCount * iDataCount, true, false);
		for (int iLoop = 0; iLoop < iDataCount; iLoop++) {
			iRowTemp = iRowTemp + iRowCount;
			for (int iLoop1 = 0; iLoop1 < iRowCount; iLoop1++) {
				oldRow = sheet.getRow(iRowStart + iLoop1);
				newRow = sheet.createRow(iRowTemp + iLoop1);
				newRow.setHeight(oldRow.getHeight());
				for (int iLoop2 = 0; iLoop2 < iColCount; iLoop2++) {
					oldCell = oldRow.getCell(iColStart + iLoop2);
					newCell = newRow.createCell(iColStart + iLoop2);
					newCell.setCellStyle(oldCell.getCellStyle());
					cellType = oldCell.getCellType();
					newCell.setCellType(cellType);
					setCellValue(oldCell, newCell);
				}
			}
		}
	}

	/**
	 * 
	 * @param fromCell
	 * @param toCell
	 */
	private static void setCellValue(HSSFCell fromCell, HSSFCell toCell) {
		if (fromCell == null)
			return;

		int cellType = fromCell.getCellType();
		switch (cellType) {

		case HSSFCell.CELL_TYPE_BOOLEAN://布尔值
			toCell.setCellValue(fromCell.getBooleanCellValue());
			break;
		case HSSFCell.CELL_TYPE_ERROR://错误信息
			toCell.setCellErrorValue(fromCell.getErrorCellValue());
			break;
		case HSSFCell.CELL_TYPE_FORMULA://公式
			toCell.setCellFormula(parseFormula(fromCell.getCellFormula()));
			break;
		case HSSFCell.CELL_TYPE_NUMERIC://数字
			toCell.setCellValue(fromCell.getNumericCellValue());
			break;
		case HSSFCell.CELL_TYPE_STRING://字符串
			toCell.setCellValue(fromCell.getRichStringCellValue());
			break;
		default:
			break;
		}
	}

	/**
	 * 
	 * @param strFormula
	 * @return
	 */
	private static String parseFormula(String strFormula) {
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

    /***
     * 将生成好excel数据保存到生成好的文件中
     * @param  wb
     * @param  targetName
     */
    public static boolean writeFile(HSSFWorkbook wb, String targetName){
        FileOutputStream fileOut;
        try {
            fileOut = new FileOutputStream(new File(targetName));
            wb.write(fileOut);   
            fileOut.close(); 
        } catch (FileNotFoundException e) {
            logger.error("[资金能力业管]FileNotFoundException", e);
            return false;
        } catch (IOException e) {
            logger.error("[资金能力业管]IOException", e);
            return false;
        }   
        return true;
    }
    
    public static void returnExcel(HttpServletResponse response, HSSFWorkbook workbook, String fileName) {
//		response.setCharacterEncoding("UTF-8");
		response.setHeader("Content-Type", "application/force-download");
		response.setHeader("Content-Type", "application/vnd.ms-excel");
		try {
			fileName = URLEncoder.encode(fileName, "UTF-8");
			response.setHeader("Content-disposition", "attachment;filename=\"" + fileName + ".xls\"");
			OutputStream ouputStream;
			ouputStream = response.getOutputStream();
			workbook.write(ouputStream);
			ouputStream.flush();
			ouputStream.close();
		} catch (UnsupportedEncodingException e) {
			logger.info("在对文件名进行转码时,出现问题,请您输入正确的Encode字符串!");
			e.printStackTrace();
		} catch (IOException e) {
			logger.info("准备将生成的Excel输出到response时出现问题,原因有可能是response已经关闭或其他问题!");
			e.printStackTrace();
		}
	}
	
}
