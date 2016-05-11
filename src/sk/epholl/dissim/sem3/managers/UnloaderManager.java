package sk.epholl.dissim.sem3.managers;

import OSPABA.Agent;
import OSPABA.Manager;
import OSPABA.MessageForm;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.agents.UnloaderAgent;
import sk.epholl.dissim.sem3.simulation.Mc;

//meta! id="6"
public class UnloaderManager extends Manager {
    public UnloaderManager(int id, Simulation mySim, Agent myAgent) {
        super(id, mySim, myAgent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication

        if (petriNet() != null) {
            petriNet().clear();
        }
    }

    //meta! sender="QuarryTransportationModelAgent", id="13", type="Request"
    public void processRequestMaterialConsumption(MessageForm message) {
    }

    //meta! userInfo="Process messages defined in code", id="0"
    public void processDefault(MessageForm message) {
        switch (message.code()) {
        }
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    public void init() {
    }

    @Override
    public void processMessage(MessageForm message) {
        switch (message.code()) {
            case Mc.requestMaterialConsumption:
                processRequestMaterialConsumption(message);
                break;

            default:
                processDefault(message);
                break;
        }
    }
    //meta! tag="end"

    @Override
    public UnloaderAgent myAgent() {
        return (UnloaderAgent) super.myAgent();
    }

}