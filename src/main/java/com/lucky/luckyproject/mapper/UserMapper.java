package com.lucky.luckyproject.mapper;

import com.concentrix.lgintegratedadmin.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // ID로 사용자 정보 조회
    UserDto findByUserId(String userId);

    // 신규 사용자 등록
    void saveUser(UserDto userDto);
}
