package com.lucky.luckyproject.dto;

import com.lucky.luckyproject.config.UpperSnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(UpperSnakeCaseStrategy.class) // JSON ì§ë ¬?????€ë¬¸ì ?¤ë„¤?´í¬ ì¼€?´ìŠ¤(?? A04_FRT_CHG_UNIT_AMT)ë¡?ë³€??
public class OmsTransferDto {

    /* --- Aê·¸ë£¹: ê¸°í? ì½”ë“œ ë°?ê¸ˆì•¡ --- */
    private BigDecimal a04FrtChgUnitAmt;   // ?´ì„ ë³€ê²??¨ìœ„ ê¸ˆì•¡
    private String a13SerialNo;            // ?œë¦¬??ë²ˆí˜¸ (A13)
    private String a24;                    // ê¸°í? ê´€ë¦??„ë“œ 24
    private String a26;                    // ê¸°í? ê´€ë¦??„ë“œ 26
    private String a30CarrierCode;         // ?´ì†¡??ì½”ë“œ
    private BigDecimal a38DelyFeeAmt;      // ë°°ì†¡ë¹?ê¸ˆì•¡
    private BigDecimal a39InstallFeeAmt;   // ?¤ì¹˜ë¹?ê¸ˆì•¡
    private BigDecimal a40CollectFeeAmt;   // ?˜ê±°ë¹?ê¸ˆì•¡
    private String a50PromotionApplyFlag;  // ?„ë¡œëª¨ì…˜ ?ìš© ?¬ë? (Y/N)
    private String a65;                    // ê¸°í? ê´€ë¦??„ë“œ 65
    private String a66;                    // ê¸°í? ê´€ë¦??„ë“œ 66
    private String a67;                    // ê¸°í? ê´€ë¦??„ë“œ 67

    /* --- Billing: ì²?µ¬ì§€ ?•ë³´ --- */
    private String billToCode;                 // ì²?µ¬ì²?ì½”ë“œ
    private String billingAddressLine1Info;    // ì²?µ¬ì§€ ì£¼ì†Œ 1
    private String billingAddressLine2Info;    // ì²?µ¬ì§€ ì£¼ì†Œ 2
    private String billingAddressLine3Info;    // ì²?µ¬ì§€ ì£¼ì†Œ 3
    private String billingCityName;            // ì²?µ¬ì§€ ?„ì‹œëª?
    private String billingCompanyName;         // ì²?µ¬ì²??Œì‚¬ëª?
    private String billingConsigneeName;       // ì²?µ¬ì²??˜ì·¨?¸ëª…
    private String billingCountryCode;         // ì²?µ¬ì§€ êµ?? ì½”ë“œ
    private String billingCustomerName;        // ì²?µ¬ì²?ê³ ê°ëª?
    private String billingPhoneNo;             // ì²?µ¬ì²??„í™”ë²ˆí˜¸
    private String billingPostalCode;          // ì²?µ¬ì§€ ?°í¸ë²ˆí˜¸
    private String billingProvinceCode;        // ì²?µ¬ì§€ ì£?Province) ì½”ë“œ
    private String billingRuc;                 // ì²?µ¬ì²??¬ì—…???±ë¡ë²ˆí˜¸ (?¨ë? ??
    private String billingStateCode;           // ì²?µ¬ì§€ ì£?State) ì½”ë“œ
    private String billingTaxNumber;           // ì²?µ¬ì²??¸ê¸ˆ ë²ˆí˜¸

    /* --- Customer: ê³ ê° ?•ë³´ --- */
    private String affiliateCode;              // ?œíœ´??ì½”ë“œ
    private String buyerTaxRegistration;       // êµ¬ë§¤???¸ë¬´ ?±ë¡ ë²ˆí˜¸
    private String customerBizName;            // ê³ ê° ?¬ì—…?ëª…
    private String customerBranchCode;         // ê³ ê° ì§€??ì½”ë“œ
    private String customerCountryName;        // ê³ ê° êµ??ëª?
    private String customerDebitNo;            // ê³ ê° ?°ë¹— ë²ˆí˜¸
    private String customerFirstName;          // ê³ ê° ?´ë¦„ (First Name)
    private String customerLastName;           // ê³ ê° ??(Last Name)
    private String customerName;               // ê³ ê° ?„ì²´ ?´ë¦„
    private String customerIdNumber;           // ê³ ê° ?ë³„ ë²ˆí˜¸
    private String customerIdNumberCheck;      // ê³ ê° ?ë³„ ë²ˆí˜¸ ì²´í¬ê°?
    private String customerIdType;             // ê³ ê° ?ë³„ ? í˜•
    private String customerRegime;             // ê³ ê° ê³¼ì„¸ ? í˜• (Regime)
    private String customerTaxResp;            // ê³ ê° ?¸ë¬´ ì±…ì„ (Tax Responsibility)
    private String customerType;               // ê³ ê° ? í˜• (ê°œì¸/ë²•ì¸ ??
    private BigDecimal customerUnitPrice;      // ê³ ê° ê³„ì•½ ?¨ê?

