package com.dzhao.example.webservices.server.inject;

import com.dzhao.example.webservices.server.MyServlet;
import com.google.inject.persist.PersistFilter;
import com.google.inject.persist.jpa.JpaPersistModule;
import com.google.inject.servlet.ServletModule;

import java.util.HashMap;
import java.util.Map;

public class MyConfigServletModule extends ServletModule {

    private static final String PKG_RESOURCE = "com.regulationworks.pj.tools.web.services.rest";

    @Override
    protected void configureServlets() {
        super.configureServlets();
        install(new JpaPersistModule("mssql"));
        filter("*").through(PersistFilter.class);

        Map<String, String> initParam = new HashMap<String, String>();
        initParam.put("jaxrs.serviceClasses", "com.dzhao.example.webservices.MyRestServiceImpl");

        serve("/rest/*").with(MyServlet.class, initParam);

    }
}