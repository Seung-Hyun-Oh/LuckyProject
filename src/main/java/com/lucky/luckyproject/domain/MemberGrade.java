package com.lucky.luckyproject.domain;

public enum MemberGrade {
    BRONZE(0.01), SILVER(0.03), GOLD(0.05), VIP(0.10); // ?±ê¸‰ë³?ì¶”ê? ? ì¸??
    private final double bonusRate;
    MemberGrade(double bonusRate) { this.bonusRate = bonusRate; }
    public double getBonusRate() { return bonusRate; }
}
