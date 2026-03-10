package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

/**
 * 전사 공통 API 응답 규격 클래스
 *
 * <p>모든 REST API 응답은 이 클래스를 래퍼(Wrapper)로 사용하여 형식을 통일함.</p>
 * <p>EP 통합결제 시스템의 프론트엔드 및 대외 시스템 연동 시 표준 규격으로 활용됨.</p>
 *
 * @param <T> 응답 데이터의 타입
 * @since 1.8
 * @version 1.1 (2025.12.18 Swagger Schema 적용)
 */
@Getter
@Builder
@Schema(description = "공통 API 응답 래퍼")
public class ApiResponse<T> {

    /**
     * 응답 상태 코드 (예: SUCCESS, ERROR, AUTH_FAIL)
     */
    @Schema(description = "응답 상태", example = "SUCCESS")
    private String status;

    /**
     * 응답 메시지 (사용자 알림용 또는 에러 상세 내용)
     */
    @Schema(description = "응답 메시지", example = "요청이 정상적으로 처리되었습니다.")
    private String message;

    /**
     * 실제 반환될 데이터 (성공 시 객체 담기, 실패 시 null)
     */
    @Schema(description = "응답 데이터 객체")
    private T data;

    /**
     * 성공 응답 생성 (데이터 포함)
     *
     * @param <T> 응답 데이터 타입
     * @param data 결과 데이터
     * @return 성공 규격의 ApiResponse 객체
     */
    public static <T> ApiResponse<T> success(T data) {
        return ApiResponse.<T>builder()
            .status("SUCCESS")
            .message("요청이 정상적으로 처리되었습니다.")
            .data(data)
            .build();
    }

    /**
     * 에러 응답 생성
     *
     * @param <T> 응답 데이터 타입
     * @param message 에러 메시지
     * @return 에러 규격의 ApiResponse 객체
     */
    public static <T> ApiResponse<T> error(String message) {
        return ApiResponse.<T>builder()
            .status("ERROR")
            .message(message)
            .data(null)
            .build();
    }
}
