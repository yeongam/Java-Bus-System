package com.bus.timetable.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

/**
 * 버스 시간표 (특정 노선의 출발 시각)
 * TODO: 실제 시간표 데이터에 맞게 필드 추가/수정 필요
 */
@Entity
@Table(name = "bus_schedules")
@Getter @Setter @NoArgsConstructor
public class BusSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "route_id")
    private BusRoute route;

    // 출발 정류장
    private String departureStop;

    // 출발 시각
    private LocalTime departureTime;

    // 운행 구분 (평일 / 토요일 / 일요일·공휴일)
    @Enumerated(EnumType.STRING)
    private DayType dayType;

    public enum DayType {
        WEEKDAY,    // 평일
        SATURDAY,   // 토요일
        HOLIDAY     // 일요일·공휴일
    }
}
