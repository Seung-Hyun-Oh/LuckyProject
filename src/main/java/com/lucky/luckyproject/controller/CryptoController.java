package com.lucky.luckyproject.controller;

import com.lucky.luckyproject.service.EncryptionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.*;

/**
 * [?´ì˜ ì£¼ì˜] Jasypt ?„ë¡œ?¼í‹° ?”í˜¸???„êµ¬
 * ??ì»¨íŠ¸ë¡¤ëŸ¬??ë³´ì•ˆ??ë¡œì»¬ IP(localhost)?ì„œë§??¸ì¶œ ê°€?¥í•˜?„ë¡ SecurityConfig?ì„œ ?œí•œ??
 */
@Tag(name = "Jasypt ?”í˜¸??API", description = "?¤ì •ê°?yml) ?”ë³µ?¸í™” ?ŒìŠ¤?¸ë? ?„í•œ ê´€ë¦¬ì ?„êµ¬")
@RestController
@RequestMapping("/api/crypto")
public class CryptoController {

    private final EncryptionService encryptionService;

    // ?ì„±??ì£¼ì…
    public CryptoController(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Operation(summary = "ë¬¸ì???”í˜¸??, description = "?‰ë¬¸??Jasypt ?¤ë¡œ ?”í˜¸?”í•˜??ENC(...) ?•ì‹?¼ë¡œ ë°˜í™˜?©ë‹ˆ??")
    @GetMapping("/encrypt")
    public String encrypt(
            @Parameter(description = "?”í˜¸?”í•  ?‰ë¬¸(DB ?¨ìŠ¤?Œë“œ ??", example = "myPassword123")
            @RequestParam String text) {
        String encrypted = encryptionService.encrypt(text);
        // application.yml??ë°”ë¡œ ë³µì‚¬?´ì„œ ?¬ìš©?????ˆë„ë¡?ENC() ?¬ë§·?¼ë¡œ ë°˜í™˜
        return "ENC(" + encrypted + ")";
    }

    @Operation(summary = "ë¬¸ì??ë³µí˜¸??, description = "?”í˜¸?”ëœ ë¬¸ì?´ì„ ?‰ë¬¸?¼ë¡œ ë³µí˜¸?”í•˜???•ì¸?©ë‹ˆ??")
    @GetMapping("/decrypt")
    public String decrypt(
            @Parameter(description = "ë³µí˜¸?”í•  ?”í˜¸ë¬?(ENC ?‘ë‘???œì™¸)", example = "vK7X9b/4S9f...")
            @RequestParam String encryptedText) {
        return encryptionService.decrypt(encryptedText);
    }
}
