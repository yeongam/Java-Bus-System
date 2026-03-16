package com.bus.timetable.service;

import com.bus.timetable.model.BusSchedule;
import com.bus.timetable.model.DayType;
import com.bus.timetable.repository.BusScheduleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

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

    @Transactional
    public BusSchedule save(BusSchedule schedule) {
        return busScheduleRepository.save(schedule);
    }

    @Transactional
    public void delete(Long id) {
        if (!busScheduleRepository.existsById(id)) {
            throw new NoSuchElementException("시간표를 찾을 수 없습니다. id=" + id);
        }
        busScheduleRepository.deleteById(id);
    }

    @Transactional
    public void deleteAllByRouteId(Long routeId) {
        busScheduleRepository.deleteByRouteId(routeId);
    }
}
