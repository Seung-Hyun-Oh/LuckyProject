package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.util.StringUtils;

import java.security.SecureRandom;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/**
 * 문자열 변환, 검증 및 케이스 변경을 위한 공통 유틸리티입니다.
 * 2025년 기준 고성능 정규식 처리와 Java Stream API를 활용합니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Tag(name = "String Utility", description = "문자열 조작 및 변환 도구")
public class StringUtil {

    private static final Pattern CAMEL_CASE_PATTERN = Pattern.compile("([a-z0-9])([A-Z])");
    private static final Pattern HTML_TAG_PATTERN = Pattern.compile("<[^>]*>");
    private static final String RANDOM_CHARS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    private static final SecureRandom RANDOM = new SecureRandom();

    /**
     * 값이 비어있거나 공백만 포함되어 있는지 확인합니다.
     */
    @Operation(summary = "문자열 비어있음 확인", description = "null, 빈 문자열, 혹은 공백만 있는 경우 true를 반환합니다.")
    public static boolean isEmpty(@Schema(description = "검사 대상 객체") Object obj) {
        return obj == null || !StringUtils.hasText(obj.toString());
    }

    /**
     * isEmpty의 반대 결과를 반환합니다.
     */
    public static boolean isNotEmpty(Object obj) {
        return !isEmpty(obj);
    }

    /**
     * 문자열이 비어있을 경우 기본값을 반환합니다.
     */
    @Operation(summary = "기본 문자열 처리", description = "값이 비어있다면 지정된 기본값을 반환합니다.")
    public static String defaultString(String str, String defaultStr) {
        return isEmpty(str) ? defaultStr : str;
    }

    /**
     * 카멜케이스(camelCase)를 스네이크케이스(snake_case)로 변환합니다.
     * DB 컬럼명 자동 생성 등에 활용됩니다.
     */
    @Operation(summary = "Snake Case 변환", description = "camelCase를 snake_case로 변환합니다.")
    public static String toSnakeCase(String str) {
        if (isEmpty(str)) return str;
        return CAMEL_CASE_PATTERN.matcher(str).replaceAll("$1_$2").toLowerCase();
    }

    /**
     * 스네이크케이스(snake_case)를 카멜케이스(camelCase)로 변환합니다.
     */
    @Operation(summary = "Camel Case 변환", description = "snake_case를 camelCase로 변환합니다.")
    public static String toCamelCase(String str) {
        if (isEmpty(str)) return str;

        StringBuilder sb = new StringBuilder();
        String[] words = str.split("_");

        for (int i = 0; i < words.length; i++) {
            String word = words[i].toLowerCase();
            if (i == 0) {
                sb.append(word);
            } else if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0))).append(word.substring(1));
            }
        }
        return sb.toString();
    }

    /**
     * 리스트의 문자열을 구분자로 연결합니다.
     */
    @Operation(summary = "문자열 결합", description = "리스트 요소들을 특정 구분자로 연결합니다.")
    public static String join(List<String> list, String delimiter) {
        if (list == null || list.isEmpty()) return "";
        return String.join(defaultString(delimiter, ""), list);
    }

    /**
     * 구분자로 연결된 문자열을 리스트로 분리합니다. (빈 값 제외)
     */
    @Operation(summary = "문자열 분리", description = "구분자로 연결된 문자열을 리스트로 변환하며 공백은 제거합니다.")
    public static List<String> splitToList(String str, String delimiter) {
        if (isEmpty(str)) return List.of();
        return Arrays.stream(str.split(Pattern.quote(delimiter)))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
    }

    /**
     * [추가 기능] HTML 태그를 제거합니다. (XSS 방어 및 로그 기록용)
     */
    @Operation(summary = "HTML 태그 제거", description = "문자열 내의 모든 HTML 태그를 제거하고 순수 텍스트만 추출합니다.")
    public static String stripHtml(String html) {
        if (isEmpty(html)) return html;
        return HTML_TAG_PATTERN.matcher(html).replaceAll("");
    }

    /**
     * [추가 기능] 지정된 길이의 랜덤 문자열을 생성합니다. (임시 비밀번호, 인증코드용)
     */
    @Operation(summary = "랜덤 문자열 생성", description = "영문 대소문자와 숫자를 조합하여 고유한 랜덤 코드를 생성합니다.")
    public static String randomAlphanumeric(@Parameter(description = "생성할 길이", example = "8") int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(RANDOM_CHARS.charAt(RANDOM.nextInt(RANDOM_CHARS.length())));
        }
        return sb.toString();
    }
}
