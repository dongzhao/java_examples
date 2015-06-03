package com.dzhao.example.dao;

import javax.persistence.EntityManager;
import java.util.List;

public interface DaoHelper {

    public <T> List<T> findResults(Class<T> clazz);

    public <T> List<T> findResults(Class<T> clazz, EntityManager em);

    public <T> int executeUpdate(T obj);

    public <T> int executeUpdate(T obj, EntityManager em) throws IllegalAccessException;

    public void close();
}
