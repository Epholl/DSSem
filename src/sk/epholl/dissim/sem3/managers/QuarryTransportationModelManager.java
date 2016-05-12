package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.QuarryTransportationModelAgent;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;

//meta! id="3"
public class QuarryTransportationModelManager extends Manager {
    public QuarryTransportationModelManager(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
    }

	//meta! sender="SurroundingsAgent", id="11", type="Notice"
	public void processMaterialDelivered(MessageForm message) {
		message.setAddressee(Id.loaderAgent);
		notice(message);
    }

	//meta! sender="LoaderAgent", id="17", type="Response"
	public void processLoadVehicle(MessageForm message) {
    }

	//meta! sender="TransportationAgent", id="16", type="Response"
	public void processTransferVehicle(MessageForm message) {
    }

	//meta! sender="UnloaderAgent", id="13", type="Response"
	public void processRequestMaterialConsumptionUnloaderAgent(MessageForm message) {
    }

	//meta! sender="SurroundingsAgent", id="12", type="Request"
	public void processRequestMaterialConsumptionSurroundingsAgent(MessageForm message) {
    }

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

	//meta! sender="UnloaderAgent", id="65", type="Response"
	public void processUnloadVehicle(MessageForm message) {
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init() {
	}

	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
		case Mc.transferVehicle:
			processTransferVehicle(message);
		break;

		case Mc.unloadVehicle:
			processUnloadVehicle(message);
		break;

		case Mc.materialDelivered:
			processMaterialDelivered(message);
		break;

		case Mc.loadVehicle:
			processLoadVehicle(message);
		break;

		case Mc.requestMaterialConsumption:
			switch (message.sender().id()) {
			case Id.unloaderAgent:
				processRequestMaterialConsumptionUnloaderAgent(message);
			break;

			case Id.surroundingsAgent:
				processRequestMaterialConsumptionSurroundingsAgent(message);
			break;
			}
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

    @Override
    public QuarryTransportationModelAgent myAgent() {
        return (QuarryTransportationModelAgent) super.myAgent();
    }

}