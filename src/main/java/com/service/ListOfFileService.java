package com.service;

import java.io.File;
import java.util.stream.Stream;

public class ListOfFileService implements FileInfoService {

    public String execute(String input) {
        StringBuilder builder = new StringBuilder();

        File[] files = new File(System.getProperty("user.dir")).listFiles();
        Stream.of(files)
                .forEach(e -> builder.append(e.getName()).append("\n"));
        builder.append("200 OK");
        System.out.println(builder.toString());
        return builder.toString();
    }
}
