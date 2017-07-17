package com.common.util.test;

import com.common.util.log.LogUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.*;
import java.lang.Thread;

/**
 * Created by lixiaoshuai on 2017/7/13.
 *
 * @mail sxlshuai@foxmail.com
 */
public class Test {

    private static Logger logger = LoggerFactory.getLogger(Test.class);
    public static void main(String[] args)throws Exception {

        say();
//        LogUtil.bulidLogger("Test");
//        for (int i = 0; i < 100; i++){
//            java.lang.Thread.sleep(1000);
//            logger.info("String:{},name:{},age:{}", "学校", "小华", "25");
//            LogUtil.get().info("String:{},name:{},age:{}", "学校", "小华", "25");
//        }
//        LogUtil.remove();
    }




    public static void say() {

        for (int i = 0; i < 1000; i++) {
            if (i % 10000 == 0) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
            logger.debug("##########################################");
            logger.error("##########################################");
            logger.info("##########################################");
            logger.trace("##########################################");

        }
    }
}
