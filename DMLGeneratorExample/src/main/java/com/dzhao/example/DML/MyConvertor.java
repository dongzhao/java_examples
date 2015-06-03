/*
package com.dzhao.example.DML;

import ch.lambdaj.group.Group;
import com.dzhao.example.DML.domain.ConfigInfoTemplate;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static ch.lambdaj.Lambda.*;
import static ch.lambdaj.Lambda.select;

public class MyConvertor {

    private static String SCHEMA = "dbo";
    private static String SCHEMA_AUDIT = "AUDIT";
    private static String DB_RW = "mydb";
    private static String DB_AUDIT = "myAuditDb";

    private MyConvertor(){}

    public static List<ConfigInfoTemplate> getAuditConfigInfos(){
        DaoHelper helper = new DaoHelperImpl("audit");
        return helper.findResults(ConfigInfoTemplate.class);
    }

    public static void generateAuditConfigTemplate(String output){
        List<ConfigInfoTemplate> auditConfigInfos = getAuditConfigInfos();
        Group<ConfigInfoTemplate> auditConfigInfoGroup = group(auditConfigInfos, by(on(ConfigInfoTemplate.class).getTableName()));
        Map<String, List<String>> auditConfigInfoMap = new HashMap<String, List<String>>();
        for (String tableName : auditConfigInfoGroup.keySet()){
            List<ConfigInfoTemplate> auditConfigInfoList = auditConfigInfoGroup.find(tableName);
            List<String> columnNames = extract(auditConfigInfoList, on(ConfigInfoTemplate.class).getColumnName());
            auditConfigInfoMap.put(tableName, columnNames);
        }
        try {
            ExcelSheetWriter.createTemplate(output, auditConfigInfoMap);
        } catch (IOException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static void output(String fileName, String content){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            writer.write(content);
            writer.flush();
            writer.close();

            System.out.println(content);

        } catch (IOException e) {
            logger.error(e.getMessage());
            e.printStackTrace();
        }
    }
}
*/
