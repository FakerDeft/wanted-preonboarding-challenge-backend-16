package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.global.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketService;
import com.wanted.preonboarding.ticket.domain.dto.request.FindPerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.FindReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservePerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindPerformanceResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReservationResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservePerformanceResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReserveController {
    private final TicketService ticketService;

    @GetMapping("/find-reservation")
    public ResponseEntity<ResponseHandler<FindReservationResponse>> findReservation(
            @RequestBody @Valid final FindReservationRequest findReservationRequest
    ) {
        return ResponseEntity.ok()
                .body(ResponseHandler.<FindReservationResponse>builder()
                        .statusCode(HttpStatus.OK)
                        .message("예약을 찾았습니다.")
                        .data(ticketService.findReservation(findReservationRequest))
                        .build());
    }

    @GetMapping("/find-performance")
    public ResponseEntity<ResponseHandler<List<FindPerformanceResponse>>> findReservation(
            @RequestBody @Valid final FindPerformanceRequest findPerformanceRequest
    ) {
        return ResponseEntity.ok()
                .body(ResponseHandler.<List<FindPerformanceResponse>>builder()
                        .statusCode(HttpStatus.OK)
                        .message("예매 가능한 공연 리스트를 찾았습니다.")
                        .data(ticketService.findEnablePerformanceList(findPerformanceRequest))
                        .build());
    }

    @PostMapping("/reserve")
    public ResponseEntity<ResponseHandler<ReservePerformanceResponse>> reserve(
            @RequestBody @Valid final ReservePerformanceRequest reservePerformanceRequest
    ) {
        return ResponseEntity.ok()
                .body(ResponseHandler.<ReservePerformanceResponse>builder()
                        .statusCode(HttpStatus.OK)
                        .message("공연 예약을 성공했습니다.")
                        .data(ticketService.reserve(reservePerformanceRequest))
                        .build());
    }
}
