package com.service;

public class QuitService  implements FileInfoService {
    @Override
    public String execute(String input) {
        return "200 Goodbye";
    }
}
