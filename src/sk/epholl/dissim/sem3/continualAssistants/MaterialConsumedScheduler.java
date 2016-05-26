package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Scheduler;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.SurroundingsAgent;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;
import sk.epholl.dissim.sem3.simulation.MySimulation;
import sk.epholl.dissim.sem3.util.Rand;
import sk.epholl.dissim.sem3.util.Utils;

import java.time.LocalTime;

//meta! id="32"
public class MaterialConsumedScheduler extends Scheduler {

	public static final LocalTime START_TIME = LocalTime.of(7, 0);
	public static final LocalTime END_TIME = LocalTime.of(22, 0);

	private Rand amountGenerator;
	private LocalTime nextConsumption;

    public MaterialConsumedScheduler(int id, Simulation mySim, CommonAgent myAgent) {
		super(id, mySim, myAgent);
		amountGenerator = ((MySimulation)mySim).getSimParams().consumerAmountRandom;
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
		nextConsumption = LocalTime.of(7, 0);
    }

	//meta! sender="SurroundingsAgent", id="33", type="Start"
	public void prepare(MessageForm message) {
		LocalTime now = ((MySimulation)mySim()).getSimDateTime().toLocalTime();
		if (now.isBefore(START_TIME) || now.isAfter(END_TIME)) {
			nextConsumption = START_TIME;
		} else {
			while (nextConsumption.isBefore(now)) {
				if (nextConsumption.isAfter(END_TIME)) {
					nextConsumption = START_TIME;
					break;
				}
				nextConsumption = nextConsumption.plusMinutes(30);
			}
		}
		message.setCode(Mc.finish);
		hold(Utils.secondsUntilNextTime(now, nextConsumption), message);
    }

	public void prepareNext(MessageForm message) {
		LocalTime now = ((MySimulation)mySim()).getSimDateTime().toLocalTime();
		nextConsumption = nextConsumption.plusMinutes(30);
		if (nextConsumption.isBefore(START_TIME) || nextConsumption.isAfter(END_TIME)) {
			nextConsumption = START_TIME;
		}

		message.setCode(Mc.finish);
		hold(Utils.secondsUntilNextTime(now, nextConsumption), message);
	}

	//meta! userInfo="Generated code: do not modify", tag="begin"
	@Override
	public void processMessage(MessageForm message) {
		switch (message.code()) {
		case Mc.start:
			prepare(message);
		break;

		default:
			MyMessage copy = (MyMessage) message.createCopy();
			copy.setAmount(amountGenerator.sample());
			assistantFinished(copy);
			prepareNext(message);
		break;
		}
	}
	//meta! tag="end"

    @Override
    public SurroundingsAgent myAgent() {
        return (SurroundingsAgent) super.myAgent();
    }

}