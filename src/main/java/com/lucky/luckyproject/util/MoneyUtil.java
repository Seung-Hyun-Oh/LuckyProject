package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * 정밀한 금액 연산 및 국가별 통화 포맷팅을 처리하는 유틸리티입니다.
 * 부동 소수점 오차 방지를 위해 모든 연산은 BigDecimal을 사용합니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Tag(name = "Money Utility", description = "금액 산술 연산 및 통화 포맷팅 도구")
public class MoneyUtil {

    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;
    private static final int DEFAULT_SCALE = 2;

    /**
     * 금액 덧셈 (Null 안전)
     */
    @Operation(summary = "금액 합산", description = "두 금액을 더합니다. Null은 0으로 처리합니다.")
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        BigDecimal val1 = a == null ? BigDecimal.ZERO : a;
        BigDecimal val2 = b == null ? BigDecimal.ZERO : b;
        return val1.add(val2);
    }

    /**
     * 금액 뺄셈 (a - b)
     */
    @Operation(summary = "금액 차감", description = "첫 번째 금액에서 두 번째 금액을 뺍니다.")
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        BigDecimal val1 = a == null ? BigDecimal.ZERO : a;
        BigDecimal val2 = b == null ? BigDecimal.ZERO : b;
        return val1.subtract(val2);
    }

    /**
     * 금액 곱셈
     */
    @Operation(summary = "금액 곱셈", description = "금액에 수량 또는 배율을 곱합니다.")
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return BigDecimal.ZERO;
        return a.multiply(b);
    }

    /**
     * 금액 나눗셈 (기본 소수점 2자리 반올림)
     */
    @Operation(summary = "금액 나눗셈", description = "금액을 나누며, 기본적으로 소수점 2자리에서 반올림합니다.")
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return divide(a, b, DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * 상세 설정을 포함한 나눗셈
     */
    @Operation(summary = "정밀 나눗셈", description = "자리수와 반올림 정책을 지정하여 나눗셈을 수행합니다.")
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode mode) {
        if (a == null || b == null || b.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return a.divide(b, scale, mode);
    }

    /**
     * 금액 비교 (a > b)
     */
    @Operation(summary = "금액 큼 비교", description = "첫 번째 금액이 두 번째 금액보다 큰지 확인합니다.")
    public static boolean isGreaterThan(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return false;
        return a.compareTo(b) > 0;
    }

    /**
     * 금액 비교 (a == b)
     */
    @Operation(summary = "금액 동일 여부", description = "두 금액의 값이 동일한지 확인합니다. (Scale 무관)")
    public static boolean isEqual(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return a == b;
        return a.compareTo(b) == 0;
    }

    /**
     * 천 단위 콤마 포맷팅 (소수점 포함)
     * 예: 1234567.89 -> 1,234,567.89
     */
    @Operation(summary = "천 단위 콤마 변환", description = "금액에 콤마를 추가한 문자열로 반환합니다.")
    public static String formatWithComma(
            @Parameter(description = "금액", example = "1234567.89") BigDecimal amount) {
        if (amount == null) return "0";
        DecimalFormat df = new DecimalFormat("#,##0.##");
        return df.format(amount);
    }

    /**
     * 통화 기호 포함 포맷팅 (Locale 기준)
     * 예: KRW -> ₩1,234 / USD -> $1,234.56
     */
    @Operation(summary = "국가별 통화 포맷팅", description = "로케일 설정을 바탕으로 통화 기호와 함께 포맷팅합니다.")
    public static String formatCurrency(BigDecimal amount, Locale locale) {
        if (amount == null) return "0";
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale != null ? locale : Locale.KOREA);
        return nf.format(amount);
    }

    /**
     * 원화(KRW) 전용 절삭 처리 (1원 단위 절삭 등)
     * 예: 1234.56 -> 1230 (10원 단위 반올림)
     */
    @Operation(summary = "원화 절삭 처리", description = "한국 원화 기준 특정 단위에서 절삭/반올림 처리합니다.")
    public static BigDecimal roundKrw(BigDecimal amount, int unit) {
        if (amount == null) return BigDecimal.ZERO;
        // unit이 10인 경우 10원 단위 반올림
        return amount.divide(BigDecimal.valueOf(unit), 0, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(unit));
    }
}
