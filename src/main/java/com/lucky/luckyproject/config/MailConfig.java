package com.lucky.luckyproject.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

/**
 * ?´ë©”??ë°œì†¡???„í•œ êµ¬ì„±???´ë‹¹?˜ëŠ” ?¤ì • ?´ë˜?¤ì…?ˆë‹¤.
 * application.yml???•ì˜???ì„±ê°’ì„ ?½ì–´ JavaMailSender ë¹ˆì„ ?˜ë™?¼ë¡œ ?ì„±?©ë‹ˆ??
 */
@Configuration
public class MailConfig {

    // @Value ?´ë…¸?Œì´?˜ì„ ?µí•´ application.yml???¤ì •??ê°’ì„ ?„ë“œ??ì£¼ì…ë°›ìŠµ?ˆë‹¤.
    @Value("${spring.mail.host}")
    private String host; // SMTP ?œë²„ ?¸ìŠ¤??ì£¼ì†Œ (?? smtp.gmail.com)

    @Value("${spring.mail.port}")
    private int port; // SMTP ?œë²„ ?¬íŠ¸ ë²ˆí˜¸ (?? 587)

    @Value("${spring.mail.username}")
    private String username; // ë°œì‹ ??ë©”ì¼ ê³„ì • ?„ì´??

    @Value("${spring.mail.password}")
    private String password; // ë°œì‹ ??ë©”ì¼ ê³„ì • ë¹„ë?ë²ˆí˜¸ ?ëŠ” ??ë¹„ë?ë²ˆí˜¸

    @Value("${spring.mail.properties.mail.smtp.auth}")
    private boolean auth; // SMTP ?¸ì¦ ?¬ìš© ?¬ë?

    @Value("${spring.mail.properties.mail.smtp.starttls.enable}")
    private boolean starttlsEnable; // STARTTLS ?”í˜¸???°ê²° ?¬ìš© ?¬ë?

    /**
     * JavaMailSender ?¸í„°?˜ì´?¤ë? êµ¬í˜„??JavaMailSenderImpl ê°ì²´ë¥??¤í”„ë§?ë¹ˆìœ¼ë¡??±ë¡?©ë‹ˆ??
     * ?´ì œ ?œë¹„??ê³„ì¸µ?ì„œ @Autowired ?ëŠ” ?ì„±??ì£¼ì…???µí•´ ??ê°ì²´ë¥??¬ìš©?????ˆìŠµ?ˆë‹¤.
     */
    @Bean
    public JavaMailSender javaMailSender() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();

        // 1. ê¸°ë³¸ ?°ê²° ?¤ì •
        mailSender.setHost(host);
        mailSender.setPort(port);
        mailSender.setUsername(username);
        mailSender.setPassword(password);

        // 2. ì¶”ê??ì¸ ?¸ë? SMTP ?ì„± ?¤ì •
        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.transport.protocol", "smtp"); // ?„ì†¡ ?„ë¡œ? ì½œ ?¤ì •
        props.put("mail.smtp.auth", String.valueOf(auth)); // SMTP ?¸ì¦ ?¬ë? ?¤ì •
        props.put("mail.smtp.starttls.enable", String.valueOf(starttlsEnable)); // TLS ë³´ì•ˆ ?¤ì •

        // ë©”ì¼ ë°œì†¡ ??ì½˜ì†”?ì„œ ?ì„¸ ë¡œê·¸ë¥??•ì¸?˜ê³  ?¶ì„ ??trueë¡??¤ì •?©ë‹ˆ??
        // ê°œë°œ ?˜ê²½(Dev)?ì„œ??true, ?´ì˜ ?˜ê²½(Prod)?ì„œ??falseë¥?ê¶Œì¥?©ë‹ˆ??
        props.put("mail.debug", "true");

        return mailSender;
    }
}
