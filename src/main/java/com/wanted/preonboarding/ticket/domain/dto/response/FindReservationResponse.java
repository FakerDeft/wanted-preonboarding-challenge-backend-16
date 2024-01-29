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
            final Reservation reservation,
            final Performance performance
    ) {
        return new FindReservationResponse(
                performance.getPerformanceId(),
                reservation.getMemberName(),
                reservation.getMemberPhoneNumber(),
                performance.getPerformanceName(),
                performance.getRound(),
                reservation.getGate(),
                reservation.getLine(),
                reservation.getSeat()
        );
    }
}
