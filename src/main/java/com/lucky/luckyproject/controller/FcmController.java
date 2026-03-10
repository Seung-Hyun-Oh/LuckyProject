package com.lucky.luckyproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FCM API", description = "푸시 알림 전송 관련 API")
@RestController
@RequestMapping("/api/v1/fcm")
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @Operation(summary = "단일 기기 푸시 전송", description = "특정 기기의 FCM 토큰을 사용하여 알림을 전송합니다.")
    @PostMapping("/send")
    public ResponseEntity<String> pushMessage(@RequestBody FcmRequestDto requestDto) {
        try {
            String response = fcmService.sendNotification(requestDto);
            return ResponseEntity.ok("성공적으로 전송되었습니다: " + response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("전송 실패: " + e.getMessage());
        }
    }
}
