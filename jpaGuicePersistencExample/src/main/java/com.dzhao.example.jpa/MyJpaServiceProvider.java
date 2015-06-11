package com.dzhao.example.jpa;

import com.google.inject.Provider;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;


public class MyJpaServiceProvider implements Provider<EntityManager> {

    private EntityManagerFactory emf;

    public MyJpaServiceProvider(EntityManagerFactory emf){
        this.emf = emf;
    }

    @Override
    public EntityManager get() {
        return emf.createEntityManager();
    }
}
