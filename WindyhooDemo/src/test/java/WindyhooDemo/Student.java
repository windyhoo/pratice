package WindyhooDemo;

public class Student extends Person{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Student() {
		
	}

	public Student(Person person) {
		this.setName(person.getName());
		this.setSex(person.getSex());
	}

	//Ñ§ºÅ
	private String stuNo;
	
	public String getStuNo() {
		return stuNo;
	}

	public void setStuNo(String stuNo) {
		this.stuNo = stuNo;
	}
	
	public static void main(String[] args) {
		Person person=new Person("aa","male");
		
		Student student =new Student(person);
		System.out.println(student.getSex());
	}
}
