package com.lucky.luckyproject.mapper;

import com.lucky.luckyproject.dto.UserDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    // IDë¡??¬ìš©???•ë³´ ì¡°íšŒ
    UserDto findByUserId(String userId);

    // ? ê·œ ?¬ìš©???±ë¡
    void saveUser(UserDto userDto);
}
