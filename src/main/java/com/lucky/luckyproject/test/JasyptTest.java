package com.lucky.luckyproject.test;

import org.jasypt.encryption.pbe.StandardPBEStringEncryptor;

public class JasyptTest {
    public static void main(String[] args) {
        StandardPBEStringEncryptor jasypt = new StandardPBEStringEncryptor();
        jasypt.setPassword("my_password_key"); // 여기에 마스터 키 입력
        jasypt.setAlgorithm("PBEWithMD5AndDES");

        String plainText = "osh3069@"; // 암호화할 실제 값
        String encryptedText = jasypt.encrypt(plainText);

        System.out.println("Encrypted Value: " + encryptedText);
        System.out.println("Decrypted Value: " + jasypt.decrypt(encryptedText));
    }
}
