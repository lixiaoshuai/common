package com.sxl.netty.common.util.xml.castor;


public class People {
	private int id;
	private String name;
	private int age;
	
	private Emp emp;
	
	
	public People() {
		super();
	}
	public People(int id, String name, int age, Emp emp) {
		super();
		this.id = id;
		this.name = name;
		this.age = age;
		this.emp = emp;
	}
	
	public Emp getEmp() {
		return emp;
	}
	public void setEmp(Emp emp) {
		this.emp = emp;
	}
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
