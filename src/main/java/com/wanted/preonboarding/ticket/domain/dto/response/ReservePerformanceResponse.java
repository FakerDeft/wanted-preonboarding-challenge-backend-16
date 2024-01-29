package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;

import java.util.UUID;

public record ReservePerformanceResponse(
        UUID performanceId, // 공연 아이디
        String name, // 예약자 이름
        String phoneNumber, // 예약자 폰 번호
        String performanceName, // 공연명
        int round, // 공연 회차
        int gate, // 좌석 정보
        char line, // 좌석 정보
        int seat // 좌석 정보
) {
    public static ReservePerformanceResponse of(
            final Reservation reservationInfo,
            final Performance performanceInfo
    ) {
        return new ReservePerformanceResponse(
                performanceInfo.getId(),
                reservationInfo.getName(),
                reservationInfo.getPhoneNumber(),
                performanceInfo.getName(),
                performanceInfo.getRound(),
                reservationInfo.getGate(),
                reservationInfo.getLine(),
                reservationInfo.getSeat()
        );
    }
}
