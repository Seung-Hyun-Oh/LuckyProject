package com.lucky.luckyproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * 이메일 발송을 위한 구성을 담당하는 설정 클래스입니다.
 * application.yml에 정의된 속성값을 읽어 JavaMailSender 빈을 수동으로 생성합니다.
 */
@Configuration
public class MailConfig {

    // @Value 어노테이션을 통해 application.yml에 설정된 값을 필드에 주입받습니다.
    @Value("${spring.mail.host}")
    private String host; // SMTP 서버 호스트 주소 (예: smtp.gmail.com)

    @Value("${spring.mail.port}")
    private int port; // SMTP 서버 포트 번호 (예: 587)

    @Value("${spring.mail.username}")
    private String username; // 발신자 메일 계정 아이디

    @Value("${spring.mail.password}")
    private String password; // 발신자 메일 계정 비밀번호 또는 앱 비밀번호

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth; // SMTP 인증 사용 여부

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable; // STARTTLS 암호화 연결 사용 여부

    /**
     * JavaMailSender 인터페이스를 구현한 JavaMailSenderImpl 객체를 스프링 빈으로 등록합니다.
     * 이제 서비스 계층에서 @Autowired 또는 생성자 주입을 통해 이 객체를 사용할 수 있습니다.
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // 1. 기본 연결 설정
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // 2. 추가적인 세부 SMTP 속성 설정
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp"); // 전송 프로토콜 설정
        props.put("mail.smtp.auth", String.valueOf(auth)); // SMTP 인증 여부 설정
        props.put("mail.smtp.starttls.enable", String.valueOf(starttlsEnable)); // TLS 보안 설정

        // 메일 발송 시 콘솔에서 상세 로그를 확인하고 싶을 때 true로 설정합니다.
        // 개발 환경(Dev)에서는 true, 운영 환경(Prod)에서는 false를 권장합니다.
        props.put("mail.debug", "true");

        return mailSender;
    }
}