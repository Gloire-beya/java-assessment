package com.gloire.assessment.model;

import javax.persistence.*;
import java.util.LinkedList;

@Entity
public class Planet {
    @Id
    @Column(name = "planet_node", length = 5)
    private String planetNode;
    @Column(name = "planet_name", length = 100)
    private String name;
    @Transient
    private boolean visited;
    @Transient
    LinkedList<Route> routes;

    public Planet() {
    }

    public Planet(String planetNode, String name) {
        this.planetNode = planetNode;
        this.name = name;
        this.visited = false;
        this.routes = new LinkedList<>();
    }

    public String getPlanetNode() {
        return planetNode;
    }

    public void setPlanetNode(String planetID) {
        this.planetNode = planetID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setVisited(boolean visited) {
        this.visited = visited;
    }

    public LinkedList<Route> getRoutes() {
        return routes;
    }

    public void setRoutes(LinkedList<Route> routes) {
        this.routes = routes;
    }

    public boolean isVisited() {
        return visited;
    }

    public void visit() {
        this.visited = true;
    }

    public void unvisit() {
        this.visited = false;
    }
}
