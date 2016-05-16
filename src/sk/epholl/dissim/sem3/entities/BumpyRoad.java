package sk.epholl.dissim.sem3.entities;

import OSPABA.Entity;
import OSPABA.Simulation;
import sk.epholl.dissim.sem2.util.Utils;
import sk.epholl.dissim.sem3.simulation.MyMessage;

import java.util.LinkedList;

/**
 * Created by Tomáš on 12.05.2016.
 */
public class BumpyRoad extends Entity {

    private double length;
    private String name;

    private LinkedList<MyMessage> vehicles;

    public BumpyRoad(Simulation sim, String name,  double length) {
        super(sim);
        this.length = length;
        this.name = name;
    }

    public double accept(Vehicle vehicle) {
        double travelDurationInHours = length / vehicle.getSpeed();
        double travelDurationInSeconds = Utils.hoursToSeconds(travelDurationInHours);

        double breakupTime = vehicle.calculateTimeSpentRepairing();
        double totalDuration = travelDurationInSeconds + breakupTime;

        vehicle.setState(vehicle.isBreakdown() ?
                Vehicle.STATE_BREAKDOWN + getName() : Vehicle.STATE_TRAVELLING + getName());

        return totalDuration;
    }

    public String getName() {
        return name;
    }
}
