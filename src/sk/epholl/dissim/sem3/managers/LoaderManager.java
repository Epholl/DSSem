package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.LoaderAgent;
import sk.epholl.dissim.sem3.entities.Loader;
import sk.epholl.dissim.sem3.simulation.Id;
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
		for (Loader loader: myAgent().loaders) {
			MyMessage msg = (MyMessage) message.createCopy();
			msg.setLoader(loader);
			msg.setCode(Mc.start);
			msg.setAddressee(Id.loaderOpenScheduler);
			startContinualAssistant(msg);
		}
    }

	//meta! sender="QuarryTransportationModelAgent", id="17", type="Request"
	public void processLoadVehicle(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		myAgent().enqueueVehicle(msg);
		tryLoadingNextVehicle();
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
		tryLoadingNextVehicle();
    }

	//meta! sender="LoaderProcess", id="53", type="Finish"
	public void processFinishLoaderProcess(MessageForm message) {
		message.setCode(Mc.vehicleLoaded);
		response(message);
        tryLoadingNextVehicle();
	}

	public void tryLoadingNextVehicle() {
		if (myAgent().hasEnqueuedVehicles() && myAgent().hasLoadingCapacityOpen() && myAgent().hasCargoToLoad()) {
			MyMessage msg = myAgent().dequeueVehicle();
			Loader loader = myAgent().getFreeLoader();
			loader.setLoadedVehicle(msg.getVehicle());
			msg.setLoader(loader);
			msg.setCode(Mc.start);
			msg.setAddressee(Id.loaderProcess);
			startContinualAssistant(msg);
		}
	}

	//meta! sender="LoaderOpenScheduler", id="68", type="Finish"
	public void processFinishLoaderOpenScheduler(MessageForm message) {
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	public void init() {
	}

	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
		case Mc.finish:
			switch (message.sender().id()) {
			case Id.loaderProcess:
				processFinishLoaderProcess(message);
			break;

			case Id.loaderOpenScheduler:
				processFinishLoaderOpenScheduler(message);
			break;
			}
		break;

		case Mc.materialDelivered:
			processMaterialDelivered(message);
		break;

		case Mc.init:
			processInit(message);
		break;

		case Mc.loadVehicle:
			processLoadVehicle(message);
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