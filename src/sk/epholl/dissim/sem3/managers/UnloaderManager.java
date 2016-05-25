package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.UnloaderAgent;
import sk.epholl.dissim.sem3.entities.Unloader;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

//meta! id="6"
public class UnloaderManager extends Manager {
    public UnloaderManager(int id, Simulation mySim, Agent myAgent) {
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

	//meta! sender="QuarryTransportationModelAgent", id="13", type="Request"
	public void processRequestMaterialConsumption(MessageForm message) {
    }

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

	//meta! sender="QuarryTransportationModelAgent", id="65", type="Request"
	public void processUnloadVehicle(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		myAgent().enqueueVehicle(msg);
		tryUnloadingNextVehicle();
	}

	//meta! sender="UnloaderOpenScheduler", id="73", type="Finish"
	public void processFinishUnloaderOpenScheduler(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		boolean unloadersOpen = myAgent().getOpenUnloadersCount() > 0;
		if (unloadersOpen) {
			tryUnloadingNextVehicle();
		}

		msg.setCode(Mc.unloadersStateChanged);
		msg.setUnloadersOpen(unloadersOpen);
		msg.setAddressee(Id.quarryTransportationModelAgent);
		notice(msg);
	}

	//meta! sender="UnloaderProcess", id="59", type="Finish"
	public void processFinishUnloaderProcess(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		if (msg.getVehicle().getCurrentLoad() > 0) {
			msg.setCode(Mc.unloadVehicle);
			myAgent().returnVehicleToQueue(msg);
		} else {
			message.setCode(Mc.vehicleUnloaded);
			response(message);
		}
		tryUnloadingNextVehicle();
	}

	public void tryUnloadingNextVehicle() {
		if (myAgent().hasEnqueuedVehicles() && myAgent().hasUnloadingCapacityOpen() && myAgent().canUnloadCargo()) {
			MyMessage msg = myAgent().dequeueVehicle();
			Unloader unloader = myAgent().getFreeUnloader();
			unloader.setUnloadedVehicle(msg.getVehicle());
			msg.setUnloader(unloader);
			msg.setCode(Mc.start);
			msg.setAddressee(Id.unloaderProcess);
			startContinualAssistant(msg);
		}
	}

	//meta! sender="QuarryTransportationModelAgent", id="80", type="Notice"
	public void processInit(MessageForm message) {
		for (Unloader unloader: myAgent().unloaders) {
			MyMessage msg = (MyMessage) message.createCopy();
			msg.setUnloader(unloader);
			msg.setCode(Mc.start);
			msg.setAddressee(Id.unloaderOpenScheduler);
			startContinualAssistant(msg);
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init() {
	}

	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
		case Mc.init:
			processInit(message);
		break;

		case Mc.finish:
			switch (message.sender().id()) {
			case Id.unloaderProcess:
				processFinishUnloaderProcess(message);
			break;

			case Id.unloaderOpenScheduler:
				processFinishUnloaderOpenScheduler(message);
			break;
			}
		break;

		case Mc.requestMaterialConsumption:
			processRequestMaterialConsumption(message);
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
    public UnloaderAgent myAgent() {
        return (UnloaderAgent) super.myAgent();
    }

}