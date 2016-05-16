package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import OSPDataStruct.SimQueue;
import sk.epholl.dissim.sem3.continualAssistants.Loader1Process;
import sk.epholl.dissim.sem3.continualAssistants.Loader2Process;
import sk.epholl.dissim.sem3.continualAssistants.LoaderOpenScheduler;
import sk.epholl.dissim.sem3.entities.Loader;
import sk.epholl.dissim.sem3.managers.LoaderManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//meta! id="5"
public class LoaderAgent extends Agent {

    private double currentStorageCargo = 3500D;

    private SimQueue<MyMessage> loaderQueue = new SimQueue<>();

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
        return getFreeLoader() != null;
    }

    public Loader getFreeLoader() {
        for (Loader loader: loaders) {
            if (loader.isOpen() && loader.canAccept()) {
                return loader;
            }
        }
        return null;
    }

    public Loader getLoader(int index) {
        return loaders.get(index);
    }

    public boolean hasEnqueuedVehicles() {
        return !loaderQueue.isEmpty();
    }

    public void enqueueVehicle(MyMessage message) {
        loaderQueue.addLast(message);
    }

    public MyMessage dequeueVehicle() {
        return loaderQueue.removeFirst();
    }

    public SimQueue<MyMessage> getQueue() {
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