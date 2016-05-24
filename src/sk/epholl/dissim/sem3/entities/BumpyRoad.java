package sk.epholl.dissim.sem3.entities;

import OSPABA.Entity;
import OSPABA.Simulation;
import sk.epholl.dissim.sem2.util.Utils;
import sk.epholl.dissim.sem3.simulation.MyMessage;

/**
 * Created by Tomáš on 12.05.2016.
 */
public class BumpyRoad extends Entity {

    private double length;
    private String name;

    public BumpyRoad(Simulation sim, double length, String name) {
        super(sim);
        this.length = length;
        this.name = name;
    }

    public double accept(MyMessage message) {
        Vehicle vehicle = message.getVehicle();
        double travelDurationInHours = length / vehicle.getSpeed();
        double travelDurationInSeconds = Utils.hoursToSeconds(travelDurationInHours);

        double breakupTime = vehicle.calculateTimeSpentRepairing();
        double totalDurationSeconds = travelDurationInSeconds + breakupTime;

        vehicle.setState(vehicle.isBreakdown() ?
                Vehicle.STATE_BREAKDOWN + getName() : Vehicle.STATE_TRAVELLING + getName());

        return totalDurationSeconds;
    }

    public String getName() {
        return name;
    }
}
