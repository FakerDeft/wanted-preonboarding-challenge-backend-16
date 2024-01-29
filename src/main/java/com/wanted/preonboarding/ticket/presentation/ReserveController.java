package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.global.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketService;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReserveController {
    private final TicketService ticketService;

    @PostMapping("/reserve")
    public ResponseEntity<ResponseHandler<Reservation>> reservation(
            @RequestBody @Valid final ReservationRequest reservationRequest
    ) {

        return ResponseEntity.ok()
                .body(ResponseHandler.<Reservation>builder()
                        .statusCode(HttpStatus.OK)
                        .message("공연 예약을 성공했습니다.")
                        .data(ticketService.reserve(reservationRequest))
                        .build());
    }
}
