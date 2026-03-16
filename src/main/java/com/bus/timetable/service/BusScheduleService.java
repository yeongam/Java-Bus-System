package com.bus.timetable.service;

import com.bus.timetable.model.BusRoute;
import com.bus.timetable.model.BusSchedule;
import com.bus.timetable.model.BusSchedule.DayType;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 버스 시간표 서비스
 * TODO: Repository 주입 후 실제 DB 조회 로직으로 교체 필요
 */
@Service
public class BusScheduleService {

    /**
     * 전체 노선 목록 조회
     */
    public List<BusRoute> getAllRoutes() {
        // TODO: return routeRepository.findAll();
        return List.of(); // 임시 빈 목록
    }

    /**
     * 노선 번호로 노선 조회
     */
    public BusRoute getRouteByNumber(String routeNumber) {
        // TODO: return routeRepository.findByRouteNumber(routeNumber);
        return null;
    }

    /**
     * 특정 노선·요일 유형의 시간표 조회
     */
    public List<BusSchedule> getSchedules(Long routeId, DayType dayType) {
        // TODO: return scheduleRepository.findByRouteIdAndDayTypeOrderByDepartureTime(routeId, dayType);
        return List.of(); // 임시 빈 목록
    }
}
