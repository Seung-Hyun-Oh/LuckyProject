package com.lucky.luckyproject.domain;

public enum MemberGrade {
    BRONZE(0.01), SILVER(0.03), GOLD(0.05), VIP(0.10); // 등급별 추가 할인율
    private final double bonusRate;
    MemberGrade(double bonusRate) { this.bonusRate = bonusRate; }
    public double getBonusRate() { return bonusRate; }
}
