package dps.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

//日志记录拦截器
public class LogInterceptor implements MethodInterceptor {
	public Object invoke(MethodInvocation arg0) throws Throwable {
		String methodName = arg0.getMethod().getName();
		Object obj  =  arg0.proceed();
		System.out.println("==拦截器===日志记录： 尝试执行"+methodName+"方法");
		return obj;
	}
}