package WindyhooDemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ThreadDemo extends Thread {
	private Logger logger=LoggerFactory.getLogger(ThreadDemo.class);
	private SyncTest synctest;
	private int type;
	
	public ThreadDemo(SyncTest synctest,int type) {
		this.synctest=synctest;
		this.type=type;
	}
	
	public void run(){
		if(type==1) {
			synctest.addData("1");
		} else if(type==2) {
			synctest.removeData("1");
		} else {
			synctest.sayHello();
		}
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
	}

}
