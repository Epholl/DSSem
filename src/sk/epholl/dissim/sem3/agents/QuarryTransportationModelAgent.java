package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.continualAssistants.MorningWakeupScheduler;
import sk.epholl.dissim.sem3.entities.Vehicle;
import sk.epholl.dissim.sem3.managers.QuarryTransportationModelManager;
import sk.epholl.dissim.sem3.simulation.*;
import sk.epholl.dissim.sem3.util.Log;

import java.util.LinkedList;

//meta! id="3"
public class QuarryTransportationModelAgent extends Agent {

    private boolean loadersOpen;
    private boolean unloadersOpen;

    private LinkedList<MyMessage> nightParkedVehicles = new LinkedList<>();

    public QuarryTransportationModelAgent(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        Log.setSimulation(mySim());
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

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
            message.setVehicle(new Vehicle(mySim(), v.capacity, v.speed, v.breakdownProbability, v.repairTime));
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