package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * Excel 생성 및 다운로드를 처리하는 유틸리티 클래스입니다.
 * Apache POI의 XSSF(일반) 및 SXSSF(대용량 스트리밍) 방식을 지원합니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Tag(name = "Excel Utility", description = "엑셀 파일 생성 및 대용량 다운로드 관련 도구")
public class ExcelUtil {

    /**
     * 표준 XSSFWorkbook을 사용하여 소량의 데이터를 엑셀로 변환합니다.
     *
     * @param sheetName 엑셀 시트 이름
     * @param headers   상단 헤더 배열
     * @param dataList  행 데이터 리스트 (Map 형태)
     * @return 생성된 Workbook 객체
     */
    @Operation(summary = "일반 엑셀 워크북 생성", description = "메모리 내에서 전체 엑셀 데이터를 처리하여 워크북 객체를 반환합니다.")
    public static Workbook createExcel(
            @Schema(description = "시트명", example = "Sheet1") String sheetName,
            @Schema(description = "헤더 목록") String[] headers,
            @Schema(description = "데이터 리스트") List<Map<String, Object>> dataList) {

        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet(sheetName);

        // 헤더 생성
        Row headerRow = sheet.createRow(0);
        for (int i = 0; i < headers.length; i++) {
            headerRow.createCell(i).setCellValue(headers[i]);
        }

        // 데이터 생성
        int rowIdx = 1;
        if (dataList != null) {
            for (Map<String, Object> data : dataList) {
                Row row = sheet.createRow(rowIdx++);
                int colIdx = 0;
                // Map의 키 순서에 의존하므로, 정렬된 Map(LinkedHashMap 등) 사용을 권장합니다.
                for (String key : data.keySet()) {
                    row.createCell(colIdx++).setCellValue(String.valueOf(data.get(key)));
                }
            }
        }
        return workbook;
    }

    /**
     * SXSSF 방식을 사용하여 서버 메모리 부하를 최소화하며 대용량 엑셀을 다운로드합니다.
     * 100개의 행마다 임시 파일로 flush하여 OutOfMemory를 방지합니다.
     *
     * @param response  HttpServletResponse 객체
     * @param fileName  다운로드될 파일명 (확장자 제외)
     * @param headers   엑셀 헤더 배열
     * @param dataList  출력할 대용량 데이터
     * @throws IOException 파일 스트림 처리 중 발생할 수 있는 예외
     */
    @Operation(
            summary = "대용량 엑셀 다운로드",
            description = "SXSSF 스트리밍 방식을 사용하여 대용량 데이터를 엑셀 파일로 클라이언트에 전송합니다.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "다운로드 성공",
                            content = @Content(mediaType = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet")
                    )
            }
    )
    public static void downloadLargeExcel(
            @Parameter(hidden = true) HttpServletResponse response,
            @Parameter(description = "파일명", example = "order_list_2025") String fileName,
            @Parameter(description = "헤더 배열") String[] headers,
            @Parameter(description = "데이터 리스트") List<Map<String, Object>> dataList) throws IOException {

        // 1. SXSSFWorkbook 생성 (windowSize: 100 - 메모리에 100개 행만 유지)
        try (SXSSFWorkbook workbook = new SXSSFWorkbook(100)) {
            workbook.setCompressTempFiles(true); // 임시 파일 압축으로 디스크 공간 절약

            Sheet sheet = workbook.createSheet("Data List");

            // 2. 헤더 생성
            Row headerRow = sheet.createRow(0);
            for (int i = 0; i < headers.length; i++) {
                Cell cell = headerRow.createCell(i);
                cell.setCellValue(headers[i]);
            }

            // 3. 데이터 입력
            int rowNum = 1;
            if (dataList != null) {
                for (Map<String, Object> data : dataList) {
                    Row row = sheet.createRow(rowNum++);
                    int colNum = 0;
                    for (String key : data.keySet()) {
                        row.createCell(colNum++).setCellValue(String.valueOf(data.get(key)));
                    }
                }
            }

            // 4. 응답 설정 및 파일명 인코딩 (RFC 5987 준수 방식 권장)
            String encodedFileName = URLEncoder.encode(fileName, StandardCharsets.UTF_8).replaceAll("\\+", "%20");

            response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
            response.setCharacterEncoding("UTF-8");
            response.setHeader("Content-Disposition", "attachment; filename=\"" + encodedFileName + ".xlsx\"; filename*=UTF-8''" + encodedFileName + ".xlsx");

            workbook.write(response.getOutputStream());

            // 5. 임시 파일 삭제 및 메모리 해제 (매우 중요)
            workbook.dispose();
        } catch (IOException e) {
            throw new IOException("엑셀 생성 중 오류가 발생했습니다: " + e.getMessage(), e);
        }
    }
}
