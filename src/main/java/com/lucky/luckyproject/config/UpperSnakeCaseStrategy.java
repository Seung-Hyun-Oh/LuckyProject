package com.lucky.luckyproject.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class UpperSnakeCaseStrategy extends PropertyNamingStrategies.SnakeCaseStrategy {
    @Override
    public String translate(String input) {
        if (input == null) return input;
        // 기본 snake_case로 변환 후 대문자로 변경
        return super.translate(input).toUpperCase();
    }
}
