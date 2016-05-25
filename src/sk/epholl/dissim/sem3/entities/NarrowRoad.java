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

    private LinkedList<MyMessage> messages = new LinkedList<>();
    private LinkedList<Vehicle> vehicles = new LinkedList<>();

    public NarrowRoad(Simulation sim, double length, String name) {
        super(sim);
        this.length = length;
        this.name = name;
    }

    public double accept(MyMessage message) {
        double travelDurationInHours = length / message.getVehicle().getSpeed();
        double travelDurationInSeconds = Utils.hoursToSeconds(travelDurationInHours);

        double arriveTime = mySim().currentTime() + travelDurationInSeconds;

        for (MyMessage travelling: messages) {
            if (travelling.getTravelArriveTime() > arriveTime) {
                arriveTime = travelling.getTravelArriveTime();
            }
        }

        travelDurationInSeconds = arriveTime - mySim().currentTime();
        message.setTravelArriveTime(arriveTime);
        messages.addLast(message);
        vehicles.addLast(message.getVehicle());
        return travelDurationInSeconds;
    }

    public Vehicle dequeueTopVehicle() {
        return vehicles.removeFirst();
    }
    public void removeFromQueue(MyMessage msg) {
        if (!messages.remove(msg)) {
            throw new IllegalStateException("Dequeued a vehicle not on road.");
        }
    }
}
