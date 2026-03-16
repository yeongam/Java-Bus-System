package com.bus.timetable.model;

import jakarta.persistence.*;

@Entity
@Table(name = "bus_routes")
public class BusRoute {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String routeNumber;

    @Column(nullable = false)
    private String startStop;

    @Column(nullable = false)
    private String endStop;

    private String description;

    protected BusRoute() {}

    public BusRoute(String routeNumber, String startStop, String endStop, String description) {
        this.routeNumber = routeNumber;
        this.startStop = startStop;
        this.endStop = endStop;
        this.description = description;
    }

    public Long getId() { return id; }
    public String getRouteNumber() { return routeNumber; }
    public String getStartStop() { return startStop; }
    public String getEndStop() { return endStop; }
    public String getDescription() { return description; }

    public void setRouteNumber(String routeNumber) { this.routeNumber = routeNumber; }
    public void setStartStop(String startStop) { this.startStop = startStop; }
    public void setEndStop(String endStop) { this.endStop = endStop; }
    public void setDescription(String description) { this.description = description; }
}
