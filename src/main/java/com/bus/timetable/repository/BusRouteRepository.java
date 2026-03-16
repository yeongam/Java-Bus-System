package com.bus.timetable.repository;

import com.bus.timetable.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {

    Optional<BusRoute> findByRouteNumber(String routeNumber);
}
