package com.dzhao.example.dao;


import com.dzhao.example.DML.annotation.QueryInfo;
import com.dzhao.example.dao.domain.QuerySet;
import com.dzhao.example.utility.PojoConvertor;
import com.dzhao.example.utility.XmlConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.xml.bind.JAXBException;
import java.io.IOException;
import java.util.List;

public abstract class AbstractDaoHelper implements DaoHelper{

    private static final Logger logger = LoggerFactory.getLogger(DaoHelper.class);

    protected static EntityManagerFactory emf = null;

    public <T> List<T> findResults(Class<T> clazz, EntityManager em) {
        List<T> results = null;
        QueryInfo queryInfo = clazz.getAnnotation(QueryInfo.class);
        String queryValue = null;
        try {
            queryValue = getQuery(queryInfo);
        } catch (JAXBException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        Query query = em.createNativeQuery(queryValue);
        List<Object[]> objects = query.getResultList();
        try {
            results = PojoConvertor.convert(objects, clazz);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
        return results;
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

    private String getQuery(QueryInfo queryInfo) throws JAXBException {
        String queryValue = queryInfo.nativeQuery();
        if(!queryValue.isEmpty()){
            return queryValue;
        }
        String queryPath = queryInfo.nativeQueryFrom();
        QuerySet queryXml = XmlConvertor.fromXML(XmlConvertor.class.getClassLoader().getResourceAsStream(queryPath), QuerySet.class);
        return queryXml.getQuery();
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

    public <T> int executeUpdate(T obj, EntityManager em) {
        String sqlStr;
        int count = 0;
        try {
            Class<?> clazz = obj.getClass();
            QueryInfo queryInfo = clazz.getAnnotation(QueryInfo.class);
            if(queryInfo==null)
                throw new RuntimeException("Unsupported object");
            sqlStr = getQuery(queryInfo);
        } catch (JAXBException e) {
            throw new RuntimeException(e);
        }

        EntityTransaction tx = null;
        try {
            tx = em.getTransaction();
            tx.begin();
            Query query = em.createNativeQuery(sqlStr);
            count = query.executeUpdate();
            tx.commit();
        }catch (Exception ex) {
            if(tx!=null)
                tx.rollback();
            logger.error("failed to update status of sync task." + ex.getMessage());
        }finally {
            if(tx!=null && tx.isActive())
                tx.rollback();
        }
        return count;

    }

    @Override
    public void close(){
        if(emf.isOpen())
            emf.close();
    }
}
