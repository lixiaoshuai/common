package com.common.util.test.log;

import com.common.util.log.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lixiaoshuai on 2017/7/13.
 *
 * @mail sxlshuai@foxmail.com
 */
public class Log {

    public static void main(String[] args)throws Exception {

        LogUtil.bulidLogger("1");
        for (int i = 0; i < 10000; i++){
            java.lang.Thread.sleep(50);
            LogUtil.get().info("Stringinfo:Stringinfo:Stringinfo:Stringinfo:Stringinfo:Stringinfo:Stringinfo:Stringinfo:Stringinfo:{},name:{},age:{}", "学校", "小华", "25");
            LogUtil.get().error("Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:Stringerror:{},name:{},age:{}", "学校", "小华", "25");
            LogUtil.get().debug("Stringdebug:{},name:{},age:{}", "学校", "小华", "25");
        }
        LogUtil.remove();
        System.out.println(1);
    }




}
