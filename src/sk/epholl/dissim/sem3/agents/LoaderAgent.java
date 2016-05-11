package sk.epholl.dissim.sem3.agents;

import OSPABA.Agent;
import OSPABA.Simulation;
import sk.epholl.dissim.sem3.managers.LoaderManager;
import sk.epholl.dissim.sem3.simulation.Id;
import sk.epholl.dissim.sem3.simulation.Mc;

//meta! id="5"
public class LoaderAgent extends Agent {
    public LoaderAgent(int id, Simulation mySim, Agent parent) {
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
        new LoaderManager(Id.loaderManager, mySim(), this);
        addOwnMessage(Mc.init);
        addOwnMessage(Mc.loadVehicle);
    }
    //meta! tag="end"
}