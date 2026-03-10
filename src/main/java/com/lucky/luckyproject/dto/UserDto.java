package com.lucky.luckyproject.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor // MyBatis ê²°ê³¼ ì£¼ìž…???„í•´ ?„ìš”
@AllArgsConstructor // Builder ?¬ìš©???„í•´ ?„ìš”
public class UserDto {
    private String usrId;     // ?¬ìš©??ê³„ì • ID
    private String email;      // ?¬ìš©???´ë©”??
    private String usrNm;       // ?¬ìš©???´ë¦„
    private String roleGrpId;       // ?¬ìš©??ê¶Œí•œ (?? ROLE_USER, ROLE_ADMIN)
}
