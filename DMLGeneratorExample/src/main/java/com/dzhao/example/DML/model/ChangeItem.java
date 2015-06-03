package com.dzhao.example.DML.model;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(schema = "MYAUDIT", name = "AUDIT_CHANGE_ITEM")
public class ChangeItem {
    @Id
    private String id;
    @Column
    private String name;
    @Column
    private String action;
    @Column(name = "effective_datetime")
    private Date effectiveDatetime;
    @OneToMany(mappedBy = "changeItems")
    private List<ChangeItemDetail> changeItemDetails;

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

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public Date getEffectiveDatetime() {
        return effectiveDatetime;
    }

    public void setEffectiveDatetime(Date effectiveDatetime) {
        this.effectiveDatetime = effectiveDatetime;
    }

    public List<ChangeItemDetail> getChangeItemDetails() {
        return changeItemDetails;
    }

    public void setChangeItemDetails(List<ChangeItemDetail> changeItemDetails) {
        this.changeItemDetails = changeItemDetails;
    }
}
