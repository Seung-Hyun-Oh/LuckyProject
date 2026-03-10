package com.lucky.luckyproject.service;

import com.concentrix.lgintegratedadmin.dto.OmsInterfaceRequestDto;
import com.concentrix.lgintegratedadmin.dto.OmsTransferDto;
import com.concentrix.lgintegratedadmin.util.DateUtil;
import com.concentrix.lgintegratedadmin.util.RestTemplateUtil;
import com.concentrix.lgintegratedadmin.util.SignatureUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;

@Service
@RequiredArgsConstructor
public class OmsApiService {

    private final RestTemplateUtil restTemplate;
    private final String API_KEY = "OMS_CLIENT_KEY";
    private final String SECRET_KEY = "YOUR_SECRET_KEY"; // 서명 생성을 위한 비밀키
    private final String ENDPOINT_URL = "https://stg.shop.lg.com/rest/V1/integrated-admin/orders/revise-date";

    public void sendOrderReviseDate(OmsTransferDto omsTransferDto) {
        String timestamp = DateUtil.getCurrentDateTime();

        HashMap<String, Object> traceMetadata = new HashMap<>();
        traceMetadata.put("requested_by", "integration_service");
        traceMetadata.put("requested_at", System.currentTimeMillis());

        // 1. 요청 페이로드 조립 (통합 DTO 구조)
        OmsInterfaceRequestDto requestPayload = OmsInterfaceRequestDto.builder()
                .interfaceId("IF_OMS_REVISE_DATE_2026") // 인터페이스 ID
                .orderNo(omsTransferDto.getCustPoNo())   // 주문 번호
                .metadata(traceMetadata)    // 메타데이터
                .fixedData(omsTransferDto)             // 본체 데이터
                .build();

        // 2. 헤더 설정 (인증 토큰이 필요한 경우 추가)
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X_API_KEY", API_KEY);
        headers.set("X_TIMESTAMP", timestamp);
        headers.set("X_SIGNATURE", SignatureUtils.generateSignature(timestamp, SECRET_KEY));
        // headers.setBearerAuth("YOUR_ACCESS_TOKEN"); // 토큰 필요 시 활성화

        // 3. API 호출
        try {
            ResponseEntity<Object> response = restTemplate.exchangeWithHeaders(
                    ENDPOINT_URL,
                    HttpMethod.POST,
                    headers,
                    requestPayload,
                    ResponseEntity.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("전송 성공: " + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("API 호출 중 오류 발생: " + e.getMessage());
            // 에러 핸들링 로직
        }
    }
}
