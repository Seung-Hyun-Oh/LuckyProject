package com.lucky.luckyproject.config;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;
import org.jasypt.encryption.StringEncryptor;
import org.jasypt.encryption.pbe.PooledPBEStringEncryptor;
import org.jasypt.encryption.pbe.config.SimpleStringPBEConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * [2025 ?œì?] ?¤ì •?Œì¼(application.yml) ???”í˜¸?”ëœ ê°’ì„ ë³µí˜¸?”í•˜ê¸??„í•œ Jasypt ?¤ì •
 */
@Configuration
@EnableEncryptableProperties
public class JasyptConfig {

    @Bean("jasyptStringEncryptor")
    public StringEncryptor stringEncryptor() {
        PooledPBEStringEncryptor encryptor = new PooledPBEStringEncryptor();
        SimpleStringPBEConfig config = new SimpleStringPBEConfig();

        // VM Options ?ëŠ” ?˜ê²½ ë³€??-Djasypt.encryptor.password=...)?ì„œ ë§ˆìŠ¤???¤ë? ?½ìŒ
        // ë³´ì•ˆ???ŒìŠ¤ì½”ë“œ???¤ì •?Œì¼??ì§ì ‘ ê¸°ë¡?˜ì? ?ŠëŠ” ê²ƒì´ ?ì¹™?…ë‹ˆ??
        String password = System.getProperty("jasypt.encryptor.password");
        if (password == null || password.isEmpty()) {
            throw new RuntimeException("Jasypt ë§ˆìŠ¤???”í˜¸???¤ê? VM ?µì…˜???¤ì •?˜ì? ?Šì•˜?µë‹ˆ??");
        }
        System.out.println("password: " + password);

        config.setPassword(password);                // ?”í˜¸??ë³µí˜¸?”ìš© ë§ˆìŠ¤????
        config.setAlgorithm("PBEWithMD5AndDES");      // ?”í˜¸???Œê³ ë¦¬ì¦˜
        config.setKeyObtentionIterations("1000");     // ?´ì‹± ë°˜ë³µ ?Ÿìˆ˜
        config.setPoolSize("1");                      // ?¸ì½”???€ ?¬ì´ì¦?
        config.setProviderName("SunJCE");             // Java ?”í˜¸???„ë¡œë°”ì´??
        config.setSaltGeneratorClassName("org.jasypt.salt.RandomSaltGenerator");
        config.setIvGeneratorClassName("org.jasypt.iv.NoIvGenerator");
        config.setStringOutputType("base64");         // ?¸ì½”??ê²°ê³¼ ?•ì‹

        encryptor.setConfig(config);
        return encryptor;
    }
}
