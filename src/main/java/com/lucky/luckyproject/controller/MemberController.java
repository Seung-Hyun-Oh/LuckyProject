package com.lucky.luckyproject.controller;

import com.lucky.luckyproject.dto.MemberLoginRequest;
import com.lucky.luckyproject.dto.MemberLoginResponse;
import com.lucky.luckyproject.dto.MemberSignupRequest;
import com.lucky.luckyproject.service.MemberService;
import com.lucky.luckyproject.util.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "?Œì› ê´€??API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/members")
public class MemberController {

    private final MemberService memberService;

    @Operation(summary = "?Œì›ê°€??, description = "?´ë¦„, ?´ë©”?? ë¹„ë?ë²ˆí˜¸ë¥??…ë ¥ë°›ì•„ ?Œì›ê°€?…ì„ ì§„í–‰?©ë‹ˆ??")
    @PostMapping("/signup")
    public ApiResponse<String> signup(@Valid @RequestBody MemberSignupRequest request) {
        memberService.signup(request);
        return ApiResponse.success("?Œì›ê°€?…ì´ ?„ë£Œ?˜ì—ˆ?µë‹ˆ??");
    }

    @Operation(summary = "ë¡œê·¸??, description = "?´ë©”?¼ê³¼ ë¹„ë?ë²ˆí˜¸ë¥??…ë ¥ë°›ì•„ ë¡œê·¸?¸ì„ ì§„í–‰?˜ê³  JWT ? í°??ë°˜í™˜?©ë‹ˆ??")
    @PostMapping("/login")
    public ApiResponse<MemberLoginResponse> login(@Valid @RequestBody MemberLoginRequest request) {
        MemberLoginResponse response = memberService.login(request);
        return ApiResponse.success(response);
    }
}
