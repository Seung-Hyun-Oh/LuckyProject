package com.lucky.luckyproject.controller;

import com.concentrix.lgintegratedadmin.dto.MemberLoginRequest;
import com.concentrix.lgintegratedadmin.dto.MemberLoginResponse;
import com.concentrix.lgintegratedadmin.dto.MemberSignupRequest;
import com.concentrix.lgintegratedadmin.service.MemberService;
import com.concentrix.lgintegratedadmin.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "회원 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "회원가입", description = "이름, 이메일, 비밀번호를 입력받아 회원가입을 진행합니다.")
    @PostMapping("/signup")
    public ApiResponse<String> signup(@Valid @RequestBody MemberSignupRequest request) {
        memberService.signup(request);
        return ApiResponse.success("회원가입이 완료되었습니다.");
    }

    @Operation(summary = "로그인", description = "이메일과 비밀번호를 입력받아 로그인을 진행하고 JWT 토큰을 반환합니다.")
    @PostMapping("/login")
    public ApiResponse<MemberLoginResponse> login(@Valid @RequestBody MemberLoginRequest request) {
        MemberLoginResponse response = memberService.login(request);
        return ApiResponse.success(response);
    }
}
