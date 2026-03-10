package com.lucky.luckyproject.service;

import com.lucky.luckyproject.dto.OmsInterfaceRequestDto;
import com.lucky.luckyproject.dto.OmsTransferDto;
import com.lucky.luckyproject.util.DateUtil;
import com.lucky.luckyproject.util.RestTemplateUtil;
import com.lucky.luckyproject.util.SignatureUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class OmsApiService {

    private final RestTemplateUtil restTemplate;
    private final String API_KEY = "OMS_CLIENT_KEY";
    private final String SECRET_KEY = "YOUR_SECRET_KEY"; // ?œëª… ?ì„±???„í•œ ë¹„ë???
    private final String ENDPOINT_URL = "https://stg.shop.lg.com/rest/V1/integrated-admin/orders/revise-date";

    public void sendOrderReviseDate(OmsTransferDto omsTransferDto) {
        String timestamp = DateUtil.getCurrentDateTime();

        HashMap<String, Object> traceMetadata = new HashMap<>();
        traceMetadata.put("requested_by", "integration_service");
        traceMetadata.put("requested_at", System.currentTimeMillis());

        // 1. ?”ì²­ ?˜ì´ë¡œë“œ ì¡°ë¦½ (?µí•© DTO êµ¬ì¡°)
        OmsInterfaceRequestDto requestPayload = OmsInterfaceRequestDto.builder()
                .interfaceId("IF_OMS_REVISE_DATE_2026") // ?¸í„°?˜ì´??ID
                .orderNo(omsTransferDto.getCustPoNo())   // ì£¼ë¬¸ ë²ˆí˜¸
                .metadata(traceMetadata)    // ë©”í??°ì´??
                .fixedData(omsTransferDto)             // ë³¸ì²´ ?°ì´??
                .build();

        // 2. ?¤ë” ?¤ì • (?¸ì¦ ? í°???„ìš”??ê²½ìš° ì¶”ê?)
//        HttpHeaders headers = new HttpHeaders();
//        headers.setContentType(MediaType.APPLICATION_JSON);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X_API_KEY", API_KEY);
        headers.set("X_TIMESTAMP", timestamp);
        headers.set("X_SIGNATURE", SignatureUtils.generateSignature(timestamp, SECRET_KEY));
        // headers.setBearerAuth("YOUR_ACCESS_TOKEN"); // ? í° ?„ìš” ???œì„±??

        // 3. API ?¸ì¶œ
        try {
            ResponseEntity<Object> response = restTemplate.exchangeWithHeaders(
                    ENDPOINT_URL,
                    HttpMethod.POST,
                    headers,
                    requestPayload,
                    ResponseEntity.class
            );

            if (response.getStatusCode() == HttpStatus.OK) {
                System.out.println("?„ì†¡ ?±ê³µ: " + response.getBody());
            }
        } catch (Exception e) {
            System.err.println("API ?¸ì¶œ ì¤??¤ë¥˜ ë°œìƒ: " + e.getMessage());
            // ?ëŸ¬ ?¸ë“¤ë§?ë¡œì§
        }
    }
}
