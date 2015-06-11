package com.dzhao.example.jpa;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.name.Named;

import javax.persistence.EntityManager;

public class MyApplicationScopedJpaService {

    @Inject
    @Named("applicationScoped")
    private Provider<EntityManager> em;

    public MyApplicationScopedJpaService(){

    }
}
