package com.lucky.luckyproject.mapper;

import com.lucky.luckyproject.domain.Member;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface MemberMapper {
    void save(Member member);
    Member findByEmail(@Param("email") String email);
}
