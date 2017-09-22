package com.common.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by lixiaoshuai on 2017/9/19.
 *
 * @mail sxlshuai@foxmail.com
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AnnoEntry {

    String value() default "abc";

    String name() default "张三";

    String addres() default "123";
}
