package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.service.EncryptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * [운영 주의] Jasypt 프로퍼티 암호화 도구
 * 이 컨트롤러는 보안상 로컬 IP(localhost)에서만 호출 가능하도록 SecurityConfig에서 제한됨.
 */
@Tag(name = "Jasypt 암호화 API", description = "설정값(yml) 암복호화 테스트를 위한 관리자 도구")
@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final EncryptionService encryptionService;

    // 생성자 주입
    public CryptoController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Operation(summary = "문자열 암호화", description = "평문을 Jasypt 키로 암호화하여 ENC(...) 형식으로 반환합니다.")
    @GetMapping("/encrypt")
    public String encrypt(
            @Parameter(description = "암호화할 평문(DB 패스워드 등)", example = "myPassword123")
            @RequestParam String text) {
        String encrypted = encryptionService.encrypt(text);
        // application.yml에 바로 복사해서 사용할 수 있도록 ENC() 포맷으로 반환
        return "ENC(" + encrypted + ")";
    }

    @Operation(summary = "문자열 복호화", description = "암호화된 문자열을 평문으로 복호화하여 확인합니다.")
    @GetMapping("/decrypt")
    public String decrypt(
            @Parameter(description = "복호화할 암호문 (ENC 접두사 제외)", example = "vK7X9b/4S9f...")
            @RequestParam String encryptedText) {
        return encryptionService.decrypt(encryptedText);
    }
}
