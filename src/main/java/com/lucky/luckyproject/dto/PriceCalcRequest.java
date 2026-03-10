package com.lucky.luckyproject.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Schema(description = "가�?계산 ?�청 ?�보")
public class PriceCalcRequest {
    @Schema(description = "?�품 코드", example = "PROD_001")
    private String productCode;
    @Schema(description = "기본 ?�량", example = "10")
    private int quantity;
    @Schema(description = "쿠폰 코드", example = "DISCOUNT_2026")
    private String couponCode;
}
