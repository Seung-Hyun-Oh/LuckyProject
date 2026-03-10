package com.lucky.luckyproject.test;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTest {
    public static void main(String[] args) {
        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword("my_password_key"); // ?¬ê¸°??ë§ˆìŠ¤?????…ë ¥
        jasypt.setAlgorithm("PBEWithMD5AndDES");

        String plainText = "osh3069@"; // ?”í˜¸?”í•  ?¤ì œ ê°?
        String encryptedText = jasypt.encrypt(plainText);

        System.out.println("Encrypted Value: " + encryptedText);
        System.out.println("Decrypted Value: " + jasypt.decrypt(encryptedText));
    }
}
