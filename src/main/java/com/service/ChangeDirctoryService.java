package com.service;

import java.io.File;

public class ChangeDirctoryService implements FileInfoService {

    public String execute(String input) {
        String[] split = input.split(" ");
        if (split.length == 1){
            return "500 Invalid command. Missing parameters";
        }
        File file = new File(System.getProperty("user.dir"), split[1]);
        if(!file.exists()){
            return "501 Directory " + split[1] + " not found";
        }else {
            return "200 OK. Current directory is "+split[1];
        }
    }
}
