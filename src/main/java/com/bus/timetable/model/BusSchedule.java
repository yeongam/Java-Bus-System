package com.bus.timetable.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "bus_schedules")
public class BusSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "route_id", nullable = false)
    private BusRoute route;

    @Column(nullable = false)
    private String departureStop;

    @Column(nullable = false)
    private LocalTime departureTime;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DayType dayType;

    protected BusSchedule() {}

    public BusSchedule(BusRoute route, String departureStop, LocalTime departureTime, DayType dayType) {
        this.route = route;
        this.departureStop = departureStop;
        this.departureTime = departureTime;
        this.dayType = dayType;
    }

    public Long getId() { return id; }
    public BusRoute getRoute() { return route; }
    public String getDepartureStop() { return departureStop; }
    public LocalTime getDepartureTime() { return departureTime; }
    public DayType getDayType() { return dayType; }

    public void setDepartureStop(String departureStop) { this.departureStop = departureStop; }
    public void setDepartureTime(LocalTime departureTime) { this.departureTime = departureTime; }
    public void setDayType(DayType dayType) { this.dayType = dayType; }
}
