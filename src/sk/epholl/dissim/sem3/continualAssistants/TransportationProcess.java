package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Process;
import OSPABA.Simulation;

/**
 * Created by Tomáš on 22.05.2016.
 */
public class TransportationProcess extends Process {

    public TransportationProcess(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    @Override
    public void processMessage(MessageForm messageForm) {

    }
}
