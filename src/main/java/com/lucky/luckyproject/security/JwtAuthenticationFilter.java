package com.lucky.luckyproject.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

/**
 * JWT 인증 필터
 * OncePerRequestFilter를 상속받아 모든 요청당 단 한 번만 실행됨을 보장합니다.
 */
@Slf4j
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtTokenProvider jwtTokenProvider;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        try {
            // 1. 요청 헤더(Authorization)에서 "Bearer {token}" 추출
            String token = jwtTokenProvider.resolveToken(request);

            // 2. 토큰이 존재하고 유효성 검사(만료일, 서명 등)를 통과하면 인증 처리
            if (token != null && jwtTokenProvider.validateToken(token)) {
                // 토큰 내부 정보를 기반으로 Authentication(인증) 객체 생성
                Authentication authentication = jwtTokenProvider.getAuthentication(token);

                // 현재 요청 스레드의 SecurityContext에 인증 정보 저장 (이후 Controller 등에서 사용 가능)
                SecurityContextHolder.getContext().setAuthentication(authentication);

                log.debug("JWT 토큰 인증 성공: {}", authentication.getName());
            }
        } catch (Exception e) {
            // 토큰이 잘못되었거나 파싱 중 예외가 발생해도, 정적 리소스/공개 엔드포인트까지 500으로 터뜨리지 않도록 방어합니다.
            // 보호 API에서의 최종 401/403 처리는 Spring Security의 인가 설정에서 맡기는 것이 장기적으로 유지보수에 유리합니다.
            SecurityContextHolder.clearContext();
            log.debug("JWT 처리 중 예외 발생 (요청은 익명으로 계속 진행): method={}, uri={}, msg={}",
                    request.getMethod(), request.getRequestURI(), e.getMessage());
        }
        // 3. 다음 필터로 요청 전달
        filterChain.doFilter(request, response);
    }

    /**
     * 필터링을 거치지 않을 경로 설정
     * 2026년 표준: 로그인이 필요한 서비스 외의 공개 API는 필터 실행 자체를 방지하여 성능 향상
     */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String method = request.getMethod();
        if ("OPTIONS".equalsIgnoreCase(method)) {
            // CORS preflight는 인증 없이 통과해야 정상 동작합니다.
            return true;
        }

        String path = request.getServletPath();
        // 로그인, 회원가입, Swagger UI 등은 JWT 검증 제외
        boolean bool = path.startsWith("/login") || path.startsWith("/signup") || path.startsWith("/swagger-ui");

        bool = bool || path.startsWith("/v3/api-docs");
        bool = bool || path.startsWith("/saml2");
        bool = bool || path.startsWith("/crypto");

        return bool;
    }
}
