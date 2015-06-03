package com.dzhao.example.DML.model;

import javax.persistence.*;

@Entity
@Table(schema = "MYAUDIT", name = "AUDIT_CHANGE_ITEM_DETAIL")
public class ChangeItemDetail {

    @Id
    private String id;
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private ChangeItem changeItem;
    @Column
    private String name;
    @Column(name = "from_value")
    private String fromValue;
    @Column(name = "to_value")
    private String toValue;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFromValue() {
        return fromValue;
    }

    public void setFromValue(String fromValue) {
        this.fromValue = fromValue;
    }

    public String getToValue() {
        return toValue;
    }

    public void setToValue(String toValue) {
        this.toValue = toValue;
    }

    public ChangeItem getChangeItem() {
        return changeItem;
    }

    public void setChangeItem(ChangeItem changeItem) {
        this.changeItem = changeItem;
    }
}
