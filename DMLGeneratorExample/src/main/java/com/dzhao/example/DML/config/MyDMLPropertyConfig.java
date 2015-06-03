package com.dzhao.example.DML.config;

import com.dzhao.example.DML.annotation.ConfigItem;

public class MyDMLPropertyConfig {

    @ConfigItem(configKey = "trigger.database.name")
    private String triggerDbName;
    @ConfigItem(configKey = "trigger.database.schema")
    private String triggerDbSchema;
    @ConfigItem(configKey = "audit.database.name")
    private String auditDbName;
    @ConfigItem(configKey = "audit.database.schema")
    private String auditDbSchema;

    public String getTriggerDbName() {
        return triggerDbName;
    }

    public void setTriggerDbName(String triggerDbName) {
        this.triggerDbName = triggerDbName;
    }

    public String getTriggerDbSchema() {
        return triggerDbSchema;
    }

    public void setTriggerDbSchema(String triggerDbSchema) {
        this.triggerDbSchema = triggerDbSchema;
    }

    public String getAuditDbName() {
        return auditDbName;
    }

    public void setAuditDbName(String auditDbName) {
        this.auditDbName = auditDbName;
    }

    public String getAuditDbSchema() {
        return auditDbSchema;
    }

    public void setAuditDbSchema(String auditDbSchema) {
        this.auditDbSchema = auditDbSchema;
    }

}
