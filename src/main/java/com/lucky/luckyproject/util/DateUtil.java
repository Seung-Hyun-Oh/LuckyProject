package com.lucky.luckyproject.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

/**
 * 날짜 및 시간 처리를 위한 공통 유틸리티 클래스
 *
 * <p>이 클래스는 Java 8(JDK 1.8) 이상에서 도입된 java.time 패키지를 기반으로 함.</p>
 *
 * @since 1.8
 * @version 1.1 (2025.12.18 기준 JDK 17 최적화)
 */
public class DateUtil {

    /** 기본 날짜/시간 포맷: yyyy-MM-dd HH:mm:ss */
    public static final String DEFAULT_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 현재 시스템의 날짜와 시간을 기본 포맷(yyyy-MM-dd HH:mm:ss)으로 반환합니다.
     *
     * @return 현재 시간 문자열
     * @since 1.8
     */
    public static String getCurrentDateTime() {
        return LocalDateTime.now().format(DateTimeFormatter.ofPattern(DEFAULT_FORMAT));
    }

    /**
     * 입력받은 LocalDateTime 객체를 지정된 패턴의 문자열로 변환합니다.
     *
     * @param dateTime 변환할 날짜 객체 (null 불가)
     * @param pattern  적용할 포맷 패턴 (예: yyyyMMdd)
     * @return 포맷팅된 날짜 문자열
     * @throws NullPointerException dateTime 또는 pattern이 null일 경우 발생
     * @since 1.8
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        Objects.requireNonNull(dateTime, "dateTime 객체는 null일 수 없습니다.");
        Objects.requireNonNull(pattern, "pattern 문자열은 null일 수 없습니다.");
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 특정 날짜 문자열이 유효한지 검증하거나 포맷을 변경할 때 사용합니다.
     *
     * @param dateStr 변환할 날짜 문자열
     * @param fromPattern 현재 패턴
     * @param toPattern   변경할 패턴
     * @return 변경된 날짜 문자열
     * @since 1.8
     */
    public static String convertFormat(String dateStr, String fromPattern, String toPattern) {
        LocalDateTime ldt = LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(fromPattern));
        return ldt.format(DateTimeFormatter.ofPattern(toPattern));
    }

    /**
     * 두 날짜 사이의 일수(Days) 차이 계산
     * @param start
     * @param end
     * @return
     */
    public static long getDaysBetween(LocalDate start, LocalDate end) {
        return ChronoUnit.DAYS.between(start, end);
    }

    /**
     * 특정 시간만큼 더하기/빼기
     * @param time
     * @param hours
     * @return
     */
    public static LocalDateTime addHours(LocalDateTime time, long hours) {
        return time.plusHours(hours);
    }
}
