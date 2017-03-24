package com.sxl.common.util.xml.castor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBException;

import com.sxl.common.util.xml.CastorXMLUtil;


public class CastorText {
	
	public static void main(String[] args) throws JAXBException, IOException {
		
		List<String> list = new ArrayList<String>();
		
		list.add("你好1");
		list.add("你好2");
		Duby duby = new Duby("木工","3年");
		
		Emp emp = new Emp("heNa",list,duby);
		
		People people = new People(12, "张三", 23, emp);
		String xml = CastorXMLUtil.obj2Xml(people, "utf-8",false);
		System.out.println(xml);
		people = (People)CastorXMLUtil.xml2Obj(People.class, xml);
		
		System.out.println(people.getEmp().getAddress());
		System.out.println(people.getEmp().getList().get(0));
		System.out.println(people.getEmp().getDuby().getDuby());
	}
	

}
