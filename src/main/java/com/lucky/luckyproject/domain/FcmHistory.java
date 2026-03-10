package com.lucky.luckyproject.domain;

import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FcmHistory {
    private Long id;
    private String targetToken;
    private String title;
    private String body;
    private String status;
    private String responseMsg;
    private LocalDateTime createdAt;
}
