package com.bus.timetable;

import com.bus.timetable.model.BusRoute;
import com.bus.timetable.model.BusSchedule;
import com.bus.timetable.model.DayType;
import com.bus.timetable.repository.BusRouteRepository;
import com.bus.timetable.repository.BusScheduleRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalTime;

@Component
public class DataInitializer implements CommandLineRunner {

    private final BusRouteRepository routeRepo;
    private final BusScheduleRepository scheduleRepo;

    public DataInitializer(BusRouteRepository routeRepo, BusScheduleRepository scheduleRepo) {
        this.routeRepo = routeRepo;
        this.scheduleRepo = scheduleRepo;
    }

    @Override
    public void run(String... args) {
        if (routeRepo.count() > 0) return; // 이미 데이터 있으면 스킵

        // === 노선 1: 101번 ===
        BusRoute r1 = routeRepo.save(new BusRoute("101", "강남역", "잠실역", "강남~잠실 직행"));

        // 평일
        for (int h = 6; h <= 22; h++) {
            scheduleRepo.save(new BusSchedule(r1, "강남역", LocalTime.of(h, 0), DayType.WEEKDAY));
            scheduleRepo.save(new BusSchedule(r1, "강남역", LocalTime.of(h, 30), DayType.WEEKDAY));
        }
        // 토요일
        for (int h = 7; h <= 21; h++) {
            scheduleRepo.save(new BusSchedule(r1, "강남역", LocalTime.of(h, 0), DayType.SATURDAY));
            scheduleRepo.save(new BusSchedule(r1, "강남역", LocalTime.of(h, 40), DayType.SATURDAY));
        }
        // 일요일·공휴일
        for (int h = 8; h <= 20; h++) {
            scheduleRepo.save(new BusSchedule(r1, "강남역", LocalTime.of(h, 0), DayType.HOLIDAY));
        }

        // === 노선 2: 202번 ===
        BusRoute r2 = routeRepo.save(new BusRoute("202", "서울역", "홍대입구역", "서울~홍대 순환"));

        for (int h = 6; h <= 23; h++) {
            scheduleRepo.save(new BusSchedule(r2, "서울역", LocalTime.of(h, 0), DayType.WEEKDAY));
            scheduleRepo.save(new BusSchedule(r2, "서울역", LocalTime.of(h, 20), DayType.WEEKDAY));
            scheduleRepo.save(new BusSchedule(r2, "서울역", LocalTime.of(h, 40), DayType.WEEKDAY));
        }
        for (int h = 7; h <= 22; h++) {
            scheduleRepo.save(new BusSchedule(r2, "서울역", LocalTime.of(h, 0), DayType.SATURDAY));
            scheduleRepo.save(new BusSchedule(r2, "서울역", LocalTime.of(h, 30), DayType.SATURDAY));
        }
        for (int h = 8; h <= 21; h++) {
            scheduleRepo.save(new BusSchedule(r2, "서울역", LocalTime.of(h, 0), DayType.HOLIDAY));
            scheduleRepo.save(new BusSchedule(r2, "서울역", LocalTime.of(h, 30), DayType.HOLIDAY));
        }

        // === 노선 3: 303번 ===
        BusRoute r3 = routeRepo.save(new BusRoute("303", "인천공항", "서울역", "공항 리무진"));

        int[] weekdayHours = {5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 21, 22};
        for (int h : weekdayHours) {
            scheduleRepo.save(new BusSchedule(r3, "인천공항", LocalTime.of(h, 0), DayType.WEEKDAY));
        }
        for (int h : weekdayHours) {
            scheduleRepo.save(new BusSchedule(r3, "인천공항", LocalTime.of(h, 0), DayType.SATURDAY));
        }
        int[] holidayHours = {6, 8, 10, 12, 14, 16, 18, 20};
        for (int h : holidayHours) {
            scheduleRepo.save(new BusSchedule(r3, "인천공항", LocalTime.of(h, 0), DayType.HOLIDAY));
        }

        // === 노선 4: 404번 ===
        BusRoute r4 = routeRepo.save(new BusRoute("404", "수원역", "사당역", "수원~사당 급행"));

        for (int h = 6; h <= 22; h++) {
            scheduleRepo.save(new BusSchedule(r4, "수원역", LocalTime.of(h, 0), DayType.WEEKDAY));
            scheduleRepo.save(new BusSchedule(r4, "수원역", LocalTime.of(h, 30), DayType.WEEKDAY));
        }
        for (int h = 7; h <= 21; h++) {
            scheduleRepo.save(new BusSchedule(r4, "수원역", LocalTime.of(h, 0), DayType.SATURDAY));
        }
        for (int h = 8; h <= 20; h++) {
            scheduleRepo.save(new BusSchedule(r4, "수원역", LocalTime.of(h, 0), DayType.HOLIDAY));
        }

        // === 노선 5: 505번 ===
        BusRoute r5 = routeRepo.save(new BusRoute("505", "부산역", "해운대", "부산 해운대 급행"));

        for (int h = 6; h <= 22; h++) {
            scheduleRepo.save(new BusSchedule(r5, "부산역", LocalTime.of(h, 0), DayType.WEEKDAY));
            scheduleRepo.save(new BusSchedule(r5, "부산역", LocalTime.of(h, 15), DayType.WEEKDAY));
            scheduleRepo.save(new BusSchedule(r5, "부산역", LocalTime.of(h, 30), DayType.WEEKDAY));
            scheduleRepo.save(new BusSchedule(r5, "부산역", LocalTime.of(h, 45), DayType.WEEKDAY));
        }
        for (int h = 7; h <= 22; h++) {
            scheduleRepo.save(new BusSchedule(r5, "부산역", LocalTime.of(h, 0), DayType.SATURDAY));
            scheduleRepo.save(new BusSchedule(r5, "부산역", LocalTime.of(h, 30), DayType.SATURDAY));
        }
        for (int h = 8; h <= 21; h++) {
            scheduleRepo.save(new BusSchedule(r5, "부산역", LocalTime.of(h, 0), DayType.HOLIDAY));
            scheduleRepo.save(new BusSchedule(r5, "부산역", LocalTime.of(h, 30), DayType.HOLIDAY));
        }

        System.out.println("샘플 데이터 초기화 완료: " + routeRepo.count() + "개 노선 등록");
    }
}
