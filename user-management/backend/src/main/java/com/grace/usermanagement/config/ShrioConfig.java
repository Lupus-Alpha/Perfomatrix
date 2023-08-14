package com.grace.usermanagement.config;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;



@Configuration
public class ShrioConfig {

    //配置过滤器
//    @Bean(name = "shiroFilter")
//    public FilterRegistrationBean<Filter> shiroFilter() throws Exception {
//        FilterRegistrationBean filterRegistration = new FilterRegistrationBean();
//        filterRegistration.setFilter(new DelegatingFilterProxy("shiroFilter"));
//        filterRegistration.setEnabled(true);
//        filterRegistration.addUrlPatterns("/*"); //过滤规则，即所有的请求
//        filterRegistration.setDispatcherTypes(DispatcherType.REQUEST);
//        return filterRegistration;

//        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
//        registration.setFilter((Filter) Objects.requireNonNull(shiroFilterFactoryBean.getObject())); // 可以通过类名获取Bean实例,类名.getObject()获得
//        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class)); // 配置Servlet的Dispatcher类型,处理所有类型的请求，包括普通请求、转发请求、包含请求和异步请求。
//        return registration;
    //}

    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm());
        //manager.setCacheManager(cacheManager());
        //manager.setSessionManager(defaultWebSessionManager());

        return manager;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        DefaultShiroFilterChainDefinition chainDefinition = new DefaultShiroFilterChainDefinition();
        chainDefinition.addPathDefinition("/admin/**", "authc");
        chainDefinition.addPathDefinition("/**", "anon");
        return chainDefinition;
    }

    @Bean
    public ShiroFilterFactoryBean shiroFilterFactoryBean() {
        ShiroFilterFactoryBean filterFactoryBean = new ShiroFilterFactoryBean();
        filterFactoryBean.setSecurityManager(securityManager());
        filterFactoryBean.setFilterChainDefinitionMap(shiroFilterChainDefinition().getFilterChainMap());
        return filterFactoryBean;
    }



    @Bean(name="sessionManager")
    public DefaultWebSessionManager defaultWebSessionManager() {
        DefaultWebSessionManager sessionManager = new DefaultWebSessionManager();
        //sessionManager.setCacheManager(cacheManager());
        sessionManager.setGlobalSessionTimeout(1800000);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionValidationSchedulerEnabled(true);
        sessionManager.setDeleteInvalidSessions(true);
        sessionManager.setSessionIdCookie(getSessionIdCookie());
        return sessionManager;
    }
    @Bean(name="sessionIdCookie")
    public SimpleCookie getSessionIdCookie(){
        SimpleCookie simpleCookie = new SimpleCookie("webcookie");
        /**
         * HttpOnly标志的引入是为了防止设置了该标志的cookie被JavaScript读取，
         * 但事实证明设置了这种cookie在某些浏览器中却能被JavaScript覆盖，
         * 可被攻击者利用来发动session fixation攻击
         */
        simpleCookie.setHttpOnly(true);
        /**
         * 设置浏览器cookie过期时间，如果不设置默认为-1，表示关闭浏览器即过期
         * cookie的单位为秒 比如60*60为1小时
         */
        simpleCookie.setMaxAge(-1);
        return simpleCookie;
    }


    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public UserRealm userRealm() {
        //UserRealm userRealm = new UserRealm();
        //userRealm.setCredentialsMatcher(credentialsMatcher());
        //userRealm.setCacheManager(cacheManager());
        return new UserRealm();
    }
//    @Bean(name="credentialsMatcher")
//    public CredentialsMatcher credentialsMatcher() {
//        return new RetryLimitHashedCredentialsMatcher();
//    }
    @Bean
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor() {
        return new LifecycleBeanPostProcessor();
    }


}
