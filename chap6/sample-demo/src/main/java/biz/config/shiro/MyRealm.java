package biz.config.shiro;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;

public class MyRealm extends AuthorizingRealm {
	// 权限过滤
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		//Long roleId = (Long) SecurityUtils.getSubject().getSession().getAttribute("roleId");
		// 获取当前用户的所有可操作的约定字符串集合
		SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
		/*List<String> resourceList = permissionService.loadUserResources(roleId);
		for (String r : resourceList) {
			System.out.println(r);
			if (r != null) {	
				//添加到用户访问拦截权限集合
				authorizationInfo.addStringPermission(r);
			}
		}*/
		return authorizationInfo;
	}

	/**
	 * 该方法主要执行以下操作:
		1、检查提交的进行认证的令牌信息
		2、根据令牌信息从数据源(通常为数据库)中获取用户信息
		3、对用户信息进行匹配验证。
		4、验证通过将返回一个封装了用户信息的AuthenticationInfo实例。
		5、验证失败则抛出AuthenticationException异常信息。
	 */
	// 登录身份认证
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
		SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
				authcToken.getPrincipal(), authcToken.getCredentials(), // ByteSource.Util.bytes(user.getEmail()),//salt=username+salt,
				getName());
		
		return authenticationInfo;
	}

}
