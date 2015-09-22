/*
 * @(#) ISSHProvider.java 1.0 2008-9-4
 *
 * Copyright 2006 Eshore All rights reserved.
 *
 */
package ssh;

import java.util.Hashtable;
/*
 * ==========================================================
 * Version         Author          Date          Description
 *  1.0            dengguanxiong   2008-9-4	      创建
 *  1.1            wankun		   2009-8-13	 增加可连接性和登录认证接口			
 * ==========================================================
 */

/** 
 * <p>Title: ISSHProvider.java</p>
 * <p>Description: </p>
 * <p>Copyright: Copyright (c) 2006</p>
 * <p>Company: Eshore Technology Co.,Ltd.</p>
 * @author dengguanxiong
 * @version 1.0
 */
public interface ISshProvider {
	 
	/**
	 * 执行设定的命令集
	 * 
	 * @param host       目标主机
	 * @param port       登录端口
	 * @param username   登录用户
	 * @param pwd        登录密码
	 * @param cmds       需执行的命令集(组)
	 * @return           以映射表封闭的指令结果.K=指令,V=指令输出
	 */
	public Hashtable<String, String> executeCommands(String host, int port, String username, String pwd, String[] cmds)throws Exception ;
	
	
	/**
	 * 与目标主机建立连接
	 * 
	 * @param host       目标主机
	 * @param port       登录端口
	 * @param username   登录用户
	 * @return  逻辑真代表可以成功建立连接，否则反之。
	 */
	public boolean connect(String host, int port, String username);
	
	/**
	 * 与目标主机断开连接
	 * 
	 * @param host       目标主机
	 * @param port       登录端口
	 * @param username   登录用户
	 */
	public void disconnect(String host, int port, String username);
	
	
	/**
	 * 验证登录是否成功
	 * 
	 * @param host       目标主机
	 * @param port       登录端口
	 * @param username   登录用户
	 * @param pwd        登录密码
	 * @return  逻辑真代表可以成功登录，否则反之。
	 */
	public boolean loginAuthenticate(String host, int port, String username, String pwd);
}

