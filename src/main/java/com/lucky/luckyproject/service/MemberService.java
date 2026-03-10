package com.lucky.luckyproject.service;

import com.concentrix.lgintegratedadmin.domain.Member;
import com.concentrix.lgintegratedadmin.dto.MemberLoginRequest;
import com.concentrix.lgintegratedadmin.dto.MemberLoginResponse;
import com.concentrix.lgintegratedadmin.dto.MemberSignupRequest;
import com.concentrix.lgintegratedadmin.dto.UserDto;
import com.concentrix.lgintegratedadmin.exception.BusinessException;
import com.concentrix.lgintegratedadmin.mapper.MemberMapper;
import com.concentrix.lgintegratedadmin.security.JwtTokenProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
public class MemberService {

    private final MemberMapper memberMapper;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, Object> redisTemplate;

    @Transactional
    public void signup(MemberSignupRequest request) {
        // 중복 이메일 체크
        if (memberMapper.findByEmail(request.getEmail()) != null) {
            throw new BusinessException("이미 존재하는 이메일입니다.");
        }

        // 비밀번호 암호화
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // 회원 저장
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();

        memberMapper.save(member);
        log.info("회원가입 완료: {}", member.getEmail());
    }

    @Transactional(readOnly = true)
    public MemberLoginResponse login(MemberLoginRequest request) {
        // 이메일로 회원 조회
        Member member = memberMapper.findByEmail(request.getEmail());
        if (member == null) {
            throw new BusinessException("가입되지 않은 이메일입니다.");
        }

        // 비밀번호 확인
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException("비밀번호가 일치하지 않습니다.");
        }

        // JWT 토큰 생성
        UserDto userDto = UserDto.builder()
                .usrId(member.getEmail())
                .email(member.getEmail())
                .usrNm(member.getName())
                .roleGrpId("ROLE_USER")
                .build();

        String token = jwtTokenProvider.createToken(userDto);

        // Redis에 토큰 저장 (Key: RT:email, Value: token, TTL: 1시간)
        // 실무에서는 보통 Refresh Token을 저장하지만, 여기서는 세션 관리 예시로 Access Token을 저장함
        redisTemplate.opsForValue().set("RT:" + member.getEmail(), token, 1, TimeUnit.HOURS);

        log.info("로그인 성공 및 Redis 토큰 저장: {}", member.getEmail());

        return MemberLoginResponse.builder()
                .token(token)
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
