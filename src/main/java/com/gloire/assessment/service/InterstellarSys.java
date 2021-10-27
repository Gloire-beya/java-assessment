package com.gloire.assessment.service;

import com.gloire.assessment.model.Planet;
import com.gloire.assessment.model.Route;

import java.util.*;

public class InterstellarSys {
    private Set<Planet> planets;
    private boolean directed;

    public InterstellarSys(boolean directed) {
        this.directed = directed;
        this.planets = new HashSet<>();
    }

    public void addPlanet(Planet... planets) {
        this.planets.addAll(Arrays.asList(planets));
    }

    public void addRoute(Planet origin, Planet destination, double distance) {
        planets.add(origin);
        planets.add(destination);

        addRouteHelper(origin, destination, distance);
        if (!directed && origin != destination) {
            addRouteHelper(destination, origin, distance);
        }
    }

    private void addRouteHelper(Planet origin, Planet destination, double distance) {
        for (Route route : origin.getRoutes()) {
            if (route.getOrigin() == origin && route.getDestination() == destination) {
                route.setDistance(distance);
                return;
            }
        }
        origin.getRoutes().add(new Route(origin, destination, distance));
    }

    public boolean hasRoute(Planet origin, Planet destination) {
        final LinkedList<Route> routes = origin.getRoutes();
        for (Route route : routes) {
            if (route.getDestination() == destination) {
                return true;
            }
        }
        return false;
    }

    public void resetPlanetVisited() {
        for (Planet planet : planets) {
            planet.unvisit();
        }
    }

    public void DijkstraShortestPath(Planet start, Planet end) {
        HashMap<Planet, Planet> changeAt = new HashMap<>();
        changeAt.put(start, null);

        HashMap<Planet, Double> shortestPathMap = new HashMap<>();

        for (Planet planet : planets) {
            if (planet == start) shortestPathMap.put(start, 0.0);
            else shortestPathMap.put(planet, Double.POSITIVE_INFINITY);
        }

        for (Route route : start.getRoutes()) {
            shortestPathMap.put(route.getDestination(), route.getDistance());
            changeAt.put(route.getDestination(), start);
        }

        start.visit();

        while (true) {
            Planet currentPlanet = closestReachableUnvisited(shortestPathMap);

            if (currentPlanet == null) {
                System.out.println("There is no routes between " + start.getName() + " and " + end.getName());
                return;
            }

            if (currentPlanet == end) {
                System.out.println("The shortest route between " + start.getName() + " and " + end.getName() + " is: ");

                Planet child = end;

                String path = end.getName();
                while (true) {
                    final Planet parent = changeAt.get(child);
                    if (parent == null) break;


                    path = parent.getName() + " " + path;
                    child = parent;
                }
                System.out.println(path);
                System.out.println("The path coasts " + shortestPathMap.get(end));
                return;
            }
            currentPlanet.visit();
            for (Route route : currentPlanet.getRoutes()) {
                if (route.getDestination().isVisited()) {
                    continue;
                }
                if (shortestPathMap.get(currentPlanet) + route.getDistance()
                        < shortestPathMap.get(route.getDestination())) {
                    shortestPathMap.put(route.getDestination(),
                            shortestPathMap.get(currentPlanet) + route.getDistance());
                    changeAt.put(route.getDestination(), currentPlanet);
                }
            }
        }
    }

    private Planet closestReachableUnvisited(HashMap<Planet, Double> shortestPathMap) {

        double shortestDistance = Double.POSITIVE_INFINITY;
        Planet closestReachablePlanet = null;
        for (Planet planet : planets) {
            if (planet.isVisited()) continue;

            double currentDistance = shortestPathMap.get(planet);
            if (currentDistance == Double.POSITIVE_INFINITY) continue;

            if (currentDistance < shortestDistance) {
                shortestDistance = currentDistance;
                closestReachablePlanet = planet;
            }
        }
        return closestReachablePlanet;
    }


    public static void main(String[] args) {
        InterstellarSys graphWeighted = new InterstellarSys(true);
        Planet zero = new Planet("0", "0");
        Planet one = new Planet("1", "1");
        Planet two = new Planet("2", "2");
        Planet three = new Planet("3", "3");
        Planet four = new Planet("4", "4");
        Planet five = new Planet("5", "5");
        Planet six = new Planet("6", "6");

        // Our addEdge method automatically adds Nodes as well.
        // The addNode method is only there for unconnected Nodes,
        // if we wish to add any
        graphWeighted.addRoute(zero, one, 8);
        graphWeighted.addRoute(zero, two, 11);
        graphWeighted.addRoute(one, three, 3);
        graphWeighted.addRoute(one, four, 8);
        graphWeighted.addRoute(one, two, 7);
        graphWeighted.addRoute(two, four, 9);
        graphWeighted.addRoute(three, four, 5);
        graphWeighted.addRoute(three, five, 2);
        graphWeighted.addRoute(four, six, 6);
        graphWeighted.addRoute(five, four, 1);
        graphWeighted.addRoute(five, six, 8);

        graphWeighted.DijkstraShortestPath(zero, four);
    }
}

