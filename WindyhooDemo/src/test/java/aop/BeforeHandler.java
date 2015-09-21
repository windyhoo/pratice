package aop;

import java.lang.reflect.Method;

public abstract class BeforeHandler extends AbstractHandler {
	public abstract void handleBefore(Object proxy,Method method,Object[] args);
	
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		handleBefore(proxy,method,args);
		
		return method.invoke(this.getTargetObject(), args);
	}
}
