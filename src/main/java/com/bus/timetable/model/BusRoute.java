package com.bus.timetable.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;

/**
 * 버스 노선 정보
 * TODO: 실제 노선 데이터에 맞게 필드 추가/수정 필요
 */
@Entity
@Table(name = "bus_routes")
@Getter @Setter @NoArgsConstructor
public class BusRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // 버스 번호 (예: "100", "271", "간선버스 001")
    private String routeNumber;

    // 기점
    private String startStop;

    // 종점
    private String endStop;

    // 노선 설명
    private String description;
}
