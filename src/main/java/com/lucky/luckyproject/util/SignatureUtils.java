package com.lucky.luckyproject.util;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

/**
 * <h2>SignatureUtils</h2>
 * <p>HMAC-SHA256 알고리즘을 사용한 데이터 서명 및 검증 유틸리티 클래스입니다.</p>
 * <p>API 연동 시 데이터의 위변조를 방지하기 위한 보안 목적으로 사용됩니다.</p>
 */
@Schema(description = "서명 생성 및 검증 유틸리티")
public class SignatureUtils {

    private static final String ALGORITHM = "HmacSHA256";

    /**
     * <p>데이터와 비밀키를 결합하여 HMAC-SHA256 서명을 생성합니다.</p>
     * <p>생성된 바이트 배열은 최종적으로 <b>Base64</b> 문자열로 인코딩됩니다.</p>
     *
     * @param data 서명할 원본 데이터 (ex: JSON body, Query String 등)
     * @param key  서명에 사용할 비밀 키 (API Secret Key)
     * @return Base64로 인코딩된 서명 문자열
     * @throws RuntimeException 암호화 알고리즘 부재 또는 초기화 실패 시 발생
     */
    @Schema(description = "HMAC-SHA256 서명 생성", example = "qU7pX...v0=")
    public static String generateSignature(
            @Schema(description = "서명할 데이터") String data,
            @Schema(description = "비밀 키") String key
    ) {
        if (data == null || key == null) {
            throw new IllegalArgumentException("서명 데이터와 키는 null일 수 없습니다.");
        }

        try {
            // UTF-8 기준으로 키 바이트 추출 및 알고리즘 설정
            SecretKeySpec signingKey = new SecretKeySpec(
                    key.getBytes(StandardCharsets.UTF_8),
                    ALGORITHM
            );

            // Mac 인스턴스 획득 및 초기화
            Mac mac = Mac.getInstance(ALGORITHM);
            mac.init(signingKey);

            // HMAC 계산 수행
            byte[] rawHmac = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));

            // Base64 인코딩 결과 반환
            return Base64.getEncoder().encodeToString(rawHmac);

        } catch (Exception e) {
            // 암호화 실패 시 런타임 예외로 래핑하여 던짐
            throw new RuntimeException("서명 생성 실패: " + e.getMessage(), e);
        }
    }

    /**
     * <p>수신된 서명 값과 원본 데이터를 기반으로 생성한 서명이 일치하는지 검증합니다.</p>
     *
     * @param data      원본 데이터
     * @param key       비밀 키
     * @param signature 검증할 서명 값 (Base64 인코딩된 상태)
     * @return 서명 일치 여부 (true: 일치, false: 불일치)
     */
    @Schema(description = "서명 일치 여부 검증")
    public static boolean verifySignature(String data, String key, String signature) {
        if (signature == null) return false;
        String computedSignature = generateSignature(data, key);
        return computedSignature.equals(signature);
    }
}