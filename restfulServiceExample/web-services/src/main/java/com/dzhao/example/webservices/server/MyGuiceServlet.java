package com.dzhao.example.webservices.server;

import com.dzhao.example.webservices.inject.MyServieModule;
import com.dzhao.example.webservices.server.inject.MyConfigServletModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import org.apache.shiro.guice.web.ShiroWebModule;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

public class MyGuiceServlet extends GuiceServletContextListener {

    private static Injector injector;
    private ServletContext servletContext;

    public void contextInitialized(ServletContextEvent servletContextEvent){
        this.servletContext = servletContextEvent.getServletContext();
        super.contextInitialized(servletContextEvent);
    }

    @Override
    protected Injector getInjector() {
        if(injector==null){
            this.injector = Guice.createInjector(
                    new MyConfigServletModule(),
                    new MyServieModule(),
                    ShiroWebModule.guiceFilterModule()
            );
        }
        return injector;
    }
}
