package com.lucky.luckyproject.controller;

import com.lucky.luckyproject.dto.EmailRequestDto;
import com.lucky.luckyproject.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Email API", description = "ë©”ì¼ ë°œì†¡ ê´€??API") // API ê·¸ë£¹??
@RestController
@RequiredArgsConstructor // Lombok?¼ë¡œ ?˜ì¡´??ì£¼ì… ( ?´ê²Œ ìµœì‹  ?¸ë Œ?¸ì•¼, ?œëƒ?˜ë©´ ?ŒìŠ¤ ê°„ê²°?? ?˜ì?ë§?ë©”ì„œ??ë§Œë“¤?´ì„œ ì£¼ì…?´ë„ ë¬´ê???)
@RequestMapping("/api/mail")
public class EmailController {

    private final EmailService emailService;

//    public EmailController(EmailService emailService) {
//        this.emailService = emailService;
//    }

    @Operation(
            summary = "?¨ìˆœ ?ìŠ¤???´ë©”??ë°œì†¡",
            description = "?˜ì‹ ?ì—ê²??œëª©ê³?ë³¸ë¬¸???¬í•¨??ë©”ì¼??ë°œì†¡?©ë‹ˆ??"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "ë°œì†¡ ?±ê³µ"),
            @ApiResponse(responseCode = "500", description = "ë©”ì¼ ?œë²„ ?¤ë¥˜ ?ëŠ” ?¤ì • ë¯¸ë¹„")
    })
    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequestDto requestDto) {
        emailService.sendSimpleEmail(requestDto.getTo(), requestDto.getSubject(), requestDto.getText());
        return "Email sent successfully";
    }
}

