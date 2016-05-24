package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.entities.Vehicle;
import sk.epholl.dissim.sem3.managers.QuarryTransportationModelManager;
import sk.epholl.dissim.sem3.simulation.*;
import sk.epholl.dissim.sem3.util.Log;

//meta! id="3"
public class QuarryTransportationModelAgent extends Agent {
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

        for (SimulationParameters.Vehicle v: params.availableVehicles) {
            message = new MyMessage(mySim());
            message.setVehicle(new Vehicle(mySim(), v.capacity, v.speed, v.breakdownProbability, v.repairTime));
            ((QuarryTransportationModelManager)manager()).processLoadVehicle(message);
        }
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new QuarryTransportationModelManager(Id.quarryTransportationModelManager, mySim(), this);
		addOwnMessage(Mc.materialDelivered);
		addOwnMessage(Mc.loadVehicle);
		addOwnMessage(Mc.unloadVehicle);
		addOwnMessage(Mc.transferVehicle);
		addOwnMessage(Mc.requestMaterialConsumption);
        addOwnMessage(Mc.vehicleLoaded);
        addOwnMessage(Mc.vehicleTransferred);
        addOwnMessage(Mc.vehicleUnloaded);
	}
	//meta! tag="end"
}