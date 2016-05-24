package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.TransportationAgent;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

//meta! id="4"
public class TransportationManager extends Manager {
    public TransportationManager(int id, Simulation mySim, Agent myAgent) {
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

	//meta! sender="QuarryTransportationModelAgent", id="16", type="Request"
	public void processTransferVehicle(MessageForm message) {
        MyMessage msg = (MyMessage) message;
        msg.setAddressee(Id.transportationProcess);
        startContinualAssistant(message);
    }

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
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

            case Mc.finish:
                message.setCode(Mc.vehicleTransferred);
                response(message);
                break;

            default:
			    processDefault(message);
		        break;
		}
	}
	//meta! tag="end"

    @Override
    public TransportationAgent myAgent() {
        return (TransportationAgent) super.myAgent();
    }

}