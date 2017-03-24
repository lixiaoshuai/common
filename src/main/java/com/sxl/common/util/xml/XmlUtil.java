package com.sxl.common.util.xml;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

public class XmlUtil {
	/**
	 * 
	 * @param object  xml绑定的对象的类
	 * @param classesToBeBound 
	 * @return
	 * @throws JAXBException
	 * 
	 * 
	 * class<?>  任意类型的对象
	 */
	//object转xml
	public static  String ObjToXml(Object object , Class<?> classesToBeBound) throws JAXBException{
		// 详细解析  http://blog.163.com/fly_sky_java/blog/static/140422234201042025455686/
		
		//声明对象 
		//  classesToBeBound  类
		JAXBContext context = JAXBContext.newInstance(classesToBeBound);
		//将 Java 内容树序列化回 XML 数据
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		m.setSchema(null);
		StringWriter sw = new StringWriter();
		m.marshal(object, sw);
		return sw.toString();
	}
	
	/**
	 * 
	 * @param xml   字符编码为UTF-8的xml字符串
	 * @param ObjectClazz   xml绑定的对象的类 
	 * @return  返回xml绑定的对象
	 * @throws JAXBException
	 * 
	 * 
	 * class<?>  任意类型的对象
	 */
	//string转object
	public static Object XmlToObj(String xml , Class<?> ObjectClazz) throws JAXBException{
		JAXBContext context = JAXBContext.newInstance(ObjectClazz);
		// XML 数据反序列化为新创建的 Java 内容树的过程
		Unmarshaller um = context.createUnmarshaller();
		StringReader reader = new StringReader(xml);
		return um.unmarshal(reader);
	}


}
