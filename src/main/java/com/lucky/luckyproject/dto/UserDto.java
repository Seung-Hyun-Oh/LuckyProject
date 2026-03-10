package com.lucky.luckyproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor // MyBatis 결과 주입을 위해 필요
@AllArgsConstructor // Builder 사용을 위해 필요
public class UserDto {
    private String usrId;     // 사용자 계정 ID
    private String email;      // 사용자 이메일
    private String usrNm;       // 사용자 이름
    private String roleGrpId;       // 사용자 권한 (예: ROLE_USER, ROLE_ADMIN)
}
