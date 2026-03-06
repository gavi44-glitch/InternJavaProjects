package com.example.demo.config;

import java.security.MessageDigest;

public class PasswordConfig {
    public static String sha256(String password) {
        try{
            // ini class dari Java security API yang provide algoritma hash kriptografi
            // sha-256 ini salah satu algoritmanya begitu,,
            //output sha-256 = selalu 256 bit = 32 byte
            MessageDigest md = MessageDigest.getInstance("SHA-256");

            //ini mengubah password menjadi hash, jadi password yang awalnya String -> jadi bytes
            //dengan tipe encoding UTF-8
            // lalu bytes ini diproses oleh SHA-256 menghasilkan array byte[32]
            byte[] hash = md.digest(password.getBytes("UTF-8"));

            // ini penampung String hasil hash -> untuk menggabungkan String secara efisien
            StringBuilder hexString = new StringBuilder();

            // jadi karena hasil hash itu berupa bytes, dilakukan for looping
            // untuk tiap tiap byte hasil hash
            for (byte b : hash){
                // ini intinya mengubah byte menjadi hex
                String hex = Integer.toHexString(0xff & b);

                // ini memastikan hex selalu 2 digit
                if(hex.length() == 1) hexString.append('0');

                // ini menambahkan hex ke hasil akhir
                hexString.append(hex);
            }

            // ini return hasil hash keseluruhan gitulah
            return hexString.toString();
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}

//package com.example.demo.config;
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
//
//@Configuration
//public class PasswordConfig{
//
//    @Bean // jadi ini spt membuat Bean password encoder yang bisa dipakai" di services
//    public BCryptPasswordEncoder passwordEncoder(){
//        return new BCryptPasswordEncoder();
//    }
//}
