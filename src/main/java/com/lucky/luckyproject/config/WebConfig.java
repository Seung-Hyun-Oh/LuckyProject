package com.lucky.luckyproject.config;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * 시스템 공통 웹 통신 설정을 위한 Configuration 클래스
 *
 * <p>외부 API(EP, PG, AD 등) 연동을 위한 RestTemplate 및 WebClient 빈을 정의함.</p>
 *
 * @since 2025.12.22
 * @version 1.0
 */
@Configuration
@Tag(name = "Infrastructure Config", description = "시스템 인프라 및 네트워크 통신 설정")
public class WebConfig {

    /**
     * REST 기반 동기 API 호출을 위한 RestTemplate 빈 등록
     *
     * <p>Legacy 시스템 연동 및 단순 REST API 호출에 사용함.</p>
     *
     * @return RestTemplate 인스턴스
     */
    @Bean
    @Operation(summary = "RestTemplate 빈 등록", description = "동기 방식의 HTTP 통신을 위한 RestTemplate 객체를 생성합니다.", hidden = true)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Non-blocking 기반 통신을 위한 WebClient.Builder 빈 등록
     *
     * <p>Spring Boot 3.4 환경의 표준 통신 모듈로, 확장성을 고려한 비차단 I/O 지원.</p>
     * <p>ApiUtil 유틸리티 클래스에서 주입받아 사용됨.</p>
     *
     * @return WebClient.Builder 인스턴스
     */
    @Bean
    @Operation(summary = "WebClient Builder 등록", description = "현대적 API 연동을 위한 WebClient 빌더를 생성합니다.", hidden = true)
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RestClient restClient() {
        // 2025년 기준 JDK HttpClient 기반의 커넥션 타임아웃 설정
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory())
                .defaultHeader("Accept", "application/json")
                .build();
    }
}