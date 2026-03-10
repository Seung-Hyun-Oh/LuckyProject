package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.util.ApiResponse;
import com.concentrix.lgintegratedadmin.util.ExcelUtil;
import com.concentrix.lgintegratedadmin.util.StringUtil;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Tag(name = "시스템 공통 API", description = "메시지 조회 및 엑셀 다운로드 API")
@RestController
@RequestMapping("/api/common")
@RequiredArgsConstructor // 생성자 주입 자동화
public class CommonController {

    private final MessageSource messageSource;

    @Operation(summary = "다국어 인사말 조회", description = "설정된 Locale에 따른 환영 메시지를 반환합니다.")
    @GetMapping("/welcome")
    public ApiResponse<String> getWelcome(
            @Parameter(description = "사용자 이름", example = "admin")
            @RequestParam(required = false) String name) {

        // 1. StringUtil을 활용한 기본값 처리
        String userName = StringUtil.defaultString(name, "Guest");

        // 2. MessageSource를 활용한 다국어 메시지 취득
        String message = messageSource.getMessage("welcome.message", null, LocaleContextHolder.getLocale());
        String fullMessage = userName + "님, " + message;

        // 3. 표준 ApiResponse 성공 규격으로 반환
        return ApiResponse.success(fullMessage);
    }

    @Operation(summary = "대용량 데이터 엑셀 다운로드", description = "SXSSF 방식을 사용하여 대용량 데이터를 엑셀로 추출합니다.")
    @GetMapping("/excel/download")
    public void downloadData(HttpServletResponse response) throws IOException {

        // 실무 예시 데이터 생성 (실제로는 Service에서 DB 조회)
        String[] headers = {"ID", "이름", "상태", "등록일"};
        List<Map<String, Object>> dataList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String now = LocalDateTime.now().format(formatter);
//        LocalDate now1 = java.time.LocalDate.now();

        for (int i = 1; i <= 1000; i++) {
            Map<String, Object> row = new LinkedHashMap<>();
            row.put("id", i);
            row.put("name", "User_" + i);
            row.put("status", i % 2 == 0 ? "ACTIVE" : "INACTIVE");
            row.put("regDate", now);
            dataList.add(row);
        }

        // 유틸리티를 활용한 다운로드 실행
        // 주의: 파일 다운로드는 ApiResponse 래퍼를 씌우지 않고 직접 OutputStream에 씁니다.
        ExcelUtil.downloadLargeExcel(response, "User_List_2025", headers, dataList);
    }

    @Operation(summary = "문자열 케이스 변환 테스트", description = "카멜케이스를 스네이크케이스로 변환하여 반환합니다.")
    @GetMapping("/convert/{text}")
    public ApiResponse<Map<String, String>> convertText(@PathVariable String text) {

        if (StringUtil.isEmpty(text)) {
            return ApiResponse.error("변환할 텍스트가 비어있습니다.");
        }

        Map<String, String> result = new HashMap<>();
        result.put("original", text);
        result.put("snakeCase", StringUtil.toSnakeCase(text));

        return ApiResponse.success(result);
    }
}
