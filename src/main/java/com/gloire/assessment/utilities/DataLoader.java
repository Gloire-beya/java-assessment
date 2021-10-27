package com.gloire.assessment.utilities;

import com.gloire.assessment.model.Planet;
import com.gloire.assessment.model.Route;
import com.gloire.assessment.repository.PlanetRepository;
import com.gloire.assessment.repository.RouteRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.LinkedList;

@Component
public class DataLoader implements CommandLineRunner {
    private PlanetRepository planetRepository;
    private RouteRepository routeRepository;

    public DataLoader(PlanetRepository planetRepository, RouteRepository routeRepository) {
        this.planetRepository = planetRepository;
        this.routeRepository = routeRepository;
    }

    public void run(String... args) throws Exception {
        LinkedList<Planet> planets = ExcelUtils.getPlanetData();
        planetRepository.saveAll(planets);

        final LinkedList<Route> routes = ExcelUtils.getRouteData();
        routeRepository.saveAll(routes);
    }
}
