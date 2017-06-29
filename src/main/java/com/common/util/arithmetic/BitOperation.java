package com.common.util.arithmetic;



/**
 * http://blog.csdn.net/swing_xiao5/article/details/6625530
 * 
 * @author sxl
 * 
 */
public class BitOperation {

	public static void main(String[] args) {
		Integer j = Integer.MAX_VALUE;
		System.out.println(j);
		int i = (j + j) / 2;
		System.out.println(i);
		int k = average(j, j);
		System.out.println(k);

	}

	/**
	 * 平均数
	 * @param x
	 * @param y
	 * @return
	 */
	public static int average(int x, int y) {// 返回X,Y 的平均值
		return (x & y) + ((x ^ y) >> 1);
	}
	
}
