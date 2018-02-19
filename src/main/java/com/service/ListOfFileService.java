package com.service;// Unpublished Work (c) 2018 Deere & Company

import java.io.File;
import java.util.stream.Stream;

public class ListOfFileService implements com.FileInfoService {

    public String execute(String input) {
        StringBuilder builder = new StringBuilder();

        File[] files = new File(System.getProperty("user.dir")).listFiles();
        Stream.of(files)
                .forEach(e -> builder.append(e.getName() + "\n"));
        builder.append("200 OK");
        System.out.println(builder.toString());
        return builder.toString();
    }
}
