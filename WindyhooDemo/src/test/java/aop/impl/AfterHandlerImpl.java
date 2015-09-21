package aop.impl;

import java.lang.reflect.Method;

import aop.AfterHandler;

public class AfterHandlerImpl extends AfterHandler{
	public void handleAfter(Object proxy, Method method, Object[] args) {
		//Provide your own cross cutting concern
		System.out.println("Handling after actual method execution ........");
	}
}
