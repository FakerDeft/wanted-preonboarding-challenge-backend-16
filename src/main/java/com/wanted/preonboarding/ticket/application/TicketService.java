package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceDTO;
import com.wanted.preonboarding.ticket.domain.dto.request.FindReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservePerformanceRequest;
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

    public List<PerformanceDTO> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
                .stream()
                .map(PerformanceDTO::of)
                .toList();
    }

    public PerformanceDTO getPerformanceInfoDetail(String name) {
        return PerformanceDTO.of(performanceRepository.findByName(name));
    }

    /**
     * [예약 조회 메서드]
     * 회원의 이름과 핸드폰 번호로 예약 조회
     * 예약 조회 후 결과 리턴
     */
    public Reservation findReservation(final FindReservationRequest findReservationRequest) {
        return reservationRepository.findByMemberNameAndMemberPhoneNumber(
                        findReservationRequest.memberName(),
                        findReservationRequest.memberPhoneNumber())
                .orElseThrow(() -> new TicketException.ReservationNotFoundException(findReservationRequest.memberName(), findReservationRequest.memberPhoneNumber()));
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

    private long calculateReservationPrice(
            final long amount,
            final long performancePrice,
            final long discount
    ) {
        return amount - performancePrice - discount;
    }
}
