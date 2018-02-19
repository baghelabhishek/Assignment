package com.service;

public class BadRequestService implements FileInfoService {

    @Override
    public String execute(String input) {
        return "503 Unknown command";
    }
}
