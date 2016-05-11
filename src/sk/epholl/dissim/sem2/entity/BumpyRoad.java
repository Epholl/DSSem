package sk.epholl.dissim.sem2.entity;

import sk.epholl.dissim.sem2.core.SimulationCore;
import sk.epholl.dissim.sem2.event.TravelFinishedEvent;
import sk.epholl.dissim.sem2.util.Utils;

/**
 * Created by Tomáš on 13.04.2016.
 */
public class BumpyRoad extends SimulationComponent {

    private double length;

    public BumpyRoad(SimulationCore core, String name, double length) {
        super(core, name);
        this.length = length;
    }

    @Override
    public void accept(Vehicle vehicle) {
        double currentTime = simulationCore.getSimulationTime();
        double travelDurationInHours = length / vehicle.getSpeed();
        double travelDurationInSeconds = Utils.hoursToSeconds(travelDurationInHours);
        double arriveTimeWithoutBreakup = currentTime + travelDurationInSeconds;

        double breakupTime = vehicle.calculateTimeSpentRepairing();
        double arriveTime = arriveTimeWithoutBreakup + breakupTime;

        vehicle.setState(vehicle.isBreakdown()?
                Vehicle.STATE_BREAKDOWN + getName() : Vehicle.STATE_TRAVELLING + getName());

        simulationCore.addEvent(new TravelFinishedEvent(arriveTime, vehicle, this));
    }

    @Override
    public void finished(Vehicle vehicle) {
        onFinishedListener.onVehicleFinished(vehicle);
    }
}
