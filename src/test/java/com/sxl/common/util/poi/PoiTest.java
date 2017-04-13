package com.sxl.common.util.poi;

public class PoiTest {

	@org.junit.Test
	public void readExcel() throws Exception {
		// 文件所在路径
		String execelFile = "d:/222.xls";

		System.out.println();
		// 读取Excel
		StringBuffer sb = new PoiUtil().readExcel(execelFile, 0, 0);
		System.out.println(sb.toString());
	}
	
	
}
