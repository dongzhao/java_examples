package com.dzhao.example.DML;

import ch.lambdaj.group.Group;
import com.dzhao.example.DML.config.MyDMLPropertyConfig;
import com.dzhao.example.DML.domain.ChangeItemInfo;
import com.dzhao.example.dao.DaoHelper;
import com.dzhao.example.dao.DaoHelperImpl;
import com.dzhao.example.utility.FileWriter;
import org.apache.commons.cli.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static ch.lambdaj.Lambda.*;
import static ch.lambdaj.Lambda.extract;
import static ch.lambdaj.Lambda.on;

public class DMLGenerator {

    private static Logger logger =  LoggerFactory.getLogger(DMLGenerator.class);

    /**
     *
     * @param args -o [operation name (insert/update)] -t [table name] such as -o insert -t RW_CLASS
     *
     */
    public static void main(String[] args){
        final Options options = new Options();
        options.addOption("o", "operation", true, "generate DML by operation value");
        options.addOption("t", "table", true, "generate table DML");

        final CommandLineParser parser = new GnuParser();
        String tableName = "";
        String triggerType = "";
        try {
            final CommandLine cmd =  parser.parse(options, args);
            if(cmd.hasOption("o")){
                triggerType = cmd.getOptionValue("o");
            }
            if(cmd.hasOption("t")){
                tableName = cmd.getOptionValue("t");
            }
            generateDMLScript(triggerType, tableName);
        } catch (ParseException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

    }

    private static void generateDMLScript(String triggerType, String tableName) {
        DaoHelper helper = new DaoHelperImpl("mssql");
        List<ChangeItemInfo> infos = helper.findResults(ChangeItemInfo.class);
        Group<ChangeItemInfo> auditConfigInfoGroup = group(infos, by(on(ChangeItemInfo.class).getTableName()));

        //MyDMLPropertyConfig config = ConfigUtil.getConfigObject(MyDMLPropertyConfig.class, "config.properties");
        MyDMLPropertyConfig config = new MyDMLPropertyConfig();

        if(triggerType.isEmpty() || triggerType.equalsIgnoreCase("insert")){
            String generatedInsertTriggerScript = "";
            if(tableName.isEmpty()){
                generatedInsertTriggerScript = generateInsertTriggerScript(auditConfigInfoGroup, config);
            }else{
                generatedInsertTriggerScript = generateInsertTriggerScript(auditConfigInfoGroup, config, tableName);
            }
            FileWriter.output("triggers-insert.sql", generatedInsertTriggerScript);
        }

        if(triggerType.isEmpty() || triggerType.equalsIgnoreCase("update")){
            String generatedUpdateTriggerScript = "";
            if(tableName.isEmpty()){
                generatedUpdateTriggerScript = generateUpdateTriggerScript(auditConfigInfoGroup, config);
            }else{
                generatedUpdateTriggerScript = generateUpdateTriggerScript(auditConfigInfoGroup, config, tableName);
            }
            FileWriter.output("triggers-update.sql", generatedUpdateTriggerScript);
        }
    }

    private static String trInsertStartWithStr =
            "\nCREATE TRIGGER [$trigger_schema].[$trigger_name]\n" +
                    "ON [$trigger_schema].[$table_name]\n" +
                    "AFTER INSERT \n" +
                    "AS\n" +
                    "BEGIN\n" +
                    "\tDECLARE @itemId char(255)\n" +
                    "\tDECLARE @changeItemId char(255)\n" +
                    "\tDECLARE @new_value char(255)\n";

    private static String trUpdateStartWithStr =
            "\nCREATE TRIGGER [$trigger_schema].[$trigger_name]\n" +
                    "ON [$trigger_schema].[$table_name]\n" +
                    "AFTER UPDATE \n" +
                    "AS\n" +
                    "BEGIN\n" +
                    "\tDECLARE @item_id char(255)\n" +
                    "\tDECLARE @changeItemId char(255)\n" +
                    "\tDECLARE @old_value char(255)\n" +
                    "\tDECLARE @new_value char(255)\n";

    private static String auditTableStr =
            "\tSET @changeItemId = CONVERT(char(255), NEWID()) \n" +
                    "\tSELECT @itemId = i.id FROM inserted i\n" +
                    "\tINSERT INTO [$audit_db].[$audit_schema].[AUDIT_CHANGE_ITEM]([id],[name],[item_id],[action],[effective_datetime]) VALUES(@changeItemId, '$table_name', @itemId, 'INSERT', GETDATE())\n";

    private static String auditColumnInsertStr =
            "\tIF UPDATE($column_name)\n" +
                    "\tBEGIN\n" +
                    "\t\tSELECT @new_value = $column_name FROM inserted\n" +
                    "\t\tINSERT INTO [$audit_db].[$audit_schema].[AUDIT_CHANGE_ITEM_DETAIL]([id],[change_item_ID],[name],[from_value],[to_value]) SELECT CONVERT(char(255), NEWID()), @changeItemId, '$column_name', null, i.$column_name FROM inserted i\n" +
                    "\tEND\n";

    private static String auditColumnUpdateStr =
            "\tIF UPDATE($column_name)\n" +
                    "\t\tBEGIN\t\n" +
                    "\t\t\tSELECT @old_value = $column_name FROM deleted,\n" +
                    "\t\t\tSELECT @new_value = $column_name FROM inserted\n" +
                    "\t\t\tINSERT INTO [$audit_db].[$audit_schema].[AUDIT_CHANGE_ITEM_DETAIL]([id],[change_item_ID],[name],[from_value],[to_value])\n" +
                    "\t\t\tVALUES(CONVERT(char(255), NEWID()), @changeItemId, '$column_name', @old_value, @new_value)  \n" +
                    "\t\tEND \n";

    private static String triEndWithStr = "END\n";

    private static String generateInsertTriggerScript(Group<ChangeItemInfo> auditConfigInfoGroup, MyDMLPropertyConfig config){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("USE " + config.getTriggerDbName() + "\n");
        strBuilder.append("GO\n");
        for (String tableName : auditConfigInfoGroup.keySet()){
            strBuilder.append(generateInsertTriggerScript(auditConfigInfoGroup, config, tableName));
        }
        return strBuilder.toString();
    }

    private static String generateInsertTriggerScript(Group<ChangeItemInfo> auditConfigInfoGroup, MyDMLPropertyConfig config, String tableName){
        StringBuilder strBuilder = new StringBuilder();
        List<ChangeItemInfo> auditConfigInfoList = auditConfigInfoGroup.find(tableName);
        List<String> columnNames = extract(auditConfigInfoList, on(ChangeItemInfo.class).getColumnName());
        strBuilder.append("/** INSERT TRIGGER OF " + tableName + " **/");
        strBuilder.append(trInsertStartWithStr.replace("$trigger_schema", config.getTriggerDbSchema()).replace("$trigger_name", "audit_" + tableName.toLowerCase() + "_insert").replace("$table_name", tableName));
        strBuilder.append(auditTableStr.replace("$audit_schema", config.getAuditDbSchema()).replace("$audit_db", config.getAuditDbName()).replace("$table_name", tableName));
        for (String columnName : columnNames) {
            strBuilder.append(auditColumnInsertStr.replace("$audit_schema", config.getAuditDbSchema()).replace("$audit_db", config.getAuditDbName()).replace("$column_name", columnName));
        }
        strBuilder.append(triEndWithStr);
        strBuilder.append("\n");
        return strBuilder.toString();
    }

    private static String generateUpdateTriggerScript(Group<ChangeItemInfo> auditConfigInfoGroup, MyDMLPropertyConfig config){
        StringBuilder strBuilder = new StringBuilder();
        strBuilder.append("USE " + config.getTriggerDbName() + "\n");
        strBuilder.append("GO\n");
        for (String tableName : auditConfigInfoGroup.keySet()){
            strBuilder.append(generateUpdateTriggerScript(auditConfigInfoGroup, config, tableName));
        }
        return strBuilder.toString();
    }

    private static String generateUpdateTriggerScript(Group<ChangeItemInfo> auditConfigInfoGroup, MyDMLPropertyConfig config, String tableName){
        StringBuilder strBuilder = new StringBuilder();
        List<ChangeItemInfo> auditConfigInfoList = auditConfigInfoGroup.find(tableName);
        List<String> columnNames = extract(auditConfigInfoList, on(ChangeItemInfo.class).getColumnName());
        strBuilder.append("/** UPDATE TRIGGER OF " + tableName + " **/");
        strBuilder.append(trUpdateStartWithStr.replace("$trigger_schema", config.getTriggerDbSchema()).replace("$trigger_name", "audit_" + tableName.toLowerCase() + "_update").replace("$table_name", tableName));
        strBuilder.append(auditTableStr.replace("$audit_schema", config.getAuditDbSchema()).replace("$audit_db", config.getAuditDbName()).replace("$table_name", tableName));
        for (String columnName : columnNames) {
            strBuilder.append(auditColumnUpdateStr.replace("$audit_schema", config.getAuditDbSchema()).replace("$audit_db", config.getAuditDbName()).replace("$column_name", columnName));
        }
        strBuilder.append(triEndWithStr);
        strBuilder.append("\n");
        return strBuilder.toString();
    }
}
