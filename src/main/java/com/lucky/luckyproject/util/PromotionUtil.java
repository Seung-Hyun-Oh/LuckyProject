package com.lucky.luckyproject.util;

import com.concentrix.lgintegratedadmin.domain.MemberGrade;
import com.concentrix.lgintegratedadmin.domain.PolicyTarget;
import com.concentrix.lgintegratedadmin.domain.PromotionType;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;

public class PromotionUtil {

    /**
     * 최종 할인 금액 계산
     * @param originalPrice 원가
     * @param discountValue 할인 값 (10%면 10, 5000원인 경우 5000)
     * @param type 할인 타입
     * @param maxDiscountAmount 최대 할인 한도 (퍼센트 할인 시 적용, 없으면 null)
     * @return 계산된 할인 금액
     */
    public static BigDecimal calculateDiscountAmount(BigDecimal originalPrice, double discountValue, PromotionType type, BigDecimal maxDiscountAmount) {
        BigDecimal discountAmount = BigDecimal.ZERO;

        if (type == PromotionType.PERCENTAGE) {
            // 원가 * (할인율 / 100)
            discountAmount = originalPrice.multiply(BigDecimal.valueOf(discountValue / 100.0));

            // 최대 할인 한도가 설정되어 있다면 비교
            if (maxDiscountAmount != null && discountAmount.compareTo(maxDiscountAmount) > 0) {
                discountAmount = maxDiscountAmount;
            }
        } else if (type == PromotionType.FIXED_AMOUNT) {
            discountAmount = BigDecimal.valueOf(discountValue);
        }

        // 원가보다 할인 금액이 클 수 없음
        if (discountAmount.compareTo(originalPrice) > 0) {
            discountAmount = originalPrice;
        }

        return discountAmount.setScale(0, RoundingMode.HALF_UP); // 소수점 반올림 (원 단위 절사)
    }

    /**
     * 프로모션 사용 가능 여부 검증
     * @param now 현재 시간
     * @param startTime 시작 시간
     * @param endTime 종료 시간
     * @param minOrderAmount 최소 주문 필요 금액
     * @param currentOrderAmount 현재 주문 금액
     * @return 사용 가능 여부
     */
    public static boolean isPromotionValid(LocalDateTime now, LocalDateTime startTime, LocalDateTime endTime,
                                           BigDecimal minOrderAmount, BigDecimal currentOrderAmount) {

        // 1. 기간 검증
        if (now.isBefore(startTime) || now.isAfter(endTime)) {
            return false;
        }

        // 2. 최소 주문 금액 검증
        if (currentOrderAmount.compareTo(minOrderAmount) < 0) {
            return false;
        }

        return true;
    }

    /**
     * 첫 구매 할인 적용 여부 및 금액 계산
     */
    public static BigDecimal calculateFirstPurchaseDiscount(BigDecimal originalPrice, boolean isFirstPurchase) {
        if (!isFirstPurchase) return BigDecimal.ZERO;

        // 첫 구매 시 일괄 10% 할인 (예시 정책)
        return originalPrice.multiply(new BigDecimal("0.10"))
                .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 등급별 추가 할인 계산
     */
    public static BigDecimal calculateGradeDiscount(BigDecimal currentPrice, MemberGrade grade) {
        if (grade == null) return BigDecimal.ZERO;

        // 등급별 보너스 요율 적용
        return currentPrice.multiply(BigDecimal.valueOf(grade.getBonusRate()))
                .setScale(0, RoundingMode.HALF_UP);
    }

    /**
     * 복합 할인 정책 검증 (첫 구매 전용 상품에 기존 구매자가 접근 시 등)
     */
    public static boolean canApplyPolicy(PolicyTarget target, boolean isFirstPurchase, MemberGrade userGrade, MemberGrade requiredGrade) {
        if (target == PolicyTarget.FIRST_PURCHASE) {
            return isFirstPurchase;
        }

        if (target == PolicyTarget.SPECIFIC_GRADE) {
            // 등급 순서(ordinal) 비교를 통해 특정 등급 이상인지 확인
            return userGrade.ordinal() >= requiredGrade.ordinal();
        }

        return true;
    }
}
