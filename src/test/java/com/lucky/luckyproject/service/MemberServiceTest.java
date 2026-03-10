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
    @DisplayName("?Œì›ê°€???±ê³µ ?ŒìŠ¤??)
    void signupSuccess() {
        // given
        MemberSignupRequest request = MemberSignupRequest.builder()
                .name("?ŒìŠ¤??)
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
    @DisplayName("ë¡œê·¸???±ê³µ ?ŒìŠ¤??)
    void loginSuccess() {
        // given
        String email = "test@example.com";
        String password = "password123";
        String encodedPassword = passwordEncoder.encode(password);

        Member member = Member.builder()
                .email(email)
                .password(encodedPassword)
                .name("?ŒìŠ¤??)
                .build();

        MemberLoginRequest request = new MemberLoginRequest();
        // Reflection ?¹ì? ?¤ë¥¸ ë°©ë²•?¼ë¡œ ?„ë“œ ì£¼ì…???„ìš”?˜ì?ë§? ?¬ê¸°?œëŠ” ì§ì ‘ ?‘ê·¼ ê°€?¥í•œ êµ¬ì¡°?¼ê³  ê°€?•í•˜ê±°ë‚˜ 
        // ?ŒìŠ¤?¸ìš© ?ì„±??Setterê°€ ?„ìš”?????ˆìŒ. 
        // ?„ì¬ MemberLoginRequest??@Getterë§??ˆê³  NoArgsConstructorë§??ˆìŒ.
        // ?ŒìŠ¤???¸ì˜ë¥??„í•´ MemberLoginRequestë¥??˜ì •?˜ê±°???˜ë™ ?¤ì •.
        
        // Mockito??spyë¥??¬ìš©?˜ê±°???¤ì œ ê°ì²´???„ë“œë¥?Reflection?¼ë¡œ ?¤ì •
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
