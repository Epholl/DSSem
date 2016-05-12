package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Scheduler;
import OSPABA.Simulation;
import OSPRNG.ExponentialRNG;
import OSPRNG.TriangularRNG;
import sk.epholl.dissim.sem3.agents.SurroundingsAgent;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

//meta! id="21"
public class SupplierAScheduler extends Scheduler {

    private TriangularRNG amountGenerator = new TriangularRNG(4.5d, 15.0d, 16.5d);
    private ExponentialRNG timeBetweenArrivalsGenerator = new ExponentialRNG(49.5d, 1d);

    public SupplierAScheduler(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

	//meta! sender="SurroundingsAgent", id="22", type="Start"
	public void processStart(MessageForm message) {
        message.setCode(Mc.finish);
        ((MyMessage) message).setAmount(amountGenerator.sample());
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