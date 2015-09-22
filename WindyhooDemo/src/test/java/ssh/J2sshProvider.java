package ssh;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sshtools.j2ssh.SshClient;
import com.sshtools.j2ssh.authentication.AuthenticationProtocolState;
import com.sshtools.j2ssh.authentication.PasswordAuthenticationClient;
import com.sshtools.j2ssh.configuration.SshConnectionProperties;
import com.sshtools.j2ssh.connection.ChannelInputStream;
import com.sshtools.j2ssh.session.SessionChannelClient;
import com.sshtools.j2ssh.session.SessionOutputReader;
import com.sshtools.j2ssh.transport.IgnoreHostKeyVerification;

public class J2sshProvider implements ISshProvider {
	private static Logger logger=LoggerFactory.getLogger(J2sshProvider.class);

	private SSHConnectionHolder connectionHolder;
	
	private String host;
	private int port;
	private String username;
	private String pwd;
	private int timeout=10000;
	private final int reconnectInterval=120*60*1000; //重连接时间---2小时
	
	public J2sshProvider() {
		
		connectionHolder=SSHConnectionHolder.INSTANCE;
	}

	protected int authenticate() throws IOException {
		int result=AuthenticationProtocolState.FAILED;
		
		SshClient sshClient = connectionHolder.getSSHClient(host, port, username);
	
		if(sshClient!=null && !sshClient.isAuthenticated()) {
			PasswordAuthenticationClient auth = new PasswordAuthenticationClient();

			auth.setUsername(username);
			auth.setPassword(pwd);
			result = sshClient.authenticate(auth);
		} else 
			result = AuthenticationProtocolState.COMPLETE;
		
		return result;
	}

	protected void connect(String host,int port,String username,String pwd) throws IOException {
		
		SshClient sshClient=connectionHolder.getSSHClient(host, port, username);
		
		if(sshClient==null) {
			logger.info("Create sshClient for host: "+host+", port: "+port+", usrename: "+username);		
			createSshClient();
		} else
			logger.debug("sshClient still available for host: "+host+", port: "+port+", usrename: "+username);	
		
	}

	private SshClient createSshClient() throws UnknownHostException, IOException {
		SshClient sshClient=new SshClient();
		
		IgnoreHostKeyVerification ihkv=new IgnoreHostKeyVerification();
		
		SshConnectionProperties properties = new SshConnectionProperties();
		properties.setHost(host);
		properties.setPort(port);
		properties.setPrefSCEncryption("aes256-cbc");
		
		sshClient.setSocketTimeout(timeout);
	
		sshClient.connect(properties,ihkv);
		
		connectionHolder.putSSHClient(host, port, username, sshClient);
		
		return sshClient;
	}
	
	protected String executeCommand(String cmd) throws Exception{
		SshClient sshClient=connectionHolder.getSSHClient(host, port, username);
		
		if(sshClient==null) return null;
		
		SessionChannelClient session = null;
		try {
			session = sshClient.openSessionChannel();
		} catch(Exception e) {
			if(e.getMessage().indexOf("The server refused to open a session") >= 0) {
				logger.error("Exception occourred while open session for host: "+host+", port: "+port+", usrename: "+username, e);
//				if(session != null)
//					session.close();
//				throw e;
			} else if(e.getMessage().indexOf("Socket closed") >= 0 || !sshClient.isConnected()){
				logger.info("Socket has closed for host: "+host+", port: "+port+", usrename: "+username);
//				if(session != null)
//					session.close();
//				disconnect();
//				sshClient = createSshClient();
//				int ret = authenticate();
//				if(ret != AuthenticationProtocolState.COMPLETE) {
//					disconnect();
//					throw e;
//				}
//				session = sshClient.openSessionChannel();
//				UmccLogger.info("Has forced to re-create sshClient for host: "+host+", port: "+port+", usrename: "+username);		
			} else {
				logger.error("Exception occourred while open session for host: "+host+", port: "+port+", usrename: "+username, e);
//				if(session != null)
//					session.close();
//				throw e;
			}
			if(session != null)
				session.close();
			disconnect();
			sshClient = createSshClient();
			int ret = authenticate();
			if(ret != AuthenticationProtocolState.COMPLETE) {
				disconnect();
				throw e;
			}
			session = sshClient.openSessionChannel();
			logger.info("Has forced to re-create sshClient for host: "+host+", port: "+port+", usrename: "+username);		
		
		}
		try{
			if(sshClient.getActiveChannelCount()<1) {
			if(!session.requestPseudoTerminal("vt100", 80, 24, 0, 0, "")) {
				logger.error("Failed to allocate a pseudo terminal. Host="+host+", port="+port+", user name="+username+".");
			}
		}
		}catch (Exception e) {
			session.close();
			throw e;
		}

	        
		SessionOutputReader sor = new SessionOutputReader(session);
		
		session.executeCommand(cmd);
	 
		String retStr = null;
		
		ChannelInputStream os=session.getInputStream();
		/*
		byte[] rebs=new byte[1024];
		while(os.read(rebs)!=-1){
			String ss=new String(rebs,"gbk");
			System.out.println(ss);
		}
		*/
		int i=0;
		while (i<60&&session.getExitCode() == null) {
			try {
				i++; 
				Thread.sleep(100);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				logger.info(e.toString());
			}
		}
	 
		retStr = sor.getOutput();
		byte[] bs=retStr.getBytes("utf-8");
	
		session.close();
		return retStr;
	}
	
	
	private boolean verifiyParams(String host,int port,String username,String pwd,int timeout,String[] cmds) {
		if(host==null || port<=0 || port >65536 || username==null || pwd==null || cmds==null || cmds.length<=0)
			return false;
		
		this.host=host;
		this.port=port;
		this.username=username;
		this.pwd=pwd;
		
		if(timeout>0) this.timeout=timeout;
		
		return true;
	}
	
	
	protected void disconnect() {
		SshClient sshClient=connectionHolder.getSSHClient(host, port, username);
		if (sshClient != null) {
			connectionHolder.removeConnection(host, port, username);
			
			if(sshClient.isConnected())
				sshClient.disconnect();
			sshClient = null;			
		}
			
	}
	
