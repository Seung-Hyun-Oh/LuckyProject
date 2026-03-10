package com.lucky.luckyproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MemberLoginResponse {
    private String token;
    private String email;
    private String name;
}
