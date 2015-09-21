package TThread;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class CollectThreadPoolExecutor extends ThreadPoolExecutor {
//	private Logger logger=LoggerFactory.getLogger(CollectThreadPoolExecutor.class);
	
	private Map<String,Date> runningMap=new ConcurrentHashMap<String,Date>();
	public CollectThreadPoolExecutor(int corePoolSize, int maximumPoolSize,
			long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
		super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
	}

	@Override
	protected void beforeExecute(Thread t, Runnable r) {
		super.beforeExecute(t, r);
		if(r instanceof CollectFutureTask){
			runningMap.put(((CollectFutureTask)r).getIdentity(),new Date());
			System.out.println("监控任务执行开始:"+((CollectFutureTask)r).getIdentity());
		}
	} 
	
	@Override
	protected void afterExecute(Runnable r, Throwable t) {
		super.afterExecute(r, t);
		if(r instanceof CollectFutureTask){
			Date start=runningMap.remove(((CollectFutureTask)r).getIdentity());
			if(start==null){
				System.out.println("监控任务执行结束，但找不到其开始时间标记:"+((CollectFutureTask)r).getIdentity());
				return;
			}
			System.out.println("监控任务行耗时毫秒:"+((CollectFutureTask)r).getIdentity()+","+(System.currentTimeMillis()-start.getTime()));
		}
	}

	public Map<String,Date> getRunningTaskInfo(){
		Map<String,Date> copy=new HashMap<String,Date>();
		copy.putAll(runningMap);
		return copy;
	}
}
