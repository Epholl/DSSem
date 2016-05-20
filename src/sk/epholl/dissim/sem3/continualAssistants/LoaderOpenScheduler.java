package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Scheduler;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.LoaderAgent;
import sk.epholl.dissim.sem3.entities.Loader;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;
import sk.epholl.dissim.sem3.simulation.MySimulation;

import java.time.LocalTime;

//meta! id="67"
public class LoaderOpenScheduler extends Scheduler {
	public LoaderOpenScheduler(int id, Simulation mySim, CommonAgent myAgent) {
		super(id, mySim, myAgent);
	}

	@Override
	public void prepareReplication() {
		super.prepareReplication();
		// Setup component for the next replication
	}

	//meta! sender="LoaderAgent", id="68", type="Start"
	public void processStart(MessageForm message) {
		MyMessage msg = (MyMessage) message;
		Loader loader = msg.getLoader();
		if (loader.isOpen()) {
			LocalTime closeTime = loader.getClosingHours();
			double timeUntil = ((MySimulation)mySim()).durationTillTime(closeTime);
			msg.setCode(Mc.loaderClose);
			hold(timeUntil, msg);
		} else {
			LocalTime openTime = loader.getOpeningHours();
			double timeUntil = ((MySimulation)mySim()).durationTillTime(openTime);
			msg.setCode(Mc.loaderOpen);
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
		Loader loader = msg.getLoader();
		MyMessage copy;
		double timeUntil;

		switch (message.code()) {
			case Mc.start:
				processStart(message);
			break;
			case Mc.loaderClose:
				copy = (MyMessage) msg.createCopy();
				copy.setCode(Mc.loaderOpen);
				timeUntil = ((MySimulation)mySim()).durationTillTime(loader.getOpeningHours());
				hold(timeUntil, copy);
				assistantFinished(message);
			break;
			case Mc.loaderOpen:
				copy = (MyMessage) msg.createCopy();
				copy.setCode(Mc.loaderClose);
				timeUntil = ((MySimulation)mySim()).durationTillTime(loader.getClosingHours());
				hold(timeUntil, copy);
				assistantFinished(message);
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