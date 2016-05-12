package sk.epholl.dissim.sem3.simulation;

import sk.epholl.dissim.sem3.entities.Vehicle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Tomáš on 12.05.2016.
 */
public class SimulationParameters {

    public List<Vehicle> availableVehicles;

    public boolean buySecondUnloader;

    public static SimulationParameters getDefaultParameters(OSPABA.Simulation sim) {
        SimulationParameters params = new SimulationParameters();
        params.availableVehicles = new ArrayList<>();
        params.availableVehicles.add(new Vehicle(sim, 10, 60, 0.12, 80));
        params.availableVehicles.add(new Vehicle(sim, 20, 50, 0.04, 50));

        params.buySecondUnloader = false;
        return params;
    }
}
