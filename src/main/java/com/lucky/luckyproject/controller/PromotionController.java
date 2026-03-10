package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.domain.MemberGrade;
import com.concentrix.lgintegratedadmin.domain.PromotionType;
import com.concentrix.lgintegratedadmin.util.PromotionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Promotion", description = "이커머스 프로모션 계산 API")
@RestController
@RequestMapping("/api/promotion")
public class PromotionController {

    @Operation(summary = "예상 할인 금액 계산", description = "상품 가격과 프로모션 정보를 기반으로 최종 할인액을 계산합니다.")
    @GetMapping("/calculate")
    public ResponseEntity<Map<String, Object>> getDiscount(
        @RequestParam BigDecimal price,
        @RequestParam double discountValue,
        @RequestParam PromotionType type,
        @RequestParam(required = false) BigDecimal maxLimit)
    {
        BigDecimal discount = PromotionUtil.calculateDiscountAmount(price, discountValue, type, maxLimit);
        BigDecimal finalPrice = price.subtract(discount);

        Map<String, Object> result = new HashMap<>();
        result.put("originalPrice", price);
        result.put("discountAmount", discount);
        result.put("finalPrice", finalPrice);
        result.put("appliedType", type);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "최종 혜택가 계산", description = "쿠폰, 첫 구매, 등급 할인을 모두 적용한 최종가를 계산합니다.")
    @PostMapping("/calculate-benefit")
    public ResponseEntity<Map<String, Object>> calculateAllBenefits(
        @RequestParam BigDecimal originalPrice,
        @RequestParam boolean isFirstPurchase,
        @RequestParam MemberGrade userGrade)
    {
        BigDecimal currentPrice = originalPrice;

        // 1. 첫 구매 할인 적용 (10%)
        BigDecimal firstDiscount = PromotionUtil.calculateFirstPurchaseDiscount(originalPrice, isFirstPurchase);
        currentPrice = currentPrice.subtract(firstDiscount);

        // 2. 등급 할인 적용 (첫 구매 할인된 금액 기준 혹은 원가 기준 - 정책에 따라 선택)
        BigDecimal gradeDiscount = PromotionUtil.calculateGradeDiscount(currentPrice, userGrade);
        currentPrice = currentPrice.subtract(gradeDiscount);

        Map<String, Object> response = new HashMap<>();
        response.put("originalPrice", originalPrice);
        response.put("firstPurchaseBenefit", firstDiscount);
        response.put("gradeBenefit", gradeDiscount);
        response.put("totalDiscount", firstDiscount.add(gradeDiscount));
        response.put("finalPrice", currentPrice);

        return ResponseEntity.ok(response);
    }
}
