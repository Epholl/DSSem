package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.managers.QuarryTransportationModelManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;
import sk.epholl.dissim.sem3.simulation.SimulationParameters;

//meta! id="3"
public class QuarryTransportationModelAgent extends Agent {
    public QuarryTransportationModelAgent(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        MyMessage message = new MyMessage(mySim());
        message.setCode(Mc.init);
        message.setAddressee(Id.surroundingsAgent);
        manager().notice(message);

        SimulationParameters params = SimulationParameters.getDefaultParameters(mySim());
        message = new MyMessage(mySim());
        message.setCode(Mc.init);
        message.setAddressee(Id.loaderAgent);
        message.setAllVehicles(params.availableVehicles);
        manager().notice(message);

        message = new MyMessage(mySim());
        message.setCode(Mc.init);
        message.setAddressee(Id.unloaderAgent);
        manager().notice(message);
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	private void init() {
		new QuarryTransportationModelManager(Id.quarryTransportationModelManager, mySim(), this);
		addOwnMessage(Mc.materialDelivered);
		addOwnMessage(Mc.loadVehicle);
		addOwnMessage(Mc.unloadVehicle);
		addOwnMessage(Mc.transferVehicle);
		addOwnMessage(Mc.requestMaterialConsumption);
	}
	//meta! tag="end"
}