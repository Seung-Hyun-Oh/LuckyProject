package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;

/**
 * RestTemplate을 활용한 외부 시스템 HTTP 연동 유틸리티입니다.
 * 동기 방식의 API 호출을 지원하며, 공통 헤더 설정 및 예외 로깅을 처리합니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Slf4j
@Tag(name = "RestTemplate Utility", description = "기존 RestTemplate 기반 API 연동 도구")
@Component
public class RestTemplateUtil {

    private final RestTemplate restTemplate;

    public RestTemplateUtil(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * POST 요청 - JSON 본문을 전송하여 데이터를 생성하거나 처리합니다.
     *
     * @param url   호출 대상 URL
     * @param body  요청 본문 객체
     * @param clazz 응답을 변환할 클래스 타입
     * @return 역직렬화된 응답 객체
     */
    @Operation(summary = "POST 호출", description = "JSON 형식의 데이터를 POST 방식으로 전송합니다.")
    public <T> T post(@Parameter(description = "대상 URL") String url, Object body, Class<T> clazz) {
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
     * GET 요청 - 대상 URL로부터 정보를 조회합니다.
     *
     * @param url   호출 대상 URL
     * @param clazz 응답을 변환할 클래스 타입
     * @return 역직렬화된 응답 객체
     */
    @Operation(summary = "GET 호출", description = "외부 자원을 조회합니다.")
    public <T> T get(@Parameter(description = "대상 URL") String url, Class<T> clazz) {
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
     * PUT 요청 - 정보를 수정합니다.
     *
     * @param url  호출 대상 URL
     * @param body 수정할 데이터 객체
     */
    @Operation(summary = "PUT 호출", description = "기존 자원을 수정하기 위해 데이터를 전송합니다.")
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
     * DELETE 요청 - 정보를 삭제합니다.
     *
     * @param url 호출 대상 URL
     */
    @Operation(summary = "DELETE 호출", description = "지정된 URL의 자원을 삭제합니다.")
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
     * DELETE 요청 (Body 포함) - 헤더와 바디가 필요한 특수 삭제 요청 시 사용합니다.
     */
    @Operation(summary = "DELETE 호출 (Body 포함)", description = "삭제 요청 시 JSON Body를 포함하여 전송합니다.")
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
     * 커스텀 헤더를 포함한 유연한 API 호출
     * @param url     대상 URL
     * @param method  HTTP 메서드 (GET, POST 등)
     * @param headers 전송할 헤더 Map
     * @param body    전송할 본문 (없을 경우 null)
     * @param clazz   응답 타입
     */
    @Operation(summary = "커스텀 헤더 호출", description = "인증 토큰 등 특수 헤더를 포함하여 API를 호출합니다.")
    public <T> T exchangeWithHeaders(String url, HttpMethod method, HttpHeaders headers, Object body, Class<T> clazz) {
        if (headers.getContentType() == null) headers.setContentType(MediaType.APPLICATION_JSON);

        HttpEntity<Object> entity = new HttpEntity<>(body, headers);
        return restTemplate.exchange(url, method, entity, clazz).getBody();
    }
}
