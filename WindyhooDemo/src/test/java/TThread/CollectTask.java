package TThread;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class CollectTask implements Runnable {
	private String meCode;

	public void run() {
		
		try{
			System.out.println("CollectTask:"+meCode);
//			String sd="oracle.jdbc.driver.OracleDriver";  		  
//			String sc="jdbc:oracle:thin:@132.122.1.187:1521:esdb2";  
//			String userName = "op_pay_yf";
//			String password = "Aa123456";
//	
//			Class.forName(sd);
//			
//			Connection con=null;
//			System.out.println("begin create conn:"+meCode);
//			con=DriverManager.getConnection(sc,userName,password);
//			System.out.println("after create conn:"+meCode);
	
	
			Thread.sleep(20000);
	
			System.out.println("CollectTask done:"+meCode);
		
		} catch(Exception e) {  
			System.err.println(e);  
		} finally {
			System.out.println("here finally");
		}
	}

	public String getMeCode() {
		return meCode;
	}

	public void setMeCode(String meCode) {
		this.meCode = meCode;
	}

}
