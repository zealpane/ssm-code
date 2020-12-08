package biz.config;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.codec.Base64;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.CookieRememberMeManager;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.crazycake.shiro.RedisCacheManager;
import org.crazycake.shiro.RedisManager;
import org.crazycake.shiro.RedisSessionDAO;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import biz.config.shiro.MyRealm;

/**
 * 权限拦截配置
 * 
 * @author ShenHuaJie
 * @since 2017年8月14日 上午10:40:20
 */
@Configuration
public class ShiroConfig {
	
	@Value("${spring.redis.host}")
    private String host;
    @Value("${spring.redis.port}")
    private int port;
    @Value("${spring.redis.password}")
    private String password;
    
	/**
	 * anon: 所有url都都可以匿名访问;
	 * authc: 需要认证才能进行访问;
	 * user: 配置记住我或认证通过可以访问；
	 */
    static String filters = "/=anon;/docs.html=anon;/assets/**=anon;/index.jsp=anon;/regin=anon;/*.ico=anon;/upload/*=anon;"
    		+ "/forbidden=anon;/sns*=anon;/*/api-docs=anon;/callback*=anon;/swagger*=anon;" 
    		+ "/configuration/*=anon;/*/configuration/*=anon;/webjars/**=anon;"
    		+ "/user/member/login=anon;/user/member/wxlogin=anon;/user/member/register=anon;/ac/unauthorized=anon;"
    		+ "/static/**=anon;/index.html=anon;"
    		+ "/nb/iot/**=anon;/wx/*=anon;"
    		+ "/druid/**=anon;"
    		+ "/ac/acUser/**=anon;"
        + "/**=user";

    @Bean
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager) {
        ShiroFilterFactoryBean factory = new ShiroFilterFactoryBean();
        factory.setSecurityManager(securityManager);
        factory.setLoginUrl("/api/ac/unauthorized");
        factory.setUnauthorizedUrl("/forbidden");
        
//        Map<String, Filter> filtersMap = new LinkedHashMap<String, Filter>();
//        filtersMap.put("shiroLoginFilter", new ShiroLoginFilter());
//        factory.setFilters(filtersMap);
        
        Map<String, String> filterMap = new LinkedHashMap<>();
        // 过滤链定义，从上向下顺序执行
        for (String filter : filters.split("\\;")) {
            String[] keyValue = filter.split("\\=");
            filterMap.put(keyValue[0], keyValue[1]);
        }
        factory.setFilterChainDefinitionMap(filterMap);
        return factory;
    }

    @Bean
    public static LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }

    @Bean
    public MyRealm myShiroRealm() {
    	MyRealm myRealm = new MyRealm();
//    	myRealm.setCredentialsMatcher(h);
    	return myRealm;
    }
    
    @Bean
    public SimpleCookie rememberMeCookie(){
        //System.out.println("ShiroConfiguration.rememberMeCookie()");
        //这个参数是cookie的名称，对应前端的checkbox的name = rememberMe
        SimpleCookie simpleCookie = new SimpleCookie("rememberMe");
        //<!-- 记住我cookie生效时间30天 ,单位秒;-->
        simpleCookie.setMaxAge(259200);
        return simpleCookie;
    }
    @Bean
    public CookieRememberMeManager rememberMeManager(){
        //System.out.println("ShiroConfiguration.rememberMeManager()");
        CookieRememberMeManager cookieRememberMeManager = new CookieRememberMeManager();
        cookieRememberMeManager.setCookie(rememberMeCookie());
        //rememberMe cookie加密的密钥 建议每个项目都不一样 默认AES算法 密钥长度(128 256 512 位)
        cookieRememberMeManager.setCipherKey(Base64.decode("2AvVhdUs0FSA3SDFAdag=="));
        return cookieRememberMeManager;
    }
    
    @Bean
    public SecurityManager securityManager() {
    	DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
    	securityManager.setRealm(myShiroRealm());
    	// TODO 自定义缓存和session管理没
    	securityManager.setSessionManager(sessionManager());
    	/**
    	 * 再出现bug看这里  securityManager
    	 */
    	securityManager.setRememberMeManager(rememberMeManager());
    	return securityManager;
    }
    /**
     * 配置shiro redisManager
     * 使用的是shiro-redis开源插件
     */
    public RedisManager redisManager() {
    	RedisManager redisManager = new RedisManager();
    	redisManager.setHost(host);
        redisManager.setPort(port);
        redisManager.setExpire(72000);// 配置缓存过期时间
        redisManager.setPassword(password);
    	return redisManager;
    }
    /**
     * cacheManager 缓存 redis实现
     * 使用的是shiro-redis开源插件
     * @return
     */
    public RedisCacheManager cacheManager() {
        RedisCacheManager redisCacheManager = new RedisCacheManager();
        redisCacheManager.setExpire(72000);
        redisCacheManager.setRedisManager(redisManager());
        return redisCacheManager;
    }
    /**
     * RedisSessionDAO shiro sessionDao层的实现 通过redis
     * 使用的是shiro-redis开源插件
     */
    @Bean
    public RedisSessionDAO redisSessionDAO() {
        RedisSessionDAO redisSessionDAO = new RedisSessionDAO();
        redisSessionDAO.setExpire(72000);
        redisSessionDAO.setKeyPrefix("z:l:");
        redisSessionDAO.setRedisManager(redisManager());
        return redisSessionDAO;
    }
    /**
     * shiro session的管理
     */
    @Bean
    public DefaultWebSessionManager sessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        sessionManager.setSessionDAO(redisSessionDAO());
        sessionManager.setDeleteInvalidSessions(true);
        return sessionManager;
    }
    /**
     * 凭证匹配器
     * （由于我们的密码校验交给Shiro的SimpleAuthenticationInfo进行处理了
     *  所以我们需要修改下doGetAuthenticationInfo中的代码;
     */
    @Bean
    public HashedCredentialsMatcher hashedCredentialsMatcher(){
        HashedCredentialsMatcher hashedCredentialsMatcher = new HashedCredentialsMatcher();
        hashedCredentialsMatcher.setHashAlgorithmName("md5");//散列算法:这里使用MD5算法;
        hashedCredentialsMatcher.setHashIterations(2);//散列的次数，比如散列两次，相当于 md5(md5(""));

        return hashedCredentialsMatcher;
    }
    /**
     * 为了开启注解而添加
     * @param securityManager
     * @return
     */
//    @Bean
//    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager) {
//        AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor
//            = new AuthorizationAttributeSourceAdvisor();
//        authorizationAttributeSourceAdvisor.setSecurityManager(securityManager);
//        return authorizationAttributeSourceAdvisor;
//    }
//    @Bean
//    @DependsOn("lifecycleBeanPostProcessor")
//    public DefaultAdvisorAutoProxyCreator advisorAutoProxyCreator() {
//        DefaultAdvisorAutoProxyCreator creator = new DefaultAdvisorAutoProxyCreator();
//        creator.setProxyTargetClass(true);
//        return creator;
//    }
}
