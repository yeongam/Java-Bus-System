package com.bus.timetable.controller;

import com.bus.timetable.model.BusRoute;
import com.bus.timetable.model.BusSchedule;
import com.bus.timetable.model.DayType;
import com.bus.timetable.service.BusRouteService;
import com.bus.timetable.service.BusScheduleService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;
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

    // ── 노선 조회 ──────────────────────────────────────────────

    // GET /api/routes
    @GetMapping("/routes")
    public List<BusRoute> getRoutes() {
        return busRouteService.getAllRoutes();
    }

    // GET /api/routes/search?q=강남
    @GetMapping("/routes/search")
    public List<BusRoute> searchRoutes(@RequestParam String q) {
        return busRouteService.search(q);
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

    // ── 노선 생성/수정/삭제 ────────────────────────────────────

    // POST /api/routes
    @PostMapping("/routes")
    public ResponseEntity<BusRoute> createRoute(@RequestBody RouteRequest req) {
        BusRoute created = busRouteService.save(req.routeNumber(), req.startStop(),
                req.endStop(), req.description() != null ? req.description() : "");
        return ResponseEntity.ok(created);
    }

    // PUT /api/routes/{id}
    @PutMapping("/routes/{id}")
    public ResponseEntity<BusRoute> updateRoute(@PathVariable Long id,
                                                @RequestBody RouteRequest req) {
        BusRoute updated = busRouteService.update(id, req.routeNumber(), req.startStop(),
                req.endStop(), req.description() != null ? req.description() : "");
        return ResponseEntity.ok(updated);
    }

    // DELETE /api/routes/{id}
    @DeleteMapping("/routes/{id}")
    public ResponseEntity<Void> deleteRoute(@PathVariable Long id) {
        busScheduleService.deleteAllByRouteId(id);
        busRouteService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── 시간표 생성/삭제 ───────────────────────────────────────

    // POST /api/routes/{id}/schedules
    @PostMapping("/routes/{id}/schedules")
    public ResponseEntity<BusSchedule> addSchedule(@PathVariable Long id,
                                                    @RequestBody ScheduleRequest req) {
        BusRoute route = busRouteService.getRouteById(id);
        BusSchedule schedule = new BusSchedule(route, req.departureStop(),
                LocalTime.parse(req.departureTime()), DayType.valueOf(req.dayType()));
        return ResponseEntity.ok(busScheduleService.save(schedule));
    }

    // DELETE /api/schedules/{id}
    @DeleteMapping("/schedules/{id}")
    public ResponseEntity<Void> deleteSchedule(@PathVariable Long id) {
        busScheduleService.delete(id);
        return ResponseEntity.noContent().build();
    }

    // ── 요청 DTO ──────────────────────────────────────────────

    record RouteRequest(String routeNumber, String startStop, String endStop, String description) {}

    record ScheduleRequest(String departureStop, String departureTime, String dayType) {}
}
