package com.service;

import java.io.File;

public class CurrentPathService implements FileInfoService {

    @Override
    public String execute(String input) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(new File(System.getProperty("user.dir")).getAbsolutePath()).append("\n ");
        stringBuilder.append("200 OK");
        return stringBuilder.toString();
    }
}
