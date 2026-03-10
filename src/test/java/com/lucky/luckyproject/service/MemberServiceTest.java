package com.lucky.luckyproject.service;

import com.lucky.luckyproject.domain.Member;
import com.lucky.luckyproject.dto.MemberLoginRequest;
import com.lucky.luckyproject.dto.MemberLoginResponse;
import com.lucky.luckyproject.dto.MemberSignupRequest;
import com.lucky.luckyproject.dto.UserDto;
import com.lucky.luckyproject.mapper.MemberMapper;
import com.lucky.luckyproject.security.JwtTokenProvider;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MemberServiceTest {

    @Mock
    private MemberMapper memberMapper;

    @Mock
    private JwtTokenProvider jwtTokenProvider;

    @Spy
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    @InjectMocks
    private MemberService memberService;

    @Test
    @DisplayName("??醫???泥?????醫?????醫????)
    void signupSuccess() {
        // given
        MemberSignupRequest request = MemberSignupRequest.builder()
                .name("??醫????)
                .email("test@example.com")
                .password("password123")
                .build();

        when(memberMapper.findByEmail(request.getEmail())).thenReturn(null);

        // when
        memberService.signup(request);

        // then
        verify(memberMapper, times(1)).save(any(Member.class));
        verify(passwordEncoder, times(1)).encode(anyString());
    }

    @Test
    @DisplayName("?βる?????醫?????醫????)
    void loginSuccess() {
        // given
        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .name("??醫????)
                .build();

        MemberLoginRequest request = new MemberLoginRequest();
        // Reflection ??醫猷?? ??醫?????삳????醫?δ빳???醫?θキ?????????醫????醫猷???? ??醫?←??醫???嶺??????醫????띠???醫??????셋??醫????띠???醫???爾?⑤벚? 
        // ??醫????醫?????醫?⑵??Setter?띠? ??醫????????醫??? 
        // ??醫???MemberLoginRequest??@Getter????醫???NoArgsConstructor????醫???
        // ??醫??????醫???뀀???醫???MemberLoginRequest????醫????醫?→????醫?η???醫???
        
        // Mockito??spy????醫????醫?→????醫????띠鍮?????醫?θキ?밸?Reflection??醫?δ빳???醫???
        org.springframework.test.util.ReflectionTestUtils.setField(request, "email", email);
        org.springframework.test.util.ReflectionTestUtils.setField(request, "password", password);

        when(memberMapper.findByEmail(email)).thenReturn(member);
        when(jwtTokenProvider.createToken(any(UserDto.class))).thenReturn("mock-token");

        // when
        MemberLoginResponse response = memberService.login(request);

        // then
        assertThat(response.getToken()).isEqualTo("mock-token");
        assertThat(response.getEmail()).isEqualTo(email);
        verify(passwordEncoder, times(1)).matches(anyString(), anyString());
    }
}
