package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.util.ApiResponse;
import com.concentrix.lgintegratedadmin.util.ApiUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.View;
import reactor.core.publisher.Mono;

import java.util.Map;

@Tag(name = "외부 연동 관리 API", description = "PG/카드사 등 대외 시스템 인터페이스 제어")
@RestController
@RequestMapping("/api/v1/external")
@RequiredArgsConstructor
@Slf4j
public class ExternalInterfaceController {

    private final ApiUtil apiUtil;
    private final View error;

    @Operation(
            summary = "외부 결제 상태 조회",
            description = "ApiUtil을 사용하여 외부 연동 서버로부터 결제 상태 정보를 동기식으로 조회합니다."
    )
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(
                    responseCode = "200",
                    description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class))
            ),
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "504", description = "외부 시스템 타임아웃")
    })
    @GetMapping("/payment-status/{transactionId}")
    public ApiResponse<Object> getExternalPaymentStatus(
            @Parameter(description = "거래 번호", example = "TX_20260102_001")
            @PathVariable String transactionId) {

        // 외부 시스템 URL (예시)
        String targetUrl = "api.external-pg.com" + transactionId;

        try {
            // ApiUtil의 GET 메서드 활용
            Object result = apiUtil.get(targetUrl, Object.class);
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("외부 시스템 통신 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Operation(
            summary = "외부 결제 승인 요청",
            description = "결제 데이터를 외부 시스템에 동기식으로 POST로 전송하고 결과를 반환받습니다."
    )
    @PostMapping("/approve")
    public ApiResponse<Map<String, Object>> requestApprove(
            @RequestBody Map<String, Object> paymentData) {

        String targetUrl = "api.external-pg.com";

        try {
            // ApiUtil의 POST 메서드 활용 (동기 방식)
            @SuppressWarnings("unchecked")
            Map<String, Object> response = apiUtil.post(targetUrl, paymentData, Map.class);

            return ApiResponse.success(response);
        } catch (Exception e) {
            // 타임아웃 및 통신 에러 처리
            return ApiResponse.error("결제 승인 요청 실패: " + e.getMessage());
        }
    }

    @Operation(
        summary = "외부 결제 승인 요청",
        description = "결제 데이터를 외부 시스템에 비동기식으로 POST로 전송하고 결과를 반환받습니다."
    )
    @PostMapping("/approveAsync")
    public Mono<ApiResponse<Map<String, Object>>> requestApproveAsync(@RequestBody Map<String, Object> paymentData) {
        String targetUrl = "api.external-pg.com"; // URL 프로토콜 명시 권장

        return apiUtil.postAsync(targetUrl, paymentData, Map.class)
            .map(res -> {
                log.info("비동기 결과값: {}", res);
                return ApiResponse.success((Map<String, Object>) res);
            })
            .onErrorResume(e -> {
                log.error("에러 발생: {}", e.getMessage());
                return Mono.just(ApiResponse.error("결제 승인 요청 실패: " + e.getMessage()));
            });
    }
    @Operation(
        summary = "외부 결제 승인 요청",
        description = "결제 데이터를 외부 시스템에 동기식으로 POST로 전송하고 결과를 반환받습니다."
    )
    @PostMapping("/approveAsync1")
    public ApiResponse<Map<String, Object>> requestApproveAsync1(
            @RequestBody Map<String, Object> paymentData) {

        String targetUrl = "api.external-pg.com";

        try {
            // subscribe 대신 block()을 사용하여 응답이 올 때까지 기다림
            @SuppressWarnings("unchecked")
            Map<String, Object> response = (Map<String, Object>) apiUtil.postAsync(targetUrl, paymentData, Map.class)
                    .block(); // 비동기 함수를 호출하되, 여기서 결과가 나올 때까지 대기

            log.info("결과값 수신 완료: {}", response);
            return ApiResponse.success(response);
        } catch (Exception e) {
            log.error("통신 에러: {}", e.getMessage());
            return ApiResponse.error("결제 승인 요청 실패: " + e.getMessage());
        }
    }
}
