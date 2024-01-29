package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID performanceId;
    @Column(nullable = false)
    private String performanceName;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int round;
    @Column(nullable = false)
    private int type;
    @Column(nullable = false)
    private Date start_date;
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String isReserve;

    @Enumerated(EnumType.STRING)
    private PerformanceType performanceType;
}
