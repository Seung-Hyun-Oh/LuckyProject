package com.lucky.luckyproject.service;

import com.lucky.luckyproject.dto.OmsInterfaceRequestDto;
import com.lucky.luckyproject.dto.OmsTransferDto;
import com.lucky.luckyproject.dto.OmsTransferLineDto;
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
//        // 1. ë©”í??°ì´???ì„± (?„ìš” ???¸ì…˜ ?•ë³´???€?„ìŠ¤?¬í”„ ???¬í•¨)
//        Map<String, Object> traceMetadata = new HashMap<>();
//        traceMetadata.put("requested_by", "integration_service");
//        traceMetadata.put("requested_at", System.currentTimeMillis());
//
//        // 2. ?µí•© DTO ì¡°ë¦½ (Lombok Builder ?¬ìš©)
//        return OmsInterfaceRequestDto.builder()
//                .interfaceId("IF_OMS_TRANSFER_01")    // ?¸í„°?˜ì´??ID ?•ì˜
//                .orderNo(omsData.getCustPoNo())       // ì£¼ë¬¸ë²ˆí˜¸ ë§¤í•‘ (POë²ˆí˜¸ ?œìš©)
//                .metadata(traceMetadata)              // ë©”í??°ì´??ì£¼ì…
//                .fixedData(omsData)                   // ?ë³µ ê·¸ë?ë¡œì˜ DTOë¥?ë³¸ì²´???½ì…
//                .build();
//    }

    public void sendOrderReviseDate(OmsTransferDto omsTransferDto) {
        // 1. ë©”í??°ì´???ì„± (?„ìš” ???¸ì…˜ ?•ë³´???€?„ìŠ¤?¬í”„ ???¬í•¨)
        Map<String, Object> traceMetadata = new HashMap<>();
        traceMetadata.put("requested_by", "integration_service");
        traceMetadata.put("requested_at", System.currentTimeMillis());

        Object kbs = null;
        OmsTransferDto dto = (OmsTransferDto)kbs;

        OmsInterfaceRequestDto requestPayload = OmsInterfaceRequestDto.builder()
                .interfaceId("IF_OMS_REVISE_DATE_2026")
                .orderNo(omsTransferDto.getCustPoNo())
                .metadata(traceMetadata)              // ë©”í??°ì´??ì£¼ì…
                .fixedData(omsTransferDto)
                .build();

        webClient.post()
            .uri("/rest/V1/integrated-admin/orders/revise-date")
            .bodyValue(requestPayload)
            .retrieve()
            .bodyToMono(String.class)
            .subscribe(
                    response -> System.out.println("ê²°ê³¼: " + response),
                    error -> System.err.println("?ëŸ¬: " + error.getMessage())
            );
    }
}
