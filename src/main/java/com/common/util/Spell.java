package com.common.util;

import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

/**
 * 
 * Project :	Common
 * classFile :	com.sxl.common.util
 *
 * Modify Information
 *		Author		Date		
 *		=========   =============
 * 		sxl		    2017年4月11日
 * 
 * Description :
 * 	<pre>
 *			得到拼音
 *	<pre>
 */
public class Spell {
  

    /**
     * 得到全拼
     * @param str  Spell	
     * @return	china
     */
    public static String getPinYin(String str){
        char t1[]=null;
        t1=str.toCharArray();
        String[] t2=new String[t1.length];
        HanyuPinyinOutputFormat t3=new HanyuPinyinOutputFormat();
        t3.setCaseType(HanyuPinyinCaseType.LOWERCASE);
        t3.setToneType(HanyuPinyinToneType.WITHOUT_TONE);
        t3.setVCharType(HanyuPinyinVCharType.WITH_V);
        String t4="";
        int t0=t1.length;
        try {
            for ( int i = 0; i < t0; i++ ) {
                if(Character.toString(t1[i]).matches("[\\u4E00-\\u9FA5]+")){    //是用来判断是不是中文的一个条件，采用的是unicode编码
                    t2= PinyinHelper.toHanyuPinyinStringArray(t1[i],t3);
                    t4+=t2[0];
                }else {
                    t4+=Character.toString(t1[i]);
                }
            }
            return t4;
        } catch ( BadHanyuPinyinOutputFormatCombination badHanyuPinyinOutputFormatCombination ) {
            badHanyuPinyinOutputFormatCombination.printStackTrace();
        }
        return t4;
    }

    /**
     * 得到汉字首字母的拼音
     * @param str    china
     * @return	Spell
     */
    public static String getPinYinHeaderChar(String str){
        String convert="";
        for ( int i = 0; i < str.length(); i++ ) {
            char word=str.charAt(i);
            String[] pinYinArray=PinyinHelper.toHanyuPinyinStringArray(word);
            if ( pinYinArray!=null ){
                convert+=pinYinArray[0].charAt(0);
            }else {
                convert+=word;
            }
        }
        return convert;
    }


}