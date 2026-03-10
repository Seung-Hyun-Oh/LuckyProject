package com.lucky.luckyproject.exception;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 전역 예외 처리기
 *
 * <p>애플리케이션 전역에서 발생하는 예외를 가로채어 공통 응답 규격({@link com.concentrix.lgintegratedadmin.util.ApiResponse})으로 변환함.</p>
 *
 * @author 2025년형 AI 가이드
 * @version 1.0
 */
@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * 비즈니스 로직 예외 처리
     *
     * @param e 비즈니스 예외 객체
     * @return 200 OK와 함께 에러 메시지를 담은 ApiResponse
     */
    @ExceptionHandler(BusinessException.class)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "비즈니스 로직 에러 (성공 응답 내 에러 메시지)",
            content = @Content(schema = @Schema(implementation = com.concentrix.lgintegratedadmin.util.ApiResponse.class)))
    })
    public ResponseEntity<com.concentrix.lgintegratedadmin.util.ApiResponse<Void>> handleBusinessException(BusinessException e) {
        log.warn("[Business Error] Code: {}, Message: {}", e.getErrorCode(), e.getMessage());
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(com.concentrix.lgintegratedadmin.util.ApiResponse.error(e.getMessage()));
    }

    /**
     * Bean Validation 유효성 검사 실패 예외 처리
     *
     * @param e 유효성 검사 예외 객체
     * @return 400 Bad Request와 첫 번째 검사 실패 메시지
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "400", description = "잘못된 요청 파라미터",
            content = @Content(schema = @Schema(implementation = com.concentrix.lgintegratedadmin.util.ApiResponse.class)))
    })
    public ResponseEntity<com.concentrix.lgintegratedadmin.util.ApiResponse<Void>> handleValidationException(MethodArgumentNotValidException e) {
        String msg = e.getBindingResult().getAllErrors().get(0).getDefaultMessage();
        log.warn("[Validation Error] {}", msg);
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(com.concentrix.lgintegratedadmin.util.ApiResponse.error(msg));
    }

    /**
     * 시스템 런타임 예외 및 기타 예외 처리
     *
     * @param e 시스템 예외 객체
     * @return 500 Internal Server Error와 표준 안내 메시지
     */
    @ExceptionHandler(Exception.class)
    @ApiResponses(value = {
        @ApiResponse(responseCode = "500", description = "서버 내부 시스템 오류",
            content = @Content(schema = @Schema(implementation = com.concentrix.lgintegratedadmin.util.ApiResponse.class)))
    })
    public ResponseEntity<com.concentrix.lgintegratedadmin.util.ApiResponse<Void>> handleException(Exception e) {
        log.error("[System Error] ", e);
        return ResponseEntity
            .status(HttpStatus.INTERNAL_SERVER_ERROR)
            .body(com.concentrix.lgintegratedadmin.util.ApiResponse.error("시스템 내부 오류가 발생했습니다. 관리자에게 문의하세요."));
    }
}