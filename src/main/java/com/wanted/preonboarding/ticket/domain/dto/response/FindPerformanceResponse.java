package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

import java.sql.Date;

public record FindPerformanceResponse(
        String performanceName, // 공연명
        int round, // 공연 회차
        Date start_date, // 시작 일시
        String isReserve // 예매 가능 여부(enable, disable)
) {
    public static FindPerformanceResponse of(final Performance enablePerformance) {
        return new FindPerformanceResponse(
                enablePerformance.getPerformanceName(),
                enablePerformance.getRound(),
                enablePerformance.getStart_date(),
                enablePerformance.getIsReserve()
        );
    }
}
