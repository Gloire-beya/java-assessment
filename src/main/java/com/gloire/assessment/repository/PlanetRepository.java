package com.gloire.assessment.repository;

import com.gloire.assessment.model.Planet;
import org.springframework.data.repository.CrudRepository;

public interface PlanetRepository extends CrudRepository<Planet, Long> {
}
