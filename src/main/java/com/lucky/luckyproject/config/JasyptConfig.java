package com.lucky.luckyproject.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [2025 표준] 설정파일(application.yml) 내 암호화된 값을 복호화하기 위한 Jasypt 설정
 */
@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        // VM Options 또는 환경 변수(-Djasypt.encryptor.password=...)에서 마스터 키를 읽음
        // 보안상 소스코드나 설정파일에 직접 기록하지 않는 것이 원칙입니다.
        String password = System.getProperty("jasypt.encryptor.password");
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Jasypt 마스터 암호화 키가 VM 옵션에 설정되지 않았습니다!");
        }
        System.out.println("password: " + password);

        config.setPassword(password);                // 암호화/복호화용 마스터 키
        config.setAlgorithm("PBEWithMD5AndDES");      // 암호화 알고리즘
        config.setKeyObtentionIterations("1000");     // 해싱 반복 횟수
        config.setPoolSize("1");                      // 인코딩 풀 사이즈
        config.setProviderName("SunJCE");             // Java 암호화 프로바이더
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");         // 인코딩 결과 형식

        encryptor.setConfig(config);
        return encryptor;
    }
}
