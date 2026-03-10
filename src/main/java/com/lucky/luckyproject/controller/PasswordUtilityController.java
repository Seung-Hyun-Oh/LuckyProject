package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.util.PasswordGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Password Utility", description = "비밀번호 암호화 생성 및 검증 도구")
@RestController
@RequestMapping("/api/utils")
@RequiredArgsConstructor
public class PasswordUtilityController {

    private final PasswordGenerator passwordGenerator;

    @Operation(summary = "DB 저장용 비밀번호 생성", description = "평문을 입력하면 BCrypt로 암호화된 해시를 반환합니다.")
    @GetMapping("/encode")
    public String encode(
            @Parameter(description = "암호화할 평문 비밀번호") @RequestParam String password) {
        return passwordGenerator.encodePassword(password);
    }

    @Operation(summary = "비밀번호 일치 여부 검증", description = "평문과 DB 해시값이 일치하는지 테스트합니다.")
    @PostMapping("/match")
    public boolean match(
            @Parameter(description = "평문 비밀번호") @RequestParam String raw,
            @Parameter(description = "DB에 저장된 해시값") @RequestParam String encoded) {
        return passwordGenerator.matches(raw, encoded);
    }
}
