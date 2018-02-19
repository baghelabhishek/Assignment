package com.service;// Unpublished Work (c) 2018 Deere & Company

import java.io.File;

public class CurrentPathService implements com.FileInfoService {

    @Override
    public String execute(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new File(System.getProperty("user.dir")).getAbsolutePath() + "\n ");
        stringBuilder.append("200 OK");
        return stringBuilder.toString();
    }
}
