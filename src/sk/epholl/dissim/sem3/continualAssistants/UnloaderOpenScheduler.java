package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Scheduler;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.UnloaderAgent;
import sk.epholl.dissim.sem3.entities.Unloader;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;
import sk.epholl.dissim.sem3.simulation.MySimulation;

import java.time.LocalTime;

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
		MyMessage msg = (MyMessage) message;
		Unloader unloader = msg.getUnloader();
		if (unloader.isOpen()) {
			LocalTime closeTime = unloader.getClosingHours();
			double timeUntil = ((MySimulation)mySim()).durationTillTime(closeTime);
			msg.setCode(Mc.unloaderClose);
			hold(timeUntil, msg);
		} else {
			LocalTime openTime = unloader.getOpeningHours();
			double timeUntil = ((MySimulation)mySim()).durationTillTime(openTime);
			msg.setCode(Mc.unloaderOpen);
			hold(timeUntil, msg);
		}
	}

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
		switch (message.code()) {
		}
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		Unloader unloader = msg.getUnloader();
		MyMessage copy;
		double timeUntil;

		switch (message.code()) {
			case Mc.start:
				processStart(message);
				break;
			case Mc.unloaderClose:
				copy = (MyMessage) msg.createCopy();
				copy.setCode(Mc.unloaderOpen);
				timeUntil = ((MySimulation)mySim()).durationTillTime(unloader.getOpeningHours());
				hold(timeUntil, copy);
				assistantFinished(msg);
				break;
			case Mc.unloaderOpen:
				copy = (MyMessage) msg.createCopy();
				copy.setCode(Mc.unloaderClose);
				timeUntil = ((MySimulation)mySim()).durationTillTime(unloader.getClosingHours());
				hold(timeUntil, copy);
				assistantFinished(msg);
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