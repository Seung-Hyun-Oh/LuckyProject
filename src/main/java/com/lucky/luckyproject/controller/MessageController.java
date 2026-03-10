package com.lucky.luckyproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Message API", description = "다국어 메시지 테스트를 위한 API")
@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageSource messageSource;

    public MessageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Operation(summary = "환영 메시지 조회", description = "헤더의 Accept-Language 값에 따라 다국어 환영 인사를 반환합니다.")
    @GetMapping(value="/welcome")
    public String getWelcome(@Parameter(hidden = true) // Locale은 스프링이 자동 주입하므로 문서에서 파라미터로 숨김
            java.util.Locale locale) {
        System.out.println("Locale: " + locale + " :: " + LocaleContextHolder.getLocale());
        return messageSource.getMessage("welcome.message", null, locale);
    }

    @Operation(summary = "사용자별 인사말", description = "이름을 파라미터로 받아 다국어 인사말을 반환합니다.")
    @GetMapping("/userGreet/{name}")
    public String getUser(
            @Parameter(description = "사용자 이름", example = "Alice")
            @PathVariable String name) {
        return messageSource.getMessage("user.name", new Object[]{name}, LocaleContextHolder.getLocale());
    }
}
