package com.lucky.luckyproject.controller;

import com.lucky.luckyproject.util.PasswordGenerator;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Password Utility", description = "ë¹„ë?ë²ˆí˜¸ ?”í˜¸???ì„± ë°?ê²€ì¦??„êµ¬")
@RestController
@RequestMapping("/api/utils")
@RequiredArgsConstructor
public class PasswordUtilityController {

    private final PasswordGenerator passwordGenerator;

    @Operation(summary = "DB ?€?¥ìš© ë¹„ë?ë²ˆí˜¸ ?ì„±", description = "?‰ë¬¸???…ë ¥?˜ë©´ BCryptë¡??”í˜¸?”ëœ ?´ì‹œë¥?ë°˜í™˜?©ë‹ˆ??")
    @GetMapping("/encode")
    public String encode(
            @Parameter(description = "?”í˜¸?”í•  ?‰ë¬¸ ë¹„ë?ë²ˆí˜¸") @RequestParam String password) {
        return passwordGenerator.encodePassword(password);
    }

    @Operation(summary = "ë¹„ë?ë²ˆí˜¸ ?¼ì¹˜ ?¬ë? ê²€ì¦?, description = "?‰ë¬¸ê³?DB ?´ì‹œê°’ì´ ?¼ì¹˜?˜ëŠ”ì§€ ?ŒìŠ¤?¸í•©?ˆë‹¤.")
    @PostMapping("/match")
    public boolean match(
            @Parameter(description = "?‰ë¬¸ ë¹„ë?ë²ˆí˜¸") @RequestParam String raw,
            @Parameter(description = "DB???€?¥ëœ ?´ì‹œê°?) @RequestParam String encoded) {
        return passwordGenerator.matches(raw, encoded);
    }
}
