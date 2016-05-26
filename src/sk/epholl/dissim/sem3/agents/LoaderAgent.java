package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import OSPDataStruct.SimQueue;
import sk.epholl.dissim.sem3.continualAssistants.LoaderOpenScheduler;
import sk.epholl.dissim.sem3.continualAssistants.LoaderProcess;
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
        amount = Math.min(currentStorageCargo, amount);
        currentStorageCargo -= amount;
        return amount;
    }

    public boolean hasLoadingCapacityOpen() {
        return getFreeLoader() != null;
    }

    public boolean hasCargoToLoad() {
        return currentStorageCargo > 0;
    }

    public Loader getFreeLoader() {
        for (Loader loader: loaders) {
            if (loader.isOpen() && loader.canAccept()) {
                return loader;
            }
        }
        return null;
    }

    public int getOpenLoadersCount() {
        int i = 0;
        for (Loader loader: loaders) {
            if (loader.isOpen()) {
                i++;
            }
        }
        return i;
    }

    public int getOpenLoadersCount(LocalTime time) {
        int i = 0;
        for (Loader loader: loaders) {
            if (loader.isOpenAtTime(time)) {
                i++;
            }
        }
        return i;
    }

    public Loader getLoader(int index) {
        return loaders.get(index);
    }

    public boolean hasEnqueuedVehicles() {
        return !loaderQueue.isEmpty();
    }

    public void enqueueVehicle(MyMessage message) {
        loaderQueue.enqueue(message);
    }

    public MyMessage dequeueVehicle() {
        return loaderQueue.dequeue();
    }

    public SimQueue<MyMessage> getQueue() {
        return loaderQueue;
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new LoaderManager(Id.loaderManager, mySim(), this);
		new LoaderOpenScheduler(Id.loaderOpenScheduler, mySim(), this);
		new LoaderProcess(Id.loaderProcess, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.materialDelivered);
		addOwnMessage(Mc.loadVehicle);
        addOwnMessage(Mc.loaderOpen);
        addOwnMessage(Mc.loaderClose);
	}
	//meta! tag="end"

    public List<Loader> getLoaders() {
        return loaders;
    }
}