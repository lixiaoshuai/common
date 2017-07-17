package com.common.util.log;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by lixiaoshuai on 2017/7/13.
 *
 * @mail sxlshuai@foxmail.com
 */
public class LogUtil {

    private static final String LOGGER_NAME="COMMON";

    private static ThreadLocal<Logger> tlLogger = new ThreadLocal<Logger>();
    /** 默认的Logger对象 */
    private static Logger logger = LoggerFactory.getLogger(LOGGER_NAME);


    public static void bulidLogger(String name){
        tlLogger.set(LoggerFactory.getLogger(name));
    }

    /**
     * @deprecated 获取该进程的Logger对象
     *
     * @return Logger
     */
    public static Logger get(){
        logger  = tlLogger.get();
        if(logger==null){
            return logger;
        }
        return logger;
    }


    /**
     * @deprecated 销毁进程
     *
     * @return Logger
     */
    public static void remove(){
        tlLogger.remove();
    }


}
