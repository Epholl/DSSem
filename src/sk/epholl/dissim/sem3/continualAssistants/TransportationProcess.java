package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Process;
import OSPABA.Simulation;
import OSPExceptions.CantResendAScheduledMessageException;
import sk.epholl.dissim.sem3.agents.TransportationAgent;
import sk.epholl.dissim.sem3.entities.BumpyRoad;
import sk.epholl.dissim.sem3.entities.NarrowRoad;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;
import sk.epholl.dissim.sem3.util.Log;

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
        MyMessage msg = (MyMessage) messageForm;
        MyMessage dequeued;
        double time;
        try {
            switch (msg.code()) {
                case Mc.start:
                    msg.setCode(Mc.finish);
                    switch (msg.getTarget()) {
                        case "B":
                            NarrowRoad roadAb = ((TransportationAgent) myAgent()).getRoadAb();
                            time = roadAb.accept(msg);
                            hold(time, msg);
                            break;
                        case "C":
                            BumpyRoad roadBc = ((TransportationAgent) myAgent()).getRoadBc();
                            time = roadBc.accept(msg);
                            hold(time, msg);
                            break;
                        case "A":
                            NarrowRoad roadCa = ((TransportationAgent) myAgent()).getRoadCa();
                            time = roadCa.accept(msg);
                            hold(time, msg);
                            break;
                    }
                    break;
                case Mc.finish:
                    switch (msg.getTarget()) {
                        case "B":
                            NarrowRoad roadAb = ((TransportationAgent) myAgent()).getRoadAb();
                            dequeued = roadAb.dequeueTop();
                            dequeued.setCode(Mc.vehicleTransferred);
                            assistantFinished(dequeued);
                            break;
                        case "C":
                            msg.setCode(Mc.vehicleTransferred);
                            assistantFinished(msg);
                            break;
                        case "A":
                            NarrowRoad roadCa = ((TransportationAgent) myAgent()).getRoadCa();
                            dequeued = roadCa.dequeueTop();
                            dequeued.setCode(Mc.vehicleTransferred);
                            assistantFinished(dequeued);
                            break;
                    }
                    break;
            }
        } catch (CantResendAScheduledMessageException e) {
            Log.println("Something really wrong happened.");
        }
    }
}
