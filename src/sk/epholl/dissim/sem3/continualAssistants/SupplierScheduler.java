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

import java.time.LocalDateTime;

/**
 * Created by Tomáš on 22.05.2016.
 */
public class SupplierScheduler extends Scheduler {

    private Rand amountGenerator;
    private Rand timeBetweenArrivalsGenerator;

    private LocalDateTime supplierEndingDate;
    private String name;

    public SupplierScheduler(int id, Simulation mySim, CommonAgent myAgent, Rand amountGen, Rand timeGen, String name) {
        super(id, mySim, myAgent);
        this.amountGenerator = amountGen;
        this.timeBetweenArrivalsGenerator = timeGen;
        this.name = name;
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }
    //meta! sender="SurroundingsAgent", id="22", type="Start"
    public void checkAndScheduleNext(MessageForm message) {
        if (supplierEndingDate != null && ((MySimulation)mySim()).getSimDateTime().isAfter(supplierEndingDate)) {
            return;
        }
        message.setCode(Mc.finish);
        ((MyMessage) message).setFrom(name);
        ((MyMessage) message).setAmount(amountGenerator.sample());
        hold(timeBetweenArrivalsGenerator.sample()*60, message);
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.start:
                checkAndScheduleNext(message);
                break;

            case Mc.finish:
                MessageForm copy = message.createCopy();
                checkAndScheduleNext(copy);

                assistantFinished(message);
        }
    }
    //meta! tag="end"

    @Override
    public SurroundingsAgent myAgent() {
        return (SurroundingsAgent) super.myAgent();
    }

}
