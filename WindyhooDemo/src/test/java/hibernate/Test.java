package hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class Test {
	private static final SessionFactory sessionFactory = buildSessionFactory();
	
	private static SessionFactory buildSessionFactory() {
		Configuration cfg = new Configuration().configure();
		
		StandardServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(cfg.getProperties()).build();
		
		return cfg.buildSessionFactory(serviceRegistry);
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	
	public static void addCustomer() {
		Session session=getSessionFactory().openSession();
		session.beginTransaction();
		Customer customer = new Customer();
		customer.setId(1);
		customer.setPassword("yuhao");
		customer.setUsername("yuhao");
		session.save(customer);
		session.getTransaction().commit();
	}
	
	public static void main(String[] args) {
		Test test=new Test();
		test.addCustomer();
		getSessionFactory().close();
	}
}
