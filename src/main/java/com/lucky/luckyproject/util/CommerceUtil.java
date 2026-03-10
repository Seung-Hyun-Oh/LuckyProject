package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * 커머스 관련 비즈니스 로직을 처리하는 공통 유틸리티 컴포넌트입니다.
 * 주문 번호 생성, 금액 계산, 세금 계산 등의 기능을 제공합니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Tag(name = "Commerce Utility", description = "주문 및 결제 관련 계산 유틸리티")
@Component
public class CommerceUtil {

    /**
     * 현재 날짜와 UUID를 조합하여 고유한 주문 번호를 생성합니다.
     *
     * @return 생성된 주문 번호 (예: 20251224-A1B2C3D4)
     */
    @Operation(summary = "주문 번호 생성", description = "오늘 날짜와 랜덤 문자열을 조합하여 유니크한 주문 번호를 반환합니다.")
    public String generateOrderNo() {
        String datePrefix = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        String randomSuffix = UUID.randomUUID().toString().substring(0, 8).toUpperCase();
        return datePrefix + "-" + randomSuffix;
    }

    /**
     * 정가와 할인율을 기반으로 할인 금액을 계산합니다.
     *
     * @param originalPrice 정가 (BigDecimal)
     * @param discountRate  할인율 (0~100 사이의 double)
     * @return 계산된 할인 금액 (소수점 첫째 자리에서 반올림)
     */
    @Operation(summary = "할인 금액 계산", description = "정가와 할인율을 입력받아 실제 할인될 금액을 계산합니다.")
    public BigDecimal calculateDiscountAmount(
            @Parameter(description = "상품 정가", example = "50000") BigDecimal originalPrice,
            @Parameter(description = "할인율 (%)", example = "15.5") double discountRate) {

        if (originalPrice == null || discountRate <= 0) return BigDecimal.ZERO;

        // 정가 * (할인율 / 100)
        return originalPrice.multiply(BigDecimal.valueOf(discountRate))
                .divide(BigDecimal.valueOf(100), 0, RoundingMode.HALF_UP);
    }

    /**
     * 정가에서 할인을 적용하고 배송비를 합산하여 최종 결제 금액을 산출합니다.
     *
     * @param originalPrice  정가
     * @param discountAmount 할인 금액 (직전 계산 결과값 권장)
     * @param deliveryFee    배송비
     * @return 최종 실결제 금액
     */
    @Operation(summary = "최종 결제 금액 계산", description = "정가에서 할인가를 빼고 배송비를 더한 최종 금액을 구합니다.")
    public BigDecimal calculateFinalPrice(
            @Schema(description = "상품 정가") BigDecimal originalPrice,
            @Schema(description = "차감할 할인액") BigDecimal discountAmount,
            @Schema(description = "추가될 배송비") BigDecimal deliveryFee) {

        BigDecimal base = originalPrice != null ? originalPrice : BigDecimal.ZERO;
        BigDecimal discount = discountAmount != null ? discountAmount : BigDecimal.ZERO;
        BigDecimal delivery = deliveryFee != null ? deliveryFee : BigDecimal.ZERO;

        return base.subtract(discount).add(delivery);
    }

    /**
     * 총 결제 금액(공급가액 + 부가세)에서 부가세(10%)를 역산합니다.
     *
     * @param totalAmount 부가세가 포함된 총 금액
     * @return 계산된 부가세 (1/11 비율 계산)
     */
    @Operation(summary = "부가세(VAT) 계산", description = "포함된 총 금액에서 부가세 10%를 역산하여 산출합니다.")
    public BigDecimal calculateTax(
            @Parameter(description = "총 결제 금액", required = true) BigDecimal totalAmount) {

        if (totalAmount == null) return BigDecimal.ZERO;

        // 총액 / 11 (소수점 첫째 자리에서 반올림)
        return totalAmount.divide(BigDecimal.valueOf(11), 0, RoundingMode.HALF_UP);
    }
}
