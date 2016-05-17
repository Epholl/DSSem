package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.UnloaderAgent;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;

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
	}

	//meta! sender="UnloaderOpenScheduler", id="73", type="Finish"
	public void processFinishUnloaderOpenScheduler(MessageForm message) {
	}

	//meta! sender="Unloader1Process", id="59", type="Finish"
	public void processFinishUnloader1Process(MessageForm message) {
	}

	//meta! sender="Unloader2Process", id="63", type="Finish"
	public void processFinishUnloader2Process(MessageForm message) {
	}

	//meta! sender="QuarryTransportationModelAgent", id="80", type="Notice"
	public void processInit(MessageForm message) {
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
			case Id.unloader1Process:
				processFinishUnloader1Process(message);
			break;

			case Id.unloaderOpenScheduler:
				processFinishUnloaderOpenScheduler(message);
			break;

			case Id.unloader2Process:
				processFinishUnloader2Process(message);
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