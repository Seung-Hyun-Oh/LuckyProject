package com.lucky.luckyproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(description = "?´ë©”??ë°œì†¡ ?”ì²­ ?•ë³´") // ?´ë˜???¤ëª…
public class EmailRequestDto {

    @Schema(description = "?˜ì‹ ???´ë©”??ì£¼ì†Œ", example = "user@example.com")
    private String to;

    @Schema(description = "ë©”ì¼ ?œëª©", example = "?ˆë…•?˜ì„¸?? ?¸ì¦ ì½”ë“œ?…ë‹ˆ??")
    private String subject;

    @Schema(description = "ë©”ì¼ ?´ìš©", example = "?¸ì¦ ë²ˆí˜¸??[123456] ?…ë‹ˆ??")
    private String text;
}
