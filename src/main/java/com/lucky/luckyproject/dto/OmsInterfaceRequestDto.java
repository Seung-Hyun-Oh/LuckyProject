package com.lucky.luckyproject.dto;

import lombok.*;

import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OmsInterfaceRequestDto {

    private String interfaceId;    // ?�터?�이???�별??(?? IF_OMS_001)
    private String orderNo;        // 주문 번호
    private Map<String, Object> metadata;       // 추적??메�??�이??(JSON String ?�는 로그??

    private OmsTransferDto fixedData; // ?�제 ?�이??본체
}
