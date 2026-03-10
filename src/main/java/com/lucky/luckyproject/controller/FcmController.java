package com.lucky.luckyproject.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "FCM API", description = "?¸ì‹œ ?Œë¦¼ ?„ì†¡ ê´€??API")
@RestController
@RequestMapping("/api/v1/fcm")
@RequiredArgsConstructor
public class FcmController {

    private final FcmService fcmService;

    @Operation(summary = "?¨ì¼ ê¸°ê¸° ?¸ì‹œ ?„ì†¡", description = "?¹ì • ê¸°ê¸°??FCM ? í°???¬ìš©?˜ì—¬ ?Œë¦¼???„ì†¡?©ë‹ˆ??")
    @PostMapping("/send")
    public ResponseEntity<String> pushMessage(@RequestBody FcmRequestDto requestDto) {
        try {
            String response = fcmService.sendNotification(requestDto);
            return ResponseEntity.ok("?±ê³µ?ìœ¼ë¡??„ì†¡?˜ì—ˆ?µë‹ˆ?? " + response);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body("?„ì†¡ ?¤íŒ¨: " + e.getMessage());
        }
    }
}
