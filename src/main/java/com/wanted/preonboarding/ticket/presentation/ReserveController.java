package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.global.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketService;
import com.wanted.preonboarding.ticket.domain.dto.request.FindReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservePerformanceRequest;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReserveController {
    private final TicketService ticketService;

    @GetMapping("/find-reservation")
    public ResponseEntity<ResponseHandler<Reservation>> findReservation(
            @RequestBody @Valid final FindReservationRequest findReservationRequest
    ) {
        return ResponseEntity.ok()
                .body(ResponseHandler.<Reservation>builder()
                        .statusCode(HttpStatus.OK)
                        .message("공연을 찾았습니다.")
                        .data(ticketService.findReservation(findReservationRequest))
                        .build());
    }

    @PostMapping("/reserve")
    public ResponseEntity<ResponseHandler<Reservation>> reserve(
            @RequestBody @Valid final ReservePerformanceRequest reservePerformanceRequest
    ) {
        return ResponseEntity.ok()
                .body(ResponseHandler.<Reservation>builder()
                        .statusCode(HttpStatus.OK)
                        .message("공연 예약을 성공했습니다.")
                        .data(ticketService.reserve(reservePerformanceRequest))
                        .build());
    }
}
