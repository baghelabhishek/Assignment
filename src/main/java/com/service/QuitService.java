package com.service;

public class QuitService  implements com.FileInfoService{
    @Override
    public String execute(String input) {
        return "200 Goodbye";
    }
}
