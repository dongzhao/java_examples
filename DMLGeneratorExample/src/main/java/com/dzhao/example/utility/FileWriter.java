package com.dzhao.example.utility;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

public class FileWriter {

    private static Logger logger = LoggerFactory.getLogger(FileWriter.class);

    private FileWriter(){}

    public static void output(String fileName, String content){
        try {
            File file = new File(fileName);
            if(!file.exists()){
                file.createNewFile();
            }
            java.io.FileWriter writer = new java.io.FileWriter(file);
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
