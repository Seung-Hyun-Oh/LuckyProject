package com.lucky.luckyproject.service;

import com.lucky.luckyproject.dto.UserDto;
import com.lucky.luckyproject.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class UserService {

    private final UserMapper userMapper;

    public UserService(UserMapper userMapper) {
        this.userMapper = userMapper;
    }

//    public Optional<UserDto> getUserById(String userId) {
//        Optional<UserDto> UserDto = userMapper.findByUserId(userId);
//        log.info("UserDto: {}", UserDto);
//        return UserDto;
//    }

    public UserDto getUserById(String userId) {
        UserDto UserDto = userMapper.findByUserId(userId);
        log.info("UserDto: {}", UserDto);
        return UserDto;
    }
}
