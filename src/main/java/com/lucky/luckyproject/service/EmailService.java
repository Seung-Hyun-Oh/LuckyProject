package com.lucky.luckyproject.service;

import com.concentrix.lgintegratedadmin.dto.EmailRequestDto;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * AWS SES를 이용한 이메일 발송 서비스
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class EmailService {

    private final JavaMailSender javaMailSender;

    /**
     * [텍스트 메일 발송] - DTO 기반
     * @param requestDto 수신자, 제목, 내용이 포함된 객체
     * @throws RuntimeException 메일 발송 실패 시 발생
     */
    @Schema(description = "DTO 기반 단순 텍스트 메일 발송 로직")
    public void sendSimpleEmail(@RequestBody EmailRequestDto requestDto) {
        SimpleMailMessage message = new SimpleMailMessage();
        String to = requestDto.getTo();
        String subject = requestDto.getSubject();
        String content = requestDto.getText();

        message.setFrom("verified-sender@example.com"); // SES에서 인증된 발신자 주소
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            javaMailSender.send(message);
            log.info("SES 텍스트 메일 발송 완료: {}", to);
        } catch (Exception e) {
            log.error("SES 텍스트 메일 발송 실패: {}", e.getMessage());
            throw new RuntimeException("이메일 발송 중 오류 발생");
        }
    }

    /**
     * [텍스트 메일 발송] - 파라미터 기반
     * @param to 수신자 이메일
     * @param subject 메일 제목
     * @param content 메일 내용
     */
    @Schema(description = "개별 파라미터 기반 단순 텍스트 메일 발송 로직")
    public void sendSimpleEmail(String to, String subject, String content) {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("verified-sender@example.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(content);

        try {
            javaMailSender.send(message);
            log.info("SES 텍스트 메일 발송 완료: {}", to);
        } catch (Exception e) {
            log.error("SES 텍스트 메일 발송 실패: {}", e.getMessage());
            throw new RuntimeException("이메일 발송 중 오류 발생");
        }
    }

    /**
     * [HTML 템플릿 메일 발송] - 비동기(Async) 처리
     * @param to 수신자 이메일 주소
     * @param subject 메일 제목
     * @param htmlBody 메일 본문 (HTML 태그 포함 가능)
     */
    @Async
    @Schema(description = "비동기 방식의 HTML 이메일 발송")
    public void sendHtmlEmail(String to, String subject, String htmlBody) {
        MimeMessage message = javaMailSender.createMimeMessage();

        try {
            // MimeMessageHelper를 사용하여 HTML 및 멀티파트 메시지 구성
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom("verified-sender@example.com");
            helper.setTo(to);
            helper.setSubject(subject);
            helper.setText(htmlBody, true); // 두 번째 인자를 true로 설정해야 HTML로 렌더링됨

            javaMailSender.send(message);
            log.info("SES HTML 메일 발송 완료: {}", to);
        } catch (MessagingException e) {
            log.error("SES HTML 메일 발송 실패: {}", e.getMessage());
            // 비동기 메서드이므로 예외를 던지기보다 로그를 남기는 것이 일반적
        }
    }
}