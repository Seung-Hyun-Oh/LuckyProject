package com.lucky.luckyproject.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * [2025 ?œì?] BCrypt ?Œê³ ë¦¬ì¦˜???´ìš©??ë¹„ë?ë²ˆí˜¸ ?”í˜¸??ë°?ê²€ì¦?? í‹¸ë¦¬í‹°
 * BCrypt???¤í–‰ ë§ˆë‹¤ ?”íŠ¸(Salt)ë¥??ë™?¼ë¡œ ?ì„±?˜ì—¬ ë³´ì•ˆ?±ì´ ë§¤ìš° ?’ìŠµ?ˆë‹¤.
 */
@Component
@RequiredArgsConstructor // final ?„ë“œ???€???ì„±??ì£¼ì… ?˜í–‰
public class PasswordGenerator {

    // 1. finalë¡?? ì–¸?˜ì—¬ ?¤í”„ë§ì´ ì£¼ì…?˜ê²Œ ?©ë‹ˆ??
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * ?‰ë¬¸ ë¹„ë?ë²ˆí˜¸ë¥?DB ?€?¥ìš© ?´ì‹œê°’ìœ¼ë¡??”í˜¸??
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * ?…ë ¥ë°›ì? ?‰ë¬¸ê³?DB???”í˜¸?”ëœ ê°’ì´ ?¼ì¹˜?˜ëŠ”ì§€ ?•ì¸
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * ?ŒìŠ¤?¸ë? ?„í•œ main ë©”ì„œ??
     * main ë©”ì„œ?œëŠ” ?¤í”„ë§?ì»¨í…?¤íŠ¸ ë°–ì´ë¯€ë¡?ì§ì ‘ ê°ì²´ë¥??ì„±?´ì„œ ?ŒìŠ¤?¸í•´???©ë‹ˆ??
     */
    public static void main(String[] args) {
        // main?ì„œ ?¤í–‰???ŒëŠ” ?¤í”„ë§ì˜ ì£¼ì…??ë°›ì„ ???†ìœ¼ë¯€ë¡?ì§ì ‘ ?¸ì½”?”ë? ?ì„±?˜ì—¬ ?„ë‹¬?©ë‹ˆ??
        BCryptPasswordEncoder testEncoder = new BCryptPasswordEncoder();
        PasswordGenerator generator = new PasswordGenerator(testEncoder);

        String rawPassword = "myPassword123!";
        String encodedPassword = generator.encodePassword(rawPassword);

        System.out.println("DB ?€?¥ìš© ?”í˜¸??ë¹„ë?ë²ˆí˜¸: " + encodedPassword);

        boolean isMatch = generator.matches(rawPassword, encodedPassword);
        System.out.println("?¼ì¹˜ ?¬ë?: " + isMatch);
    }
}
