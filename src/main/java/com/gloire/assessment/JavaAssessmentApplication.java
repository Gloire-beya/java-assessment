package com.gloire.assessment;

import com.gloire.assessment.model.Planet;
import com.gloire.assessment.model.Route;
import com.gloire.assessment.repository.PlanetRepository;
import com.gloire.assessment.repository.RouteRepository;
import com.gloire.assessment.utilities.ExcelUtils;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.LinkedList;

@SpringBootApplication
public class JavaAssessmentApplication {

    public static void main(String[] args) {
        SpringApplication.run(JavaAssessmentApplication.class, args);
    }
}
