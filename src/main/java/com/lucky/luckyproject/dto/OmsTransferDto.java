package com.lucky.luckyproject.dto;

import com.concentrix.lgintegratedadmin.config.UpperSnakeCaseStrategy;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonNaming(UpperSnakeCaseStrategy.class) // JSON 직렬화 시 대문자 스네이크 케이스(예: A04_FRT_CHG_UNIT_AMT)로 변환
public class OmsTransferDto {

    /* --- A그룹: 기타 코드 및 금액 --- */
    private BigDecimal a04FrtChgUnitAmt;   // 운임 변경 단위 금액
    private String a13SerialNo;            // 시리얼 번호 (A13)
    private String a24;                    // 기타 관리 필드 24
    private String a26;                    // 기타 관리 필드 26
    private String a30CarrierCode;         // 운송사 코드
    private BigDecimal a38DelyFeeAmt;      // 배송비 금액
    private BigDecimal a39InstallFeeAmt;   // 설치비 금액
    private BigDecimal a40CollectFeeAmt;   // 수거비 금액
    private String a50PromotionApplyFlag;  // 프로모션 적용 여부 (Y/N)
    private String a65;                    // 기타 관리 필드 65
    private String a66;                    // 기타 관리 필드 66
    private String a67;                    // 기타 관리 필드 67

    /* --- Billing: 청구지 정보 --- */
    private String billToCode;                 // 청구처 코드
    private String billingAddressLine1Info;    // 청구지 주소 1
    private String billingAddressLine2Info;    // 청구지 주소 2
    private String billingAddressLine3Info;    // 청구지 주소 3
    private String billingCityName;            // 청구지 도시명
    private String billingCompanyName;         // 청구처 회사명
    private String billingConsigneeName;       // 청구처 수취인명
    private String billingCountryCode;         // 청구지 국가 코드
    private String billingCustomerName;        // 청구처 고객명
    private String billingPhoneNo;             // 청구처 전화번호
    private String billingPostalCode;          // 청구지 우편번호
    private String billingProvinceCode;        // 청구지 주(Province) 코드
    private String billingRuc;                 // 청구처 사업자 등록번호 (남미 등)
    private String billingStateCode;           // 청구지 주(State) 코드
    private String billingTaxNumber;           // 청구처 세금 번호

    /* --- Customer: 고객 정보 --- */
    private String affiliateCode;              // 제휴사 코드
    private String buyerTaxRegistration;       // 구매자 세무 등록 번호
    private String customerBizName;            // 고객 사업자명
    private String customerBranchCode;         // 고객 지점 코드
    private String customerCountryName;        // 고객 국가명
    private String customerDebitNo;            // 고객 데빗 번호
    private String customerFirstName;          // 고객 이름 (First Name)
    private String customerLastName;           // 고객 성 (Last Name)
    private String customerName;               // 고객 전체 이름
    private String customerIdNumber;           // 고객 식별 번호
    private String customerIdNumberCheck;      // 고객 식별 번호 체크값
    private String customerIdType;             // 고객 식별 유형
    private String customerRegime;             // 고객 과세 유형 (Regime)
    private String customerTaxResp;            // 고객 세무 책임 (Tax Responsibility)
    private String customerType;               // 고객 유형 (개인/법인 등)
    private BigDecimal customerUnitPrice;      // 고객 계약 단가

    /* --- Order & Delivery: 주문 및 배송 기본 정보 --- */
    private String addressLine1Info;       // 배송지 주소 1
    private String addressLine2Info;       // 배송지 주소 2
    private String addressLine3Info;       // 배송지 주소 3
    private String addressLine4Info;       // 배송지 주소 4
    private String cityName;               // 도시명
    private String cityPostalCode;         // 도시 우편번호
    private String countryCode;            // 국가 코드
    private String deliveryTypeCode;       // 배송 유형 코드
    private String context;                // 데이터 컨텍스트 (필드셋 구분 등)
    private String contributorClass;       // 기여자 클래스 (세무 구분)
    private LocalDateTime createdDate;     // 데이터 생성 일시
    private LocalDateTime creationDate;    // 주문 생성 일시
    private String currencyCode;           // 통화 코드 (KRW, USD 등)
    private String custPoNo;               // 고객 구매 주문 번호 (PO)
    private String creditCardType;         // 신용카드 종류
    private Long cancelQty;                // 취소 수량
    private String calculationPriceFlag;   // 가격 계산 여부 플래그

    /* --- Consignee: 수취인 정보 --- */
    private String consigneeName;          // 수납인/수취인 성명
    private String consigneePhone1No;      // 수취인 연락처 1
    private String consumerPurchaseDate;   // 소비자 구매일
    private String consumerVatNo;          // 소비자 부가세 번호

    /* --- ECS & EW: 서비스 및 연장보증 --- */
    private String ecsDeliveryGrade;       // ECS 배송 등급
    private String ecsDeliveryType;        // ECS 배송 유형
    private String ewCompanyName;          // 연장보증(EW) 보험사명
    private String ewMbrNo;                // 연장보증 회원 번호
    private String ewModelCodeRel;         // 연장보증 관련 모델 코드
    private String ewSerialNumber;         // 연장보증 시리얼 번호

    /* --- Pricing & Amount: 가격 및 금액 상세 --- */
    private BigDecimal finalSourcePrice;   // 최종 원천 가격
    private BigDecimal netAmt;              // 순금액
    private BigDecimal obsDcAmount;         // OBS 할인 금액
    private BigDecimal obsListPrice;        // OBS 리스트 가격
    private BigDecimal obsSellingPrice;     // OBS 판매 가격
    private BigDecimal sourcePrice;         // 원천 가격
    private BigDecimal unitListPrice;       // 단위 리스트 가격
    private BigDecimal unitSellingPrice;    // 단위 판매 가격
    private BigDecimal commisionPerQty;     // 수량당 수수료
    private BigDecimal membershipDiscountPercentage; // 멤버십 할인율

