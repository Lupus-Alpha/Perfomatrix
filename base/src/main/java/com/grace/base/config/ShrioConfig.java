package com.grace.base.config;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;
import java.util.logging.Filter;

@Configuration
public class ShrioConfig {

    @Bean(name = "shiroFilter")
    public ShiroFilterFactoryBean customFilterFactoryBean(){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setLoginUrl("/login");
        shiroFilterFactoryBean.setSecurityManager(securityManager()); //配置 ShiroFilterFactoryBean 使用的 SecurityManager，以确保 Shiro 能够正确执行身份验证和授权操作
        shiroFilterFactoryBean.setUnauthorizedUrl("/403");
        shiroFilterFactoryBean.setSuccessUrl("/");

        //shiroFilterFactoryBean.getFilters().put("apikey", new ApiKeyFilter()); //向ShiroFilterFactoryBean 中添加自定义的过滤器，并为这些过滤器指定一个名称
        //shiroFilterFactoryBean.getFilters().put("csrf", new CsrfFilter()); // 可以使用这些过滤器名称来配置 URL 的过滤规则，指定某些 URL 需要经过特定的过滤器进行处理
        Map<String, String> filterChainDefinitionMap = shiroFilterFactoryBean.getFilterChainDefinitionMap();

        //ShiroUtils.loadBaseFilterChain(filterChainDefinitionMap);

        //ShiroUtils.ignoreCsrfFilter(filterChainDefinitionMap);
        filterChainDefinitionMap.put("/Users/**", "anon");
        filterChainDefinitionMap.put("/**", "authc");
        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterChainDefinitionMap); //配置 URL 的过滤规则，Shiro 会根据这些 URL 的配置对请求进行过滤和验证
        return shiroFilterFactoryBean;


    }

    @Bean(name="securityManager")
    public DefaultWebSecurityManager securityManager() {
        DefaultWebSecurityManager manager = new DefaultWebSecurityManager();
        manager.setRealm(userRealm());
        //manager.setCacheManager(cacheManager());
        manager.setSessionManager(defaultWebSessionManager());
        return manager;
    }

    @Bean
    @DependsOn(value="lifecycleBeanPostProcessor")
    public UserRealm userRealm() {
        UserRealm userRealm = new UserRealm();
        userRealm.setCredentialsMatcher(credentialsMatcher());
        //userRealm.setCacheManager(cacheManager());
        return userRealm;
    }


    @Bean(name = "shiroFilter")
    public FilterRegistrationBean<Filter> shiroFilter(ShiroFilterFactoryBean shiroFilterFactoryBean) throws Exception {
        FilterRegistrationBean<Filter> registration = new FilterRegistrationBean<>();
        registration.setFilter((new DelegatingFilterProxy("shiroFilter")); // 可以通过类名获取Bean实例,类名.getObject()获得
        registration.setDispatcherTypes(EnumSet.allOf(DispatcherType.class)); // 配置Servlet的Dispatcher类型,处理所有类型的请求，包括普通请求、转发请求、包含请求和异步请求。
        return registration;
    }
}