	public Hashtable<String,String> executeCommands(String host,int port,String username,String pwd,String[] cmds) throws Exception{
		if(!verifiyParams(host, port, username, pwd, timeout, cmds)) {
			logger.error("SSH监控参数非法，监控任务无法继续执行。当前参数：主机="+host+", 端口号="+port+", 用户名="+username+", 密码="+pwd+(cmds==null?"指令数组为null。":"指令数组长度="+cmds.length));
			throw new Exception("SSH监控参数非法，监控任务无法继续执行");
			
		}
		long connectedTime=connectionHolder.getSSHClientConnectedTime(host, port, username);
		if(connectedTime>reconnectInterval) {
			disconnect();
		}
		
		
		try {
			connect(host, port, username, pwd);
		} catch (IOException e) {
			logger.error("SSH监控无法与主机建立连接。当前参数：主机="+host+", 端口号="+port+", 用户名="+username);
			throw new Exception("SSH监控无法与主机建立连接");
			
		}
		int ret = -1;
		try {
			ret = authenticate();
			if(ret != AuthenticationProtocolState.COMPLETE) {
				logger.error("SSH监控登录检验失败。当前参数：主机="+host+", 端口号="+port+", 用户名="+username+", 密码="+pwd);
				disconnect();
				throw new Exception("SSH监控登录检验失败");
				
			}
		} catch (IOException e) {
			logger.error("SSH监控登录检验出错。当前参数：主机="+host+", 端口号="+port+", 用户名="+username+", 密码="+pwd);
			disconnect();
			throw new Exception("SSH监控登录检验失败");
		}
		
		Hashtable<String,String> execResultMap=new Hashtable<String, String>();
		int i=0;
		for(String cmd:cmds) {
			String result=null;
			try {
				if(i==0){
				String s= executeCommand("uname");
			
				System.out.println(s);
				}
				result=executeCommand(cmd);
				System.out.println(result);

			} catch (Exception e) {
				logger.error("SSH监控指令无法执行。当前参数：主机="+host+", 端口号="+port+", 用户名="+username+", 指令="+cmd, e);
			}
			
			if(result!=null) {
				execResultMap.put(cmd, result);	
			}else {
				logger.error("监控结果为空值。当前参数：主机="+host+", 端口号="+port+", 用户名="+username+", 指令="+cmd);
			}
			i++;
		}
		
		
		return execResultMap;
		
	}

	/**
	 * @param host
	 * @param port
	 * @return
	 * @deprecated
	 */
	protected boolean isConnect(String host, int port) {
		boolean isConnection = true;
		SshClient sshClient = new SshClient();
		IgnoreHostKeyVerification ihkv = new IgnoreHostKeyVerification();
		SshConnectionProperties properties = new SshConnectionProperties();
		properties.setHost(host);
		properties.setPort(port);
		properties.setPrefSCEncryption("aes256-cbc");
		sshClient.setSocketTimeout(timeout);
		try {
			sshClient.connect(properties, ihkv);
		} catch (Throwable e) {
			isConnection = false;
		} finally {
			if (isConnection) {
				sshClient.disconnect();
			}
		}
		return isConnection;
	}
	
	

	public boolean connect(String host, int port, String username) {
		boolean isConnection = true;
		try {
			this.host = host;
			this.port = port;
			this.username = username;
			connect(host, port, username, null);
		} catch (Throwable e) {
			isConnection = false;
		}
		return isConnection;
	}

	public void disconnect(String host, int port, String username) {
		this.host = host;
		this.port = port;
		this.username = username;
		disconnect();
	}

	public boolean loginAuthenticate(String host, int port, String username, String pwd) {
		boolean isLogin = true;
		this.host = host;
		this.port = port;
		this.username = username;
		this.pwd = pwd;
		try {
			int result = authenticate();
			isLogin = (result == AuthenticationProtocolState.FAILED) ? false : true;
		} catch (IOException e) {
			isLogin = false;
		}
		return isLogin;
	}
	
	
	
	
	
}