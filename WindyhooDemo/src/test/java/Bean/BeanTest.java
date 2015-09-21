package Bean;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

public class BeanTest {
    public static void main( String[] args )
    {
        ApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
  
        Customer cust = (Customer)context.getBean("CustomerBean");
        System.out.println(cust.getLists());
    }
}
