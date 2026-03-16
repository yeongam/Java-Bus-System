package com.bus.timetable.service;

import com.bus.timetable.model.BusRoute;
import com.bus.timetable.repository.BusRouteRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@Transactional(readOnly = true)
public class BusRouteService {

    private final BusRouteRepository busRouteRepository;

    public BusRouteService(BusRouteRepository busRouteRepository) {
        this.busRouteRepository = busRouteRepository;
    }

    public List<BusRoute> getAllRoutes() {
        return busRouteRepository.findAll();
    }

    public BusRoute getRouteById(Long id) {
        return busRouteRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("노선을 찾을 수 없습니다. id=" + id));
    }

    public BusRoute getRouteByNumber(String routeNumber) {
        return busRouteRepository.findByRouteNumber(routeNumber)
                .orElseThrow(() -> new NoSuchElementException("노선을 찾을 수 없습니다. routeNumber=" + routeNumber));
    }
}
