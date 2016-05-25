package sk.epholl.dissim.sem3.continualAssistants;

import OSPABA.CommonAgent;
import OSPABA.MessageForm;
import OSPABA.Scheduler;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.simulation.Mc;
import sk.epholl.dissim.sem3.simulation.MyMessage;
import sk.epholl.dissim.sem3.simulation.MySimulation;
import sk.epholl.dissim.sem3.util.Utils;

import java.time.LocalTime;

/**
 * Created by Tomáš on 25.05.2016.
 */
public class MorningWakeupScheduler extends Scheduler {

    private static final double SECONDS_IN_DAY = 60 * 60 * 24;

    public MorningWakeupScheduler(int id, Simulation mySim, CommonAgent myAgent) {
        super(id, mySim, myAgent);
    }

    @Override
    public void processMessage(MessageForm message) {
        MyMessage msg = (MyMessage) message;

        switch (msg.code()) {
            case Mc.start:
                MySimulation sim = (MySimulation) mySim();
                LocalTime nextTime = sim.getSimParams().wakeupTime;
                LocalTime now = sim.getSimDateTime().toLocalTime();
                double secondsTillNextOccurence = nextTime.equals(now) ? SECONDS_IN_DAY : Utils.secondsUntilNextTime(now, nextTime);
                msg.setCode(Mc.finish);
                hold(secondsTillNextOccurence, msg);

                break;
            case Mc.finish:
                MyMessage copy = (MyMessage) msg.createCopy();
                hold(SECONDS_IN_DAY, msg);
                assistantFinished(copy);
                break;
        }
    }
}
