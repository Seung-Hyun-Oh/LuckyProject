package com.lucky.luckyproject.controller;

import com.lucky.luckyproject.util.ApiResponse;
import com.lucky.luckyproject.util.RestClientUtil;
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

@Tag(name = "ê²°ì œ ?°ë™ API (RestClient)", description = "Spring 6.1 RestClient ê¸°ë°˜ ?€???œìŠ¤???°ë™")
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentInterfaceController {

    private final RestClientUtil restClientUtil;

    @Operation(summary = "?¸ë? ê²°ì œ ?•ë³´ ì¡°íšŒ", description = "Query ?Œë¼ë¯¸í„°ë¥??¬ìš©?˜ì—¬ ?¸ë? ?œìŠ¤?œì˜ ê²°ì œ ?•ë³´ë¥?ì¡°íšŒ?©ë‹ˆ??")
    @ApiResponses(value = {
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "200", description = "ì¡°íšŒ ?±ê³µ",
                    content = @Content(schema = @Schema(implementation = ApiResponse.class)))
    })
    @GetMapping("/info")
    public ApiResponse<Object> getPaymentInfo(
            @Parameter(description = "ì£¼ë¬¸ ë²ˆí˜¸", example = "ORD-2026-0001") @RequestParam String orderId,
            @Parameter(description = "ê°€ë§¹ì  ì½”ë“œ", example = "CONCENTRIX_01") @RequestParam String mallId) {

        String targetUrl = "api.external-pg.com";

        // Query ?Œë¼ë¯¸í„° êµ¬ì„±
        Map<String, String> queryParams = new HashMap<>();
        queryParams.put("orderId", orderId);
        queryParams.put("mallId", mallId);

        // RestClientUtil ?¬ìš©
        Object result = restClientUtil.get(targetUrl, queryParams, Object.class);

        return ApiResponse.success(result);
    }

    @Operation(summary = "ë³´ì•ˆ ê²°ì œ ?¹ì¸ ?”ì²­", description = "API Key ?¤ë”?€ JSON ë°”ë””ë¥??¬í•¨?˜ì—¬ ?¸ë? ?œìŠ¤?œì— ?¹ì¸???”ì²­?©ë‹ˆ??")
    @PostMapping("/secure-approve")
    public ApiResponse<Map<String, Object>> requestSecureApprove(
            @RequestBody Map<String, Object> paymentPayload) {

        String targetUrl = "api.external-pg.com";

        // ?¸ì¦ ?¤ë” êµ¬ì„±
        Map<String, String> headers = new HashMap<>();
        headers.put("X-API-KEY", "SECRET_KEY_2026");
        headers.put("Authorization", "Bearer TOKEN_STRING");

        // RestClientUtil??postWithHeaders ?¬ìš©
        @SuppressWarnings("unchecked")
        Map<String, Object> response = restClientUtil.postWithHeaders(targetUrl, headers, paymentPayload, Map.class);

        return ApiResponse.success(response);
    }

    @Operation(summary = "ê²°ì œ ?˜ë‹¨ ?˜ì •", description = "PUT ë©”ì„œ?œë? ?¸ì¶œ?˜ì—¬ ?±ë¡??ê²°ì œ ?˜ë‹¨ ?•ë³´ë¥?ë³€ê²½í•©?ˆë‹¤.")
    @PutMapping("/method")
    public ApiResponse<Object> updatePaymentMethod(@RequestBody Map<String, Object> updateData) {

        String targetUrl = "api.external-pg.com";
        Object result = restClientUtil.put(targetUrl, updateData, Object.class);

        return ApiResponse.success(result);
    }

    @Operation(summary = "ê²°ì œ ì·¨ì†Œ ?”ì²­", description = "DELETE ë©”ì„œ?œë? ?¬ìš©?˜ì—¬ ?¸ë? ?œìŠ¤?œì— ê²°ì œ ì·¨ì†Œë¥??”ì²­?©ë‹ˆ??")
    @DeleteMapping("/cancel/{txId}")
    public ApiResponse<String> cancelPayment(
            @Parameter(description = "ê±°ë˜ ê³ ìœ  ë²ˆí˜¸", example = "TX_99999") @PathVariable String txId) {

        String targetUrl = "api.external-pg.com" + txId;

        // RestClientUtil??delete ?¬ìš©
        restClientUtil.delete(targetUrl);

        return ApiResponse.success("ì·¨ì†Œ ?”ì²­???‘ìˆ˜?˜ì—ˆ?µë‹ˆ??");
    }
}
