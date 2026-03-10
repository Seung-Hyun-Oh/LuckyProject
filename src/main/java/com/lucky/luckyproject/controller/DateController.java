package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.util.DateUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Tag(name = "Date Utility", description = "날짜 및 시간 계산 유틸리티 API")
@Slf4j
@RestController
@RequestMapping("/api/util/date")
public class DateController {

    @Operation(summary = "현재 서버 시간 조회", description = "서버의 현재 시스템 날짜와 시간을 반환합니다.")
    @GetMapping("/now")
    public ResponseEntity<String> getCurrentTime() {
        String now = DateUtil.getCurrentDateTime();
        log.info("현재 시간 조회 요청: {}", now);
        return ResponseEntity.ok(now);
    }

    @Operation(summary = "날짜 차이 계산", description = "두 날짜 사이의 총 일수(Days) 차이를 계산합니다.")
    @GetMapping("/diff-days")
    public ResponseEntity<Long> getDiffDays(
            @Parameter(description = "시작 날짜 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate start,
            @Parameter(description = "종료 날짜 (yyyy-MM-dd)")
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate end) {

        long diff = DateUtil.getDaysBetween(start, end);
        log.info("날짜 차이 계산: {} ~ {} -> {}일", start, end, diff);
        return ResponseEntity.ok(diff);
    }

    @Operation(summary = "시간 더하기", description = "특정 시간에 입력한 시간(Hour)만큼 더한 결과를 반환합니다.")
    @GetMapping("/add-hours")
    public ResponseEntity<String> addHours(
            @Parameter(description = "시간을 더할 기준 시간 (yyyy-MM-dd HH:mm:ss)")
            @RequestParam String baseTime,
            @Parameter(description = "더할 시간(Hour) 수")
            @RequestParam long hours) {

        // 1. 문자열을 LocalDateTime으로 변환 (기본 포맷 사용)
        LocalDateTime ldt = LocalDateTime.parse(baseTime,
                java.time.format.DateTimeFormatter.ofPattern(DateUtil.DEFAULT_FORMAT));

        // 2. 시간 계산
        LocalDateTime resultTime = DateUtil.addHours(ldt, hours);

        // 3. 다시 문자열로 포맷팅하여 반환
        String result = DateUtil.format(resultTime, DateUtil.DEFAULT_FORMAT);
        log.info("시간 계산 완료: {} + {}시간 = {}", baseTime, hours, result);

        return ResponseEntity.ok(result);
    }

    @Operation(summary = "날짜 포맷 변경", description = "날짜 문자열의 포맷을 변경합니다. (예: 2026-01-02 -> 20260102)")
    @PostMapping("/convert")
    public ResponseEntity<Map<String, String>> convertFormat(
            @Parameter(description = "날짜 문자열") @RequestParam String dateStr,
            @Parameter(description = "원본 패턴") @RequestParam String fromPattern,
            @Parameter(description = "변경할 패턴") @RequestParam String toPattern) {

        String converted = DateUtil.convertFormat(dateStr, fromPattern, toPattern);

        Map<String, String> response = new HashMap<>();
        response.put("original", dateStr);
        response.put("converted", converted);

        return ResponseEntity.ok(response);
    }
}
