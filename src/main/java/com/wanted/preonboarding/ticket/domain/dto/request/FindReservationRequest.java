package com.wanted.preonboarding.ticket.domain.dto.request;

public record FindReservationRequest(
        String name, // 예약자 이름
        String phoneNumber // 예약자 폰 번호
) {
}
