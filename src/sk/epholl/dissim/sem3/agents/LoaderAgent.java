package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import OSPDataStruct.SimQueue;
import sk.epholl.dissim.sem3.entities.Vehicle;
import sk.epholl.dissim.sem3.managers.LoaderManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;

//meta! id="5"
public class LoaderAgent extends Agent {

    private double currentStorageCargo = 3500D;

    private SimQueue<Vehicle> loaderQueue = new SimQueue<>();

    public LoaderAgent(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
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

    public SimQueue<Vehicle> getQueue() {
        return loaderQueue;
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new LoaderManager(Id.loaderManager, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.materialDelivered);
		addOwnMessage(Mc.loadVehicle);
	}
	//meta! tag="end"
}