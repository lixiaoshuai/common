package com.common.annotation;

import java.lang.annotation.*;

/**
 * Created by lixiaoshuai on 2017/9/28.
 *
 * @mail sxlshuai@foxmail.com
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Ignore {
}
