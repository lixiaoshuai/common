package com.common.util.collection;

import java.util.Iterator;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by lixiaoshuai on 2017/6/29.
 *
 * @mail sxlshuai@foxmail.com
 */
public class Collection {
    /**
     * 将Map中的数据转换成按照Key的ascii码排序后的key1=value1&key2=value2的形式 不包含签名域signature
     *
     * @param data
     *            待拼接的Map数据
     * @return 拼接好后的字符串
     */
    public static String covertMapToString(Map<String, String> data) {
        TreeMap<String, String> tree = new TreeMap<String, String>();
        Iterator<Map.Entry<String, String>> it = data.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            tree.put(en.getKey(), en.getValue());
        }
        it = tree.entrySet().iterator();
        StringBuffer sf = new StringBuffer();
        while (it.hasNext()) {
            Map.Entry<String, String> en = it.next();
            sf.append(en.getKey() + "=" + en.getValue()
                    + "&");
        }
        return sf.substring(0, sf.length() - 1);
    }


}
