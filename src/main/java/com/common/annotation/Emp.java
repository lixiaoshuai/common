package com.common.annotation;

/**
 * Created by lixiaoshuai on 2017/9/20.
 *
 * @mail sxlshuai@foxmail.com
 */
public class Emp {

    @AnnoEntry
    private String name;

    @AnnoEntry
    private String addres;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddres() {
        return addres;
    }

    public void setAddres(String addres) {
        this.addres = addres;
    }
}


