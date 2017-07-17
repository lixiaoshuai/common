package com.common.util.arithmetic;

/**
 * Created by lixiaoshuai on 2017/7/14.
 *
 * @mail sxlshuai@foxmail.com
 */
public class Sort {

    /**
     * 插入排序
     * 	不是最优的排序算法
     * 	特点是简单，不需要额外的存储空间，在元素少的时候工作得好
     * @author sxl
     *
     */
    public static void insertion_sort(int[] unsorted) {
        for (int i = 1; i < unsorted.length; i++) {
            if (unsorted[i - 1] > unsorted[i]) {
                int temp = unsorted[i];
                int j = i;
                while (j > 0 && unsorted[j - 1] > temp) {
                    unsorted[j] = unsorted[j - 1];
                    j--;
                }
                unsorted[j] = temp;
            }
        }
    }

    public static void main(String[] args) {
        int[] x = { 6, 2, 4, 1, 5, 9 ,4};
        insertion_sort(x);
        for (int i : x) {
            if (i > 0)
                System.out.print(i + ",");
        }

        System.out.println();
    }
}
