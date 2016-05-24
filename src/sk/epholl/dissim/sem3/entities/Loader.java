package sk.epholl.dissim.sem3.entities;

import OSPABA.Entity;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.LoaderAgent;
import sk.epholl.dissim.sem3.simulation.MySimulation;
import sk.epholl.dissim.sem3.util.Utils;

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
        LocalTime currentTime = ((MySimulation)mySim()).getSimDateTime().toLocalTime();
        return Utils.timeInInterval(openingHours, closingHours, currentTime);
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

    public double getCurrentVehicleCargoStatus() {
        double timeDiff = endTime - startTime;
        double elapsedTime = loaderAgent.mySim().currentTime() - startTime;
        double currentlyLoaded = loadedCargo * (elapsedTime / timeDiff);
        loadedVehicle.setLoad(currentlyLoaded);
        return currentlyLoaded;
    }

    public double getCurrentLoaderCargoStatus() {
        double timeDiff = endTime - startTime;
        double elapsedTime = loaderAgent.mySim().currentTime() - startTime;
        return (loadedCargo * (1 - (elapsedTime / timeDiff)));
    }

    public Vehicle finishVehicle() {
        loadedVehicle.setLoad(loadedCargo);
        loadedCargo = 0D;
        Vehicle returned = loadedVehicle;
        loadedVehicle = null;
        return returned;
    }

    public LocalTime getOpeningHours() {
        return openingHours;
    }

    public void setOpeningHours(LocalTime openingHours) {
        this.openingHours = openingHours;
    }

    public LocalTime getClosingHours() {
        return closingHours;
    }

    public void setClosingHours(LocalTime closingHours) {
        this.closingHours = closingHours;
    }

    public LoaderAgent getLoaderAgent() {
        return loaderAgent;
    }
}
