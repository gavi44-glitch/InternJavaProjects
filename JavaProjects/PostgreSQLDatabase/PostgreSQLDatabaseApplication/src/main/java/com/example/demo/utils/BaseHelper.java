package com.example.demo.utils;

import javax.crypto.Cipher;
//import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.ServletRequest;
//import java.io.PrintWriter;
//import java.io.StringWriter;
//import java.net.InetAddress;
//import java.net.SocketException;
//import java.net.UnknownHostException;
import java.security.MessageDigest;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;
//import java.util.HashMap;
//import java.util.Random;
//import java.util.UUID;

public class BaseHelper {

    private static final String BASE_KEY = "7a0eb2847a62aab64efadb4e7cef9486";
    @SuppressWarnings("unused")
    private static final String ALGO = "AES";
    @SuppressWarnings("unused")
    private static final byte[] keyValue = new byte[] { '4', 'L', 'P', '4', 'B', '1', 't', 'p', '3', 'R', 'm', '4', 'T','a' };

//    public static Timestamp getCurrentTimestamp(){
//        Date dt = new Date();
//        return new Timestamp(dt.getTime());
//    }
//
//    public static String getRandomNumber() {
//        Random random = new Random();
//        return String.valueOf(random.nextInt(9999));
//    }
//
//    public static String generateUUID() {
//        return UUID.randomUUID().toString();
//    }
//
//
//    public static String generatePassword() throws Exception {
//        return generateKey().substring(0, 8);
//    }

    public static String getPasswordMD5(String password) throws Exception {
        MessageDigest md = MessageDigest.getInstance("MD5");
        md.update(password.getBytes());

        byte byteData[] = md.digest();

        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            String hex = Integer.toHexString(0xff & byteData[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static String byteToHex(byte[] data) {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            sb.append(Integer.toString((data[i] & 0xff) + 0x100, 16).substring(1));
        }

        StringBuffer hexString = new StringBuffer();
        for (int i = 0; i < data.length; i++) {
            String hex = Integer.toHexString(0xff & data[i]);
            if (hex.length() == 1)
                hexString.append('0');
            hexString.append(hex);
        }

        return hexString.toString();
    }

    public static byte[] hexStringToByteArray(String s) {
        int len = s.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(s.charAt(i), 16) << 4) + Character.digit(s.charAt(i + 1), 16));
        }
        return data;
    }

//    public static String generateKey() throws Exception {
//        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
//        keyGenerator.init(128);
//        SecretKey secretKey = keyGenerator.generateKey();
//
//        return byteToHex(secretKey.getEncoded());
//    }

    public static SecretKey getDefaultSecretKey() throws Exception {
        byte[] secretKeyByte = hexStringToByteArray(BASE_KEY);
        SecretKey secretKey = new SecretKeySpec(secretKeyByte, 0, secretKeyByte.length, "AES");

        return secretKey;
    }

    public static String encrypt(String plainText) throws Exception {

        byte[] plainTextByte = plainText.getBytes();
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, getDefaultSecretKey());

        byte[] encryptedByte = cipher.doFinal(plainTextByte);

        return byteToHex(encryptedByte);
    }

//    public static String decrypt(String encryptedText) throws Exception {
//        byte[] encryptedTextByte = hexStringToByteArray(encryptedText);
//        Cipher cipher = Cipher.getInstance("AES");
//        cipher.init(Cipher.DECRYPT_MODE, getDefaultSecretKey());
//        byte[] decryptedByte = cipher.doFinal(encryptedTextByte);
//        String decryptedText = new String(decryptedByte);
//        return decryptedText;
//    }
//
//    public static String generateUUIDWithDate(){
//        return getCurrentDateFormatted("ddMMyyyy")+generateUUID();
//    }

    public static String getCurrentDateFormatted(String format){
        try{
            SimpleDateFormat sdf = new SimpleDateFormat(format);
            return sdf.format(new Date());
        }catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }


    public static String convertTimestampToString(Timestamp time) {
        Date date = new Date();
        date.setTime(time.getTime());
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
        String strDate  = dateFormat.format(date);
        return strDate;
    }


    public static void main(String[] args) {
        try {
            System.out.println(getPasswordMD5("P@ssw0rd"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
