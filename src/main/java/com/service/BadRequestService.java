package com.service;// Unpublished Work (c) 2018 Deere & Company

public class BadRequestService implements com.FileInfoService {

    @Override
    public String execute(String input) {
        return "503 Unknown command";
    }
}
