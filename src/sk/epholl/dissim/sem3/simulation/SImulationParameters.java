package sk.epholl.dissim.sem3.simulation;

import OSPRNG.*;
import org.apache.commons.math3.distribution.BetaDistribution;
import sk.epholl.dissim.sem3.util.Rand;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomáš on 12.05.2016.
 */
public class SimulationParameters {

    public List<Vehicle> availableVehicles;
    public List<Supplier> suppliers;

    public Road roadAb;
    public Road roadBc;
    public Road roadCa;

    public boolean buySecondUnloader;

    public static SimulationParameters getDefaultParameters() {
        SimulationParameters params = new SimulationParameters();
        params.availableVehicles = new ArrayList<>();
        params.availableVehicles.add(new Vehicle(10, 60, 0.12, 80));
        params.availableVehicles.add(new Vehicle(20, 50, 0.04, 50));

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

        params.buySecondUnloader = false;

        params.roadAb = new Road(45);
        params.roadBc = new Road(15);
        params.roadCa = new Road(35);

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

        public Vehicle(double capacity, double speed, double breakdownProbability, double repairTime) {
            this.capacity = capacity;
            this.speed = speed;
            this.breakdownProbability = breakdownProbability;
            this.repairTime = repairTime;
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
