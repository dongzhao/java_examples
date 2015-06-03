package com.dzhao.example.dao;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;
import java.util.List;

public class DaoHelperImpl extends AbstractDaoHelper{

    private static final Logger logger = LoggerFactory.getLogger(DaoHelper.class);

    public DaoHelperImpl(String persistenceUnitName){
        if(emf == null){
            emf = Persistence.createEntityManagerFactory(persistenceUnitName);
        }
    }

    public <T> List<T> findResults(Class<T> clazz) {
        EntityManager em = emf.createEntityManager();
        List<T> results = null;
        try {
            results = findResults(clazz, em);
        } catch (Exception e) {
            logger.error(e.getMessage());
        } finally {
            em.close();
        }
        return results;
    }

    public <T> int executeUpdate(T obj) {
        EntityManager em = emf.createEntityManager();
        int count = 0;
        try {
            count = executeUpdate(obj, em);
        }catch (Exception ex) {
            logger.error(ex.getMessage());
        }finally {
            em.close();
        }
        return count;
    }

    public void close(){
        if(emf.isOpen())
            emf.close();
    }

}
