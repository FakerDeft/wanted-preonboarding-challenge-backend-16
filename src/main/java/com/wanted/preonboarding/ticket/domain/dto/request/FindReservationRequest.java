package com.wanted.preonboarding.ticket.domain.dto.request;

public record FindReservationRequest(
        String memberName, // 예약자 이름
        String memberPhoneNumber // 예약자 폰 번호
) {
}
