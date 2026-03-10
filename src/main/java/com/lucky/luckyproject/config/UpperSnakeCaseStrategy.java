package com.lucky.luckyproject.config;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;

public class UpperSnakeCaseStrategy extends PropertyNamingStrategies.SnakeCaseStrategy {
    @Override
    public String translate(String input) {
        if (input == null) return input;
        // ê¸°ë³¸ snake_caseë¡?ë³€?????€ë¬¸ìë¡?ë³€ê²?
        return super.translate(input).toUpperCase();
    }
}
