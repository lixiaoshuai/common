package com.common.util.string;

/**
 * Created by lixiaoshuai on 2017/9/26.
 *
 * @mail sxlshuai@foxmail.com
 */
public class StringUtil {
    /**
     * 判断字符串是否存在字符数组中
     *
     * @param str	待比对字符串
     * @param array	字符数组
     * @return true-存在；false-不存在
     */
    public static boolean searchInArray(String str, String array[]){
        for(String one : array){
            if(one.equals(str)){
                return true;
            }
        }
        return false;
    }
}
