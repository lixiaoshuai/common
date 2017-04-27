package com.common.util.xml;



import java.util.List;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * @author sxl
 *
 */

//XmlAccessType.FIELD:java对象中的所有成员变量
@XmlAccessorType(XmlAccessType.FIELD)
//用于类级别的注解，对应xml的跟元素
//bppos 跟元素的名称
@XmlRootElement(name="bppos")
public class Emp extends People{
	
	//将java对象的属性映射为xml的节点
	@XmlElement 
	private String address;
	
	@XmlElement
	private List<String> list;
	
	
	/**
	 * @return the list
	 */
	public List<String> getList() {
		return list;
	}
	/**
	 * @param list the list to set
	 */
	public void setList(List<String> list) {
		this.list = list;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	
}
