package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.LoaderAgent;
import sk.epholl.dissim.sem3.entities.Loader;
import sk.epholl.dissim.sem3.entities.Vehicle;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

//meta! id="5"
public class LoaderManager extends Manager {

	private List<Loader> loaders;

    public LoaderManager(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
		loaders = new ArrayList<>();
		loaders.add(new Loader(mySim, 180, LocalTime.of(7, 0), LocalTime.of(18, 0), myAgent()));
		loaders.add(new Loader(mySim, 250, LocalTime.of(9, 0), LocalTime.of(22, 0), myAgent()));
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

	public boolean hasAFreeLoader() {
		for (Loader loader : loaders) {
			if (loader.canAccept()) {
				return true;
			}
		}
		return false;
	}

	public boolean hasAnOpenLoader() {
		for (Loader loader : loaders) {
			if (loader.isOpen()) {
				return true;
			}
		}
		return false;
	}

	//meta! sender="QuarryTransportationModelAgent", id="15", type="Notice"
	public void processInit(MessageForm message) {
        List<Vehicle> vehicles = ((MyMessage) message).getAllVehicles();
        for (Vehicle vehicle: vehicles) {
            MyMessage msg = new MyMessage(mySim());
            msg.setVehicle(vehicle);
            msg.setCode(Mc.loadVehicle);
            processLoadVehicle(msg);
        }
    }

	//meta! sender="QuarryTransportationModelAgent", id="17", type="Request"
	public void processLoadVehicle(MessageForm message) {
        if (myAgent().)
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

	//meta! sender="Loader2Process", id="55", type="Finish"
	public void processFinishLoader2Process(MessageForm message) {
	}

	//meta! sender="Loader1Process", id="53", type="Finish"
	public void processFinishLoader1Process(MessageForm message) {
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
		case Mc.materialDelivered:
			processMaterialDelivered(message);
		break;

		case Mc.finish:
			switch (message.sender().id()) {
			case Id.loader2Process:
				processFinishLoader2Process(message);
			break;

			case Id.loader1Process:
				processFinishLoader1Process(message);
			break;

			case Id.loaderOpenScheduler:
				processFinishLoaderOpenScheduler(message);
			break;
			}
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