package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.QuarryTransportationModelAgent;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;
import sk.epholl.dissim.sem3.util.Log;

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
		//Log.println("material delivered " + ((MyMessage)message).getFrom() + ": " + ((MyMessage)message).getAmount());
		message.setAddressee(Id.loaderAgent);
		notice(message);
    }

	//meta! sender="LoaderAgent", id="17", type="Response"
	public void processLoadVehicle(MessageForm message) {
        MyMessage msg = (MyMessage) message;
        msg.setCode(Mc.loadVehicle);
        msg.setAddressee(Id.loaderAgent);
        request(message);
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
		MyMessage msg = (MyMessage) message;
        switch (msg.code()) {
			case Mc.vehicleLoaded:
				//Log.println("vehicle loaded: " + msg.getVehicle().toString());
				msg.setCode(Mc.transferVehicle);
				msg.setAddressee(Id.transportationAgent);
				msg.setTarget("B");
				request(msg);
				break;
			case Mc.vehicleUnloaded:
				//Log.println("Vehicle unloaded! " + msg.getVehicle().toString());
				msg.setCode(Mc.transferVehicle);
				msg.setAddressee(Id.transportationAgent);
				msg.setTarget("C");
				request(msg);
				break;
			case Mc.vehicleTransferred:
                //Log.println("vehicle transferred to " + msg.getTarget() + ", " + msg.getVehicle().toString());
                switch (msg.getTarget()) {
                    case "A":
                        processLoadVehicle(message);
                        break;
                    case "B":
                        msg.setCode(Mc.unloadVehicle);
                        msg.setAddressee(Id.unloaderAgent);
                        request(msg);
                        break;
                    case "C":
                        msg.setCode(Mc.transferVehicle);
                        msg.setAddressee(Id.transportationAgent);
                        msg.setTarget("A");
                        request(msg);
                        break;
                }
				break;
			case Mc.loadersStateChanged:
				Log.println("Loaders status has changed, open: " + msg.areLoadersOpen());
				break;
			case Mc.unloadersStateChanged:
				Log.println("Unloaders status has changed, open: " + msg.areLoadersOpen());
				break;
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
			case Mc.requestMaterialConsumption:
				switch (message.sender().id()) {
				case Id.surroundingsAgent:
					processRequestMaterialConsumptionSurroundingsAgent(message);
				break;

				case Id.unloaderAgent:
					processRequestMaterialConsumptionUnloaderAgent(message);
				break;
				}
			break;

			case Mc.loadVehicle:
				processLoadVehicle(message);
			break;

			case Mc.materialDelivered:
				processMaterialDelivered(message);
			break;

			case Mc.transferVehicle:
				processTransferVehicle(message);
			break;

			case Mc.unloadVehicle:
				processUnloadVehicle(message);
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