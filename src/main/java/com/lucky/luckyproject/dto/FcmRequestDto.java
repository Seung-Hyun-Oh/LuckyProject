package com.lucky.luckyproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "FCM 알림 전송 요청 객체")
public class FcmRequestDto {
    @Schema(description = "대상 기기 토큰", example = "fcm_token_here")
    private String targetToken;

    @Schema(description = "알림 제목", example = "안녕하세요!")
    private String title;

    @Schema(description = "알림 내용", example = "Spring Boot에서 보낸 푸시입니다.")
    private String body;
}
