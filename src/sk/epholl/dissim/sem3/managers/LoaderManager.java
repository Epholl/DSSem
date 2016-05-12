package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.LoaderAgent;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

//meta! id="5"
public class LoaderManager extends Manager {
    public LoaderManager(int id, Simulation mySim, Agent myAgent) {
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

	//meta! sender="QuarryTransportationModelAgent", id="15", type="Notice"
	public void processInit(MessageForm message) {
    }

	//meta! sender="QuarryTransportationModelAgent", id="17", type="Request"
	public void processLoadVehicle(MessageForm message) {
    }

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

	//meta! sender="QuarryTransportationModelAgent", id="30", type="Notice"
	public void processMaterialDelivered(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		myAgent().addToCargo(msg.getAmount());
    }

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init() {
	}

	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
		case Mc.materialDelivered:
			processMaterialDelivered(message);
		break;

		case Mc.loadVehicle:
			processLoadVehicle(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

    @Override
    public LoaderAgent myAgent() {
        return (LoaderAgent) super.myAgent();
    }

}