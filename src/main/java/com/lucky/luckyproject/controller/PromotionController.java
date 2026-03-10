package com.lucky.luckyproject.controller;

import com.lucky.luckyproject.domain.MemberGrade;
import com.lucky.luckyproject.domain.PromotionType;
import com.lucky.luckyproject.util.PromotionUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Promotion", description = "?´ì»¤ë¨¸ìŠ¤ ?„ë¡œëª¨ì…˜ ê³„ì‚° API")
@RestController
@RequestMapping("/api/promotion")
public class PromotionController {

    @Operation(summary = "?ˆìƒ ? ì¸ ê¸ˆì•¡ ê³„ì‚°", description = "?í’ˆ ê°€ê²©ê³¼ ?„ë¡œëª¨ì…˜ ?•ë³´ë¥?ê¸°ë°˜?¼ë¡œ ìµœì¢… ? ì¸?¡ì„ ê³„ì‚°?©ë‹ˆ??")
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

    @Operation(summary = "ìµœì¢… ?œíƒê°€ ê³„ì‚°", description = "ì¿ í°, ì²?êµ¬ë§¤, ?±ê¸‰ ? ì¸??ëª¨ë‘ ?ìš©??ìµœì¢…ê°€ë¥?ê³„ì‚°?©ë‹ˆ??")
    @PostMapping("/calculate-benefit")
    public ResponseEntity<Map<String, Object>> calculateAllBenefits(
        @RequestParam BigDecimal originalPrice,
        @RequestParam boolean isFirstPurchase,
        @RequestParam MemberGrade userGrade)
    {
        BigDecimal currentPrice = originalPrice;

        // 1. ì²?êµ¬ë§¤ ? ì¸ ?ìš© (10%)
        BigDecimal firstDiscount = PromotionUtil.calculateFirstPurchaseDiscount(originalPrice, isFirstPurchase);
        currentPrice = currentPrice.subtract(firstDiscount);

        // 2. ?±ê¸‰ ? ì¸ ?ìš© (ì²?êµ¬ë§¤ ? ì¸??ê¸ˆì•¡ ê¸°ì? ?¹ì? ?ê? ê¸°ì? - ?•ì±…???°ë¼ ? íƒ)
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
