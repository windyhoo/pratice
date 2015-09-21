package TThread;

import java.util.concurrent.FutureTask;

public class CollectFutureTask extends FutureTask<String> {
	private String identity;
	
	public CollectFutureTask(CollectTask collTask) {
		super(collTask,collTask.getMeCode());
		this.identity=collTask.getMeCode()+"_"+System.currentTimeMillis();
	}
	
	public String getIdentity(){
		return identity;
	}

}
