package com.lucky.luckyproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "이메일 발송 요청 정보") // 클래스 설명
public class EmailRequestDto {

    @Schema(description = "수신자 이메일 주소", example = "user@example.com")
    private String to;

    @Schema(description = "메일 제목", example = "안녕하세요. 인증 코드입니다.")
    private String subject;

    @Schema(description = "메일 내용", example = "인증 번호는 [123456] 입니다.")
    private String text;
}