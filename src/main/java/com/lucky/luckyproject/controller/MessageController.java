package com.lucky.luckyproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Message API", description = "?¤êµ­??ë©”ì‹œì§€ ?ŒìŠ¤?¸ë? ?„í•œ API")
@RestController
@RequestMapping("/api")
public class MessageController {

    private final MessageSource messageSource;

    public MessageController(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Operation(summary = "?˜ì˜ ë©”ì‹œì§€ ì¡°íšŒ", description = "?¤ë”??Accept-Language ê°’ì— ?°ë¼ ?¤êµ­???˜ì˜ ?¸ì‚¬ë¥?ë°˜í™˜?©ë‹ˆ??")
    @GetMapping(value="/welcome")
    public String getWelcome(@Parameter(hidden = true) // Locale?€ ?¤í”„ë§ì´ ?ë™ ì£¼ì…?˜ë?ë¡?ë¬¸ì„œ?ì„œ ?Œë¼ë¯¸í„°ë¡??¨ê?
            java.util.Locale locale) {
        System.out.println("Locale: " + locale + " :: " + LocaleContextHolder.getLocale());
        return messageSource.getMessage("welcome.message", null, locale);
    }

    @Operation(summary = "?¬ìš©?ë³„ ?¸ì‚¬ë§?, description = "?´ë¦„???Œë¼ë¯¸í„°ë¡?ë°›ì•„ ?¤êµ­???¸ì‚¬ë§ì„ ë°˜í™˜?©ë‹ˆ??")
    @GetMapping("/userGreet/{name}")
    public String getUser(
            @Parameter(description = "?¬ìš©???´ë¦„", example = "Alice")
            @PathVariable String name) {
        return messageSource.getMessage("user.name", new Object[]{name}, LocaleContextHolder.getLocale());
    }
}
