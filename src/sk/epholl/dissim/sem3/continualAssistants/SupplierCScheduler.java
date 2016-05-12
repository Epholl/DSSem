package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Scheduler;
import OSPABA.Simulation;
import OSPRNG.*;
import sk.epholl.dissim.sem3.agents.SurroundingsAgent;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

//meta! id="25"
public class SupplierCScheduler extends Scheduler {

    private EmpiricRNG amountGenerator = new EmpiricRNG(
            new EmpiricPair(new UniformContinuousRNG(5D, 10D), 0.03D),
            new EmpiricPair(new UniformContinuousRNG(10D, 20D), 0.2D),
            new EmpiricPair(new UniformContinuousRNG(20D, 25D), 0.77D)
    );
    private ExponentialRNG timeBetweenArrivalsGenerator = new ExponentialRNG(25.8D, 1d);

    public SupplierCScheduler(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

	//meta! sender="SurroundingsAgent", id="26", type="Start"
	public void processStart(MessageForm message) {
        message.setCode(Mc.finish);
        ((MyMessage) message).setAmount((double)amountGenerator.sample());
        hold(timeBetweenArrivalsGenerator.sample()*60, message);
    }

	//meta! userInfo="Process messages defined in code", id="0"
	public void processDefault(MessageForm message) {
        switch (message.code()) {
            case Mc.finish:
                MessageForm copy = message.createCopy();
                processStart(copy);

                assistantFinished(message);
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
    public SurroundingsAgent myAgent() {
        return (SurroundingsAgent) super.myAgent();
    }

}