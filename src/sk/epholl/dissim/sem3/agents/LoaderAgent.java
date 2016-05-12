package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import OSPDataStruct.SimQueue;
import sk.epholl.dissim.sem3.entities.Loader;
import sk.epholl.dissim.sem3.entities.Vehicle;
import sk.epholl.dissim.sem3.managers.LoaderManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//meta! id="5"
public class LoaderAgent extends Agent {

    private double currentStorageCargo = 3500D;

    private SimQueue<Vehicle> loaderQueue = new SimQueue<>();

    public List<Loader> loaders = new ArrayList<>();

    public LoaderAgent(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        loaders.add(new Loader(mySim(), 180, LocalTime.of(7, 0), LocalTime.of(18, 0), this));
        loaders.add(new Loader(mySim(), 250, LocalTime.of(9, 0), LocalTime.of(22, 0), this));
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    public double getCurrentStorageCargo() {
        return currentStorageCargo;
    }

    public void addToCargo(double amount) {
        currentStorageCargo += amount;
    }

    public double removeFromCargo(double amount) {
        amount = Math.min(0d, amount);
        currentStorageCargo -= amount;
        return amount;
    }

    public boolean hasLoadingCapacityOpen() {
        for (Loader loader: loaders) {
            if (loader.isOpen() && loader.canAccept()) {
                return true;
            }
        }
        return false;
    }

    public boolean hasEnqueuedVehicles() {
        return !loaderQueue.isEmpty();
    }

    public void enqueueVehicle(Vehicle vehicle) {
        loaderQueue.addLast(vehicle);
    }

    public Vehicle dequeueVehicle() {
        return loaderQueue.removeFirst();
    }

    public SimQueue<Vehicle> getQueue() {
        return loaderQueue;
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new LoaderManager(Id.loaderManager, mySim(), this);
		new Loader2Process(Id.loader2Process, mySim(), this);
		new Loader1Process(Id.loader1Process, mySim(), this);
		new LoaderOpenScheduler(Id.loaderOpenScheduler, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.materialDelivered);
		addOwnMessage(Mc.loadVehicle);
	}
	//meta! tag="end"
}