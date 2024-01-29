package com.wanted.preonboarding.ticket.exception;

import java.util.UUID;

public class TicketException extends RuntimeException {

    public TicketException(final String message) {
        super(message);
    }

    public static class PerformanceNotFoundException extends TicketException {

        public PerformanceNotFoundException(final UUID performanceId) {
            super(String.format("조회하는 공연이 존재하지 않습니다. - request info { performance : %s }", performanceId));
        }
    }
}
