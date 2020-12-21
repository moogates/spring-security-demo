package com.example.springbootvuebookshiro;

import at.pollux.thymeleaf.shiro.dialect.ShiroDialect;
import org.apache.shiro.realm.Realm;
import org.apache.shiro.realm.text.TextConfigurationRealm;
import org.apache.shiro.spring.web.config.DefaultShiroFilterChainDefinition;
import org.apache.shiro.spring.web.config.ShiroFilterChainDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ShiroConfig {
    @Bean
    public Realm demoRealm() {
        // 定义用户及其角色
        TextConfigurationRealm realm = new TextConfigurationRealm();
        realm.setUserDefinitions("sang=123,user\n admin=123,admin");

        // 为角色分配权限（Roles with assigned permissions）
        realm.setRoleDefinitions("admin=read,write\n user=read");
        return realm;
    }

    @Bean
    public ShiroFilterChainDefinition shiroFilterChainDefinition() {
        // https://shiro.apache.org/web.html#Web-DefaultFilters

        // 定义url的“认证”要求，注意并不是授权要求，只是认证要求
        DefaultShiroFilterChainDefinition filter = new DefaultShiroFilterChainDefinition();

        // anon 表示可以匿名访问
        filter.addPathDefinition("/login", "anon");
        filter.addPathDefinition("/doLogin", "anon");

        filter.addPathDefinition("/logout", "logout");
        // authc 表示需要登录访问
        filter.addPathDefinition("/**", "authc");
        return filter;
    }

    @Bean
    // 支持thymeleaf标签使用
    public ShiroDialect shiroDialect() {
        return new ShiroDialect();
    }
}
