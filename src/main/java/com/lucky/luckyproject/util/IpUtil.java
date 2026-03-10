package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.util.StringUtils;

/**
 * 접속자의 실제 IP 주소를 추출하기 위한 유틸리티 클래스입니다.
 * 다양한 Proxy 및 Load Balancer 환경(X-Forwarded-For 등)을 지원합니다.
 *
 * @author 2025 Developer
 * @since 2025-12-24
 */
@Tag(name = "IP Utility", description = "접속자 IP 식별 및 로컬 여부 확인 도구")
public class IpUtil {

    private static final String UNKNOWN = "unknown";
    private static final String[] IP_HEADER_CANDIDATES = {
            "X-Forwarded-For",
            "Proxy-Client-IP",
            "WL-Proxy-Client-IP",
            "HTTP_CLIENT_IP",
            "HTTP_X_FORWARDED_FOR",
            "X-Real-IP",
            "X-RealIP",
            "REMOTE_ADDR"
    };

    /**
     * 요청 객체로부터 접속자의 실제 IP 주소를 추출합니다.
     * Proxy 환경일 경우 X-Forwarded-For 헤더의 첫 번째 IP를 반환합니다.
     *
     * @param request HttpServletRequest
     * @return 접속자 IP 주소 (추출 불가 시 request.getRemoteAddr())
     */
    @Operation(summary = "접속자 실제 IP 추출", description = "Proxy, L4 등을 거쳐온 경우에도 실제 클라이언트 IP를 식별합니다.")
    public static String getRemoteIp(@Parameter(hidden = true) HttpServletRequest request) {
        String ip = null;

        for (String header : IP_HEADER_CANDIDATES) {
            ip = request.getHeader(header);
            if (StringUtils.hasText(ip) && !UNKNOWN.equalsIgnoreCase(ip)) {
                // X-Forwarded-For의 경우 "client, proxy1, proxy2" 형태로 올 수 있으므로 첫 번째 IP만 추출
                if (ip.contains(",")) {
                    ip = ip.split(",")[0].trim();
                }
                break;
            }
        }

        if (!StringUtils.hasText(ip) || UNKNOWN.equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        // IPv6 로컬 주소(0:0:0:0:0:0:0:1)를 IPv4(127.0.0.1)로 가독성 있게 변환
        return "0:0:0:0:0:0:0:1".equals(ip) ? "127.0.0.1" : ip;
    }

    /**
     * 접속한 IP가 내부망(로컬) 주소인지 확인합니다.
     *
     * @param ip 확인할 IP 주소
     * @return 로컬/내부망 여부
     */
    @Operation(summary = "로컬 IP 여부 확인", description = "입력된 IP가 루프백(127.0.0.1) 또는 내부 사설망 주소인지 판별합니다.")
    public static boolean isLocalIp(String ip) {
        if (!StringUtils.hasText(ip)) return false;
        return ip.equals("127.0.0.1") || ip.equals("0:0:0:0:0:0:0:1") ||
                ip.startsWith("192.168.") || ip.startsWith("10.");
    }
}
