package com.common.util.test.encryDncry;

import com.common.annotation.AnnoEncrypt;

/**
 * Created by lixiaoshuai on 2017/9/26.
 *
 * @mail sxlshuai@foxmail.com
 */
public class People {

    @AnnoEncrypt
    public String name;
    public String age;
    @AnnoEncrypt
    public String email;
    public String phone;

/*
    public String address;


    public String sex;

    public String height;

    public String weight;
    public String qq;*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }


}
