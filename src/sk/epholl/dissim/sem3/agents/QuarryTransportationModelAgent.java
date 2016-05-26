package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import OSPStat.Stat;
import sk.epholl.dissim.sem3.continualAssistants.MorningWakeupScheduler;
import sk.epholl.dissim.sem3.entities.Vehicle;
import sk.epholl.dissim.sem3.managers.QuarryTransportationModelManager;
import sk.epholl.dissim.sem3.simulation.*;
import sk.epholl.dissim.sem3.util.Log;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

//meta! id="3"
public class QuarryTransportationModelAgent extends Agent {

    private boolean loadersOpen;
    private boolean unloadersOpen;

    private LinkedList<MyMessage> nightParkedVehicles = new LinkedList<>();
    private List<Vehicle> vehicles = new ArrayList<>();

    private Stat cargoDeliveredA;
    private Stat cargoDeliveredB;
    private Stat cargoDeliveredC;

    private long countA;
    private long countB;
    private long countC;

    private double sumA;
    private double sumB;
    private double sumC;

    public QuarryTransportationModelAgent(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        Log.setSimulation(mySim());
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        nightParkedVehicles.clear();
        vehicles.clear();
        loadersOpen = true;
        unloadersOpen = true;

        cargoDeliveredA = new Stat();
        cargoDeliveredB = new Stat();
        cargoDeliveredC = new Stat();

        countA = 0;
        countB = 0;
        countC = 0;

        sumA = 0.0;
        sumB = 0.0;
        sumC = 0.0;

        SimulationParameters params = ((MySimulation)mySim()).getSimParams();

        MyMessage message = new MyMessage(mySim());
        message.setCode(Mc.init);
        message.setAddressee(Id.surroundingsAgent);
        manager().notice(message);

        message = new MyMessage(mySim());
        message.setCode(Mc.init);
        message.setAddressee(Id.loaderAgent);
        manager().notice(message);

        message = new MyMessage(mySim());
        message.setCode(Mc.init);
        message.setAddressee(Id.unloaderAgent);
        manager().notice(message);

        message = new MyMessage(mySim());
        message.setCode(Mc.init);
        message.setAddressee(Id.quarryTransportationModelAgent);
        manager().notice(message);

        for (SimulationParameters.Vehicle v: params.availableVehicles) {
            message = new MyMessage(mySim());
            Vehicle vehicle = new Vehicle(mySim(), v.capacity, v.speed, v.breakdownProbability, v.repairTime);
            vehicles.add(vehicle);
            message.setVehicle(vehicle);
            ((QuarryTransportationModelManager)manager()).processLoadVehicle(message);
        }
    }

    public boolean isLoadersOpen() {
        return loadersOpen;
    }

    public void setLoadersOpen(boolean loadersOpen) {
        this.loadersOpen = loadersOpen;
    }

    public boolean isUnloadersOpen() {
        return unloadersOpen;
    }

    public void setUnloadersOpen(boolean unloadersOpen) {
        this.unloadersOpen = unloadersOpen;
    }

    public Stat getCargoDeliveredA() {
        return cargoDeliveredA;
    }

    public Stat getCargoDeliveredB() {
        return cargoDeliveredB;
    }

    public Stat getCargoDeliveredC() {
        return cargoDeliveredC;
    }

    public List<Vehicle> getVehicles() {
        return vehicles;
    }

    public double getSumA() {
        return sumA;
    }

    public double getSumB() {
        return sumB;
    }

    public double getSumC() {
        return sumC;
    }

    public long getCountA() {
        return countA;
    }

    public long getCountB() {
        return countB;
    }

    public long getCountC() {
        return countC;
    }

    public void cargoDelivered(String from, double amount) {
        switch (from) {
            case "A":
                countA++;
                sumA += amount;
                cargoDeliveredA.addSample(amount);
                break;
            case "B":
                countB++;
                sumB+= amount;
                cargoDeliveredB.addSample(amount);
                break;
            case "C":
                countC++;
                sumC += amount;
                cargoDeliveredC.addSample(amount);
                break;
        }
    }

    public void parkForNight(MyMessage msg) {
        String parkedAt = ((MySimulation)mySim()).getSimParams().parkingArea == SimulationParameters.NightParking.LOADERS ?
                "Loader area" : "Unloader area";
        msg.getVehicle().setState(Vehicle.STATE_NIGHT + " , " + parkedAt );
        nightParkedVehicles.addLast(msg);
    }

    public LinkedList<MyMessage> getParkedVehicles() {
        return nightParkedVehicles;
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new QuarryTransportationModelManager(Id.quarryTransportationModelManager, mySim(), this);
        new MorningWakeupScheduler(Id.wakeupScheduler, mySim(), this);
		addOwnMessage(Mc.materialDelivered);
		addOwnMessage(Mc.loadVehicle);
		addOwnMessage(Mc.unloadVehicle);
		addOwnMessage(Mc.transferVehicle);
		addOwnMessage(Mc.requestMaterialConsumption);
        addOwnMessage(Mc.vehicleLoaded);
        addOwnMessage(Mc.vehicleTransferred);
        addOwnMessage(Mc.vehicleUnloaded);
        addOwnMessage(Mc.loadersStateChanged);
        addOwnMessage(Mc.unloadersStateChanged);
        addOwnMessage(Mc.morningWakeup);
        addOwnMessage(Mc.init);
	}
	//meta! tag="end"
}