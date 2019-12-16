package dps.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

import dps.bean.User;

//权限验证拦截器
public class AuthorityInterceptor implements MethodInterceptor {
	private User user; 
	public void setUser(User user) {
		this.user = user;
	}
	public Object invoke(MethodInvocation arg0) throws Throwable {
		System.out.println("==拦截器===权限验证开始======");
		String username = this.user.getUsername();
		String methodName = arg0.getMethod().getName();
		if(!username.equals("admin")&&!username.equals("register"))
		{
			System.out.println("没有任何执行权限");
			return null;
		}
		else if(username.equals("register") && methodName.equals("modify"))
		{
			System.out.println("register用户没有write权限");
			return null;
		}
		else
		{
			Object obj = arg0.proceed();
			System.out.println("==拦截器===权限验证结束======");
			System.out.println();
			return obj;
		}
	}
}