    /* --- Order & Delivery: ì£¼ë¬¸ ë°?ë°°ì†¡ ê¸°ë³¸ ?•ë³´ --- */
    private String addressLine1Info;       // ë°°ì†¡ì§€ ì£¼ì†Œ 1
    private String addressLine2Info;       // ë°°ì†¡ì§€ ì£¼ì†Œ 2
    private String addressLine3Info;       // ë°°ì†¡ì§€ ì£¼ì†Œ 3
    private String addressLine4Info;       // ë°°ì†¡ì§€ ì£¼ì†Œ 4
    private String cityName;               // ?„ì‹œëª?
    private String cityPostalCode;         // ?„ì‹œ ?°í¸ë²ˆí˜¸
    private String countryCode;            // êµ?? ì½”ë“œ
    private String deliveryTypeCode;       // ë°°ì†¡ ? í˜• ì½”ë“œ
    private String context;                // ?°ì´??ì»¨í…?¤íŠ¸ (?„ë“œ??êµ¬ë¶„ ??
    private String contributorClass;       // ê¸°ì—¬???´ë˜??(?¸ë¬´ êµ¬ë¶„)
    private LocalDateTime createdDate;     // ?°ì´???ì„± ?¼ì‹œ
    private LocalDateTime creationDate;    // ì£¼ë¬¸ ?ì„± ?¼ì‹œ
    private String currencyCode;           // ?µí™” ì½”ë“œ (KRW, USD ??
    private String custPoNo;               // ê³ ê° êµ¬ë§¤ ì£¼ë¬¸ ë²ˆí˜¸ (PO)
    private String creditCardType;         // ? ìš©ì¹´ë“œ ì¢…ë¥˜
    private Long cancelQty;                // ì·¨ì†Œ ?˜ëŸ‰
    private String calculationPriceFlag;   // ê°€ê²?ê³„ì‚° ?¬ë? ?Œë˜ê·?

    /* --- Consignee: ?˜ì·¨???•ë³´ --- */
    private String consigneeName;          // ?˜ë‚©???˜ì·¨???±ëª…
    private String consigneePhone1No;      // ?˜ì·¨???°ë½ì²?1
    private String consumerPurchaseDate;   // ?Œë¹„??êµ¬ë§¤??
    private String consumerVatNo;          // ?Œë¹„??ë¶€ê°€??ë²ˆí˜¸

    /* --- ECS & EW: ?œë¹„??ë°??°ì¥ë³´ì¦ --- */
    private String ecsDeliveryGrade;       // ECS ë°°ì†¡ ?±ê¸‰
    private String ecsDeliveryType;        // ECS ë°°ì†¡ ? í˜•
    private String ewCompanyName;          // ?°ì¥ë³´ì¦(EW) ë³´í—˜?¬ëª…
    private String ewMbrNo;                // ?°ì¥ë³´ì¦ ?Œì› ë²ˆí˜¸
    private String ewModelCodeRel;         // ?°ì¥ë³´ì¦ ê´€??ëª¨ë¸ ì½”ë“œ
    private String ewSerialNumber;         // ?°ì¥ë³´ì¦ ?œë¦¬??ë²ˆí˜¸

    /* --- Pricing & Amount: ê°€ê²?ë°?ê¸ˆì•¡ ?ì„¸ --- */
    private BigDecimal finalSourcePrice;   // ìµœì¢… ?ì²œ ê°€ê²?
    private BigDecimal netAmt;              // ?œê¸ˆ??
    private BigDecimal obsDcAmount;         // OBS ? ì¸ ê¸ˆì•¡
    private BigDecimal obsListPrice;        // OBS ë¦¬ìŠ¤??ê°€ê²?
    private BigDecimal obsSellingPrice;     // OBS ?ë§¤ ê°€ê²?
    private BigDecimal sourcePrice;         // ?ì²œ ê°€ê²?
    private BigDecimal unitListPrice;       // ?¨ìœ„ ë¦¬ìŠ¤??ê°€ê²?
    private BigDecimal unitSellingPrice;    // ?¨ìœ„ ?ë§¤ ê°€ê²?
    private BigDecimal commisionPerQty;     // ?˜ëŸ‰???˜ìˆ˜ë£?
    private BigDecimal membershipDiscountPercentage; // ë©¤ë²„??? ì¸??

