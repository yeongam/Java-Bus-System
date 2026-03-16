package com.bus.timetable.controller;

import com.bus.timetable.model.BusRoute;
import com.bus.timetable.model.BusSchedule;
import com.bus.timetable.model.DayType;
import com.bus.timetable.repository.BusScheduleRepository;
import com.bus.timetable.service.BusRouteService;
import com.bus.timetable.service.BusScheduleService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/admin")
public class AdminController {

    private final BusRouteService routeService;
    private final BusScheduleService scheduleService;
    private final BusScheduleRepository scheduleRepository;

    public AdminController(BusRouteService routeService,
                           BusScheduleService scheduleService,
                           BusScheduleRepository scheduleRepository) {
        this.routeService = routeService;
        this.scheduleService = scheduleService;
        this.scheduleRepository = scheduleRepository;
    }

    // ── 대시보드 ──────────────────────────────────────────────
    @GetMapping
    public String dashboard(Model model) {
        model.addAttribute("routes", routeService.getAllRoutes());
        return "admin/dashboard";
    }

    // ── 노선 추가 폼 ──────────────────────────────────────────
    @GetMapping("/routes/new")
    public String newRouteForm() {
        return "admin/route-form";
    }

    // ── 노선 저장 ─────────────────────────────────────────────
    @PostMapping("/routes")
    public String saveRoute(
            @RequestParam String routeNumber,
            @RequestParam String startStop,
            @RequestParam String endStop,
            @RequestParam(required = false) String description,
            RedirectAttributes ra) {
        routeService.save(routeNumber.trim(), startStop.trim(), endStop.trim(),
                description != null ? description.trim() : "");
        ra.addFlashAttribute("message", "'" + routeNumber + "' 노선이 추가되었습니다.");
        return "redirect:/admin";
    }

    // ── 노선 수정 폼 ──────────────────────────────────────────
    @GetMapping("/routes/{id}/edit")
    public String editRouteForm(@PathVariable Long id, Model model) {
        model.addAttribute("route", routeService.getRouteById(id));
        return "admin/route-form";
    }

    // ── 노선 수정 저장 ────────────────────────────────────────
    @PostMapping("/routes/{id}/update")
    public String updateRoute(
            @PathVariable Long id,
            @RequestParam String routeNumber,
            @RequestParam String startStop,
            @RequestParam String endStop,
            @RequestParam(required = false) String description,
            RedirectAttributes ra) {
        routeService.update(id, routeNumber.trim(), startStop.trim(), endStop.trim(),
                description != null ? description.trim() : "");
        ra.addFlashAttribute("message", "'" + routeNumber + "' 노선이 수정되었습니다.");
        return "redirect:/admin";
    }

    // ── 노선 삭제 ─────────────────────────────────────────────
    @PostMapping("/routes/{id}/delete")
    public String deleteRoute(@PathVariable Long id, RedirectAttributes ra) {
        BusRoute route = routeService.getRouteById(id);
        scheduleService.deleteAllByRouteId(id);
        routeService.delete(id);
        ra.addFlashAttribute("message", "'" + route.getRouteNumber() + "' 노선이 삭제되었습니다.");
        return "redirect:/admin";
    }

    // ── 시간표 관리 페이지 ────────────────────────────────────
    @GetMapping("/routes/{id}/schedules")
    public String scheduleManage(
            @PathVariable Long id,
            @RequestParam(defaultValue = "WEEKDAY") DayType dayType,
            Model model) {
        model.addAttribute("route", routeService.getRouteById(id));
        model.addAttribute("schedules", scheduleService.getSchedules(id, dayType));
        model.addAttribute("selectedDayType", dayType);
        model.addAttribute("dayTypes", DayType.values());
        return "admin/schedule-manage";
    }

    // ── 시간표 추가 (1건) ─────────────────────────────────────
    @PostMapping("/routes/{id}/schedules")
    public String addSchedule(
            @PathVariable Long id,
            @RequestParam String departureStop,
            @RequestParam String departureTime,
            @RequestParam DayType dayType,
            RedirectAttributes ra) {
        BusRoute route = routeService.getRouteById(id);
        scheduleService.save(new BusSchedule(route, departureStop.trim(),
                LocalTime.parse(departureTime), dayType));
        ra.addFlashAttribute("message", dayType.getLabel() + " " + departureTime + " 시간표가 추가되었습니다.");
        return "redirect:/admin/routes/" + id + "/schedules?dayType=" + dayType;
    }

    // ── 시간표 일괄 추가 (시작~종료, 배차 간격) ───────────────
    @PostMapping("/routes/{id}/schedules/bulk")
    public String addBulkSchedules(
            @PathVariable Long id,
            @RequestParam String departureStop,
            @RequestParam String startTime,
            @RequestParam String endTime,
            @RequestParam int intervalMinutes,
            @RequestParam DayType dayType,
            RedirectAttributes ra) {
        BusRoute route = routeService.getRouteById(id);
        LocalTime current = LocalTime.parse(startTime);
        LocalTime end = LocalTime.parse(endTime);
        int count = 0;
        while (!current.isAfter(end)) {
            scheduleService.save(new BusSchedule(route, departureStop.trim(), current, dayType));
            current = current.plusMinutes(intervalMinutes);
            count++;
        }
        ra.addFlashAttribute("message",
                dayType.getLabel() + " " + count + "개 시간표가 일괄 추가되었습니다.");
        return "redirect:/admin/routes/" + id + "/schedules?dayType=" + dayType;
    }

    // ── 시간표 삭제 ───────────────────────────────────────────
    @PostMapping("/schedules/{scheduleId}/delete")
    public String deleteSchedule(
            @PathVariable Long scheduleId,
            @RequestParam Long routeId,
            @RequestParam DayType dayType,
            RedirectAttributes ra) {
        scheduleService.delete(scheduleId);
        ra.addFlashAttribute("message", "시간표가 삭제되었습니다.");
        return "redirect:/admin/routes/" + routeId + "/schedules?dayType=" + dayType;
    }

    // ── DB 시각화 뷰어 ────────────────��───────────────────────
    @GetMapping("/db")
    public String dbViewer(Model model) {
        List<BusRoute> routes = routeService.getAllRoutes();

        // 노선별 시간표 수
        Map<Long, Long> countByRoute = new LinkedHashMap<>();
        for (BusRoute r : routes) {
            countByRoute.put(r.getId(), scheduleRepository.countByRouteId(r.getId()));
        }

        // 요일별 시간표 수
        Map<String, Long> countByDayType = new LinkedHashMap<>();
        for (DayType dt : DayType.values()) {
            countByDayType.put(dt.getLabel(), scheduleRepository.countByDayType(dt));
        }

        long totalSchedules = countByDayType.values().stream().mapToLong(Long::longValue).sum();

        model.addAttribute("routes", routes);
        model.addAttribute("countByRoute", countByRoute);
        model.addAttribute("countByDayType", countByDayType);
        model.addAttribute("totalSchedules", totalSchedules);
        return "admin/db-viewer";
    }
}
