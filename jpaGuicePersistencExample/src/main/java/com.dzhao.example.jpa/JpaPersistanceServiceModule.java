package com.dzhao.example.jpa;


import com.google.inject.AbstractModule;
import com.google.inject.name.Names;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.servlet.SessionScoped;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

/**
 * http://stackoverflow.com/questions/18101488/does-guice-persist-provide-transaction-scoped-or-application-managed-entitymanag
 * http://stackoverflow.com/questions/8051051/gwt-p-jpa-hibernate-entitymanager
 * http://stackoverflow.com/questions/8502271/best-way-to-initialize-jpapersistmodule
 *
 */

public class JpaPersistanceServiceModule extends AbstractModule {
    @Override
    protected void configure() {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("mssql");

        bind(EntityManager.class).annotatedWith(Names.named("requestScoped")).toProvider(new MyJpaServiceProvider(emf))
                .in(RequestScoped.class);
        bind(EntityManager.class).annotatedWith(Names.named("sessionScoped")).toProvider(new MyJpaServiceProvider(emf))
                .in(SessionScoped.class);
        bind(EntityManager.class).annotatedWith(Names.named("applicationScoped")).toProvider(new MyJpaServiceProvider(emf))
                .asEagerSingleton();
        bind(EntityManager.class).annotatedWith(Names.named("nonScoped")).toProvider(new MyJpaServiceProvider(emf));
    }
}