    /* --- Installation & Flag: ?¤ì¹˜ ë°?ê°ì¢… ?Œë˜ê·?--- */
    private String haulawayFlag;           // ?ê????˜ê±° ?¬ë? (Y/N)
    private String installationFlag;       // ?¤ì¹˜ ?¬ë? (Y/N)
    private String installationType;       // ?¤ì¹˜ ? í˜•
    private String invoicePrintFlag;       // ?¸ë³´?´ìŠ¤ ì¶œë ¥ ?¬ë?
    private String isMembershipFlag;       // ë©¤ë²„???Œì› ?¬ë?
    private String onetimeShipToFlag;      // 1?Œì„± ë°°ì†¡ì§€ ?¬ë?
    private String partialFlag;            // ë¶€ë¶?ë°°ì†¡ ?¬ë?
    private String preOrderFlag;           // ?ˆì•½ ì£¼ë¬¸ ?¬ë?
    private String transferFlag;           // ?¸í„°?˜ì´???„ì†¡ ?¬ë?

    /* --- Inscription: ë¸Œë¼ì§????¹ì • êµ?? ?¸ë¬´ ?•ë³´ --- */
    private String inscriptionBranch;      // ?±ë¡ ì§€??
    private String inscriptionDigit;       // ?±ë¡ ë²ˆí˜¸ ?ë¦¬??
    private String inscriptionNumber;      // ?±ë¡ ë²ˆí˜¸
    private String inscriptionType;        // ?±ë¡ ? í˜•
    private String minicipalInscription;   // ???±ë¡ ë²ˆí˜¸ (Municipal Inscription)
    private String stateInscription;       // ì£??±ë¡ ë²ˆí˜¸ (State Inscription)
    private String suframaInscriptionNo;   // SUFRAMA ?±ë¡ ë²ˆí˜¸ (ë¸Œë¼ì§?ë©´ì„¸êµ¬ì—­)

    /* --- Item & Warehouse: ?í’ˆ ë°?ì°½ê³  --- */
    private String itemNo;                 // ?í’ˆ ë²ˆí˜¸
    private String itemTypeCode;           // ?í’ˆ ? í˜• ì½”ë“œ
    private String origWarehouseCode;      // ??ì°½ê³  ì½”ë“œ
    private String warehouseCode;          // ì°½ê³  ì½”ë“œ
    private String subinventoryCode;       // ?˜ìœ„ ?¸ë²¤? ë¦¬ ì½”ë“œ
    private String primaryUomCode;         // ê¸°ë³¸ ?¨ìœ„ ì½”ë“œ (UOM)

    /* --- Origin Reference: ?ì²œ ì£¼ë¬¸ ì°¸ì¡° --- */
    private String oldOrigSysLineRef;      // ?´ì „ ?ì²œ ?œìŠ¤???¼ì¸ ì°¸ì¡°
    private String origSysDocumentRef;     // ?ì²œ ?œìŠ¤??ë¬¸ì„œ ì°¸ì¡°
    private String origSysLineRef;         // ?ì²œ ?œìŠ¤???¼ì¸ ì°¸ì¡°
    private String originalHeaderId;       // ??ì£¼ë¬¸ ?¤ë” ID
    private String originalLineId;         // ??ì£¼ë¬¸ ?¼ì¸ ID

    /* --- Receiver: ?¤ì œ ?˜ë ¹???ì„¸ --- */
    private String receiverCity;           // ?˜ë ¹???„ì‹œ
    private String receiverMobileNo;       // ?˜ë ¹???´ë???
    private String receiverName;           // ?˜ë ¹???±ëª…
    private String receiverPhoneNo;        // ?˜ë ¹???„í™”ë²ˆí˜¸
    private String receiverRegion;         // ?˜ë ¹??ì§€??
    private String receiverState;          // ?˜ë ¹??ì£?State)
    private String mobilePhoneNo;          // ?´ë???ë²ˆí˜¸
    private String phoneNo;                // ?„í™”ë²ˆí˜¸
    private String emailAddr;              // ?´ë©”??ì£¼ì†Œ

