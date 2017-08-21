package com.common.util.test.xml.castor;

import com.common.util.test.xml.castor.Duby;

import java.util.List;


/**
 * @author sxl
 *
 */


public class Emp  {
	

	private String address;

	private List<String> list;

	private Duby duby;
	
	
	
	public Emp(String address, List<String> list, Duby duby) {
		super();
		this.address = address;
		this.list = list;
		this.duby = duby;
	}
	public Duby getDuby() {
		return duby;
	}
	public void setDuby(Duby duby) {
		this.duby = duby;
	}
	public Emp() {
		super();
	}
	public Emp(String address, List<String> list) {
		super();
		this.address = address;
		this.list = list;
	}
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
