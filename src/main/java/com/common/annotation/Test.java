package com.common.annotation;

import java.lang.reflect.Field;

/**
 * Created by lixiaoshuai on 2017/9/20.
 *
 * @mail sxlshuai@foxmail.com
 */
public class Test {

    public static void main(String[] args) throws Exception{


        Emp e = new Emp();

        e.setAddres("河南");
        e.setName("asd");

        priObject(e);





    }


    public static String priObject(Object object)throws  Exception{
        Object value ;
        Class<?> cls = object.getClass();

        Field[]fields = cls.getDeclaredFields();

        for (Field field :fields){
            field.setAccessible(true);
            value = field.get(object);
            if(null == value){
                continue;
            }
            if(field.isAnnotationPresent(AnnoEntry.class)){
                AnnoEntry annoEncrypt = field.getAnnotation(AnnoEntry.class);
                String name = annoEncrypt.addres();
                System.out.println(name);
            }
            System.out.println(value);
        }


        return null;
    }
}
