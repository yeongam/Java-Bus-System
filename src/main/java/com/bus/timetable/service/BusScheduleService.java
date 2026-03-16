package com.bus.timetable.service;

import com.bus.timetable.model.BusSchedule;
import com.bus.timetable.model.DayType;
import com.bus.timetable.repository.BusScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class BusScheduleService {

    private final BusScheduleRepository busScheduleRepository;

    public BusScheduleService(BusScheduleRepository busScheduleRepository) {
        this.busScheduleRepository = busScheduleRepository;
    }

    public List<BusSchedule> getSchedules(Long routeId, DayType dayType) {
        return busScheduleRepository.findByRouteIdAndDayTypeOrderByDepartureTime(routeId, dayType);
    }
}
