package sk.epholl.dissim.sem3.simulation;

import OSPRNG.*;
import org.apache.commons.math3.distribution.BetaDistribution;
import sk.epholl.dissim.sem3.util.Rand;
import sk.epholl.dissim.sem3.util.Utils;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomáš on 12.05.2016.
 */
public class SimulationParameters {

    public enum NightParking {
        LOADERS,
        UNLOADERS
    }

    public LocalDateTime startDateTime;
    public int durationInMonths;

    public List<Vehicle> availableVehicles;
    public List<Supplier> suppliers;

    public LocalTime wakeupTime;

    public Road roadAb;
    public Road roadBc;
    public Road roadCa;

    public Rand consumerAmountRandom;

    public int unloaderCount;

    public NightParking parkingArea;

    public LocalDateTime getEndingDateTime() {
        return startDateTime.plusMonths(durationInMonths);
    }

    public double getEndingSimulationTime() {
        return Utils.secondsBetweenDateTimes(startDateTime, getEndingDateTime());
    }

    public static List<Vehicle> getVanillaVehicleTypes() {
        List<Vehicle> types = new ArrayList<>();
        types.add(new Vehicle(10, 60, 0.12, 80, 30000, 3));
        types.add(new Vehicle(20, 50, 0.04, 50, 55000, Long.MAX_VALUE));
        types.add(new Vehicle(25, 45, 0.04, 100, 40000, Long.MAX_VALUE));
        types.add(new Vehicle(5, 70, 0.11, 44, 60000, Long.MAX_VALUE));
        types.add(new Vehicle(40, 30, 0.06, 170, 10000, 2));
        return types;
    }

    public static SimulationParameters getDefaultParameters() {
        SimulationParameters params = new SimulationParameters();
        params.availableVehicles = new ArrayList<>();
        params.availableVehicles.add(new Vehicle(10, 60, 0.12, 80, 30000, 3));
        params.availableVehicles.add(new Vehicle(20, 50, 0.04, 50, 55000, Long.MAX_VALUE));
        params.availableVehicles.add(new Vehicle(25, 45, 0.04, 100, 40000, Long.MAX_VALUE));
        params.availableVehicles.add(new Vehicle(5, 70, 0.11, 44, 60000, Long.MAX_VALUE));
        params.availableVehicles.add(new Vehicle(40, 30, 0.06, 170, 10000, 2));

        params.startDateTime = LocalDateTime.of(2016, 1, 1, 7, 0);
        params.durationInMonths = 18;

        params.suppliers = new ArrayList<>();
        params.suppliers.add(new Supplier(
                "A",
                new Rand(new TriangularRNG(4.5d, 15.0d, 16.5d)),
                new Rand(new ExponentialRNG(49.5d, 1d))
        ));
        params.suppliers.add(new Supplier(
                "B",
                new Rand(new BetaDistribution(11.9, 3.1), 5.5, 16),
                new Rand(new ExponentialRNG(36.8, 3D))
        ));
        params.suppliers.add(new Supplier(
                "C",
                new Rand(new EmpiricRNG(
                        new EmpiricPair(new UniformContinuousRNG(5D, 10D), 0.03D),
                        new EmpiricPair(new UniformContinuousRNG(10D, 20D), 0.2D),
                        new EmpiricPair(new UniformContinuousRNG(20D, 25D), 0.77D)
                )),
                new Rand(new ExponentialRNG(25.8D, 1d))
        ));

        params.unloaderCount = 1;

        params.parkingArea = NightParking.LOADERS;
        params.wakeupTime = LocalTime.of(7, 0);

        params.roadAb = new Road(45);
        params.roadBc = new Road(15);
        params.roadCa = new Road(35);

        params.consumerAmountRandom = new Rand(new EmpiricRNG(
                new EmpiricPair(new UniformDiscreteRNG(10, 20), 0.02D),
                new EmpiricPair(new UniformDiscreteRNG(21, 48), 0.2D),
                new EmpiricPair(new UniformDiscreteRNG(49, 65), 0.33D),
                new EmpiricPair(new UniformDiscreteRNG(66, 79), 0.3D),
                new EmpiricPair(new UniformDiscreteRNG(80, 99), 0.15D)
                ));

        return params;
    }

    public static class Road {
        public double length;

        public Road(double length) {
            this.length = length;
        }
    }

    public static class Vehicle {
        public double capacity;
        public double speed;
        public double breakdownProbability;
        public double repairTime;

        public long price;
        public long maxCount;

        public long realCount = 0;

        public Vehicle(double capacity, double speed, double breakdownProbability, double repairTime, long price, long maxCount) {
            this.capacity = capacity;
            this.speed = speed;
            this.breakdownProbability = breakdownProbability;
            this.repairTime = repairTime;
            this.price = price;
            this.maxCount = maxCount;
        }

        public Vehicle clone() {
            return new Vehicle(capacity, speed, breakdownProbability, repairTime, price, maxCount);
        }
    }

    public static class Supplier {
        public String name;
        public Rand amountGenerator;
        public Rand timeBetweenGenerator;
        public LocalDateTime supplierEndTime;

        public Supplier(String name, Rand amountGen, Rand timeBetweenGen) {
            this.name = name;
            this.amountGenerator = amountGen;
            this.timeBetweenGenerator = timeBetweenGen;
        }

        public Supplier(String name, Rand amountGen, Rand timeBetweenGen, LocalDateTime endDateTime) {
            this.name = name;
            this.amountGenerator = amountGen;
            this.timeBetweenGenerator = timeBetweenGen;
            this.supplierEndTime = endDateTime;
        }
    }
}
