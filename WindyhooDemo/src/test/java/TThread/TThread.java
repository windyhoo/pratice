package TThread;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;

public class TThread {
//	ExecutorService service1 = Executors.newFixedThreadPool(5);
//	ExecutorService service2 = Executors.newCachedThreadPool();
//	ExecutorService service3 = Executors.newSingleThreadExecutor();
//	ExecutorService service4 = Executors.newScheduledThreadPool(5);
	
	public static void main(final String argv[]) {
		
		CollectThreadPoolExecutor pool=new CollectThreadPoolExecutor(3,10,3,TimeUnit.SECONDS,new SynchronousQueue<Runnable>());
		System.out.println(pool.getActiveCount());
		System.out.println(pool.getCompletedTaskCount());
		System.out.println(pool.getCorePoolSize());

		CollectTask command=new CollectTask();
		command.setMeCode("1_2_3");
		CollectFutureTask future = new CollectFutureTask(command);
		pool.execute(future);
	
//		CollectTask command2=new CollectTask();
//		command2.setMeCode("1_2_4");
//		CollectFutureTask future2 = new CollectFutureTask(command2);
//		pool.execute(future2);

		try {
			System.out.println("begin");
			System.out.println(pool.getActiveCount());
			System.out.println(pool.getCompletedTaskCount());
			System.out.println(pool.getCorePoolSize());
			
			Thread.sleep(1000);

//			future.cancel(true);
//			future2.cancel(true);
			try {
				System.out.println("before future get");

				System.out.println("future get:"+future.get());
				
				System.out.println("after future get");

			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			System.out.println("begin sleep");
			Thread.sleep(1000);
			
			System.out.println(pool.getActiveCount());
			System.out.println(pool.getCompletedTaskCount());
			System.out.println(pool.getCorePoolSize());
			
//			CollectTask command2=new CollectTask();
//			command2.setMeCode("1_2_4");
//			CollectFutureTask future2 = new CollectFutureTask(command2);
//			pool.execute(future2);
			
			System.out.println("begin sleep");
			Thread.sleep(5000);
			System.out.println(pool.getActiveCount());
			System.out.println(pool.getCompletedTaskCount());
			System.out.println(pool.getCorePoolSize());

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
