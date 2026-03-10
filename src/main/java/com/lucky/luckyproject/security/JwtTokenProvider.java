package com.lucky.luckyproject.security;

import com.concentrix.lgintegratedadmin.dto.UserDto;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Collections;
import java.util.Date;

/**
 * JWT(JSON Web Token) 생성 및 유효성 검증을 담당하는 컴포넌트
 *
 * <p>시스템의 인증 및 인가(RBAC)를 위한 토큰 기반 보안 체계를 관리함.</p>
 * <p>AD(Active Directory) 연동 후 발급되는 사용자 정보를 기반으로 토큰을 생성함.</p>
 *
 * @since 2026.01.05
 * @author 프로젝트 담당자
 */
@Component
@Tag(name = "Security Config", description = "인증/인가 및 토큰 관리 설정")
public class JwtTokenProvider {

    @Value("${jwt.secret}")
    private String jwtSecret;

    @Schema(description = "토큰 유효 시간 (1시간)", example = "3600000")
    private final long EXPIRATION_TIME = 3600000;

    /**
     * 사용자 정보를 기반으로 JWT 토큰 생성
     *
     * @param userDto AD 및 DB에서 조회된 사용자 정보 객체
     * @return 생성된 JWT Bearer 토큰 문자열
     */
    @Operation(summary = "JWT 토큰 생성", description = "로그인 성공 시 사용자의 ID, 성명, 권한 정보를 포함한 암호화 토큰을 생성합니다.", hidden = true)
    public String createToken(UserDto userDto) {
        // 2026년 보안 권장사항: 최소 256비트 이상의 시크릿 키 사용 (HS256)
        SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        return Jwts.builder()
                .subject(userDto.getUsrId())          // Subject: 사용자 고유 식별자(ID)
                .claim("email", userDto.getEmail())   // Claim: 사용자 이메일
                .claim("name", userDto.getUsrNm())    // Claim: 사용자 성명
                .claim("role", userDto.getRoleGrpId())// Claim: 시스템 권한 그룹 ID
                .issuedAt(new Date())                 // 토큰 발행 시간
                .expiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 만료 시간 설정
                .signWith(key)                        // 지정된 키로 디지털 서명
                .compact();
    }

    /**
     * HTTP 요청 헤더에서 토큰 추출
     *
     * @param request HttpServletRequest 객체
     * @return 추출된 토큰 문자열 (없거나 형식이 맞지 않으면 null)
     */
    @Operation(summary = "Header 토큰 추출", description = "Authorization 헤더에서 'Bearer ' 접두사를 제외한 순수 토큰 문자열을 추출합니다.", hidden = true)
    public String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    /**
     * 토큰의 유효성 및 만료 여부 검증
     *
     * @param token 검증할 JWT 토큰
     * @return 유효성 여부 (true: 유효함, false: 위변조 또는 만료됨)
     */
    @Operation(summary = "토큰 유효성 검증", description = "서명 확인 및 만료 시간을 체크하여 토큰의 유효성을 검증합니다.", hidden = true)
    public boolean validateToken(String token) {
        try {
            SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
            // Jwts parser를 통한 구문 분석 및 서명 검증
            Jwts.parser().verifyWith(key).build().parseSignedClaims(token);
            return true;
        } catch (Exception e) {
            // 상세 에러 처리(ExpiredJwtException, MalformedJwtException 등) 필요 시 추가
            return false;
        }
    }

    /**
     * 토큰에서 사용자 인증 정보를 추출하여 Authentication 객체 생성
     *
     * @param token 유효한 JWT 토큰
     * @return Spring Security 인증 객체
     */
    @Operation(summary = "인증 객체 생성", description = "토큰의 Payload에서 사용자 정보를 추출하여 Spring Security 컨테이너용 인증 객체를 생성합니다.", hidden = true)
    public Authentication getAuthentication(String token) {
        String userId = Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(jwtSecret.getBytes()))
                .build()
                .parseSignedClaims(token)
                .getPayload()
                .getSubject();

        // 2026 프로젝트 표준: 권한 목록은 필요에 따라 부여 (현재는 기본 인증 처리)
        return new UsernamePasswordAuthenticationToken(userId, "", Collections.emptyList());
    }
}
