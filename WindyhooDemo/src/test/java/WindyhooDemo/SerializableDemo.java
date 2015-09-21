package WindyhooDemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

	//测试类
public class SerializableDemo {
	public static void main(String[] args) {
	//创建一个对象
	Person people = new Person("张三","男");
	try {
		//实例化ObjectOutputStream对象
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\person.txt"));
		//将对象写入文件
		oos.writeObject(people);
		oos.flush();
		oos.close();
		
		//实例化ObjectInputStream对象
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("D:\\person.txt"));
	
		try {
		//读取对象people,反序列化
			Person p = (Person)ois.readObject();
			System.out.println("姓名："+p.getName());
			System.out.println("性别："+p.getSex());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
} 