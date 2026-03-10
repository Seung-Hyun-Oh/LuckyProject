package com.lucky.luckyproject.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;

/**
 * Jackson ?¼ì´ë¸ŒëŸ¬ë¦¬ë? ê¸°ë°˜?¼ë¡œ ??JSON ì§ë ¬????§?¬í™” ? í‹¸ë¦¬í‹°?…ë‹ˆ??
 * Java 8 ? ì§œ/?œê°„(LocalDateTime) ì§€??ë°??œë„¤ë¦?ë¦¬ìŠ¤??ë³€??ê¸°ëŠ¥???œê³µ?©ë‹ˆ??
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Slf4j
@Tag(name = "JSON Utility", description = "JSON ?°ì´??ë³€??ë°??Œì‹± ?„êµ¬")
public class JsonUtil {

    private static final ObjectMapper objectMapper = new ObjectMapper()
            // Java 8 ? ì§œ/?œê°„ ëª¨ë“ˆ ?±ë¡ (LocalDateTime ?±ì„ ?„í•´ ?„ìˆ˜)
            .registerModule(new JavaTimeModule())
            // ? ì§œë¥??«ì ë°°ì—´???„ë‹Œ ISO-8601 ë¬¸ì???? "2025-12-24T14:00:00")ë¡?ì¶œë ¥
            .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            // JSON???•ì˜?˜ì? ?Šì? ?„ë“œê°€ ê°ì²´???†ì–´??ë¬´ì‹œ (?ëŸ¬ ë°©ì?)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
            // ê°’ì´ null???„ë“œ??JSON ?ì„± ???œì™¸ (? íƒ ?¬í•­)
            .setSerializationInclusion(JsonInclude.Include.NON_NULL);

    /**
     * Java ê°ì²´ë¥?JSON ë¬¸ì?´ë¡œ ë³€?˜í•©?ˆë‹¤.
     *
     * @param obj ë³€?˜í•  ê°ì²´
     * @return JSON ë¬¸ì??
     * @throws RuntimeException ë³€???¤íŒ¨ ??ë°œìƒ
     */
    @Operation(summary = "ê°ì²´ë¥?JSON?¼ë¡œ ë³€??, description = "Java Objectë¥?ì§ë ¬?”í•˜??JSON String???ì„±?©ë‹ˆ??")
    public static String toJson(@Schema(description = "ë³€?˜í•  Java ê°ì²´") Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            log.error("JSON Serialization Error : {}", e.getMessage());
            throw new RuntimeException("JSON ë³€??ì¤??¤ë¥˜ ë°œìƒ", e);
        }
    }

    /**
     * JSON ë¬¸ì?´ì„ ?¹ì • ?´ë˜???€?…ì˜ ê°ì²´ë¡?ë³€?˜í•©?ˆë‹¤.
     *
     * @param json  JSON ë¬¸ì??
     * @param clazz ?€???´ë˜???€??
     * @param <T>   ë°˜í™˜ ?€??
     * @return ë³€?˜ëœ ê°ì²´
     */
    @Operation(summary = "JSON??ê°ì²´ë¡?ë³€??, description = "JSON String????§?¬í™”?˜ì—¬ ì§€?•ëœ ?´ë˜???€?…ì˜ ê°ì²´ë¥?ë°˜í™˜?©ë‹ˆ??")
    public static <T> T fromJson(
            @Schema(description = "JSON ë¬¸ì??, example = "{\"id\":\"admin\"}") String json,
            Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            log.error("JSON Deserialization Error : {}", e.getMessage());
            throw new RuntimeException("ê°ì²´ ë³€??ì¤??¤ë¥˜ ë°œìƒ", e);
        }
    }

    /**
     * JSON ë¬¸ì?´ì„ List, Map ??ë³µí•© ?œë„¤ë¦??€?…ìœ¼ë¡?ë³€?˜í•©?ˆë‹¤.
     * ?? List&lt;UserVO&gt; list = JsonUtil.fromJson(json, new TypeReference&lt;List&lt;UserVO&gt;&gt;() {});
     *
     * @param json          JSON ë¬¸ì??
     * @param typeReference ë³µí•© ?€???•ë³´
     * @param <T>           ë°˜í™˜ ?€??
     * @return ë³€?˜ëœ ë³µí•© ê°ì²´
     */
    @Operation(summary = "JSON??ë³µí•© ê°ì²´(List ??ë¡?ë³€??, description = "TypeReferenceë¥??¬ìš©?˜ì—¬ List, Map ?±ì˜ ?œë„¤ë¦??€?…ì„ ? ì??˜ë©° ë³€?˜í•©?ˆë‹¤.")
    public static <T> T fromJson(String json, TypeReference<T> typeReference) {
        try {
            return objectMapper.readValue(json, typeReference);
        } catch (JsonProcessingException e) {
            log.error("JSON TypeReference Deserialization Error : {}", e.getMessage());
            throw new RuntimeException("ë³µí•© ?€??ë³€??ì¤??¤ë¥˜ ë°œìƒ", e);
        }
    }

    /**
     * JSON ë¬¸ì?´ì„ ë³´ê¸° ì¢‹ì? ?•íƒœ(Pretty Print)ë¡?ë³€?˜í•©?ˆë‹¤. (ë¡œê·¸ ì¶œë ¥??
     *
     * @param json JSON ë¬¸ì??
     * @return ?¬ë§·?…ëœ JSON ë¬¸ì??
     */
    public static String toPrettyJson(String json) {
        try {
            Object obj = objectMapper.readValue(json, Object.class);
            return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            return json; // ?¤íŒ¨ ???ë³¸ ë°˜í™˜
        }
    }
}
