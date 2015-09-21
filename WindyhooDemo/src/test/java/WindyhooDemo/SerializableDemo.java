package WindyhooDemo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

	//������
public class SerializableDemo {
	public static void main(String[] args) {
	//����һ������
	Person people = new Person("����","��");
	try {
		//ʵ����ObjectOutputStream����
		ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("D:\\person.txt"));
		//������д���ļ�
		oos.writeObject(people);
		oos.flush();
		oos.close();
		
		//ʵ����ObjectInputStream����
		ObjectInputStream ois=new ObjectInputStream(new FileInputStream("D:\\person.txt"));
	
		try {
		//��ȡ����people,�����л�
			Person p = (Person)ois.readObject();
			System.out.println("������"+p.getName());
			System.out.println("�Ա�"+p.getSex());
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