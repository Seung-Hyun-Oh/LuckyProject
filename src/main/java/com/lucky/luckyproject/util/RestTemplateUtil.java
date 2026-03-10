package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

/**
 * RestTemplate???œìš©???¸ë? ?œìŠ¤??HTTP ?°ë™ ? í‹¸ë¦¬í‹°?…ë‹ˆ??
 * ?™ê¸° ë°©ì‹??API ?¸ì¶œ??ì§€?í•˜ë©? ê³µí†µ ?¤ë” ?¤ì • ë°??ˆì™¸ ë¡œê¹…??ì²˜ë¦¬?©ë‹ˆ??
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Slf4j
@Tag(name = "RestTemplate Utility", description = "ê¸°ì¡´ RestTemplate ê¸°ë°˜ API ?°ë™ ?„êµ¬")
@Component
public class RestTemplateUtil {

    private final RestTemplate restTemplate;

    public RestTemplateUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * POST ?”ì²­ - JSON ë³¸ë¬¸???„ì†¡?˜ì—¬ ?°ì´?°ë? ?ì„±?˜ê±°??ì²˜ë¦¬?©ë‹ˆ??
     *
     * @param url   ?¸ì¶œ ?€??URL
     * @param body  ?”ì²­ ë³¸ë¬¸ ê°ì²´
     * @param clazz ?‘ë‹µ??ë³€?˜í•  ?´ë˜???€??
     * @return ??§?¬í™”???‘ë‹µ ê°ì²´
     */
    @Operation(summary = "POST ?¸ì¶œ", description = "JSON ?•ì‹???°ì´?°ë? POST ë°©ì‹?¼ë¡œ ?„ì†¡?©ë‹ˆ??")
    public <T> T post(@Parameter(description = "?€??URL") String url, Object body, Class<T> clazz) {
        // Executes POST request; returns deserialized response or throws exception
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            log.info("[RestTemplate POST] URL: {}", url);
            return restTemplate.postForObject(url, entity, clazz);
        } catch (RestClientException e) {
            log.error("[RestTemplate ERROR] POST {} - Message: {}", url, e.getMessage());
            throw e;
        }
    }

    /**
     * GET ?”ì²­ - ?€??URLë¡œë????•ë³´ë¥?ì¡°íšŒ?©ë‹ˆ??
     *
     * @param url   ?¸ì¶œ ?€??URL
     * @param clazz ?‘ë‹µ??ë³€?˜í•  ?´ë˜???€??
     * @return ??§?¬í™”???‘ë‹µ ê°ì²´
     */
    @Operation(summary = "GET ?¸ì¶œ", description = "?¸ë? ?ì›??ì¡°íšŒ?©ë‹ˆ??")
    public <T> T get(@Parameter(description = "?€??URL") String url, Class<T> clazz) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

            HttpEntity<String> entity = new HttpEntity<>(headers);
            log.info("[RestTemplate GET] URL: {}", url);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.GET, entity, clazz);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("[RestTemplate ERROR] GET {} - Message: {}", url, e.getMessage());
            throw e;
        }
    }

    /**
     * PUT ?”ì²­ - ?•ë³´ë¥??˜ì •?©ë‹ˆ??
     *
     * @param url  ?¸ì¶œ ?€??URL
     * @param body ?˜ì •???°ì´??ê°ì²´
     */
    @Operation(summary = "PUT ?¸ì¶œ", description = "ê¸°ì¡´ ?ì›???˜ì •?˜ê¸° ?„í•´ ?°ì´?°ë? ?„ì†¡?©ë‹ˆ??")
    public void put(String url, Object body) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            log.info("[RestTemplate PUT] URL: {}", url);
            restTemplate.put(url, entity);
        } catch (RestClientException e) {
            log.error("[RestTemplate ERROR] PUT {} - Message: {}", url, e.getMessage());
            throw e;
        }
    }

    /**
     * DELETE ?”ì²­ - ?•ë³´ë¥??? œ?©ë‹ˆ??
     *
     * @param url ?¸ì¶œ ?€??URL
     */
    @Operation(summary = "DELETE ?¸ì¶œ", description = "ì§€?•ëœ URL???ì›???? œ?©ë‹ˆ??")
    public void delete(String url) {
        try {
            log.info("[RestTemplate DELETE] URL: {}", url);
            restTemplate.delete(url);
        } catch (RestClientException e) {
            log.error("[RestTemplate ERROR] DELETE {} - Message: {}", url, e.getMessage());
            throw e;
        }
    }

    /**
     * DELETE ?”ì²­ (Body ?¬í•¨) - ?¤ë”?€ ë°”ë””ê°€ ?„ìš”???¹ìˆ˜ ?? œ ?”ì²­ ???¬ìš©?©ë‹ˆ??
     */
    @Operation(summary = "DELETE ?¸ì¶œ (Body ?¬í•¨)", description = "?? œ ?”ì²­ ??JSON Bodyë¥??¬í•¨?˜ì—¬ ?„ì†¡?©ë‹ˆ??")
    public <T> T delete(String url, Object body, Class<T> clazz) {
        try {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            HttpEntity<Object> entity = new HttpEntity<>(body, headers);
            log.info("[RestTemplate DELETE with Body] URL: {}", url);
            ResponseEntity<T> response = restTemplate.exchange(url, HttpMethod.DELETE, entity, clazz);
            return response.getBody();
        } catch (RestClientException e) {
            log.error("[RestTemplate ERROR] DELETE_WITH_BODY {} - Message: {}", url, e.getMessage());
            throw e;
        }
    }

    /**
     * ì»¤ìŠ¤?€ ?¤ë”ë¥??¬í•¨??? ì—°??API ?¸ì¶œ
     * @param url     ?€??URL
     * @param method  HTTP ë©”ì„œ??(GET, POST ??
     * @param headers ?„ì†¡???¤ë” Map
     * @param body    ?„ì†¡??ë³¸ë¬¸ (?†ì„ ê²½ìš° null)
     * @param clazz   ?‘ë‹µ ?€??
     */
    @Operation(summary = "ì»¤ìŠ¤?€ ?¤ë” ?¸ì¶œ", description = "?¸ì¦ ? í° ???¹ìˆ˜ ?¤ë”ë¥??¬í•¨?˜ì—¬ APIë¥??¸ì¶œ?©ë‹ˆ??")
    public <T> T exchangeWithHeaders(String url, HttpMethod method, HttpHeaders headers, Object body, Class<T> clazz) {
        if (headers.getContentType() == null) headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, method, entity, clazz).getBody();
    }
}
