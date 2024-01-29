package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;

import java.util.UUID;

public record FindReservationResponse(
        UUID performanceId, // 공연 아이디
        String memberName, // 예약자 이름
        String memberPhoneNumber, // 예약자 폰 번호
        String performanceName, // 공연명
        int round, // 공연 회차
        int gate, // 좌석 정보
        char line, // 좌석 정보
        int seat // 좌석 정보
) {
    public static FindReservationResponse of(
            final Reservation reservationInfo,
            final Performance performanceInfo
    ) {
        return new FindReservationResponse(
                performanceInfo.getPerformanceId(),
                reservationInfo.getMemberName(),
                reservationInfo.getMemberPhoneNumber(),
                performanceInfo.getPerformanceName(),
                performanceInfo.getRound(),
                reservationInfo.getGate(),
                reservationInfo.getLine(),
                reservationInfo.getSeat()
        );
    }
}
