package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.dto.PriceCalcRequest;
import com.concentrix.lgintegratedadmin.util.ApiResponse;
import com.concentrix.lgintegratedadmin.util.RestTemplateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Tag(name = "가격 계산 관리 API", description = "RestTemplate을 이용한 외부 가격/할인 시스템 연동")
@RestController
@RequestMapping("/api/v1/calc")
@RequiredArgsConstructor
public class PriceCalculationController {

    private final RestTemplateUtil restTemplateUtil;

    @Operation(summary = "최종 결제 금액 계산", description = "상품 정보와 쿠폰 정보를 외부 가격 시스템에 전송하여 할인된 최종 금액을 산출합니다.")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "계산 성공",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @PostMapping("/calculate")
    public ApiResponse<Object> calculateFinalPrice(@RequestBody PriceCalcRequest request) {

        // 1. 외부 가격 산정 API URL (가상)
        String externalUrl = "internal-api.concentrix.com";

        try {
            // 2. RestTemplateUtil.post를 사용하여 외부 시스템 호출
            Object result = restTemplateUtil.post(externalUrl, request, Object.class);

            // 3. 공통 규격으로 감싸서 반환
            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("가격 계산 시스템 연동 중 오류가 발생했습니다: " + e.getMessage());
        }
    }

    @Operation(summary = "국가별 환율 적용 가격 조회", description = "특수 헤더를 사용하여 외부 환율 시스템으로부터 변환된 가격을 가져옵니다.")
    @GetMapping("/exchange-rate")
    public ApiResponse<Map<String, Object>> getExchangePrice(
            @Parameter(description = "금액", example = "50000") @RequestParam Long amount,
            @Parameter(description = "통화코드", example = "USD") @RequestParam String currency) {

        String url = "api.external-ex.com" + amount + "&to=" + currency;

        // 커스텀 헤더 설정 (API Key 등)
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-LG-SYSTEM-ID", "LG-INT-ADMIN-01");
        headers.set("Authorization", "Bearer ADMIN_ACCESS_TOKEN");
        try {
            // RestTemplateUtil.exchangeWithHeaders 사용
            @SuppressWarnings("unchecked")
            Map<String, Object> result = restTemplateUtil.exchangeWithHeaders(url, HttpMethod.GET, headers, null, Map.class);

            return ApiResponse.success(result);
        } catch (Exception e) {
            return ApiResponse.error("환율 변환 시스템 호출 실패: " + e.getMessage());
        }
    }

    @Operation(summary = "임시 가격 정책 삭제", description = "등록된 특정 프로모션 가격 정책을 삭제합니다.")
    @DeleteMapping("/policy/{policyId}")
    public ApiResponse<String> deletePricePolicy(
            @Parameter(description = "정책 ID", example = "POL_2026_99") @PathVariable String policyId) {

        String url = "internal-api.concentrix.com" + policyId;

        try {
            // RestTemplateUtil.delete 사용
            restTemplateUtil.delete(url);
            return ApiResponse.success("정책이 성공적으로 삭제되었습니다.");
        } catch (Exception e) {
            return ApiResponse.error("정책 삭제 실패: " + e.getMessage());
        }
    }
}
