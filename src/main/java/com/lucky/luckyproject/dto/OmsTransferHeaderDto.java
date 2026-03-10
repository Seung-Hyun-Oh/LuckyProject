package com.lucky.luckyproject.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OmsTransferHeaderDto {

    private String AUART;
    private String AUDAT;
    private String AUTLF;
    private String AUZET;
    private String BSTKD;
    private String BUKRS;
    private String CITY;
    private String COUNTRY; // ???ÑÎìúÎ•??¨Ïö©?òÏó¨ Ï°∞Í±¥Î∂Ä Î°úÏßÅ Ï≤òÎ¶¨
    private String KUNAG;
    private String KUNWE;
    private String LOCATION;
    private String MOB_NUMBER;
    private String NAME;
    private String ORDERED_DATE;
    private String ORDERED_TIME;
    private String PICKING_REMART;
    private String POSTL_COD1;
    private String PROCESS_FLAG;
    private String PRSDT;
    private String PS_POSID;
    private String REGION;
    private String SDABW;
    private String SHIPPING_REMARK;
    private String SO_INTERFACE_ID;
    private String STR_SUPPL1;
    private String STR_SUPPL2;
    private String STR_SUPPL3;
    private String STREET;
    private String TEL1_NUMBER;
    private String VDATU;
    private String VSART;
    private String VSNMR_V;
    private String WAERS;
    private String ZCRAGENT;
    private String ZZ1_CNSMR_BUY_DATE_SDH;
    private String ZZ1_CNSMR_EMAIL_SDH;
    private String ZZ1_CNSMR_MOBILE_SDH;
    private String ZZ1_CNSMR_NAME_SDH;
    private String ZZ1_CNSMR_PHONE_NO_SDH;
    private String ZZ1_CUST_DEBIT_NO_SDH;
    private String ZZ1_INV_PRINT_FLAG_SDH;
    private String ZZ1_REF_DOCUMENT_SDH;

    // Î∏åÎùºÏß??ÑÏö© ?ÑÎìú (COUNTRYÍ∞Ä 'BR'??Í≤ΩÏö∞ ?†Ìö®)
    private String CREDIT_CARD_TYPE;
    private String DISTRICT;
    private String BUYER_TAX_REGIST;
    private String ZZ1_CNSMR_VAT_SDH;
    private String ZTERM;

    // ?ÑÎ¶¨?Ä ?ÑÏö© ?ÑÎìú (COUNTRYÍ∞Ä 'PH'??Í≤ΩÏö∞ ?†Ìö®)
    private String PROMOTION_APPLY_FLAG;
}
