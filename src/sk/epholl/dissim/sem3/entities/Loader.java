package sk.epholl.dissim.sem3.entities;

import OSPABA.Entity;
import OSPABA.Simulation;
import sk.epholl.dissim.sem2.util.Utils;
import sk.epholl.dissim.sem3.agents.LoaderAgent;
import sk.epholl.dissim.sem3.simulation.MySimulation;

import java.time.LocalTime;

/**
 * Created by Tomáš on 12.05.2016.
 */
public class Loader extends Entity {

    private double loadingSpeed;
    private LoaderAgent loaderAgent;

    private LocalTime openingHours;
    private LocalTime closingHours;

    private double loadedCargo;
    private Vehicle loadedVehicle;

    private double startTime;
    private double endTime;

    public Loader(Simulation sim, double loadingSpeed, LocalTime openingHour, LocalTime closingHour, LoaderAgent loaderAgent) {
        super(sim);
        this.loaderAgent = loaderAgent;
        this.loadingSpeed = loadingSpeed;

        this.openingHours = openingHour;
        this.closingHours = closingHour;
    }

    public boolean canAccept() {
        return loadedVehicle == null;
    }

    public boolean isOpen() {
        LocalTime currentTime = ((MySimulation)mySim()).getSimTimeNiceFormat().toLocalTime();
        return (currentTime.isAfter(openingHours) && currentTime.isBefore(closingHours));
    }

    public void setLoadedVehicle(Vehicle vehicle) {
        this.loadedVehicle = vehicle;
    }

    public void startWork() {
        loadedCargo = loaderAgent.removeFromCargo(loadedVehicle.getCapacity());
        double loadingTimeHours = loadedCargo / loadingSpeed;
        double loadingTimeSeconds = Utils.hoursToSeconds(loadingTimeHours);
        startTime = loaderAgent.mySim().currentTime();
        endTime = startTime + loadingTimeSeconds;
    }

    public double getLoadingDuration() {
        return endTime - startTime;
    }

    public double getCurrentVehicleLoadStatus() {
        double timeDiff = endTime - startTime;
        double currentlyDone = loaderAgent.mySim().currentTime() - startTime;
        return loadedCargo * (currentlyDone / timeDiff);
    }

    public double getCurrentCargoStatus() {
        double timeDiff = endTime - startTime;
        double currentlyDone = loaderAgent.mySim().currentTime() - startTime;
        return (loadedCargo * (1 - (currentlyDone / timeDiff)));
    }

    public Vehicle finishVehicle() {
        loadedCargo = 0D;
        loadedVehicle = null;
        return loadedVehicle;
    }

    public LoaderAgent getLoaderAgent() {
        return loaderAgent;
    }
}
