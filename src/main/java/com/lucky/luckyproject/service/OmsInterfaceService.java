package com.lucky.luckyproject.service;

import com.concentrix.lgintegratedadmin.dto.OmsInterfaceRequestDto;
import com.concentrix.lgintegratedadmin.dto.OmsTransferDto;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.Map;

@Service
public class OmsInterfaceService {

    private final WebClient webClient = WebClient.builder()
            .baseUrl("https://stg.shop.lg.com")
            .build();

//    public OmsInterfaceRequestDto createInterfacePayload(OmsTransferDto omsData) {
//        // 1. 메타데이터 생성 (필요 시 세션 정보나 타임스탬프 등 포함)
//        Map<String, Object> traceMetadata = new HashMap<>();
//        traceMetadata.put("requested_by", "integration_service");
//        traceMetadata.put("requested_at", System.currentTimeMillis());
//
//        // 2. 통합 DTO 조립 (Lombok Builder 사용)
//        return OmsInterfaceRequestDto.builder()
//                .interfaceId("IF_OMS_TRANSFER_01")    // 인터페이스 ID 정의
//                .orderNo(omsData.getCustPoNo())       // 주문번호 매핑 (PO번호 활용)
//                .metadata(traceMetadata)              // 메타데이터 주입
//                .fixedData(omsData)                   // 원복 그대로의 DTO를 본체에 삽입
//                .build();
//    }

    public void sendOrderReviseDate(OmsTransferDto omsTransferDto) {
        // 1. 메타데이터 생성 (필요 시 세션 정보나 타임스탬프 등 포함)
        Map<String, Object> traceMetadata = new HashMap<>();
        traceMetadata.put("requested_by", "integration_service");
        traceMetadata.put("requested_at", System.currentTimeMillis());

        Object kbs = null;
        OmsTransferDto dto = (OmsTransferDto)kbs;

        OmsInterfaceRequestDto requestPayload = OmsInterfaceRequestDto.builder()
                .interfaceId("IF_OMS_REVISE_DATE_2026")
                .orderNo(omsTransferDto.getCustPoNo())
                .metadata(traceMetadata)              // 메타데이터 주입
                .fixedData(omsTransferDto)
                .build();

        webClient.post()
            .uri("/rest/V1/integrated-admin/orders/revise-date")
            .bodyValue(requestPayload)
            .retrieve()
            .bodyToMono(String.class)
            .subscribe(
                    response -> System.out.println("결과: " + response),
                    error -> System.err.println("에러: " + error.getMessage())
            );
    }
}
