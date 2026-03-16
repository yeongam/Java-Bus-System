package com.bus.timetable.repository;

import com.bus.timetable.model.BusSchedule;
import com.bus.timetable.model.DayType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusScheduleRepository extends JpaRepository<BusSchedule, Long> {

    List<BusSchedule> findByRouteIdAndDayTypeOrderByDepartureTime(Long routeId, DayType dayType);
}