    /* --- Installation & Flag: 설치 및 각종 플래그 --- */
    private String haulawayFlag;           // 폐가전 수거 여부 (Y/N)
    private String installationFlag;       // 설치 여부 (Y/N)
    private String installationType;       // 설치 유형
    private String invoicePrintFlag;       // 인보이스 출력 여부
    private String isMembershipFlag;       // 멤버십 회원 여부
    private String onetimeShipToFlag;      // 1회성 배송지 여부
    private String partialFlag;            // 부분 배송 여부
    private String preOrderFlag;           // 예약 주문 여부
    private String transferFlag;           // 인터페이스 전송 여부

    /* --- Inscription: 브라질 등 특정 국가 세무 정보 --- */
    private String inscriptionBranch;      // 등록 지점
    private String inscriptionDigit;       // 등록 번호 자리수
    private String inscriptionNumber;      // 등록 번호
    private String inscriptionType;        // 등록 유형
    private String minicipalInscription;   // 시 등록 번호 (Municipal Inscription)
    private String stateInscription;       // 주 등록 번호 (State Inscription)
    private String suframaInscriptionNo;   // SUFRAMA 등록 번호 (브라질 면세구역)

    /* --- Item & Warehouse: 상품 및 창고 --- */
    private String itemNo;                 // 상품 번호
    private String itemTypeCode;           // 상품 유형 코드
    private String origWarehouseCode;      // 원 창고 코드
    private String warehouseCode;          // 창고 코드
    private String subinventoryCode;       // 하위 인벤토리 코드
    private String primaryUomCode;         // 기본 단위 코드 (UOM)

    /* --- Origin Reference: 원천 주문 참조 --- */
    private String oldOrigSysLineRef;      // 이전 원천 시스템 라인 참조
    private String origSysDocumentRef;     // 원천 시스템 문서 참조
    private String origSysLineRef;         // 원천 시스템 라인 참조
    private String originalHeaderId;       // 원 주문 헤더 ID
    private String originalLineId;         // 원 주문 라인 ID

    /* --- Receiver: 실제 수령인 상세 --- */
    private String receiverCity;           // 수령인 도시
    private String receiverMobileNo;       // 수령인 휴대폰
    private String receiverName;           // 수령인 성명
    private String receiverPhoneNo;        // 수령인 전화번호
    private String receiverRegion;         // 수령인 지역
    private String receiverState;          // 수령인 주(State)
    private String mobilePhoneNo;          // 휴대폰 번호
    private String phoneNo;                // 전화번호
    private String emailAddr;              // 이메일 주소

    /* --- Logistics & System: 물류 및 시스템 제어 --- */
    private String operationCode;          // 작업 코드
    private Long orderQty;                 // 주문 수량
    private String orderSourceName;        // 주문 출처명
    private String orderSystemCode;        // 주문 시스템 코드
    private String orderType;              // 주문 유형
    private LocalDateTime orderedDate;     // 주문 일자
    private LocalDateTime lastUpdateDate;  // 최종 수정 일시
    private String paymentMethod;          // 결제 수단
    private String paymentTerm;            // 결제 조건
    private String pickingMemoText;        // 피킹 메모 (SnakeCase 유지 확인 필요)
    private String shippingMemoText;       // 배송 메모
    private String invoiceMemoText;        // 인보이스 메모
    private String postalCode;             // 우편번호
    private LocalDateTime pricingDate;     // 가격 결정 일시
    private String processStatusCode;      // 처리 상태 코드
    private String projectCode;            // 프로젝트 코드
    private String provinceCode;           // 주(Province) 코드
    private String referenceCode;          // 참조 코드
    private LocalDateTime requestDate;     // 요청 일자
    private String requestOrderType;       // 요청 주문 유형
    private String residenceTypeName;      // 거주지 유형명
    private String serviceLevelCode;       // 서비스 레벨 코드
    private String setModelCode;           // 세트 모델 코드
    private String shipMethodCode;         // 배송 방법 코드
    private String shippingMethodCode;     // 선적 방법 코드
    private String subsidiaryCode;         // 법인/자회사 코드
    private String socialSecurityNo;       // 사회보장번호 (주민번호 등)

    /* --- Ship To: 목적지 정보 --- */
    private String shipToAddress1Info;     // 목적지 주소 1
    private String shipToAddress2Info;     // 목적지 주소 2
    private String shipToCode;             // 목적지 코드
    private String shipToCountryCode;      // 목적지 국가 코드
    private String shipToCustomerEmail;    // 목적지 고객 이메일
    private String shipToCustomerName;     // 목적지 고객명
    private String shipToPostalCode;       // 목적지 우편번호
    private String shipToStateCode;        // 목적지 주(State) 코드

    /* --- Others: 일본어 이름 및 기타 --- */
    private String firstnameFurigana;      // 이름 후리가나 (일본)
    private String lastnameFurigana;       // 성 후리가나 (일본)
    private String gstNo;                  // GST 번호 (인도 등)
    private String stateCode;              // 주 코드
    private String stateName;              // 주 명칭
    private String idType;                 // 식별 ID 유형
    private String useCustSiteProfile;     // 고객 사이트 프로필 사용 여부
    private String wallmountFlag;          // 벽걸이 설치 여부
    private String vatId;                  // VAT 식별자

    /* --- V그룹: 추가 확장 필드 --- */
    private String v01; private String v08; private String v09;
    private String v10; private String v11; private String v12;
    private String v13; private String v15;
}
