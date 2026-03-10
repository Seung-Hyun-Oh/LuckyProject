package com.lucky.luckyproject.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Jackson 라이브러리를 기반으로 한 JSON 직렬화/역직렬화 유틸리티입니다.
 * Java 8 날짜/시간(LocalDateTime) 지원 및 제네릭 리스트 변환 기능을 제공합니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Slf4j
@Tag(name = "JSON Utility", description = "JSON 데이터 변환 및 파싱 도구")
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            // Java 8 날짜/시간 모듈 등록 (LocalDateTime 등을 위해 필수)
            .registerModule(new JavaTimeModule())
            // 날짜를 숫자 배열이 아닌 ISO-8601 문자열(예: "2025-12-24T14:00:00")로 출력
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // JSON에 정의되지 않은 필드가 객체에 없어도 무시 (에러 방지)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            // 값이 null인 필드는 JSON 생성 시 제외 (선택 사항)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    /**
     * Java 객체를 JSON 문자열로 변환합니다.
     *
     * @param obj 변환할 객체
     * @return JSON 문자열
     * @throws RuntimeException 변환 실패 시 발생
     */
    @Operation(summary = "객체를 JSON으로 변환", description = "Java Object를 직렬화하여 JSON String을 생성합니다.")
    public static String toJson(@Schema(description = "변환할 Java 객체") Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JSON Serialization Error : {}", e.getMessage());
            throw new RuntimeException("JSON 변환 중 오류 발생", e);
        }
    }

    /**
     * JSON 문자열을 특정 클래스 타입의 객체로 변환합니다.
     *
     * @param json  JSON 문자열
     * @param clazz 대상 클래스 타입
     * @param <T>   반환 타입
     * @return 변환된 객체
     */
    @Operation(summary = "JSON을 객체로 변환", description = "JSON String을 역직렬화하여 지정된 클래스 타입의 객체를 반환합니다.")
    public static <T> T fromJson(
            @Schema(description = "JSON 문자열", example = "{\"id\":\"admin\"}") String json,
            Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("JSON Deserialization Error : {}", e.getMessage());
            throw new RuntimeException("객체 변환 중 오류 발생", e);
        }
    }

    /**
     * JSON 문자열을 List, Map 등 복합 제네릭 타입으로 변환합니다.
     * 예: List&lt;UserVO&gt; list = JsonUtil.fromJson(json, new TypeReference&lt;List&lt;UserVO&gt;&gt;() {});
     *
     * @param json          JSON 문자열
     * @param typeReference 복합 타입 정보
     * @param <T>           반환 타입
     * @return 변환된 복합 객체
     */
    @Operation(summary = "JSON을 복합 객체(List 등)로 변환", description = "TypeReference를 사용하여 List, Map 등의 제네릭 타입을 유지하며 변환합니다.")
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("JSON TypeReference Deserialization Error : {}", e.getMessage());
            throw new RuntimeException("복합 타입 변환 중 오류 발생", e);
        }
    }

    /**
     * JSON 문자열을 보기 좋은 형태(Pretty Print)로 변환합니다. (로그 출력용)
     *
     * @param json JSON 문자열
     * @return 포맷팅된 JSON 문자열
     */
    public static String toPrettyJson(String json) {
        try {
            Object obj = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return json; // 실패 시 원본 반환
        }
    }
}
