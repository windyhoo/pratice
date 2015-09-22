/*
 * @(#) SSH2ConnectionHelper.java 1.0 2008-8-25
 *
 * Copyright 2006 Eshore All rights reserved.
 *
 */
package ssh;

import java.util.HashMap;
import java.util.Map;

import com.sshtools.j2ssh.SshClient;

/** 
 * <p>Title: SSH2ConnectionHelper.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Eshore Technology Co.,Ltd.</p>
 * @author dengguanxiong
 * @version 1.0
 */
public class SSHConnectionHolder {
	
	private Map<ConnectionKey, ConnectionValue> connectionMap;
	public static final SSHConnectionHolder INSTANCE=new SSHConnectionHolder();
	
	private SSHConnectionHolder() {
		connectionMap=new HashMap<ConnectionKey, ConnectionValue>();
	}
	
	public synchronized SshClient getSSHClient(String host,int port,String username) {
		ConnectionValue value=getConnectionValue(host,port,username);
		if(value==null) return null;
		
		return value.getSshClient();
		
	}
	
	public synchronized long getSSHClientConnectedTime(String host,int port,String username) {
		ConnectionValue value=getConnectionValue(host,port,username);
		if(value==null) return 0;
		
		return System.currentTimeMillis()-value.getStartConnectTime();
	}
	
	public synchronized void putSSHClient(String host,int port,String username,SshClient sshClient) {
		if(!isExist(host,port,username)) {
			ConnectionKey key=INSTANCE.new ConnectionKey(host,port,username);
			ConnectionValue value=INSTANCE.new ConnectionValue(sshClient);
			connectionMap.put(key, value);
		}
	}
	
	public synchronized void removeConnection(String host,int port,String username) {
		ConnectionKey key=lookupConnectionKey(host, port, username);
		if(key!=null) {
			connectionMap.remove(key);
		}
	}
	
	public synchronized boolean isExist(String host,int port,String username) {
		
		ConnectionKey key=lookupConnectionKey(host,port,username);
		if(key==null) return false;
		return true;
	}
	
	private ConnectionValue getConnectionValue(String host,int port,String username) {
		ConnectionKey key=lookupConnectionKey(host,port,username);
		if(key==null) return null;
		
		return connectionMap.get(key);
	}
	
	private ConnectionKey lookupConnectionKey(String host,int port,String username) {
//		System.out.println("connectionMap size="+connectionMap.size());
		for(ConnectionKey key:connectionMap.keySet()) {
//			System.out.println("connectionMap key="+key);
			if(host==key.getHost() || (host!=null && host.equals(key.getHost()))) {
				if(port==key.getPort()) {
					if(username==key.getUsername()||(username!=null && username.equals(key.getUsername())))
						return key;
				}
			}
		}
		
		return null;
	}
	
	private class ConnectionKey {
		private String host;
		private int port;
		private String username;
		
		public ConnectionKey(String host,int port,String username) {
			this.host=host;
			this.port=port;
			this.username=username;
		}
		
		public String getHost() {
			return host;
		}
		
		public int getPort() {
			return port;
		}
		
		public String getUsername() {
			return username;
		}
		
		
		@Override
		public boolean equals(Object arg0) {
			if(this.getClass().isInstance(arg0)) {
				ConnectionKey ck=(ConnectionKey)arg0;
				
				if(host!=ck.getHost() || (host!=null&&!host.equals(ck.getHost()))) {
					return false;
				}
				
				if(port!=ck.getPort()) return false;
				
				if(username!=ck.getUsername() || (username!=null&&!username.equals(ck.getUsername()))) {
					return false;
				}
				
			}else return false;
			
			return true;
		}
		
		@Override
		public int hashCode() {
			int result=17;
			result=37*result+(host==null?0:host.hashCode());
			result=37*result+port;
			result=37*result+(username==null?0:username.hashCode());
			return result;
		}
		
		@Override
		public String toString() {
			return "host="+host+", port="+port+", user name="+username;
		}
		
		
	}
	
	public class ConnectionValue{
		private SshClient sshClient;
		private long startConnectTime;
		
		public ConnectionValue(SshClient sshClient) {
			this.sshClient=sshClient;
			this.startConnectTime=System.currentTimeMillis();
		}

		public SshClient getSshClient() {
			return sshClient;
		}
		
		public long getStartConnectTime() {
			return startConnectTime;
		}
	}
	
//	public static void main(String[] args) throws IOException {
//		SSH2ConnectionHolder.ConnectionKey ck=SSH2ConnectionHolder.INSTANCE.new ConnectionKey("localhost",22,"ck");
//		SSH2ConnectionHolder.ConnectionKey ck1=SSH2ConnectionHolder.INSTANCE.new ConnectionKey("localhost1",22,"ck");
//		
//		SSH2ConnectionHolder h=SSH2ConnectionHolder.INSTANCE;
//		
//		h.connectionMap.put(ck,SSH2ConnectionHolder.INSTANCE.new ConnectionValue(null,null,null,null,null));
//		h.connectionMap.put(ck1,SSH2ConnectionHolder.INSTANCE.new ConnectionValue(null,null,null,null,null));
//		
//		System.out.println(ck.equals(ck1));
//		
//		SshConnectionProperties props1=new SshConnectionProperties();
//		props1.setHost("localhost");
//		props1.setPort(22);
//		props1.setUsername("ck");
//		
//		ConnectionValue value1=SSH2ConnectionHolder.INSTANCE.getConnection(props1, null, 20);
//		System.out.println(value1);
//
//		SshConnectionProperties props2=new SshConnectionProperties();
//		props2.setHost("localhost1");
//		props2.setPort(22);
//		props2.setUsername("ck");
//		
//		ConnectionValue value2=SSH2ConnectionHolder.INSTANCE.getConnection(props2, null, 20);
//		System.out.println(value2);
//
//		System.out.println("isExit:"+INSTANCE.isExist("localhost", 22, "ck"));
//		System.out.println(INSTANCE.connectionMap.size());
//	}
}

