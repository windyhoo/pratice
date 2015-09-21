package aop.impl;

import java.lang.reflect.Method;

import aop.BeforeHandler;

public class BeforeHandlerImpl extends BeforeHandler {
	public void handleBefore(Object proxy, Method method, Object[] args) {
		//Provide your own cross cutting concern
		System.out.println("Handling before actual method execution ........");
	}
}
