package com.lucky.luckyproject.util;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;

/**
 * [2025 표준] BCrypt 알고리즘을 이용한 비밀번호 암호화 및 검증 유틸리티
 * BCrypt는 실행 마다 솔트(Salt)를 자동으로 생성하여 보안성이 매우 높습니다.
 */
@Component
@RequiredArgsConstructor // final 필드에 대해 생성자 주입 수행
public class PasswordGenerator {

    // 1. final로 선언하여 스프링이 주입하게 합니다.
    private final BCryptPasswordEncoder passwordEncoder;

    /**
     * 평문 비밀번호를 DB 저장용 해시값으로 암호화
     */
    public String encodePassword(String rawPassword) {
        return passwordEncoder.encode(rawPassword);
    }

    /**
     * 입력받은 평문과 DB의 암호화된 값이 일치하는지 확인
     */
    public boolean matches(String rawPassword, String encodedPassword) {
        return passwordEncoder.matches(rawPassword, encodedPassword);
    }

    /**
     * 테스트를 위한 main 메서드
     * main 메서드는 스프링 컨텍스트 밖이므로 직접 객체를 생성해서 테스트해야 합니다.
     */
    public static void main(String[] args) {
        // main에서 실행할 때는 스프링의 주입을 받을 수 없으므로 직접 인코더를 생성하여 전달합니다.
        BCryptPasswordEncoder testEncoder = new BCryptPasswordEncoder();
        PasswordGenerator generator = new PasswordGenerator(testEncoder);

        String rawPassword = "myPassword123!";
        String encodedPassword = generator.encodePassword(rawPassword);

        System.out.println("DB 저장용 암호화 비밀번호: " + encodedPassword);

        boolean isMatch = generator.matches(rawPassword, encodedPassword);
        System.out.println("일치 여부: " + isMatch);
    }
}