    /* --- Logistics & System: ë¬¼ë¥˜ ë°??œìŠ¤???œì–´ --- */
    private String operationCode;          // ?‘ì—… ì½”ë“œ
    private Long orderQty;                 // ì£¼ë¬¸ ?˜ëŸ‰
    private String orderSourceName;        // ì£¼ë¬¸ ì¶œì²˜ëª?
    private String orderSystemCode;        // ì£¼ë¬¸ ?œìŠ¤??ì½”ë“œ
    private String orderType;              // ì£¼ë¬¸ ? í˜•
    private LocalDateTime orderedDate;     // ì£¼ë¬¸ ?¼ì
    private LocalDateTime lastUpdateDate;  // ìµœì¢… ?˜ì • ?¼ì‹œ
    private String paymentMethod;          // ê²°ì œ ?˜ë‹¨
    private String paymentTerm;            // ê²°ì œ ì¡°ê±´
    private String pickingMemoText;        // ?¼í‚¹ ë©”ëª¨ (SnakeCase ? ì? ?•ì¸ ?„ìš”)
    private String shippingMemoText;       // ë°°ì†¡ ë©”ëª¨
    private String invoiceMemoText;        // ?¸ë³´?´ìŠ¤ ë©”ëª¨
    private String postalCode;             // ?°í¸ë²ˆí˜¸
    private LocalDateTime pricingDate;     // ê°€ê²?ê²°ì • ?¼ì‹œ
    private String processStatusCode;      // ì²˜ë¦¬ ?íƒœ ì½”ë“œ
    private String projectCode;            // ?„ë¡œ?íŠ¸ ì½”ë“œ
    private String provinceCode;           // ì£?Province) ì½”ë“œ
    private String referenceCode;          // ì°¸ì¡° ì½”ë“œ
    private LocalDateTime requestDate;     // ?”ì²­ ?¼ì
    private String requestOrderType;       // ?”ì²­ ì£¼ë¬¸ ? í˜•
    private String residenceTypeName;      // ê±°ì£¼ì§€ ? í˜•ëª?
    private String serviceLevelCode;       // ?œë¹„???ˆë²¨ ì½”ë“œ
    private String setModelCode;           // ?¸íŠ¸ ëª¨ë¸ ì½”ë“œ
    private String shipMethodCode;         // ë°°ì†¡ ë°©ë²• ì½”ë“œ
    private String shippingMethodCode;     // ? ì  ë°©ë²• ì½”ë“œ
    private String subsidiaryCode;         // ë²•ì¸/?íšŒ??ì½”ë“œ
    private String socialSecurityNo;       // ?¬íšŒë³´ì¥ë²ˆí˜¸ (ì£¼ë?ë²ˆí˜¸ ??

    /* --- Ship To: ëª©ì ì§€ ?•ë³´ --- */
    private String shipToAddress1Info;     // ëª©ì ì§€ ì£¼ì†Œ 1
    private String shipToAddress2Info;     // ëª©ì ì§€ ì£¼ì†Œ 2
    private String shipToCode;             // ëª©ì ì§€ ì½”ë“œ
    private String shipToCountryCode;      // ëª©ì ì§€ êµ?? ì½”ë“œ
    private String shipToCustomerEmail;    // ëª©ì ì§€ ê³ ê° ?´ë©”??
    private String shipToCustomerName;     // ëª©ì ì§€ ê³ ê°ëª?
    private String shipToPostalCode;       // ëª©ì ì§€ ?°í¸ë²ˆí˜¸
    private String shipToStateCode;        // ëª©ì ì§€ ì£?State) ì½”ë“œ

    /* --- Others: ?¼ë³¸???´ë¦„ ë°?ê¸°í? --- */
    private String firstnameFurigana;      // ?´ë¦„ ?„ë¦¬ê°€??(?¼ë³¸)
    private String lastnameFurigana;       // ???„ë¦¬ê°€??(?¼ë³¸)
    private String gstNo;                  // GST ë²ˆí˜¸ (?¸ë„ ??
    private String stateCode;              // ì£?ì½”ë“œ
    private String stateName;              // ì£?ëª…ì¹­
    private String idType;                 // ?ë³„ ID ? í˜•
    private String useCustSiteProfile;     // ê³ ê° ?¬ì´???„ë¡œ???¬ìš© ?¬ë?
    private String wallmountFlag;          // ë²½ê±¸???¤ì¹˜ ?¬ë?
    private String vatId;                  // VAT ?ë³„??

    /* --- Vê·¸ë£¹: ì¶”ê? ?•ì¥ ?„ë“œ --- */
    private String v01; private String v08; private String v09;
    private String v10; private String v11; private String v12;
    private String v13; private String v15;
}
