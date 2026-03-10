package com.lucky.luckyproject.mapper;

import com.lucky.luckyproject.domain.FcmHistory;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FcmMapper {
    /**
     * FCM ?熬?멸? ?????????
     */
    void insertFcmHistory(FcmHistory fcmHistory);
}
