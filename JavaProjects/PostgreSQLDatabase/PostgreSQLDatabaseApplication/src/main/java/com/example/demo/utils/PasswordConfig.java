package com.example.demo.utils;


public class PasswordConfig{

    public static String passwordConfig(String password){
        try {
            String encrypted = BaseHelper.encrypt(password);
            return encrypted;
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}
