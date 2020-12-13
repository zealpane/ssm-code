package biz.config;

import org.apache.shiro.SecurityUtils;

public class U {

	public static Long getUserId() {
		Object o = SecurityUtils.getSubject().getPrincipal();
		if (o == null) {
			return null;
		}
		String userIdStr = o.toString();
		Long userId = Long.parseLong(userIdStr);
		return userId;
	}
	

}
