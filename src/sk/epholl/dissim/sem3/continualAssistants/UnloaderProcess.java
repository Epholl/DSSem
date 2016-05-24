package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Process;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.UnloaderAgent;
import sk.epholl.dissim.sem3.entities.Unloader;
import sk.epholl.dissim.sem3.entities.Vehicle;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

//meta! id="58"
public class UnloaderProcess extends Process {
	public UnloaderProcess(int id, Simulation mySim, CommonAgent myAgent) {
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication() {
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="UnloaderAgent", id="59", type="Start"
	public void processStart(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		Unloader unloader = msg.getUnloader();
		unloader.startWork();
		msg.setCode(Mc.finish);
		hold(unloader.getLoadingDuration(), msg);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		switch (msg.code()) {
			case Mc.start:
				processStart(message);
				break;

			case Mc.finish:
				Vehicle vehicle = msg.getUnloader().finishVehicle();
				msg.setVehicle(vehicle);
				assistantFinished(msg);
				break;
		}
	}
	//meta! tag="end"

	@Override
	public UnloaderAgent myAgent() {
		return (UnloaderAgent)super.myAgent();
	}

}