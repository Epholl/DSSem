package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.managers.UnloaderManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;

//meta! id="6"
public class UnloaderAgent extends Agent {
    public UnloaderAgent(int id, Simulation mySim, Agent parent) {
        super(id, mySim, parent);
        init();
    }

    @Override
    public void prepareReplication() {
        super.prepareReplication();
        // Setup component for the next replication
    }

    //meta! userInfo="Generated code: do not modify", tag="begin"
    private void init() {
        new UnloaderManager(Id.unloaderManager, mySim(), this);
        addOwnMessage(Mc.requestMaterialConsumption);
    }
    //meta! tag="end"
}