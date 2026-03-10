package com.lucky.luckyproject.util;

import com.concentrix.lgintegratedadmin.exception.BusinessException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;

import java.util.Map;

/**
 * Spring 6.1+의 RestClient를 활용한 외부 시스템 연동 유틸리티입니다.
 * 2025년 기준 RestTemplate의 대안으로 권장되는 최신 동기식 HTTP 클라이언트 라이브러리입니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Slf4j
@Tag(name = "Rest Client Utility", description = "외부 시스템 API 연동 도구 (Spring RestClient 기반)")
@Component
@RequiredArgsConstructor
public class RestClientUtil {

    private final RestClient restClient;

    /**
     * GET 요청 - Query 파라미터 및 헤더를 포함하여 데이터를 조회합니다.
     *
     * @param url          API 엔드포인트 URL
     * @param queryParams  URL 쿼리 파라미터 Map
     * @param responseType 반환 객체 클래스 타입
     * @return 역직렬화된 응답 객체
     */
    @Operation(summary = "GET API 호출", description = "외부 시스템의 자원을 조회합니다.")
    public <T> T get(
            @Parameter(description = "호출 URL") String url,
            @Parameter(description = "쿼리 파라미터") Map<String, String> queryParams,
            Class<T> responseType) {

        log.info("[API GET REQUEST] URL: {}, Params: {}", url, queryParams);

        return restClient.get()
            .uri(uriBuilder -> {
                uriBuilder.path(url);
                if (queryParams != null) {
                    queryParams.forEach(uriBuilder::queryParam);
                }
                return uriBuilder.build();
            })
            .accept(MediaType.APPLICATION_JSON)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (req, res) -> handleApiError(url, res.getStatusCode()))
            .body(responseType);
    }

    /**
     * POST 요청 - JSON 본문을 전송하여 새로운 자원을 생성합니다.
     *
     * @param url          API 엔드포인트 URL
     * @param body         전송할 Request Body 객체
     * @param responseType 반환 객체 클래스 타입
     */
    @Operation(summary = "POST API 호출", description = "JSON 본문을 포함하여 외부 API에 자원 생성을 요청합니다.")
    public <T> T post(String url, Object body, Class<T> responseType) {
        log.info("[API POST REQUEST] URL: {}", url);

        return restClient.post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (req, res) -> handleApiError(url, res.getStatusCode()))
            .body(responseType);
    }

    /**
     * PUT 요청 - JSON 본문을 전송하여 기존 자원을 수정합니다.
     */
    @Operation(summary = "PUT API 호출", description = "외부 API의 기존 자원을 수정합니다.")
    public <T> T put(String url, Object body, Class<T> responseType) {
        log.info("[API PUT REQUEST] URL: {}", url);

        return restClient.put()
                .uri(url)
                .contentType(MediaType.APPLICATION_JSON)
                .body(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, (req, res) -> handleApiError(url, res.getStatusCode()))
                .body(responseType);
    }

    /**
     * DELETE 요청 - 특정 자원의 삭제를 요청합니다.
     */
    @Operation(summary = "DELETE API 호출", description = "외부 API의 특정 자원 삭제를 요청합니다.")
    public void delete(String url) {
        log.info("[API DELETE REQUEST] URL: {}", url);

        restClient.delete()
            .uri(url)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (req, res) -> handleApiError(url, res.getStatusCode()))
            .toBodilessEntity();
    }

    /**
     * 커스텀 헤더를 포함한 POST 요청
     * 외부 시스템 인증(API Key, Bearer Token 등)이 필요한 경우 사용합니다.
     */
    @Operation(summary = "Header 포함 POST 호출", description = "인증 토큰 등 커스텀 헤더를 포함하여 호출합니다.")
    public <T> T postWithHeaders(String url, Map<String, String> headers, Object body, Class<T> responseType) {
        return restClient.post()
            .uri(url)
            .headers(httpHeaders -> headers.forEach(httpHeaders::add))
            .contentType(MediaType.APPLICATION_JSON)
            .body(body)
            .retrieve()
            .onStatus(HttpStatusCode::isError, (req, res) -> handleApiError(url, res.getStatusCode()))
            .body(responseType);
    }

    /**
     * 공통 에러 핸들러
     * HTTP 4xx, 5xx 에러 발생 시 로그를 기록하고 프로젝트 전역 예외로 변환합니다.
     */
    private void handleApiError(String url, HttpStatusCode status) {
        log.error("[API ERROR] URL: {}, Status Code: {}", url, status);
        // 실무에서는 비즈니스 에러 코드를 정의한 CustomException 사용 권장
        throw new BusinessException("외부 시스템 연동 오류 (" + status.value() + ") - " + url);
    }
}
