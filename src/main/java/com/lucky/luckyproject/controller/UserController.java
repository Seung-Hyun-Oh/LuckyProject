package com.lucky.luckyproject.controller;

import com.lucky.luckyproject.dto.UserDto;
import com.lucky.luckyproject.mapper.UserMapper;
import com.lucky.luckyproject.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Tag(name = "User API", description = "?¬ìš©???°ì´?°ë² ?´ìŠ¤ ì¡°íšŒ API")
@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor // ?ì„±??ì£¼ì…
@Slf4j
public class UserController {

    private final UserMapper userMapper;
    private final UserService userService;

    /**
     * ?¹ì • ?¬ìš©??IDë¡?DB ?•ë³´ë¥?ì¡°íšŒ?©ë‹ˆ??
     */
    @Operation(summary = "?¬ìš©??ì¡°íšŒ", description = "DB?ì„œ ?¹ì • IDë¥?ê°€ì§??¬ìš©???•ë³´ë¥?ê°€?¸ì˜µ?ˆë‹¤.")
    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUserInfoNew(@PathVariable String userId) {
        log.info("?¬ìš©???•ë³´ ì¡°íšŒ ?”ì²­ - ID: {}", userId);
        UserDto userInfo = userService.getUserById(userId);

        return Optional.ofNullable(userInfo)
            .map(user -> {
                log.info("?¬ìš©??ì¡°íšŒ ?±ê³µ: {}", user); // ì¡°íšŒ???°ì´??ë¡œê·¸ ì¶œë ¥
                log.info("user.toString(): {}", user.getUsrId());
                return ResponseEntity.ok(user);
            })
            .orElseGet(() -> {
                log.warn("?¬ìš©??ì¡°íšŒ ?¤íŒ¨ - ì¡´ì¬?˜ì? ?ŠëŠ” ID: {}", userId); // ?¤íŒ¨ ë¡œê·¸
                return ResponseEntity.notFound().build();
            });
    }

    /**
     * ? ê·œ ?¬ìš©?ë? DB???±ë¡?©ë‹ˆ??
     */
    @Operation(summary = "?¬ìš©???±ë¡", description = "? ê·œ ?¬ìš©???•ë³´ë¥?DB???€?¥í•©?ˆë‹¤.")
    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody UserDto userDto) {
        userMapper.saveUser(userDto);
        return ResponseEntity.ok("?¬ìš©???±ë¡ ?±ê³µ: " + userDto.getUsrId());
    }
}
