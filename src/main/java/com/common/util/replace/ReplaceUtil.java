package com.common.util.replace;

/**
 * Created by lixiaoshuai on 2017/8/31.
 *
 * @mail sxlshuai@foxmail.com
 */
public class ReplaceUtil {

    public static String lineFeed = "\\r\\n";

    public static String stringReplaceAll(String str){
        str = str.replaceAll(lineFeed,"");
        return str;
    }

}
