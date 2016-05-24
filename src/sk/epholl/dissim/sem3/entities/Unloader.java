package sk.epholl.dissim.sem3.entities;

import OSPABA.Entity;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.UnloaderAgent;
import sk.epholl.dissim.sem3.simulation.MySimulation;
import sk.epholl.dissim.sem3.util.Utils;

import java.time.LocalTime;

/**
 * Created by Tomáš on 23.05.2016.
 */
public class Unloader extends Entity {

    private double loadingSpeed;
    private UnloaderAgent unloaderAgent;

    private LocalTime openingHours;
    private LocalTime closingHours;

    private double totalVehicleCargoAmount;
    private double unloadedCargoAmount;
    private Vehicle unloadedVehicle;

    private double startTime;
    private double endTime;

    public Unloader(Simulation mySim, double loadingSpeed, LocalTime openingHour, LocalTime closingHour, UnloaderAgent unloaderAgent) {
        super(mySim);
        this.loadingSpeed = loadingSpeed;
        this.unloaderAgent = unloaderAgent;

        this.openingHours = openingHour;
        this.closingHours = closingHour;
    }

    public boolean isOpen() {
        LocalTime currentTime = ((MySimulation)mySim()).getSimDateTime().toLocalTime();
        return Utils.timeInInterval(openingHours, closingHours, currentTime);
    }

    public boolean canAccept() {
        return unloadedVehicle == null;
    }

    public void setUnloadedVehicle(Vehicle vehicle) {
        this.unloadedVehicle = vehicle;
    }

    public void startWork() {
        totalVehicleCargoAmount = unloadedVehicle.getCurrentLoad();
        unloadedCargoAmount = unloaderAgent.getPossibleAddedAmount(totalVehicleCargoAmount);

        double unloadingTimeHours = unloadedCargoAmount / loadingSpeed;
        double unloadingTimeSeconds = Utils.hoursToSeconds(unloadingTimeHours);

        startTime = unloaderAgent.mySim().currentTime();
        endTime = startTime + unloadingTimeSeconds;
    }

    public double getLoadingDuration() {
        return endTime - startTime;
    }

    public double getCurrentVehicleCargoStatus() {
        double timeDiff = endTime - startTime;
        double elapsedTime = unloaderAgent.mySim().currentTime() - startTime;

        return totalVehicleCargoAmount - (unloadedCargoAmount * (elapsedTime / timeDiff));
    }

    public double getCurrentUnloaderCargoStatus() {
        double timeDiff = endTime - startTime;
        double elapsedTime = unloaderAgent.mySim().currentTime() - startTime;
        return unloadedCargoAmount * (elapsedTime / timeDiff);
    }

    public Vehicle finishVehicle() {
        unloadedVehicle.setLoad(totalVehicleCargoAmount - unloadedCargoAmount);
        getUnloaderAgent().addToCargo(unloadedCargoAmount);
        totalVehicleCargoAmount = 0D;
        unloadedCargoAmount = 0D;
        Vehicle returned = unloadedVehicle;
        unloadedVehicle = null;
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

    public UnloaderAgent getUnloaderAgent() {
        return unloaderAgent;
    }
}
