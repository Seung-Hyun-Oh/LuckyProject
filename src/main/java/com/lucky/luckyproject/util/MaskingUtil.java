package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * ISMS-P 및 보안 감사 대응을 위한 개인정보 마스킹 유틸리티입니다.
 * 2025년 최신 개인정보 처리 가이드라인에 따라 마스킹 로직을 제공합니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Tag(name = "Masking Utility", description = "개인정보(이름, 이메일, 번호 등) 마스킹 처리 도구")
public class MaskingUtil {

    /**
     * 성명 마스킹 (2글자: 홍* / 3글자: 홍*동 / 4글자 이상: 남궁**수)
     */
    @Operation(summary = "성명 마스킹", description = "성명 길이에 따라 가운데 글자를 마스킹합니다.")
    public static String maskName(
            @Parameter(description = "성명", example = "홍길동") String name) {
        if (!StringUtils.hasText(name) || name.length() < 2) return name;

        if (name.length() == 2) {
            return name.charAt(0) + "*";
        }

        String start = name.substring(0, 1);
        String end = name.substring(name.length() - 1);
        String middle = "*".repeat(name.length() - 2);

        return start + middle + end;
    }

    /**
     * 이메일 마스킹 (ab****@lge.com)
     */
    @Operation(summary = "이메일 마스킹", description = "ID 부분의 앞 2글자만 노출하고 나머지를 마스킹합니다.")
    public static String maskEmail(
            @Parameter(description = "이메일 주소", example = "admin@lge.com") String email) {
        if (!StringUtils.hasText(email) || !email.contains("@")) return email;

        String[] parts = email.split("@");
        if (parts[0].length() <= 2) return parts[0] + "*@" + parts[1];

        return parts[0].substring(0, 2) + "*".repeat(parts[0].length() - 2) + "@" + parts[1];
    }

    /**
     * 휴대폰 번호 마스킹 (010-1234-5678 -> 010-****-5678)
     */
    @Operation(summary = "휴대폰 번호 마스킹", description = "가운데 3~4자리를 마스킹합니다.")
    public static String maskPhoneNumber(
            @Parameter(description = "휴대폰 번호", example = "010-1234-5678") String phoneNumber) {
        if (!StringUtils.hasText(phoneNumber)) return phoneNumber;

        String regex = "(\\d{2,3})-?(\\d{3,4})-?(\\d{4})";
        Matcher matcher = Pattern.compile(regex).matcher(phoneNumber);

        if (matcher.find()) {
            return matcher.group(1) + "-" + "*".repeat(matcher.group(2).length()) + "-" + matcher.group(3);
        }
        return phoneNumber;
    }

    /**
     * 카드번호 마스킹 (1234-5678-1234-5678 -> 1234-****-****-5678)
     */
    @Operation(summary = "카드번호 마스킹", description = "카드번호의 중간 8자리를 마스킹합니다.")
    public static String maskCardNumber(String cardNumber) {
        if (!StringUtils.hasText(cardNumber)) return cardNumber;

        Pattern pattern = Pattern.compile("(\\d{4})(-[\\d-]{9,11}-)(\\d{4})");
        Matcher matcher = pattern.matcher(cardNumber);

        if (matcher.find()) {
            String maskedMiddle = matcher.group(2).replaceAll("\\d", "*");
            return matcher.group(1) + maskedMiddle + matcher.group(3);
        }
        return cardNumber;
    }

    /**
     * 계좌번호 마스킹 (뒤 5자리 마스킹)
     */
    @Operation(summary = "계좌번호 마스킹", description = "계좌번호의 끝 5자리를 마스킹합니다.")
    public static String maskAccountNumber(String accountNumber) {
        if (!StringUtils.hasText(accountNumber) || accountNumber.length() < 5) return accountNumber;

        return accountNumber.substring(0, accountNumber.length() - 5) + "*****";
    }

    /**
     * 주소 마스킹 (서울시 강남구 테헤란로... -> 서울시 강남구 ****)
     * 상세 주소(건물번호 이후)를 마스킹 처리합니다.
     */
    @Operation(summary = "주소 마스킹", description = "기본 주소 이후의 상세 주소를 마스킹합니다.")
    public static String maskAddress(String address) {
        if (!StringUtils.hasText(address)) return address;

        String[] parts = address.split(" ");
        if (parts.length < 3) return address;

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < parts.length; i++) {
            if (i < 2) sb.append(parts[i]).append(" "); // 시, 군/구 노출
            else sb.append("**** "); // 나머지 마스킹
        }
        return sb.toString().trim();
    }

    /**
     * 일반 ID 마스킹 (lgadmin -> lga****)
     */
    @Operation(summary = "사용자 ID 마스킹", description = "ID 앞 3자리만 노출하고 나머지를 마스킹합니다.")
    public static String maskUserId(String userId) {
        if (!StringUtils.hasText(userId) || userId.length() < 3) return userId;
        return userId.substring(0, 3) + "*".repeat(userId.length() - 3);
    }
}
