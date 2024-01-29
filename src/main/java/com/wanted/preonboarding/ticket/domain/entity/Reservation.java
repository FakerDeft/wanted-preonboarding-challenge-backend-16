package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
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
    private String memberName;
    @Column(nullable = false)
    private String memberPhoneNumber;
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
            final ReservationRequest reservationRequest,
            final long calculateReservationPrice
    ) {
        return Reservation.builder()
                .performanceId(reservationRequest.performanceId())
                .memberName(reservationRequest.memberName())
                .memberPhoneNumber(reservationRequest.memberPhoneNumber())
                .amount(calculateReservationPrice)
                .discount(reservationRequest.discount())
                .round(reservationRequest.round())
                .gate(reservationRequest.gate())
                .line(reservationRequest.line())
                .seat(reservationRequest.seat())
                .build();
    }

}
