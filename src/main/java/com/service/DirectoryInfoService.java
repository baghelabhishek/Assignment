package com.service;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DirectoryInfoService implements FileInfoService {

    public String execute(String input) {
        String[] split = input.split(" ");
        if (split.length == 1 ){
            return "500 Invalid command. Missing parameters";
        }
        File file = new File(System.getProperty("user.dir"), split[1]);
        String modifiedDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date(file.lastModified()));
        StringBuilder builder = new StringBuilder();
        if(!file.exists()){
            return "501 File " + split[1] + " not found";
        }
        return buildOutput(builder, file, modifiedDate);
    }

    private String buildOutput(StringBuilder builder, File file, String modifiedDate) {

        if(file.isDirectory()){
            builder.append("Type: "+ "File" + "\n");
            builder.append("Size: ").append(file.length()).append("\n");
        }else{
            builder.append("Type: "+ "Directory" + "\n");
        }

        builder.append("Created :").append(modifiedDate).append("\n");
        builder.append("200 OK");

        return builder.toString();
    }
}
