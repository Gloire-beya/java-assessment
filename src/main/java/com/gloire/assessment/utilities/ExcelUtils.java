package com.gloire.assessment.utilities;

import com.gloire.assessment.model.Planet;
import com.gloire.assessment.model.Route;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Map;
import java.util.stream.Collectors;


public class ExcelUtils {
    private static final DataFormatter formatter = new DataFormatter();
    private static XSSFWorkbook workbook = null;
    private static Logger logger = LoggerFactory.getLogger(ExcelUtils.class);

    static {
        try {
            workbook = new XSSFWorkbook(getDatasetFile());
        } catch (IOException | InvalidFormatException e) {
            logger.error("Something went wrong");
        }
    }

    private static File getDatasetFile() {
        // this method returns the Excel file containing sample data
        File excelFile = null;
        try {
            excelFile = ResourceUtils.getFile("classpath:dataset.xlsx");
        } catch (FileNotFoundException e) {
            logger.error("File was not found");
        }
        return excelFile;
    }

    private static void closeWorkbook() {
        try {
            workbook.close();
        } catch (IOException e) {
            logger.error("Could not close WORKBOOK");
        }
    }

    public static LinkedList<Planet> getPlanetData() {
        LinkedList<Planet> planets = new LinkedList<>();
        final XSSFSheet planetNames = workbook.getSheetAt(0);
        final int numberOfRows = getRowCountForASheet(0);
        for (int i = 1; i < numberOfRows; i++) {
            final Object node = formatter.formatCellValue(planetNames.getRow(i).getCell(0));
            final Object name = formatter.formatCellValue(planetNames.getRow(i).getCell(1));
            final Planet planet = new Planet((String) node, (String) name);
            planets.add(planet);
        }
        //closeWorkbook();
        return planets;
    }

    public static LinkedList<Route> getRouteData() {
        final Map<String, String> planetAsMap = getPlanetAsMap();
        LinkedList<Route> routes = new LinkedList<>();
        final XSSFSheet routeDetails = workbook.getSheetAt(1);
        final int numberOfRows = getRowCountForASheet(1);
        for (int i = 1; i < numberOfRows; i++) {
            final String origin = formatter.formatCellValue(routeDetails.getRow(i).getCell(1));
            final String destination = formatter.formatCellValue(routeDetails.getRow(i).getCell(2));
            final String distance = formatter.formatCellValue(routeDetails.getRow(i).getCell(3));

            final Planet originPlanet = new Planet(origin, planetAsMap.get(origin));
            final Planet destinationPlanet = new Planet(destination, planetAsMap.get(destination));


            final Route route = new Route(originPlanet, destinationPlanet, Double.parseDouble(distance));
            routes.add(route);
        }
        //closeWorkbook();
        return routes;
    }

    private static Map<String, String> getPlanetAsMap() {
        return getPlanetData().stream()
                .collect(Collectors.toMap(Planet::getPlanetNode, Planet::getName));
    }

    private static int getRowCountForASheet(int index) {
        final XSSFSheet sheetAt = workbook.getSheetAt(index);
        return sheetAt.getPhysicalNumberOfRows();
    }
}
