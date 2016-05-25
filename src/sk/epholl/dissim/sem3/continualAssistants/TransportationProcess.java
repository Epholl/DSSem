package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Process;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.TransportationAgent;
import sk.epholl.dissim.sem3.entities.BumpyRoad;
import sk.epholl.dissim.sem3.entities.NarrowRoad;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;

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
                        roadAb.removeFromQueue(msg);
                        msg.setCode(Mc.vehicleTransferred);
                        msg.setVehicle(roadAb.dequeueTopVehicle());
                        assistantFinished(msg);
                        break;
                    case "C":
                        msg.setCode(Mc.vehicleTransferred);
                        assistantFinished(msg);
                        break;
                    case "A":
                        NarrowRoad roadCa = ((TransportationAgent) myAgent()).getRoadCa();
                        roadCa.removeFromQueue(msg);
                        msg.setCode(Mc.vehicleTransferred);
                        msg.setVehicle(roadCa.dequeueTopVehicle());
                        assistantFinished(msg);
                        break;
                }
                break;
        }
    }
}
