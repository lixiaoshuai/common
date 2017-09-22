package com.common.util.bytes;

/**
 * Created by lixiaoshuai on 2017/9/22.
 *
 * @mail sxlshuai@foxmail.com
 */
public class ByteUtil {

    /**
     * @Description:  2进制转16进制
     * @param b
     * @return
     */
    public static String byteToHexstr(byte b){
        String hex = Integer.toHexString(b & 0xFF);
        if (hex.length() == 1) {
            hex = '0' + hex;
        }
        return hex.toUpperCase();
    }


    /**
     * @Description:  16进制转2进制
     * @param hexStr
     * @return
     */
    public static byte HexStrToByte(String hexStr){
        return (byte) Integer.valueOf(hexStr, 16).byteValue();
    }


    /**
     * @Description: 2进制转16进制
     *
     * @param b   字节数组
     * @return  16进制字符串
     */
    public static String byteArrayToHexStr(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(byteToHexstr(b[i]));
        }
        return result.toString();
    }


    /**
     * @Description:  16进制字符串转字节数组
     *
     * @param src 16进制字符串
     * @return 字节数组
     * @throws
     */
    public static byte[] hexStrToByteArray(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
//            ret[i] = (byte) Integer.valueOf(src.substring(i * 2, i * 2 + 2), 16).byteValue();
            ret[i] = HexStrToByte(src.substring(i * 2, i * 2 + 2));
        }
        return ret;
    }
    /**
     *
     * @Description:  字符串转16进制字符串
     * @param strPart  字符串
     * @return 16进制字符串
     * @throws
     */
    public static String string2HexString(String strPart) {
        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < strPart.length(); i++) {
            int ch = (int) strPart.charAt(i);
            String strHex = Integer.toHexString(ch);
            hexString.append(strHex);
        }
        return hexString.toString();
    }

    /**
     * @Description:16 进制字符串转字符串
     * @param src 16进制字符串
     * @return 字节数组
     * @throws
     */
    public static String hexString2String(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),16).byteValue();
        }
        return temp;
    }


}
