package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Process;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.SurroundingsAgent;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

//meta! id="42"
public class SupplierAActiveProcess extends Process {
    public SupplierAActiveProcess(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

	//meta! sender="SurroundingsAgent", id="43", type="Start"
	public void processStart(MessageForm message) {
        message.setCode(Mc.supplierEnded);
        double duration = ((MyMessage) message).getAmount();
        hold(duration, message);
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
    public SurroundingsAgent myAgent() {
        return (SurroundingsAgent) super.myAgent();
    }

}