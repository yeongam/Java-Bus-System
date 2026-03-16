package com.bus.timetable.controller;

import com.bus.timetable.model.DayType;
import com.bus.timetable.service.BusRouteService;
import com.bus.timetable.service.BusScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 화면 렌더링 전용 MVC 컨트롤러
 */
@Controller
public class BusController {

    private final BusRouteService busRouteService;
    private final BusScheduleService busScheduleService;

    public BusController(BusRouteService busRouteService, BusScheduleService busScheduleService) {
        this.busRouteService = busRouteService;
        this.busScheduleService = busScheduleService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("routes", busRouteService.getAllRoutes());
        return "index";
    }

    @GetMapping("/schedule/{routeId}")
    public String schedule(
            @PathVariable Long routeId,
            @RequestParam(defaultValue = "WEEKDAY") DayType dayType,
            Model model) {
        model.addAttribute("route", busRouteService.getRouteById(routeId));
        model.addAttribute("schedules", busScheduleService.getSchedules(routeId, dayType));
        model.addAttribute("selectedDayType", dayType);
        return "schedule";
    }
}
