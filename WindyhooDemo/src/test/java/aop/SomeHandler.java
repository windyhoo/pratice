package aop;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

import aop.impl.AfterHandlerImpl;
import aop.impl.BeforeHandlerImpl;

public class SomeHandler implements InvocationHandler{

	@Override
	public Object invoke(Object proxy, Method method, Object[] args)
			throws Throwable {
		
		Object result = method.invoke(proxy ,args);
		
		return result;
	}

	
	public static void main(String[] args) {
		CalculatorImpl calcImpl = new CalculatorImpl();
		BeforeHandler before = new BeforeHandlerImpl();
		AfterHandler after = new AfterHandlerImpl();
		List<AbstractHandler> handlers = new ArrayList<AbstractHandler>();
		handlers.add(before);
		handlers.add(after);
		Calculator proxy = (Calculator) ProxyFactory.getProxy(calcImpl,
		        handlers);
		int result = proxy.calculate(20, 10);
		System.out.println("result:"+result);
	}
}
