package com.common.util.test.xml;


import java.io.IOException;

import javax.xml.bind.JAXBException;

import com.common.util.xml.XmlUtil;

public class Text {
	
	public static void main(String[] args) throws JAXBException, IOException {
		Emp emp = new Emp();
		emp.setId(1);
		emp.setAge(23);
		emp.setName("李");
		emp.setAddress("河南");
		//xml 字符串
		String xml = XmlUtil.ObjToXml(emp, Emp.class);
		System.out.println(xml);
		Emp e = (Emp)XmlUtil.XmlToObj(xml, Emp.class);
		System.out.println(e.getAddress());
		
	}
	
	
}
