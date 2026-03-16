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

    @Transactional
    public BusRoute save(String routeNumber, String startStop, String endStop, String description) {
        return busRouteRepository.save(new BusRoute(routeNumber, startStop, endStop, description));
    }

    @Transactional
    public BusRoute update(Long id, String routeNumber, String startStop, String endStop, String description) {
        BusRoute route = getRouteById(id);
        route.setRouteNumber(routeNumber);
        route.setStartStop(startStop);
        route.setEndStop(endStop);
        route.setDescription(description);
        return busRouteRepository.save(route);
    }

    @Transactional
    public void delete(Long id) {
        busRouteRepository.deleteById(id);
    }
}
