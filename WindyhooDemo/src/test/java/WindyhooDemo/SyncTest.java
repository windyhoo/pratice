package WindyhooDemo;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SyncTest {
	private Logger logger=LoggerFactory.getLogger(SyncTest.class);

	Map<String,String> data=new HashMap<String,String>();
	Map<String,String> data2=new HashMap<String,String>();

	public void addData(String str) {
		logger.info("addData");

		synchronized(data) {
			try {
				Thread.currentThread().sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		logger.info("addData done");
	}
	
	public void removeData(String str) {
		logger.info("removeData");
		synchronized(data2) {
			try {
				Thread.currentThread().sleep(5000);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		logger.info("removeData done");
	}
	
	public void sayHello() {
		logger.info("hello");
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		SyncTest synctest=new SyncTest();
		
		new ThreadDemo(synctest,1).start();
		new ThreadDemo(synctest,2).start();
		new ThreadDemo(synctest,3).start();
	}

}
