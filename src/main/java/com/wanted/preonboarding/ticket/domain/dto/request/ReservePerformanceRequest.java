package com.wanted.preonboarding.ticket.domain.dto.request;

import java.util.UUID;

public record ReservePerformanceRequest(
        UUID performanceId, // 공연 아이디
        String memberName, // 예약자 이름
        String memberPhoneNumber, // 예약자 폰 번호
        long amount, // 결제 가능한 금액(잔고)
        long discount, // 할인 금액
        int round, // 회차
        int gate, // 좌석 정보
        char line, // 좌석 정보
        int seat // 좌석 정보
) {
}
