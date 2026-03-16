package com.bus.timetable.controller;

import com.bus.timetable.model.BusRoute;
import com.bus.timetable.model.BusSchedule;
import com.bus.timetable.model.DayType;
import com.bus.timetable.service.BusRouteService;
import com.bus.timetable.service.BusScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * JSON 응답 전용 REST API 컨트롤러
 */
@RestController
@RequestMapping("/api")
public class BusApiController {

    private final BusRouteService busRouteService;
    private final BusScheduleService busScheduleService;

    public BusApiController(BusRouteService busRouteService, BusScheduleService busScheduleService) {
        this.busRouteService = busRouteService;
        this.busScheduleService = busScheduleService;
    }

    // GET /api/routes
    @GetMapping("/routes")
    public List<BusRoute> getRoutes() {
        return busRouteService.getAllRoutes();
    }

    // GET /api/routes/{id}
    @GetMapping("/routes/{id}")
    public ResponseEntity<BusRoute> getRoute(@PathVariable Long id) {
        return ResponseEntity.ok(busRouteService.getRouteById(id));
    }

    // GET /api/routes/{id}/schedules?dayType=WEEKDAY
    @GetMapping("/routes/{id}/schedules")
    public List<BusSchedule> getSchedules(
            @PathVariable Long id,
            @RequestParam(defaultValue = "WEEKDAY") DayType dayType) {
        return busScheduleService.getSchedules(id, dayType);
    }
}
