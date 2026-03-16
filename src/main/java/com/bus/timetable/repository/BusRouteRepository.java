package com.bus.timetable.repository;

import com.bus.timetable.model.BusRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BusRouteRepository extends JpaRepository<BusRoute, Long> {

    Optional<BusRoute> findByRouteNumber(String routeNumber);

    @Query("SELECT r FROM BusRoute r WHERE " +
           "LOWER(r.routeNumber) LIKE LOWER(CONCAT('%',:q,'%')) OR " +
           "LOWER(r.startStop)   LIKE LOWER(CONCAT('%',:q,'%')) OR " +
           "LOWER(r.endStop)     LIKE LOWER(CONCAT('%',:q,'%'))")
    List<BusRoute> search(@Param("q") String q);
}
