package com.gloire.assessment.model;

import javax.persistence.*;

@Entity
public class Route {
    @Id
    @SequenceGenerator(
            name = "route_sequence",
            sequenceName = "route_sequence",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.AUTO,
            generator = "route_sequence"
    )
    @Column(name = "route_id")
    private Long routeID;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Planet origin;
    @OneToOne(cascade = CascadeType.ALL, optional = false)
    private Planet destination;
    private double distance;

    public Route() {
    }

    public Route(Planet origin, Planet destination, double distance) {
        this.origin = origin;
        this.destination = destination;
        this.distance = distance;
    }

    public Long getRouteID() {
        return routeID;
    }

    public void setRouteID(Long routeID) {
        this.routeID = routeID;
    }

    public Planet getOrigin() {
        return origin;
    }

    public void setOrigin(Planet origin) {
        this.origin = origin;
    }

    public Planet getDestination() {
        return destination;
    }

    public void setDestination(Planet destination) {
        this.destination = destination;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}
