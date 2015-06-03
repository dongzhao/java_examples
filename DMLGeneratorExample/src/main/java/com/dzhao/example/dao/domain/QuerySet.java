package com.dzhao.example.dao.domain;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name="querySet")
@XmlAccessorType(XmlAccessType.FIELD)
public class QuerySet {
    @XmlElement(name="nativeQuery")
    private String query;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }
}
