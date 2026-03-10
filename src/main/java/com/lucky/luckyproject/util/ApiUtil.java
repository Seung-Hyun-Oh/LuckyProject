package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * 외부 시스템 연동을 위한 REST API 공통 유틸리티
 *
 * <p>Spring WebFlux의 WebClient를 사용하여 Non-blocking 및 Blocking 통신을 지원함.</p>
 * <p>EP 통합결제 시스템의 대외 인터페이스(PG사, 카드사 연동 등) 표준 모듈로 활용.</p>
 *
 * @since 1.8 (Spring Framework 5.0 이상)
 * @version 1.2 (2025.12.18 JDK 17 최적화 및 타임아웃 고도화)
 */
@Component
@RequiredArgsConstructor
@Schema(description = "REST API 호출 유틸리티")
public class ApiUtil {

    private final WebClient.Builder webClientBuilder;

    /** 기본 타임아웃 설정 (10초) */
    private static final int DEFAULT_TIMEOUT = 10;

    /**
     * POST 방식 동기 호출 (JSON 규격)
     *
     * @param <T> 요청 바디 타입
     * @param <R> 응답 바디 타입
     * @param url 호출 대상 서버 URL
     * @param body 요청 객체 (DTO)
     * @param responseType 응답 받을 클래스 타입
     * @return API 응답 결과 객체
     * @throws RuntimeException API 호출 실패 또는 타임아웃 발생 시
     */
    public <T, R> R post(String url, T body, Class<R> responseType) {
        return webClientBuilder.build()
            .post()
            .uri(url)
            .contentType(MediaType.APPLICATION_JSON)
            .bodyValue(body)
            .retrieve()
            .bodyToMono(responseType)
            .block(Duration.ofSeconds(DEFAULT_TIMEOUT)); // 동기식 변환 (결제 승인 등 결과 대기 필요 시)
    }

    /**
     * 외부 API 비동기 POST 호출
     *
     * <p>WebClient를 사용하여 논블로킹(Non-blocking) 방식으로 요청을 보냅니다.
     * 호출 즉시 {@link Mono}를 반환하며, 실제 응답 처리는 결과가 도착하는 시점에 비동기로 이루어집니다.</p>
     *
     * @param url          요청 타겟 URL
     * @param body         HTTP 요청 바디에 포함할 객체 (JSON으로 직렬화됨)
     * @param responseType 응답 바디를 변환할 대상 클래스 타입
     * @param <T>          요청 바디 객체의 타입
     * @param <R>          응답 바디 객체의 타입
     * @return 응답 데이터를 담은 Mono 객체 (비동기 스트림)
     */
    public <T, R> Mono<R> postAsync(String url, T body, Class<R> responseType) {
        return webClientBuilder.build()
            .post()                             // 1. HTTP POST 메소드 설정
            .uri(url)                           // 2. 타겟 URL 설정
            .contentType(MediaType.APPLICATION_JSON) // 3. 전송 데이터 형식을 JSON으로 설정
            .bodyValue(body)                    // 4. 요청 바디(Body) 데이터 설정
            .retrieve()                         // 5. 요청 실행 및 응답 수신 시작
            .bodyToMono(responseType)           // 6. 응답 바디를 단일 객체(Mono)로 변환
            .timeout(Duration.ofSeconds(DEFAULT_TIMEOUT)); // 7. 설정된 시간 내 응답이 없으면 Timeout 예외 발생
    }

    /**
     * GET 방식 동기 호출
     *
     * @param <R> 응답 바디 타입
     * @param url 호출 대상 서버 URL
     * @param responseType 응답 받을 클래스 타입
     * @return API 응답 결과 객체
     */
    public <R> R get(String url, Class<R> responseType) {
        return webClientBuilder.build()
                .get()
                .uri(url)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(responseType)
                .block(Duration.ofSeconds(DEFAULT_TIMEOUT));
    }
}
