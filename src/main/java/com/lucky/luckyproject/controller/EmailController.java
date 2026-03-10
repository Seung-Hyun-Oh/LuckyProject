package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.dto.EmailRequestDto;
import com.concentrix.lgintegratedadmin.service.EmailService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Email API", description = "메일 발송 관련 API") // API 그룹화
@RestController
@RequiredArgsConstructor // Lombok으로 의존성 주입 ( 이게 최신 트렌트야, 왜냐하면 소스 간결화, 하지만 메서드 만들어서 주입해도 무관해 )
@RequestMapping("/api/mail")
public class EmailController {

    private final EmailService emailService;

//    public EmailController(EmailService emailService) {
//        this.emailService = emailService;
//    }

    @Operation(
            summary = "단순 텍스트 이메일 발송",
            description = "수신자에게 제목과 본문을 포함한 메일을 발송합니다."
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "발송 성공"),
            @ApiResponse(responseCode = "500", description = "메일 서버 오류 또는 설정 미비")
    })
    @PostMapping("/send")
    public String sendEmail(@RequestBody EmailRequestDto requestDto) {
        emailService.sendSimpleEmail(requestDto.getTo(), requestDto.getSubject(), requestDto.getText());
        return "Email sent successfully";
    }
}

