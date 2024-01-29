package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.request.ReservePerformanceRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    private UUID performanceId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phoneNumber;
    @Column(nullable = false)
    private long amount;
    private long discount;
    @Column(nullable = false)
    private int round;
    private int gate;
    private char line;
    private int seat;
    @Column(nullable = false)
    private String reservationStatus;

    public static Reservation of(
            final ReservePerformanceRequest reservePerformanceRequest,
            final long calculateReservationPrice
    ) {
        return Reservation.builder()
                .performanceId(reservePerformanceRequest.performanceId())
                .name(reservePerformanceRequest.name())
                .phoneNumber(reservePerformanceRequest.phoneNumber())
                .amount(calculateReservationPrice)
                .discount(reservePerformanceRequest.discount())
                .round(reservePerformanceRequest.round())
                .gate(reservePerformanceRequest.gate())
                .line(reservePerformanceRequest.line())
                .seat(reservePerformanceRequest.seat())
                .reservationStatus(reservePerformanceRequest.reservationStatus())
                .build();
    }

}
