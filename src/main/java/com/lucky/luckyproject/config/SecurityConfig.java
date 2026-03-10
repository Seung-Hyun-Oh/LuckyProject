package com.lucky.luckyproject.config;

import com.concentrix.lgintegratedadmin.security.JwtAuthenticationFilter;
import com.concentrix.lgintegratedadmin.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.opensaml.saml.saml2.core.Assertion;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.saml2.provider.service.authentication.OpenSaml5AuthenticationProvider;
import org.springframework.security.saml2.provider.service.authentication.Saml2Authentication;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.IpAddressMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

/**
 * 2025-2026년 최신 표준: AD(SAML2) 최초 로그인 및 JWT 지속 인증 통합 설정
 */
@Slf4j
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtTokenProvider jwtTokenProvider;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            // 1. 보안 기초 설정
            .csrf(AbstractHttpConfigurer::disable) // REST API 기반이므로 CSRF 보안을 비활성화 (토큰 방식을 사용하기 때문)
            .cors(cors -> cors.configurationSource(corsConfigurationSource()))                              // CORS 커스텀 설정 적용
            // 세션을 서버에 저장하지 않는 STATELESS 정책 설정 (JWT 사용 필수 조건)
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

            // 2. 인가(Authorization) 규칙
            .authorizeHttpRequests(auth -> auth
                // [로컬 전용] 암호화 API 보호
                .requestMatchers("/api/crypto/**").access((authentication, context) -> {
                    String remoteAddress = context.getRequest().getRemoteAddr();
                    // [IP 기반 제한] 암호화 API는 로컬(127.0.0.1) 호출만 허용
                    boolean isLocal = new IpAddressMatcher("127.0.0.1").matches(remoteAddress) ||
                            new IpAddressMatcher("::1").matches(remoteAddress);
                    return new AuthorizationDecision(isLocal);
                })
                // 특정 URL 패턴에 대한 접근 허용 규칙 정의
                .requestMatchers("/user/**").hasRole("USER") // 'USER' 역할을 가진 사용자만 접근 허용
                .requestMatchers("/admin/**").hasRole("ADMIN") // 'ADMIN' 역할을 가진 사용자만 접근 허용
                .requestMatchers("/shared/**").hasAnyRole("USER", "ADMIN") // 'USER' 또는 'ADMIN' 역할 접근 허용
                // 허용 경로 / 인증 없이 접근 가능한 공개 경로 설정 (API, Swagger, SAML 관련)
                .requestMatchers("/api/members/signup", "/api/members/login").permitAll()
                .requestMatchers("/api/**").permitAll()
                .requestMatchers("/v3/api-docs/**"
                    , "/swagger-ui/**"
                    , "/swagger-ui.html"
                    , "/login/**"
                    , "/saml2/**").permitAll()
                // 그 외 API는 JWT 인증 필터를 통해 걸러짐
                .anyRequest().authenticated()
            )

//            // 3. AD(SAML2) 로그인 프로세스 설정
              /* 3. AD(SAML2) 로그인 프로세스 설정 (현재 주석 처리됨)
               - SAML 인증 성공 시 JWT를 생성하여 클라이언트에게 발급하는 로직 포함
               - successHandler: 인증 성공 후 AD 정보를 추출하여 JWT 토큰으로 변환 후 응답
              */
//            .saml2Login(saml2 -> saml2
//                .authenticationManager(new ProviderManager(Collections.singletonList(saml2AuthenticationProvider())))
//                .successHandler((request, response, authentication) -> {
//                    Saml2AuthenticatedPrincipal principal = (Saml2AuthenticatedPrincipal) authentication.getPrincipal();
//                    String userId = principal.getName();
//
//                    // AD Claim URI 매핑 보완 (실제 전체 URI 사용 권장)
//                    String email = principal.getFirstAttribute("schemas.xmlsoap.org");
//                    if (email == null) email = principal.getFirstAttribute("email");
//
//                    String name = principal.getFirstAttribute("schemas.xmlsoap.org");
//                    if (name == null) name = principal.getFirstAttribute("name");
//
//                    // JWT 발급용 DTO 생성
//                    UserDto userDto = UserDto.builder()
//                        .usrId(userId)
//                        .email(email != null ? email : "Unknown")
//                        .usrNm(name != null ? name : "Unknown")
//                        .roleGrpId("ROLE_USER")
//                        .build();
//
//                    String token = jwtTokenProvider.createToken(userDto);
//                    log.info("AD 인증 성공 [ID: {}] -> JWT 발급 완료", userId);
//
//                    // 응답 본문에 토큰 반환
//                    response.setContentType("application/json;charset=UTF-8");
//                    response.setHeader("Authorization", "Bearer " + token);
//                    response.getWriter().write("{\"token\": \"" + token + "\", \"user\": \"" + userId + "\"}");
//                })
//            )

            // 4. [중요] JWT 필터 배치
            // UsernamePasswordAuthenticationFilter 이전에 두어 토큰 기반 인증을 먼저 수행하게 함
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }

    /**
     * AD(SAML2) 응답 검증 및 데이터 추출을 위한 프로바이더
     * OpenSaml5를 사용하여 SAML Assertion에서 사용자 정보를 추출함
     */
    @Bean
    public AuthenticationProvider saml2AuthenticationProvider() {
        OpenSaml5AuthenticationProvider provider = new OpenSaml5AuthenticationProvider();

        // SAML 응답(Response)을 받았을 때 실행될 컨버터 설정
        provider.setResponseAuthenticationConverter(responseToken -> {
            // 기본 SAML 인증 객체 생성
            Saml2Authentication authentication = OpenSaml5AuthenticationProvider
                .createDefaultResponseAuthenticationConverter()
                .convert(responseToken);

            if (authentication == null) return null;

            try {
                // SAML 데이터에서 실제 사용자 ID(NameID)를 추출하는 로직
                Assertion assertion = responseToken.getResponse().getAssertions().get(0);
                String userId = assertion.getSubject().getNameID().getValue();
                log.debug("SAML Assertion 수신 (사용자 ID): {}", userId);
            } catch (Exception e) {
                log.error("SAML 데이터 파싱 중 오류 발생: {}", e.getMessage());
            }

            return authentication;
        });
        return provider;
    }


    /**
     * CORS에 대한 설정을 커스텀으로 구성합니다.
     * @return
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(List.of("http://localhost:3000"));    // 허용할 오리진
        configuration.setAllowedMethods(List.of("*"));                          // 허용할 HTTP 메서드
        configuration.setAllowedHeaders(List.of("*"));                          // 모든 헤더 허용
        configuration.setAllowCredentials(true);                                    // 인증 정보 허용
        configuration.setMaxAge(3600L);                                             // 프리플라이트 요청 결과를 3600초 동안 캐시
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);             // 모든 경로에 대해 이 설정 적용
        return source;
    }
}
