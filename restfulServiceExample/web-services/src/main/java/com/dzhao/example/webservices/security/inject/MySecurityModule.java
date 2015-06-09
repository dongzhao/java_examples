package com.dzhao.example.webservices.security.inject;


import com.dzhao.example.webservices.security.config.MySecurityConfig;
import com.dzhao.example.webservices.security.realm.MySimpleAccountRealm;
import org.apache.shiro.guice.web.ShiroWebModule;
import org.apache.shiro.web.filter.authc.BasicHttpAuthenticationFilter;

import javax.servlet.ServletContext;

public class MySecurityModule extends ShiroWebModule {

    public MySecurityModule(ServletContext servletContext) {
        super(servletContext);
    }

    @Override
    protected void configureShiroWeb() {
        requestStaticInjection(BasicHttpAuthenticationFilter.class);
        bindRealm().to(MySimpleAccountRealm.class);
        MySecurityConfig config = new MySecurityConfig();
        if(config.isAuthenticationRequired()){
            addFilterChain("/rest/**", AUTHC_BASIC);
        }
    }
}
