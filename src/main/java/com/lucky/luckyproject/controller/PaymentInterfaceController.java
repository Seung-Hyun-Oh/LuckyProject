package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.util.ApiResponse;
import com.concentrix.lgintegratedadmin.util.RestClientUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@Tag(name = "결제 연동 API (RestClient)", description = "Spring 6.1 RestClient 기반 대외 시스템 연동")
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentInterfaceController {

    private final RestClientUtil restClientUtil;

    @Operation(summary = "외부 결제 정보 조회", description = "Query 파라미터를 사용하여 외부 시스템의 결제 정보를 조회합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "조회 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/info")
    public ApiResponse<Object> getPaymentInfo(
            @Parameter(description = "주문 번호", example = "ORD-2026-0001") @RequestParam String orderId,
            @Parameter(description = "가맹점 코드", example = "CONCENTRIX_01") @RequestParam String mallId) {

        String targetUrl = "api.external-pg.com";

        // Query 파라미터 구성
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("orderId", orderId);
        queryParams.put("mallId", mallId);

        // RestClientUtil 사용
        Object result = restClientUtil.get(targetUrl, queryParams, Object.class);

        return ApiResponse.success(result);
    }

    @Operation(summary = "보안 결제 승인 요청", description = "API Key 헤더와 JSON 바디를 포함하여 외부 시스템에 승인을 요청합니다.")
    @PostMapping("/secure-approve")
    public ApiResponse<Map<String, Object>> requestSecureApprove(
            @RequestBody Map<String, Object> paymentPayload) {

        String targetUrl = "api.external-pg.com";

        // 인증 헤더 구성
        Map<String, String> headers = new HashMap<>();
        headers.put("X-API-KEY", "SECRET_KEY_2026");
        headers.put("Authorization", "Bearer TOKEN_STRING");

        // RestClientUtil의 postWithHeaders 사용
        @SuppressWarnings("unchecked")
        Map<String, Object> response = restClientUtil.postWithHeaders(targetUrl, headers, paymentPayload, Map.class);

        return ApiResponse.success(response);
    }

    @Operation(summary = "결제 수단 수정", description = "PUT 메서드를 호출하여 등록된 결제 수단 정보를 변경합니다.")
    @PutMapping("/method")
    public ApiResponse<Object> updatePaymentMethod(@RequestBody Map<String, Object> updateData) {

        String targetUrl = "api.external-pg.com";
        Object result = restClientUtil.put(targetUrl, updateData, Object.class);

        return ApiResponse.success(result);
    }

    @Operation(summary = "결제 취소 요청", description = "DELETE 메서드를 사용하여 외부 시스템에 결제 취소를 요청합니다.")
    @DeleteMapping("/cancel/{txId}")
    public ApiResponse<String> cancelPayment(
            @Parameter(description = "거래 고유 번호", example = "TX_99999") @PathVariable String txId) {

        String targetUrl = "api.external-pg.com" + txId;

        // RestClientUtil의 delete 사용
        restClientUtil.delete(targetUrl);

        return ApiResponse.success("취소 요청이 접수되었습니다.");
    }
}
