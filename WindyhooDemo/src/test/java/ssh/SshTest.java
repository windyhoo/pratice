package ssh;

import java.util.Hashtable;
import java.util.Iterator;
import java.util.Map;
import java.util.Vector;

public class SshTest {
	public static ISshProvider sshExecutor  = new J2sshProvider();
	public static String host="xx";
	public static Integer port = 22;
	public static String userName = "xx";
	public static String password = "xx";

	public static void main(String[] args) {
		
		boolean isConnection = sshExecutor.connect(host, port,userName);
		if (isConnection) {
			System.out.println("conn");
			
			boolean isLogin = sshExecutor.loginAuthenticate(host, port, userName, password);
			
			if(isLogin) {
				System.out.println("login");
				String stringArray[]={"date"};
				Hashtable<String, Vector> reTable = doCommandsBySshExecutor(stringArray);
				System.out.println("reTable size:"+reTable.size());
				
				Iterator ite = reTable.keySet().iterator();
				while(ite.hasNext()) {
					String key = (String)ite.next();
					System.out.println("key:"+key);
					System.out.println(reTable.get(key));
				}
			} else {
				System.out.println("login fail");
			}
		} else {
			System.out.println("conn fail");
		}
	}
	
	
	private static String[] commandSetToArray(Hashtable<String, Integer> cmdSet) {
		if (cmdSet.isEmpty()) // 待执行的指令集为空则跳出
			return null;
		String[] cmdArys = new String[cmdSet.size()];
		int i = 0;
		for (String cmdLine : cmdSet.keySet()) {
			cmdArys[i++] = cmdLine;
		}
		return cmdArys;
	}
	
	private static Hashtable<String, Vector> doCommandsBySshExecutor(String[] execCmdArys){
		Hashtable<String, Vector> execResult = null;
//		String[] execCmdArys = commandSetToArray(execCmdSet);
		if (execCmdArys != null && execCmdArys.length > 0) {
			try{
				Map<String,String> resultMap = sshExecutor.executeCommands(host, port, userName, password, execCmdArys);
				if (resultMap != null && !resultMap.isEmpty()) {
					int size = resultMap.size();
					execResult = new Hashtable<String, Vector>(size);
					for (String execCmd : resultMap.keySet()) {
						String execResultValue = resultMap.get(execCmd);
						System.out.println("execResultValue:"+execResultValue);

						execResultValue =StringUtils.trimLine(execResultValue);
						System.out.println("execResultValue:"+execResultValue);

						Vector execResultLines = StringUtils.toVector(execCmd,execResultValue,"\n");
						System.out.println("execResultLines size:"+execResultLines.size());
						System.out.println("execResultLines:"+execResultLines.toString());

						execResult.put(execCmd, execResultLines);
					}
				}
			}catch(Exception e){
				System.out.println(e);
			}
		}
		return execResult;
	}

}
