package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Locale;

/**
 * ?•ë???ê¸ˆì•¡ ?°ì‚° ë°?êµ??ë³??µí™” ?¬ë§·?…ì„ ì²˜ë¦¬?˜ëŠ” ? í‹¸ë¦¬í‹°?…ë‹ˆ??
 * ë¶€???Œìˆ˜???¤ì°¨ ë°©ì?ë¥??„í•´ ëª¨ë“  ?°ì‚°?€ BigDecimal???¬ìš©?©ë‹ˆ??
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Tag(name = "Money Utility", description = "ê¸ˆì•¡ ?°ìˆ  ?°ì‚° ë°??µí™” ?¬ë§·???„êµ¬")
public class MoneyUtil {

    private static final RoundingMode DEFAULT_ROUNDING = RoundingMode.HALF_UP;
    private static final int DEFAULT_SCALE = 2;

    /**
     * ê¸ˆì•¡ ?§ì…ˆ (Null ?ˆì „)
     */
    @Operation(summary = "ê¸ˆì•¡ ?©ì‚°", description = "??ê¸ˆì•¡???”í•©?ˆë‹¤. Null?€ 0?¼ë¡œ ì²˜ë¦¬?©ë‹ˆ??")
    public static BigDecimal add(BigDecimal a, BigDecimal b) {
        BigDecimal val1 = a == null ? BigDecimal.ZERO : a;
        BigDecimal val2 = b == null ? BigDecimal.ZERO : b;
        return val1.add(val2);
    }

    /**
     * ê¸ˆì•¡ ëº„ì…ˆ (a - b)
     */
    @Operation(summary = "ê¸ˆì•¡ ì°¨ê°", description = "ì²?ë²ˆì§¸ ê¸ˆì•¡?ì„œ ??ë²ˆì§¸ ê¸ˆì•¡??ëºë‹ˆ??")
    public static BigDecimal subtract(BigDecimal a, BigDecimal b) {
        BigDecimal val1 = a == null ? BigDecimal.ZERO : a;
        BigDecimal val2 = b == null ? BigDecimal.ZERO : b;
        return val1.subtract(val2);
    }

    /**
     * ê¸ˆì•¡ ê³±ì…ˆ
     */
    @Operation(summary = "ê¸ˆì•¡ ê³±ì…ˆ", description = "ê¸ˆì•¡???˜ëŸ‰ ?ëŠ” ë°°ìœ¨??ê³±í•©?ˆë‹¤.")
    public static BigDecimal multiply(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return BigDecimal.ZERO;
        return a.multiply(b);
    }

    /**
     * ê¸ˆì•¡ ?˜ëˆ—??(ê¸°ë³¸ ?Œìˆ˜??2?ë¦¬ ë°˜ì˜¬ë¦?
     */
    @Operation(summary = "ê¸ˆì•¡ ?˜ëˆ—??, description = "ê¸ˆì•¡???˜ëˆ„ë©? ê¸°ë³¸?ìœ¼ë¡??Œìˆ˜??2?ë¦¬?ì„œ ë°˜ì˜¬ë¦¼í•©?ˆë‹¤.")
    public static BigDecimal divide(BigDecimal a, BigDecimal b) {
        return divide(a, b, DEFAULT_SCALE, DEFAULT_ROUNDING);
    }

    /**
     * ?ì„¸ ?¤ì •???¬í•¨???˜ëˆ—??
     */
    @Operation(summary = "?•ë? ?˜ëˆ—??, description = "?ë¦¬?˜ì? ë°˜ì˜¬ë¦??•ì±…??ì§€?•í•˜???˜ëˆ—?ˆì„ ?˜í–‰?©ë‹ˆ??")
    public static BigDecimal divide(BigDecimal a, BigDecimal b, int scale, RoundingMode mode) {
        if (a == null || b == null || b.compareTo(BigDecimal.ZERO) == 0) return BigDecimal.ZERO;
        return a.divide(b, scale, mode);
    }

    /**
     * ê¸ˆì•¡ ë¹„êµ (a > b)
     */
    @Operation(summary = "ê¸ˆì•¡ ??ë¹„êµ", description = "ì²?ë²ˆì§¸ ê¸ˆì•¡????ë²ˆì§¸ ê¸ˆì•¡ë³´ë‹¤ ?°ì? ?•ì¸?©ë‹ˆ??")
    public static boolean isGreaterThan(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return false;
        return a.compareTo(b) > 0;
    }

    /**
     * ê¸ˆì•¡ ë¹„êµ (a == b)
     */
    @Operation(summary = "ê¸ˆì•¡ ?™ì¼ ?¬ë?", description = "??ê¸ˆì•¡??ê°’ì´ ?™ì¼?œì? ?•ì¸?©ë‹ˆ?? (Scale ë¬´ê?)")
    public static boolean isEqual(BigDecimal a, BigDecimal b) {
        if (a == null || b == null) return a == b;
        return a.compareTo(b) == 0;
    }

    /**
     * ì²??¨ìœ„ ì½¤ë§ˆ ?¬ë§·??(?Œìˆ˜???¬í•¨)
     * ?? 1234567.89 -> 1,234,567.89
     */
    @Operation(summary = "ì²??¨ìœ„ ì½¤ë§ˆ ë³€??, description = "ê¸ˆì•¡??ì½¤ë§ˆë¥?ì¶”ê???ë¬¸ì?´ë¡œ ë°˜í™˜?©ë‹ˆ??")
    public static String formatWithComma(
            @Parameter(description = "ê¸ˆì•¡", example = "1234567.89") BigDecimal amount) {
        if (amount == null) return "0";
        DecimalFormat df = new DecimalFormat("#,##0.##");
        return df.format(amount);
    }

    /**
     * ?µí™” ê¸°í˜¸ ?¬í•¨ ?¬ë§·??(Locale ê¸°ì?)
     * ?? KRW -> ??,234 / USD -> $1,234.56
     */
    @Operation(summary = "êµ??ë³??µí™” ?¬ë§·??, description = "ë¡œì????¤ì •??ë°”íƒ•?¼ë¡œ ?µí™” ê¸°í˜¸?€ ?¨ê»˜ ?¬ë§·?…í•©?ˆë‹¤.")
    public static String formatCurrency(BigDecimal amount, Locale locale) {
        if (amount == null) return "0";
        NumberFormat nf = NumberFormat.getCurrencyInstance(locale != null ? locale : Locale.KOREA);
        return nf.format(amount);
    }

    /**
     * ?í™”(KRW) ?„ìš© ?ˆì‚­ ì²˜ë¦¬ (1???¨ìœ„ ?ˆì‚­ ??
     * ?? 1234.56 -> 1230 (10???¨ìœ„ ë°˜ì˜¬ë¦?
     */
    @Operation(summary = "?í™” ?ˆì‚­ ì²˜ë¦¬", description = "?œêµ­ ?í™” ê¸°ì? ?¹ì • ?¨ìœ„?ì„œ ?ˆì‚­/ë°˜ì˜¬ë¦?ì²˜ë¦¬?©ë‹ˆ??")
    public static BigDecimal roundKrw(BigDecimal amount, int unit) {
        if (amount == null) return BigDecimal.ZERO;
        // unit??10??ê²½ìš° 10???¨ìœ„ ë°˜ì˜¬ë¦?
        return amount.divide(BigDecimal.valueOf(unit), 0, RoundingMode.HALF_UP).multiply(BigDecimal.valueOf(unit));
    }
}
