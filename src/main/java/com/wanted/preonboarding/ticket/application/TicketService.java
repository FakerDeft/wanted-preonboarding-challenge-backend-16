package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.FindPerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.FindReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservePerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReservationResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.exception.TicketException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final long totalAmount = 0L;

    /**
     * [예약 조회 메서드]
     * 회원의 이름과 핸드폰 번호로 예약 조회
     * 해당 예약의 공연 id로 공연 조회
     * 조회 후 결과 리턴
     */
    public FindReservationResponse findReservation(final FindReservationRequest findReservationRequest) {

        final Reservation reservation = reservationRepository.findByMemberNameAndMemberPhoneNumber(
                        findReservationRequest.memberName(),
                        findReservationRequest.memberPhoneNumber())
                .orElseThrow(() -> new TicketException.ReservationNotFoundException(findReservationRequest.memberName(), findReservationRequest.memberPhoneNumber()));
        final Performance performance = performanceRepository.findById(reservation.getPerformanceId())
                .orElseThrow(() -> new TicketException.PerformanceNotFoundException(reservation.getPerformanceId()));

        return FindReservationResponse.of(reservation, performance);
    }

    /**
     * [예매 가능한 공연 리스트 조회 메서드]
     * 예매 가능 여부('enable')로 조회
     * 조회 후 결과 리스트 리턴
     */
    public List<Performance> getAllPerformanceInfoList(final FindPerformanceRequest findPerformanceRequest) {
        return performanceRepository.findByIsReserve(findPerformanceRequest.isReserve())
                .orElseThrow(() -> new TicketException.EnablePerformanceNotFoundException(findPerformanceRequest.isReserve()));
    }

    /**
     * [예약 메서드]
     * 요청 시 받아온 공연 ID로 해당 공연 정보 조회
     * 예약이 가능하다면
     * 잔고와 할인 금액 고려해 최종 결제 금액 산출하고
     * DB에 예약 정보 등록 후 예약 결과 리턴
     * 예약이 불가능하면 null 리턴
     */
    public Reservation reserve(final ReservePerformanceRequest reservePerformanceRequest) {
        log.info("performanceId ID => {}", reservePerformanceRequest.performanceId());
        final Performance performanceInfo = performanceRepository.findById(reservePerformanceRequest.performanceId())
                .orElseThrow(() -> new TicketException.PerformanceNotFoundException(reservePerformanceRequest.performanceId()));
        final int performancePrice = performanceInfo.getPrice();
        final String enableReserve = performanceInfo.getIsReserve();
        final long calculateReservationPrice = calculateReservationPrice(
                reservePerformanceRequest.amount(),
                performancePrice,
                reservePerformanceRequest.discount()
        );

        if (enableReserve.equalsIgnoreCase("enable")) {
            return reservationRepository.saveAndFlush(Reservation.of(reservePerformanceRequest, calculateReservationPrice));
        }
        return null;
    }

    /**
     * [최종 금액 계산 메서드]
     * 최종 금액 = 잔고 - 공연 가격 - 할인 금액
     */
    private long calculateReservationPrice(
            final long amount,
            final long performancePrice,
            final long discount
    ) {
        return amount - performancePrice - discount;
    }
}
