package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Scheduler;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.UnloaderAgent;
import sk.epholl.dissim.sem3.simulation.Mc;

//meta! id="72"
public class UnloaderOpenScheduler extends Scheduler {
	public UnloaderOpenScheduler(int id, Simulation mySim, CommonAgent myAgent) {
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication() {
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="UnloaderAgent", id="73", type="Start"
	public void processStart(MessageForm message) {
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
		switch (message.code()) {
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
	public UnloaderAgent myAgent() {
		return (UnloaderAgent)super.myAgent();
	}

}