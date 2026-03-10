package com.lucky.luckyproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "FCM ?Œë¦¼ ?„ì†¡ ?”ì²­ ê°ì²´")
public class FcmRequestDto {
    @Schema(description = "?€??ê¸°ê¸° ? í°", example = "fcm_token_here")
    private String targetToken;

    @Schema(description = "?Œë¦¼ ?œëª©", example = "?ˆë…•?˜ì„¸??")
    private String title;

    @Schema(description = "?Œë¦¼ ?´ìš©", example = "Spring Boot?ì„œ ë³´ë‚¸ ?¸ì‹œ?…ë‹ˆ??")
    private String body;
}
