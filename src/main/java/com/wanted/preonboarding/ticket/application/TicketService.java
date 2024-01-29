package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.FindPerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.FindReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservePerformanceRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindPerformanceResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReservationResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservePerformanceResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.exception.TicketException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Service
public class TicketService {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;

    /**
     * [예약 조회 메서드]
     * 회원의 이름과 핸드폰 번호로 예약 조회
     * 해당 예약의 공연 id로 공연 조회
     * 조회 후 결과 리턴
     */
    public FindReservationResponse findReservation(final FindReservationRequest findReservationRequest) {

        final Reservation reservationInfo = reservationRepository.findByNameAndPhoneNumber(
                        findReservationRequest.name(),
                        findReservationRequest.phoneNumber())
                .orElseThrow(() -> new TicketException.ReservationNotFoundException(findReservationRequest.name(), findReservationRequest.phoneNumber()));
        log.info("예약 조회 성공 : {}", reservationInfo);
        final Performance performanceInfo = performanceRepository.findById(reservationInfo.getPerformanceId())
                .orElseThrow(() -> new TicketException.PerformanceNotFoundException(reservationInfo.getPerformanceId()));
        log.info("공연 조회 성공 : {}", performanceInfo);

        return FindReservationResponse.of(reservationInfo, performanceInfo);
    }

    /**
     * [예매 가능한 공연 및 전시 리스트 조회 메서드]
     * 예매 가능 여부('enable')로 조회
     * 조회 후 결과 리스트 리턴
     */
    public List<FindPerformanceResponse> findEnablePerformanceList(final FindPerformanceRequest findPerformanceRequest) {
        final List<Performance> enablePerformances = performanceRepository.findByIsReserve(findPerformanceRequest.isReserve())
                .orElseThrow(() -> new TicketException.EnablePerformanceNotFoundException(findPerformanceRequest.isReserve()));
        log.info("예매 가능한 공연 리스트 조회 성공 : {}", enablePerformances);
        final List<FindPerformanceResponse> findPerformances = new ArrayList<>();

        for (int i = 0; i < enablePerformances.size(); i++) {
            findPerformances.add(i, FindPerformanceResponse.of(enablePerformances.get(i)));
        }
        log.info("예매 가능한 공연 리스트 : {}", findPerformances);

        return findPerformances;
    }

    /**
     * [예약 메서드]
     * 요청 시 받아온 공연 ID로 해당 공연 정보 조회
     * 예약이 가능하다면
     * 잔고와 할인 금액 고려해 최종 결제 금액 산출하고
     * DB에 예약 정보 등록 후 예약 결과 리턴
     * 예약이 불가능하면 null 리턴
     */
    public ReservePerformanceResponse reserve(final ReservePerformanceRequest reservePerformanceRequest) {
        log.info("예약 요청 : {}", reservePerformanceRequest);
        final Performance performanceInfo = performanceRepository.findById(reservePerformanceRequest.performanceId())
                .orElseThrow(() -> new TicketException.PerformanceNotFoundException(reservePerformanceRequest.performanceId()));
        log.info("공연 조회 성공 : {}", performanceInfo);
        final int performancePrice = performanceInfo.getPrice();
        final String enableReserve = performanceInfo.getIsReserve();
        final long calculateReservationPrice = calculateReservationPrice(
                reservePerformanceRequest.amount(),
                performancePrice,
                reservePerformanceRequest.discount()
        );
        log.info("최종 금액 계산 완료 : {}", calculateReservationPrice);

        if (enableReserve.equalsIgnoreCase("enable")) {
            final Reservation reservationInfo = reservationRepository.saveAndFlush(Reservation.of(reservePerformanceRequest, calculateReservationPrice));

            return ReservePerformanceResponse.of(reservationInfo, performanceInfo);
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
