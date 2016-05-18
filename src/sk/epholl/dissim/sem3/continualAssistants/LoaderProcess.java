package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Process;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.LoaderAgent;
import sk.epholl.dissim.sem3.entities.Loader;
import sk.epholl.dissim.sem3.entities.Vehicle;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

//meta! id="52"
public class LoaderProcess extends Process {
	public LoaderProcess(int id, Simulation mySim, CommonAgent myAgent) {
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication() {
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="LoaderAgent", id="53", type="Start"
	public void processStart(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		Loader loader = msg.getLoader();
		loader.startWork();
		msg.setCode(Mc.finish);
		hold(loader.getLoadingDuration(), msg);
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
		switch (message.code()) {
			case Mc.finish:
				Vehicle vehicle = myAgent().getLoader(0).finishVehicle();
				((MyMessage)message).setVehicle(vehicle);
				assistantFinished(message);
				break;
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
		case Mc.start:
			processStart(message);
		break;

		default:
			processDefault(message);
		break;
		}
	}
	//meta! tag="end"

	@Override
	public LoaderAgent myAgent() {
		return (LoaderAgent)super.myAgent();
	}

}