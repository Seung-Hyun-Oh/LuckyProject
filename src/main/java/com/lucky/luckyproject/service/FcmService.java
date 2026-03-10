package com.lucky.luckyproject.service;

import com.lucky.luckyproject.dto.FcmRequestDto;
import com.google.firebase.messaging.*;
import org.springframework.stereotype.Service;

@Service
public class FcmService {

    public String sendNotification(FcmRequestDto requestDto) throws FirebaseMessagingException {
        Notification notification = Notification.builder()
                .setTitle(requestDto.getTitle())
                .setBody(requestDto.getBody())
                .build();

        Message message = Message.builder()
                .setToken(requestDto.getTargetToken())
                .setNotification(notification)
                .build();

        return FirebaseMessaging.getInstance().send(message);
    }
}
