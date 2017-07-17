package com.sxl.netty.common.util.xml;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;

/**
 * 
 * @author sxl
 *详细解释  
 *		注解的意思 
 *网址    http://blog.csdn.net/rj_jqd/article/details/8542909
 */
//用于指定由java对象生成xml文件时对java对象属性的访问方式
//none java对象的所有属性都不映射为xml的元素
@XmlAccessorType(XmlAccessType.NONE)
public class People {
	//用于把java对象的属性映射为xml的属性
	@XmlAttribute
	private int id;
	@XmlAttribute
	private String name;
	@XmlAttribute
	private int age;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getAge() {
		return age;
	}
	public void setAge(int age) {
		this.age = age;
	}
	
}
