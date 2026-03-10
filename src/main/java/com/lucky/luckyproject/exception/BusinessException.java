package com.lucky.luckyproject.exception;

import lombok.Getter;

/**
 * 서비스 비즈니스 로직 수행 중 발생하는 사용자 정의 예외 클래스
 */
@Getter
public class BusinessException extends RuntimeException {

    /** 에러 식별 코드 */
    private final String errorCode;

    /**
     * 메시지만 전달하는 생성자
     * @param message 에러 메시지
     */
    public BusinessException(String message) {
        super(message);
        this.errorCode = "BUSINESS_ERROR";
    }

    /**
     * 에러 코드와 메시지를 함께 전달하는 생성자
     * @param errorCode 특정 에러 코드
     * @param message 에러 메시지
     */
    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }
}