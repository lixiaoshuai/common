package com.common.util.bytes;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;

/**
 * @Author lxs
 * @Description //TODO
 * @Date 2019/3/26 14:05
 **/
public class ByteUtils {

    /**
     * @Description:  2进制转16进制
     * @param b
     * @return
     */
    public static String byteToHex(byte b){
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
    public static byte HexToByte(String hexStr){
        return (byte) Integer.valueOf(hexStr, 16).byteValue();
    }


    /**
     * @Description: 2进制转16进制
     *
     * @param b   字节数组
     * @return  16进制字符串
     */
    public static String byteArrayToHex(byte[] b) {
        StringBuffer result = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            result.append(byteToHex(b[i]));
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
    public static byte[] hexToByteArray(String src) {
        int l = src.length() / 2;
        byte[] ret = new byte[l];
        for (int i = 0; i < l; i++) {
            ret[i] = HexToByte(src.substring(i * 2, i * 2 + 2));
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
    public static String strToHex(String strPart) {
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
    public static String hexToStr(String src) {
        String temp = "";
        for (int i = 0; i < src.length() / 2; i++) {
            temp = temp + (char) Integer.valueOf(src.substring(i * 2, i * 2 + 2),16).byteValue();
        }
        return temp;
    }


    /**
     *  char 转byte
     * @param c
     * @return
     */
    public static byte charTobyte(char c){
        return (byte) "0123456789ABCDEF".indexOf(c);
    }


    /**
     * @功能: BCD码转为10进制串(阿拉伯数据)
     * @参数: BCD码
     * @结果: 10进制串
     */
    public static String bcdToStr(byte[] bytes) {
        StringBuffer temp = new StringBuffer(bytes.length * 2);
        for (int i = 0; i < bytes.length; i++) {
            temp.append((byte) ((bytes[i] & 0xf0) >>> 4));
            temp.append((byte) (bytes[i] & 0x0f));
        }
        return temp.toString().substring(0, 1).equalsIgnoreCase("0") ? temp
                .toString().substring(1) : temp.toString();
    }

    /**
     * 将String转成BCD码
     *
     * @param s     数字类型的字符串
     * @return  BCD码
     */
    public static byte[] strToBCDLeft(String s) {
        if (s.length() % 2 != 0) {
            s = "0"+s;
        }
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        char[] cs = s.toCharArray();
        for (int i = 0; i < cs.length; i += 2) {
            int high = cs[i] - 48;
            int low = cs[i + 1] - 48;
            baos.write(high << 4 | low);
        }
        return baos.toByteArray();
    }


    /**
     *
     * @param bts
     * @return
     * @throws Exception
     */
    public static int byteArrayToInt(byte[] bts) throws Exception {
        return byteArrayToInt(bts,0,bts.length);
    }


    public static int byteArrayToInt(byte[] bts, int iOff, int iExpLen)  {
        int iTmp = 0;
        for(int i=0;i<iExpLen;i++) {
            iTmp = iTmp << 8;
            iTmp = iTmp | (bts[iOff++]&0x00FF);
        }
        return iTmp;
    }

    /**
     * 字符串转换为Ascii
     *
     * @param value
     * @return
     */
    public static byte[] strToAscii(String value) {
        StringBuffer sbu = new StringBuffer();
        char[] chars = value.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            if (i != chars.length - 1) {
                sbu.append((int) chars[i]).append(",");
            } else {
                sbu.append((int) chars[i]);
            }
        }
        return sbu.toString().getBytes();

    }
    /**
     * Ascii转换为字符串
     *
     * @param value
     * @return
     */
    public static String asciiToStr(String value) {
        StringBuffer sbu = new StringBuffer();
        String[] chars = value.split(",");
        for (int i = 0; i < chars.length; i++) {
            sbu.append((char) Integer.parseInt(chars[i]));
        }
        return sbu.toString();
    }


    public static byte[] BCD2ASCII(byte [] bcd_buf,int len)
    {
        int i,n;
        n=len;
        ByteBuffer asc_buf=ByteBuffer.allocate(n);
        byte tmp;
        for (i = 0; i < n/2; i++) {
            tmp = (byte) ((bcd_buf[i] & 0xf0) >> 4);
            tmp = abcdToAsc(tmp);
            asc_buf.put(tmp);
            tmp = (byte) (bcd_buf[i] & 0x0f);
            tmp = abcdToAsc(tmp);
            asc_buf.put(tmp);
        }
        if (n % 2!=0) {
            tmp = (byte) ((bcd_buf[i] & 0xf0) >> 4);
            tmp = abcdToAsc(tmp);
            asc_buf.put(tmp);
        }
        asc_buf.flip();
        byte[] res=null;
        res=new byte[asc_buf.remaining()];
        asc_buf.get(res, 0, res.length);
        return res;
    }

    private static byte abcdToAsc(byte abyte){
        if (abyte <= 9){
            abyte = (byte) (abyte + '0');}
        else{
            abyte = (byte) (abyte + 'A' - 10);}
        return (abyte);
    }

    public static int BCDTOInt(byte []bcd_buf,int len){
        try{
            return Integer.parseInt(new String(BCD2ASCII(bcd_buf,len),"US-ASCII"));
        }catch(Exception ex){
            throw new RuntimeException(ex);
        }

    }

}
