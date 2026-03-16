package com.bus.timetable.controller;

import com.bus.timetable.model.BusSchedule.DayType;
import com.bus.timetable.service.BusScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

/**
 * 버스 시간표 웹 컨트롤러
 */
@Controller
public class BusController {

    private final BusScheduleService busScheduleService;

    public BusController(BusScheduleService busScheduleService) {
        this.busScheduleService = busScheduleService;
    }

    /**
     * 메인 페이지 - 노선 목록 표시
     */
    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("routes", busScheduleService.getAllRoutes());
        return "index";
    }

    /**
     * 시간표 페이지 - 특정 노선의 시간표 표시
     *
     * @param routeId  노선 ID
     * @param dayType  요일 유형 (WEEKDAY / SATURDAY / HOLIDAY), 기본값: WEEKDAY
     */
    @GetMapping("/schedule/{routeId}")
    public String schedule(
            @PathVariable Long routeId,
            @RequestParam(defaultValue = "WEEKDAY") DayType dayType,
            Model model) {

        model.addAttribute("schedules", busScheduleService.getSchedules(routeId, dayType));
        model.addAttribute("selectedDayType", dayType);
        model.addAttribute("routeId", routeId);
        return "schedule";
    }
}
