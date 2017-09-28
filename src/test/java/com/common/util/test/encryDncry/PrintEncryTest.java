package com.common.util.test.encryDncry;

import com.common.util.encryDncry.PrintEncrypt;
import org.junit.Test;

/**
 * Created by lixiaoshuai on 2017/9/26.
 *
 * @mail sxlshuai@foxmail.com
 */
public class PrintEncryTest {


    @Test
    public void printObj(){

        People people = new People();

        people.setPhone("123123");
        people.setAge("23");
        people.setEmail("2342@163.com");
        people.setName("张三");
     /*   people.setHeight("178");
        people.setWeight("120");
        people.setQq("123456");
        people.setSex("男");
        people.setAddress("河南");*/

        String str = PrintEncrypt.printObj(people);

        System.out.println(str);

    }
}
