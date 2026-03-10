package com.lucky.luckyproject.service;

import com.lucky.luckyproject.domain.Member;
import com.lucky.luckyproject.dto.MemberLoginRequest;
import com.lucky.luckyproject.dto.MemberLoginResponse;
import com.lucky.luckyproject.dto.MemberSignupRequest;
import com.lucky.luckyproject.dto.UserDto;
import com.lucky.luckyproject.exception.BusinessException;
import com.lucky.luckyproject.mapper.MemberMapper;
import com.lucky.luckyproject.security.JwtTokenProvider;
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
        // ì¤‘ë³µ ?´ë©”??ì²´í¬
        if (memberMapper.findByEmail(request.getEmail()) != null) {
            throw new BusinessException("?´ë? ì¡´ì¬?˜ëŠ” ?´ë©”?¼ì…?ˆë‹¤.");
        }

        // ë¹„ë?ë²ˆí˜¸ ?”í˜¸??
        String encodedPassword = passwordEncoder.encode(request.getPassword());

        // ?Œì› ?€??
        Member member = Member.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(encodedPassword)
                .build();

        memberMapper.save(member);
        log.info("?Œì›ê°€???„ë£Œ: {}", member.getEmail());
    }

    @Transactional(readOnly = true)
    public MemberLoginResponse login(MemberLoginRequest request) {
        // ?´ë©”?¼ë¡œ ?Œì› ì¡°íšŒ
        Member member = memberMapper.findByEmail(request.getEmail());
        if (member == null) {
            throw new BusinessException("ê°€?…ë˜ì§€ ?Šì? ?´ë©”?¼ì…?ˆë‹¤.");
        }

        // ë¹„ë?ë²ˆí˜¸ ?•ì¸
        if (!passwordEncoder.matches(request.getPassword(), member.getPassword())) {
            throw new BusinessException("ë¹„ë?ë²ˆí˜¸ê°€ ?¼ì¹˜?˜ì? ?ŠìŠµ?ˆë‹¤.");
        }

        // JWT ? í° ?ì„±
        UserDto userDto = UserDto.builder()
                .usrId(member.getEmail())
                .email(member.getEmail())
                .usrNm(member.getName())
                .roleGrpId("ROLE_USER")
                .build();

        String token = jwtTokenProvider.createToken(userDto);

        // Redis??? í° ?€??(Key: RT:email, Value: token, TTL: 1?œê°„)
        // ?¤ë¬´?ì„œ??ë³´í†µ Refresh Token???€?¥í•˜ì§€ë§? ?¬ê¸°?œëŠ” ?¸ì…˜ ê´€ë¦??ˆì‹œë¡?Access Token???€?¥í•¨
        redisTemplate.opsForValue().set("RT:" + member.getEmail(), token, 1, TimeUnit.HOURS);

        log.info("ë¡œê·¸???±ê³µ ë°?Redis ? í° ?€?? {}", member.getEmail());

        return MemberLoginResponse.builder()
                .token(token)
                .email(member.getEmail())
                .name(member.getName())
                .build();
    }
}
