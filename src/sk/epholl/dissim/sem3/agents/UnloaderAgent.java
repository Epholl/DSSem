package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import OSPDataStruct.SimQueue;
import sk.epholl.dissim.sem3.continualAssistants.UnloaderOpenScheduler;
import sk.epholl.dissim.sem3.continualAssistants.UnloaderProcess;
import sk.epholl.dissim.sem3.entities.Unloader;
import sk.epholl.dissim.sem3.managers.UnloaderManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//meta! id="6"
public class UnloaderAgent extends Agent {

	private double currentStorageCargo = 1300D;
	private double storageCargoCapacity = 10000D;

	private SimQueue<MyMessage> unloaderQueue = new SimQueue<>();

	public List<Unloader> unloaders = new ArrayList<>();

	public UnloaderAgent(int id, Simulation mySim, Agent parent, int unloaderCount) {
        super(id, mySim, parent);
		for (int i = 0; i < unloaderCount; i++) {
			unloaders.add(new Unloader(mySim, 190, LocalTime.of(7, 30), LocalTime.of(22, 0), this));
		}
        init();
    }


    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

	public boolean hasCargoAmount(double requestedAmount) {
		return currentStorageCargo >= requestedAmount;
	}

	public boolean canAddToCargo(double amount) {
		return (currentStorageCargo + amount > storageCargoCapacity);
	}

	public double getPossibleAddedAmount(double amount) {
		if (currentStorageCargo + amount > storageCargoCapacity) {
			return (currentStorageCargo + amount) - storageCargoCapacity;
		} else {
			return amount;
		}
	}

	public double removeCargoAmount(double requestedAmount) {
		if (currentStorageCargo < requestedAmount) {
			requestedAmount = currentStorageCargo;
			currentStorageCargo = 0D;
		} else {
			currentStorageCargo -= requestedAmount;
		}
		return requestedAmount;
	}

	public void addToCargo(double amount) {
		currentStorageCargo += amount;
	}

	public Unloader getFreeUnloader() {
		for (Unloader unloader: unloaders) {
			if (unloader.isOpen() && unloader.canAccept()) {
				return unloader;
			}
		}
		return null;
	}

	public boolean hasUnloadingCapacityOpen() {
		return getFreeUnloader() != null;
	}

	public boolean hasEnqueuedVehicles() {
		return !unloaderQueue.isEmpty();
	}

	public void enqueueVehicle(MyMessage message) {
		unloaderQueue.addLast(message);
	}

	public void returnVehicleToQueue(MyMessage message) {
		unloaderQueue.addFirst(message);
	}

	public MyMessage dequeueVehicle() {
		return unloaderQueue.removeFirst();
	}

	public SimQueue<MyMessage> getQueue() {
		return unloaderQueue;
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new UnloaderManager(Id.unloaderManager, mySim(), this);
		new UnloaderProcess(Id.unloaderProcess, mySim(), this);
		new UnloaderOpenScheduler(Id.unloaderOpenScheduler, mySim(), this);
		addOwnMessage(Mc.init);
		addOwnMessage(Mc.unloadVehicle);
		addOwnMessage(Mc.requestMaterialConsumption);
	}

	public boolean canUnloadCargo() {
		return currentStorageCargo < storageCargoCapacity;
	}
	//meta! tag="end"
}