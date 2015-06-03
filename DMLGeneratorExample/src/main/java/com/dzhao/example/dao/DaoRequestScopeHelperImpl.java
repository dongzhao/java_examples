package com.dzhao.example.dao;


import com.google.inject.Inject;
import com.google.inject.Provider;

import javax.persistence.EntityManager;
import java.util.List;

public class DaoRequestScopeHelperImpl extends AbstractDaoHelper{
    @Inject
    private Provider<EntityManager> em;

    @Override
    public <T> List<T> findResults(Class<T> clazz) {
        return findResults(clazz, em.get());
    }

    @Override
    public <T> int executeUpdate(T obj){
        return executeUpdate(obj, em.get());
    }
}
