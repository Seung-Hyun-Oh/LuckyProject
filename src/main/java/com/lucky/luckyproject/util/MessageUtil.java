package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

import java.util.Locale;

/**
 * 시스템 내 다국어 메시지(i18n) 처리를 위한 공통 유틸리티입니다.
 * Spring의 MessageSource를 활용하여 브라우저 언어 설정 또는 서버 설정 Locale에 맞는 문구를 반환합니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Tag(name = "Message Utility", description = "다국어(i18n) 메시지 조회 도구")
@Component
@RequiredArgsConstructor
public class MessageUtil {

    private final MessageSource messageSource;

    /**
     * 메시지 코드에 해당하는 문구를 현재 Locale 기준으로 반환합니다.
     *
     * @param code 메시지 코드 (예: common.save.success)
     * @return 해당 언어의 메시지 (코드 미존재 시 코드 자체 반환)
     */
    @Operation(summary = "다국어 메시지 조회", description = "코드에 해당하는 메시지를 현재 접속자의 Locale에 맞춰 반환합니다.")
    public String getMessage(@Parameter(description = "메시지 코드") String code) {
        return getMessage(code, null, "");
    }

    /**
     * 파라미터가 포함된 메시지를 반환합니다. (가변 인자 지원)
     * <pre>
     * 예: "login.welcome" = "{0}님 환영합니다."
     * 사용: getMessage("login.welcome", "홍길동") -> "홍길동님 환영합니다."
     * </pre>
     *
     * @param code 메시지 코드
     * @param args 메시지에 삽입될 파라미터 (가변 인자)
     * @return 포맷팅된 메시지
     */
    @Operation(summary = "파라미터 포함 메시지 조회", description = "중괄호 {0}, {1} 등이 포함된 메시지에 값을 채워 반환합니다.")
    public String getMessage(String code, Object... args) {
        return getMessage(code, args, "");
    }

    /**
     * 메시지가 없을 경우 반환할 기본값을 지정하여 메시지를 조회합니다.
     *
     * @param code           메시지 코드
     * @param args           치환 파라미터
     * @param defaultMessage 기본값
     * @return 최종 메시지
     */
    @Operation(summary = "메시지 조회 (기본값 지정)", description = "메시지 코드가 없을 경우 지정한 기본 문구를 반환합니다.")
    public String getMessage(String code, Object[] args, String defaultMessage) {
        try {
            return messageSource.getMessage(code, args, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return defaultMessage.isEmpty() ? code : defaultMessage;
        }
    }

    /**
     * 특정 Locale을 직접 지정하여 메시지를 조회합니다. (배치 업무 또는 특정 국가 강제 시 사용)
     *
     * @param code   메시지 코드
     * @param locale 강제 지정할 Locale
     * @return 해당 언어의 메시지
     */
    @Operation(summary = "특정 Locale 메시지 조회", description = "접속자 환경과 무관하게 특정 국가 언어로 메시지를 조회합니다.")
    public String getMessageForLocale(String code, Locale locale) {
        return messageSource.getMessage(code, null, locale);
    }
}
