package com.lucky.luckyproject.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class MemberLoginRequest {

    @NotBlank(message = "?´ë©”?¼ì? ?„ìˆ˜ ?…ë ¥ê°’ì…?ˆë‹¤.")
    @Email(message = "?¬ë°”ë¥??´ë©”???•ì‹???„ë‹™?ˆë‹¤.")
    private String email;

    @NotBlank(message = "ë¹„ë?ë²ˆí˜¸???„ìˆ˜ ?…ë ¥ê°’ì…?ˆë‹¤.")
    private String password;
}
