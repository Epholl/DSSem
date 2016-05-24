package sk.epholl.dissim.sem3.entities;

import OSPABA.Entity;
import OSPABA.Simulation;
import sk.epholl.dissim.sem2.util.Utils;
import sk.epholl.dissim.sem3.simulation.MyMessage;

import java.util.LinkedList;

/**
 * Created by Tomáš on 20.05.2016.
 */
public class NarrowRoad extends Entity {

    private double length;
    private String name;

    private LinkedList<MyMessage> vehicles = new LinkedList<>();

    public NarrowRoad(Simulation sim, double length, String name) {
        super(sim);
        this.length = length;
        this.name = name;
    }

    public double accept(MyMessage vehicle) {
        double travelDurationInHours = length / vehicle.getVehicle().getSpeed();
        double travelDurationInSeconds = Utils.hoursToSeconds(travelDurationInHours);

        double arriveTime = mySim().currentTime() + travelDurationInSeconds;

        for (MyMessage travelling: vehicles) {
            if (travelling.getTravelArriveTime() > arriveTime) {
                arriveTime = travelling.getTravelArriveTime();
            }
        }

        travelDurationInSeconds = arriveTime - mySim().currentTime();
        vehicle.setTravelArriveTime(arriveTime);
        vehicles.addLast(vehicle);
        return travelDurationInSeconds;
    }

    public MyMessage dequeueTop() {
        return vehicles.removeFirst();
    }
}
