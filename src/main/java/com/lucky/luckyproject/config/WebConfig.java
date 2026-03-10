package com.lucky.luckyproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.JdkClientHttpRequestFactory;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestTemplate;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.reactive.function.client.WebClient;

/**
 * ?œìŠ¤??ê³µí†µ ???µì‹  ?¤ì •???„í•œ Configuration ?´ë˜??
 *
 * <p>?¸ë? API(EP, PG, AD ?? ?°ë™???„í•œ RestTemplate ë°?WebClient ë¹ˆì„ ?•ì˜??</p>
 *
 * @since 2025.12.22
 * @version 1.0
 */
@Configuration
@Tag(name = "Infrastructure Config", description = "?œìŠ¤???¸í”„??ë°??¤íŠ¸?Œí¬ ?µì‹  ?¤ì •")
public class WebConfig {

    /**
     * REST ê¸°ë°˜ ?™ê¸° API ?¸ì¶œ???„í•œ RestTemplate ë¹??±ë¡
     *
     * <p>Legacy ?œìŠ¤???°ë™ ë°??¨ìˆœ REST API ?¸ì¶œ???¬ìš©??</p>
     *
     * @return RestTemplate ?¸ìŠ¤?´ìŠ¤
     */
    @Bean
    @Operation(summary = "RestTemplate ë¹??±ë¡", description = "?™ê¸° ë°©ì‹??HTTP ?µì‹ ???„í•œ RestTemplate ê°ì²´ë¥??ì„±?©ë‹ˆ??", hidden = true)
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * Non-blocking ê¸°ë°˜ ?µì‹ ???„í•œ WebClient.Builder ë¹??±ë¡
     *
     * <p>Spring Boot 3.4 ?˜ê²½???œì? ?µì‹  ëª¨ë“ˆë¡? ?•ì¥?±ì„ ê³ ë ¤??ë¹„ì°¨??I/O ì§€??</p>
     * <p>ApiUtil ? í‹¸ë¦¬í‹° ?´ë˜?¤ì—??ì£¼ì…ë°›ì•„ ?¬ìš©??</p>
     *
     * @return WebClient.Builder ?¸ìŠ¤?´ìŠ¤
     */
    @Bean
    @Operation(summary = "WebClient Builder ?±ë¡", description = "?„ë???API ?°ë™???„í•œ WebClient ë¹Œë”ë¥??ì„±?©ë‹ˆ??", hidden = true)
    public WebClient.Builder webClientBuilder() {
        return WebClient.builder();
    }

    @Bean
    public RestClient restClient() {
        // 2025??ê¸°ì? JDK HttpClient ê¸°ë°˜??ì»¤ë„¥???€?„ì•„???¤ì •
        return RestClient.builder()
                .requestFactory(new JdkClientHttpRequestFactory())
                .defaultHeader("Accept", "application/json")
                .build();
    }
}
