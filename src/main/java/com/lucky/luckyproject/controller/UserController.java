package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.dto.UserDto;
import com.concentrix.lgintegratedadmin.mapper.UserMapper;
import com.concentrix.lgintegratedadmin.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "User API", description = "사용자 데이터베이스 조회 API")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor // 생성자 주입
@Slf4j
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    /**
     * 특정 사용자 ID로 DB 정보를 조회합니다.
     */
    @Operation(summary = "사용자 조회", description = "DB에서 특정 ID를 가진 사용자 정보를 가져옵니다.")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserInfoNew(@PathVariable String userId) {
        log.info("사용자 정보 조회 요청 - ID: {}", userId);
        UserDto userInfo = userService.getUserById(userId);

        return Optional.ofNullable(userInfo)
            .map(user -> {
                log.info("사용자 조회 성공: {}", user); // 조회된 데이터 로그 출력
                log.info("user.toString(): {}", user.getUsrId());
                return ResponseEntity.ok(user);
            })
            .orElseGet(() -> {
                log.warn("사용자 조회 실패 - 존재하지 않는 ID: {}", userId); // 실패 로그
                return ResponseEntity.notFound().build();
            });
    }

    /**
     * 신규 사용자를 DB에 등록합니다.
     */
    @Operation(summary = "사용자 등록", description = "신규 사용자 정보를 DB에 저장합니다.")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userMapper.saveUser(userDto);
        return ResponseEntity.ok("사용자 등록 성공: " + userDto.getUsrId());
    }
}
