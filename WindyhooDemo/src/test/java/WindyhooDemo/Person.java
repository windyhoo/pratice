package WindyhooDemo;

import java.io.Serializable;

public class Person implements Serializable{
	
	private String name;
	private String sex;
	private Integer age;
	
	public Person() {
		
	}
	
	public Integer getAge() {
		return age;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Person(String name,String sex){
		this.name=name;
		this.sex=sex;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getSex() {
		return sex;
	}
	
	public void setSex(String sex) {
		this.sex = sex;
		}
}

