package com.dzhao.example.webservices.inject;

import com.dzhao.example.webservices.MyRestServiceImpl;
import com.dzhao.example.webservices.api.MyRestService;
import com.google.inject.AbstractModule;
import com.google.inject.Singleton;

public class MyServieModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(MyRestService.class).to(MyRestServiceImpl.class).in(Singleton.class);
    }
}
