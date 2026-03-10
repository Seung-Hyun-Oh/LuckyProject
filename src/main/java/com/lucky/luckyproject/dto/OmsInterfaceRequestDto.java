package com.lucky.luckyproject.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OmsInterfaceRequestDto {

    private String interfaceId;    // 인터페이스 식별자 (예: IF_OMS_001)
    private String orderNo;        // 주문 번호
    private Map<String, Object> metadata;       // 추적용 메타데이터 (JSON String 또는 로그용)

    private OmsTransferDto fixedData; // 실제 데이터 본체
}
