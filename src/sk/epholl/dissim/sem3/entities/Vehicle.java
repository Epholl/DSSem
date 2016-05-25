package sk.epholl.dissim.sem3.entities;

import OSPABA.Entity;
import OSPABA.Simulation;
import sk.epholl.dissim.sem2.generator.ContinuousEvenRandom;
import sk.epholl.dissim.sem2.generator.RandomGenerator;
import sk.epholl.dissim.sem2.util.Utils;

/**
 * Created by Tomáš on 10.04.2016.
 */
public class Vehicle extends Entity {

    public static final String STATE_IDLE = "idle";
    public static final String STATE_TRAVELLING = "travelling ";
    public static final String STATE_BREAKDOWN = "broken ";
    public static final String STATE_WAITING = "waiting ";
    public static final String STATE_LOADING = "loading";
    public static final String STATE_UNLOADING = "unloading";
    public static final String STATE_NIGHT = "parked";

    public static final String DEFAULT_START_LOCATION = "A";

    private double capacity;
    private double speed;
    private double breakdownProbability;
    private double repairTime;

    private boolean isBreakdown = false;
    private double waitingStartedTime;

    private int finishedLoadsCount = 0;
    private String state = STATE_IDLE;
    private String location = DEFAULT_START_LOCATION;

    private RandomGenerator<Double> breakdownGenerator;

    private double currentLoad;

    public Vehicle(Simulation simulation, double capacity, double speed, double breakdownProbability, double repairTime) {
        super(simulation);
        this.capacity = capacity;
        this.speed = speed;
        this.breakdownProbability = breakdownProbability;
        this.repairTime = repairTime;

        this.breakdownGenerator = new ContinuousEvenRandom(0, 1);
    }

    public double calculateTimeSpentRepairing() {
        if (calculateIsBreakdownNextRoute()) {
            isBreakdown = true;
            return Utils.minutesToSeconds(repairTime);
        } else {
            isBreakdown = false;
            return 0;
        }
    }

    public double getCapacity() {
        return capacity;
    }

    public double getSpeed() {
        return speed;
    }

    public double getBreakdownProbability() {
        return breakdownProbability;
    }

    public double getRepairTime() {
        return repairTime;
    }

    public double getCurrentLoad() {
        return currentLoad;
    }

    public String getState() {
        return state;
    }

    public boolean isBreakdown() {
        return isBreakdown;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setLoad(double currentLoad) {
        this.currentLoad = currentLoad;
    }

    private boolean calculateIsBreakdownNextRoute() {
        double value = breakdownGenerator.nextValue();
        return value < breakdownProbability;
    }

    public String getLoadInfo() {
        return currentLoad + "/" + capacity;
    }

    @Override
    public String toString() {
        return getLoadInfo() + ", state: " + getState();
    }

    public int getFinishedLoadsCount() {
        return finishedLoadsCount;
    }

    public void setWaitingStartedTime(double time) {
        waitingStartedTime = time;
    }

    public double getWaitingStartedTime() {
        return waitingStartedTime;
    }
}
